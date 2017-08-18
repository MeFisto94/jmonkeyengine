package com.jme3.scene.plugins.gltf;

import com.google.gson.*;
import com.jme3.asset.AssetLoadException;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.Texture;
import com.jme3.util.*;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nehon on 07/08/2017.
 */
public class GltfUtils {

    private static final Logger logger = Logger.getLogger(GltfUtils.class.getName());

    public static Mesh.Mode getMeshMode(Integer mode) {
        if (mode == null) {
            return Mesh.Mode.Triangles;
        }
        //too bad, we could have returned the enum value from the ordinal
        //but LineLoop and LineStrip are inverted in the Mesh.Mode Enum declaration.
        switch (mode) {
            case 0:
                return Mesh.Mode.Points;
            case 1:
                return Mesh.Mode.Lines;
            case 2:
                return Mesh.Mode.LineLoop;
            case 3:
                return Mesh.Mode.LineStrip;
            case 4:
                return Mesh.Mode.Triangles;
            case 5:
                return Mesh.Mode.TriangleStrip;
            case 6:
                return Mesh.Mode.TriangleFan;
        }
        return Mesh.Mode.Triangles;
    }

    public static VertexBuffer.Format getVertexBufferFormat(int componentType) {
        switch (componentType) {
            case 5120:
                return VertexBuffer.Format.Byte;
            case 5121:
                return VertexBuffer.Format.UnsignedByte;
            case 5122:
                return VertexBuffer.Format.Short;
            case 5123:
                return VertexBuffer.Format.UnsignedShort;
            case 5125:
                return VertexBuffer.Format.UnsignedInt;
            case 5126:
                return VertexBuffer.Format.Float;
            default:
                throw new AssetLoadException("Illegal component type: " + componentType);
        }
    }

    public static int getNumberOfComponents(String type) {
        switch (type) {
            case "SCALAR":
                return 1;
            case "VEC2":
                return 2;
            case "VEC3":
                return 3;
            case "VEC4":
                return 4;
            case "MAT2":
                return 4;
            case "MAT3":
                return 9;
            case "MAT4":
                return 16;
            default:
                throw new AssetLoadException("Illegal type: " + type);
        }
    }

    public static VertexBuffer.Type getVertexBufferType(String attribute) {
        switch (attribute) {
            case "POSITION":
                return VertexBuffer.Type.Position;
            case "NORMAL":
                return VertexBuffer.Type.Normal;
            case "TANGENT":
                return VertexBuffer.Type.Tangent;
            case "TEXCOORD_0":
                return VertexBuffer.Type.TexCoord;
            case "TEXCOORD_1":
                return VertexBuffer.Type.TexCoord2;
            case "TEXCOORD_2":
                return VertexBuffer.Type.TexCoord3;
            case "TEXCOORD_3":
                return VertexBuffer.Type.TexCoord4;
            case "TEXCOORD_4":
                return VertexBuffer.Type.TexCoord5;
            case "TEXCOORD_5":
                return VertexBuffer.Type.TexCoord6;
            case "TEXCOORD_6":
                return VertexBuffer.Type.TexCoord7;
            case "TEXCOORD_7":
                return VertexBuffer.Type.TexCoord8;
            case "COLOR_0":
                return VertexBuffer.Type.Color;
            case "JOINTS_0":
                return VertexBuffer.Type.BoneIndex;
            case "WEIGHTS_0":
                return VertexBuffer.Type.BoneWeight;
            default:
                logger.log(Level.WARNING, "Unsupported Vertex Buffer type " + attribute);
                return null;

        }
    }

    public static int getIndex(String name) {
        String num = name.substring(name.lastIndexOf("_") + 1);
        return Integer.parseInt(num);
    }

    public static Texture.MagFilter getMagFilter(Integer value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case 9728:
                return Texture.MagFilter.Nearest;
            case 9729:
                return Texture.MagFilter.Bilinear;
        }
        return null;
    }

    public static Texture.MinFilter getMinFilter(Integer value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case 9728:
                return Texture.MinFilter.NearestNoMipMaps;
            case 9729:
                return Texture.MinFilter.BilinearNoMipMaps;
            case 9984:
                return Texture.MinFilter.NearestNearestMipMap;
            case 9985:
                return Texture.MinFilter.BilinearNearestMipMap;
            case 9986:
                return Texture.MinFilter.NearestLinearMipMap;
            case 9987:
                return Texture.MinFilter.Trilinear;

        }
        return null;
    }

    public static Texture.WrapMode getWrapMode(Integer value) {
        if (value == null) {
            return Texture.WrapMode.Repeat;
        }
        switch (value) {
            case 33071:
                return Texture.WrapMode.EdgeClamp;
            case 33648:
                return Texture.WrapMode.MirroredRepeat;
            default:
                return Texture.WrapMode.Repeat;
        }
    }

    public static void padBuffer(Object store, int bufferSize) {
        if (store instanceof Buffer) {
            Buffer buffer = (Buffer) store;
            buffer.clear();
            if (buffer instanceof IntBuffer) {
                IntBuffer ib = (IntBuffer) buffer;
                for (int i = 0; i < bufferSize; i++) {
                    ib.put(0);
                }
            } else if (buffer instanceof FloatBuffer) {
                FloatBuffer fb = (FloatBuffer) buffer;
                for (int i = 0; i < bufferSize; i++) {
                    fb.put(0);
                }
            } else if (buffer instanceof ShortBuffer) {
                ShortBuffer sb = (ShortBuffer) buffer;
                for (int i = 0; i < bufferSize; i++) {
                    sb.put((short) 0);
                }
            } else if (buffer instanceof ByteBuffer) {
                ByteBuffer bb = (ByteBuffer) buffer;
                for (int i = 0; i < bufferSize; i++) {
                    bb.put((byte) 0);
                }
            }
            buffer.rewind();
        }
        if (store instanceof short[]) {
            short[] array = (short[]) store;
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
        } else if (store instanceof float[]) {
            float[] array = (float[]) store;
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
        } else if (store instanceof Vector3f[]) {
            Vector3f[] array = (Vector3f[]) store;
            for (int i = 0; i < array.length; i++) {
                array[i] = new Vector3f();
            }
        } else if (store instanceof Quaternion[]) {
            Quaternion[] array = (Quaternion[]) store;
            for (int i = 0; i < array.length; i++) {
                array[i] = new Quaternion();
            }
        } else if (store instanceof Matrix4f[]) {
            Matrix4f[] array = (Matrix4f[]) store;
            for (int i = 0; i < array.length; i++) {
                array[i] = new Matrix4f();
            }
        }
    }

    public static void populateBuffer(Object store, byte[] source, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {

        if (store instanceof Buffer) {
            Buffer buffer = (Buffer) store;
            buffer.clear();
            if (buffer instanceof ByteBuffer) {
                populateByteBuffer((ByteBuffer) buffer, source, length, byteOffset, byteStride, numComponents, componentSize);
                return;
            }
            LittleEndien stream = getStream(source);
            if (buffer instanceof ShortBuffer) {
                populateShortBuffer((ShortBuffer) buffer, stream, length, byteOffset, byteStride, numComponents, componentSize);
            } else if (buffer instanceof IntBuffer) {
                populateIntBuffer((IntBuffer) buffer, stream, length, byteOffset, byteStride, numComponents, componentSize);
            } else if (buffer instanceof FloatBuffer) {
                populateFloatBuffer((FloatBuffer) buffer, stream, length, byteOffset, byteStride, numComponents, componentSize);
            }
            buffer.rewind();
            return;
        }
        LittleEndien stream = getStream(source);
        if (store instanceof short[]) {
            populateShortArray((short[]) store, stream, length, byteOffset, byteStride, numComponents, componentSize);
        } else
        if (store instanceof float[]) {
            populateFloatArray((float[]) store, stream, length, byteOffset, byteStride, numComponents, componentSize);
        } else if (store instanceof Vector3f[]) {
            populateVector3fArray((Vector3f[]) store, stream, length, byteOffset, byteStride, numComponents, componentSize);
        } else if (store instanceof Quaternion[]) {
            populateQuaternionArray((Quaternion[]) store, stream, length, byteOffset, byteStride, numComponents, componentSize);
        } else if (store instanceof Matrix4f[]) {
            populateMatrix4fArray((Matrix4f[]) store, stream, length, byteOffset, byteStride, numComponents, componentSize);
        }
    }

    private static void populateByteBuffer(ByteBuffer buffer, byte[] source, int length, int byteOffset, int byteStride, int numComponents, int componentSize) {
        int index = byteOffset;
        while (index < length + byteOffset) {
            for (int i = 0; i < numComponents; i++) {
                buffer.put(source[index + i]);
            }
            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateShortBuffer(ShortBuffer buffer, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        while (index < end) {
            for (int i = 0; i < numComponents; i++) {
                buffer.put(stream.readShort());
            }
            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateIntBuffer(IntBuffer buffer, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        while (index < end) {
            for (int i = 0; i < numComponents; i++) {
                buffer.put(stream.readInt());
            }
            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateFloatBuffer(FloatBuffer buffer, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        while (index < end) {
            for (int i = 0; i < numComponents; i++) {
                buffer.put(stream.readFloat());
            }
            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateShortArray(short[] array, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        int arrayIndex = 0;
        while (index < end) {
            for (int i = 0; i < numComponents; i++) {
                if (componentSize == 2) {
                    array[arrayIndex] = stream.readShort();
                } else {
                    array[arrayIndex] = stream.readByte();
                }
                arrayIndex++;
            }

            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    public static byte[] toByteArray(short[] shortArray) {
        byte[] bytes = new byte[shortArray.length];
        for (int i = 0; i < shortArray.length; i++) {
            bytes[i] = (byte) shortArray[i];
        }
        return bytes;
    }


    public static void handleSkinningBuffers(Mesh mesh, IntMap<GltfLoader.SkinBuffers> skinBuffers) {
        if (skinBuffers.size() > 0) {
            if (skinBuffers.size() == 1) {
                GltfLoader.SkinBuffers buffs = skinBuffers.get(0);
                setSkinBuffers(mesh, buffs.joints, skinBuffers.get(0).weights, buffs.componentSize);
            } else {

                int length = skinBuffers.get(0).joints.length;
                short[] jointsArray = new short[length];
                float[] weightsArray = new float[length];
                List<GltfLoader.WeightData> weightData = new ArrayList<>();
                int componentSize = 1;

                for (int i = 0; i < weightsArray.length; i += 4) {
                    weightData.clear();
                    for (int j = 0; j < skinBuffers.size(); j++) {
                        GltfLoader.SkinBuffers buffs = skinBuffers.get(j);
                        for (int k = 0; k < 4; k++) {
                            weightData.add(new GltfLoader.WeightData(buffs.weights[i + k], buffs.joints[i + k], buffs.componentSize));
                        }

                    }
                    Collections.sort(weightData, new Comparator<GltfLoader.WeightData>() {
                        @Override
                        public int compare(GltfLoader.WeightData o1, GltfLoader.WeightData o2) {
                            if (o1.value > o2.value) {
                                return -1;
                            } else if (o1.value < o2.value) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    for (int j = 0; j < 4; j++) {
                        GltfLoader.WeightData data = weightData.get(j);
                        jointsArray[i + j] = data.index;
                        weightsArray[i + j] = data.value;
                        if (data.componentSize > componentSize) {
                            componentSize = data.componentSize;
                        }
                    }
                }
                setSkinBuffers(mesh, jointsArray, weightsArray, componentSize);
            }
        }
    }


    public static void setSkinBuffers(Mesh mesh, short[] jointsArray, float[] weightsArray, int componentSize) {
        if (componentSize == 1) {
            mesh.setBuffer(VertexBuffer.Type.BoneIndex, 4, BufferUtils.createByteBuffer(toByteArray(jointsArray)));
        } else {
            mesh.setBuffer(VertexBuffer.Type.BoneIndex, 4, BufferUtils.createShortBuffer(jointsArray));
        }
        mesh.setBuffer(VertexBuffer.Type.BoneWeight, 4, BufferUtils.createFloatBuffer(weightsArray));
    }

    private static void populateFloatArray(float[] array, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        int arrayIndex = 0;
        while (index < end) {
            for (int i = 0; i < numComponents; i++) {
                array[arrayIndex] = stream.readFloat();
                arrayIndex++;
            }

            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateVector3fArray(Vector3f[] array, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        int arrayIndex = 0;
        while (index < end) {
            array[arrayIndex] = new Vector3f(
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat()
            );

            arrayIndex++;

            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateQuaternionArray(Quaternion[] array, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        int arrayIndex = 0;
        while (index < end) {
            array[arrayIndex] = new Quaternion(
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat()
            );

            arrayIndex++;

            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    private static void populateMatrix4fArray(Matrix4f[] array, LittleEndien stream, int length, int byteOffset, int byteStride, int numComponents, int componentSize) throws IOException {
        int index = byteOffset;
        int end = length * componentSize + byteOffset;
        stream.skipBytes(byteOffset);
        int arrayIndex = 0;
        while (index < end) {

            array[arrayIndex] = toRowMajor(
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat(),
                    stream.readFloat()
            );
            //gltf matrix are column major, JME ones are row major.

            arrayIndex++;

            index += Math.max(componentSize * numComponents, byteStride);
        }
    }

    public static Matrix4f toRowMajor(float m00, float m01, float m02, float m03,
                                      float m10, float m11, float m12, float m13,
                                      float m20, float m21, float m22, float m23,
                                      float m30, float m31, float m32, float m33) {
        return new Matrix4f(m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
    }

    private static LittleEndien getStream(byte[] buffer) {
        return new LittleEndien(new DataInputStream(new ByteArrayInputStream(buffer)));
    }

    public static String getAsString(JsonObject parent, String name) {
        JsonElement el = parent.get(name);
        return el == null ? null : el.getAsString();
    }

    public static Integer getAsInteger(JsonObject parent, String name) {
        JsonElement el = parent.get(name);
        return el == null ? null : el.getAsInt();
    }

    public static Integer getAsInteger(JsonObject parent, String name, int defaultValue) {
        JsonElement el = parent.get(name);
        return el == null ? defaultValue : el.getAsInt();
    }

    public static Float getAsFloat(JsonObject parent, String name) {
        JsonElement el = parent.get(name);
        return el == null ? null : el.getAsFloat();
    }

    public static Float getAsFloat(JsonObject parent, String name, float defaultValue) {
        JsonElement el = parent.get(name);
        return el == null ? defaultValue : el.getAsFloat();
    }

    public static Boolean getAsBoolean(JsonObject parent, String name) {
        JsonElement el = parent.get(name);
        return el == null ? null : el.getAsBoolean();
    }

    public static Boolean getAsBoolean(JsonObject parent, String name, boolean defaultValue) {
        JsonElement el = parent.get(name);
        return el == null ? defaultValue : el.getAsBoolean();
    }

    public static ColorRGBA getAsColor(JsonObject parent, String name) {
        JsonElement el = parent.get(name);
        if (el == null) {
            return null;
        }
        JsonArray color = el.getAsJsonArray();
        return new ColorRGBA(color.get(0).getAsFloat(), color.get(1).getAsFloat(), color.get(2).getAsFloat(), color.size() > 3 ? color.get(3).getAsFloat() : 1f);
    }

    public static ColorRGBA getAsColor(JsonObject parent, String name, ColorRGBA defaultValue) {
        ColorRGBA color = getAsColor(parent, name);
        return color == null ? defaultValue : color;
    }


    public static void assertNotNull(Object o, String errorMessage) {
        if (o == null) {
            throw new AssetLoadException(errorMessage);
        }
    }

    public static Spatial findCommonAncestor(List<Spatial> spatials) {
        Map<Spatial, List<Spatial>> flatParents = new HashMap<>();

        for (Spatial spatial : spatials) {
            List<Spatial> parents = new ArrayList<>();
            Spatial parent = spatial.getParent();
            while (parent != null) {
                parents.add(0, parent);
                parent = parent.getParent();
            }
            flatParents.put(spatial, parents);
        }

        int index = 0;
        Spatial lastCommonParent = null;
        Spatial parent = null;
        while (true) {
            for (Spatial spatial : flatParents.keySet()) {
                List<Spatial> parents = flatParents.get(spatial);
                if (index == parents.size()) {
                    //we reached the end of a spatial hierarchy let's return;
                    return lastCommonParent;
                }
                Spatial p = parents.get(index);
                if (parent == null) {
                    parent = p;
                } else if (p != parent) {
                    return lastCommonParent;
                }
            }
            lastCommonParent = parent;
            parent = null;
            index++;
        }



    }

    public static void dumpMesh(Mesh m) {
        for (VertexBuffer vertexBuffer : m.getBufferList().getArray()) {
            System.err.println(vertexBuffer.getBufferType());
            System.err.println(vertexBuffer.getFormat());
            if (vertexBuffer.getData() instanceof FloatBuffer) {
                FloatBuffer b = (FloatBuffer) vertexBuffer.getData();
                float[] arr = new float[b.capacity()];
                b.rewind();
                b.get(arr);
                b.rewind();
                for (float v : arr) {
                    System.err.print(v + ",");
                }
            }
            if (vertexBuffer.getData() instanceof ShortBuffer) {
                ShortBuffer b = (ShortBuffer) vertexBuffer.getData();
                short[] arr = new short[b.capacity()];
                b.rewind();
                b.get(arr);
                b.rewind();
                for (short v : arr) {
                    System.err.print(v + ",");
                }
            }
            if (vertexBuffer.getData() instanceof IntBuffer) {
                IntBuffer b = (IntBuffer) vertexBuffer.getData();
                int[] arr = new int[b.capacity()];
                b.rewind();
                b.get(arr);
                b.rewind();
                for (int v : arr) {
                    System.err.print(v + ",");
                }
            }
            System.err.println("\n---------------------------");
        }
    }
}