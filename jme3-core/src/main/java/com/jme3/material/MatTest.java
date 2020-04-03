package com.jme3.material;

import com.google.gson.Gson;
import com.jme3.material.gson.MaterialPOJO;

public class MatTest {

    public static String matS = "{\n" +
            "    \"name\":\"FXAA\",\n" +
            "    \"extends\":[\n" +
            "\n" +
            "    ],\n" +
            "    \"params\":{\n" +
            "        //#for i=0..6 ( $0 )\n" +
            "        \"Scene$i\":{\n" +
            "            \"type\":\"Texture2D\",\n" +
            "            \"minFilter\":\"nearestNoMipMap\",\n" +
            "            \"magFilter\":\"nearest\",\n" +
            "            \"wrapX\":\"edgeClamp\",\n" +
            "            \"wrapY\":\"edgeClamp\",\n" +
            "            \"wrapZ\":\"edgeClamp\" ,\n" +
            "            \"colorSpace\":\"linear\",\n" +
            "            \"anisotopicFilter\":\"4\",\n" +
            "            \"value\":\"\"\n" +
            "        },\n" +
            "        //#endfor \n" +
            "        \"SubPixelShift\":{\n" +
            "            \"type\":\"Float\",\n" +
            "            \"value\":0.25\n" +
            "        },\n" +
            "        \"SpanMax\":{\n" +
            "            \"type\":\"Float\",\n" +
            "            \"value\":8\n" +
            "        },\n" +
            "        \"ReduceMul\":{\n" +
            "            \"type\":\"Float\",\n" +
            "            \"value\":0.123\n" +
            "        },\n" +
            "        \"ResolutionInverse\":{\n" +
            "            \"type\":\"Vector2\",\n" +
            "            \"value\":[0,0]\n" +
            "        },\n" +
            "        \"WorldCamera\":{\n" +
            "            \"type\":\"UniformBufferObject\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"techniques\":{\n" +
            "        \"baseTechnique\":{\n" +
            "            \"extends\":[],\n" +
            "            \"shaderDefs\": {\n" +
            "            \"commonBackend\":{\n" +
            "                \"define\":{\n" +
            "                    //#for i=0..6 ( $0 )\n" +
            "                    \"SCENE_$i:\":\"Scene$i\"\n" +
            "                    //#endfor\n" +
            "                }\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"default\":{\n" +
            "            \"extends\":[\"baseTechnique\"],\n" +
            "            \"shaderDefs\": {\n" +
            "            \"angleGLSL150\":{\n" +
            "                \"extends\":[\"commonBackend\"],\n" +
            "                \"define\":{  },\n" +
            "                \"shaders\":{\n" +
            "                    \"fragment\":\"Pipeline/FXAA/fxaa.frag\",\n" +
            "                    \"vertex\":\"Pipeline/FXAA/fxaa.vert\"   \n" +
            "                } \n" +
            "            },\n" +
            "            \"legacyGLSL150\":{\n" +
            "                \"extends\":[\"angleGLSL150\"]\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "    } \n" +
            "}";

    public static void main(String... args) {
        Gson gson = new Gson();
        MaterialPOJO mat = gson.fromJson(matS, MaterialPOJO.class);
        System.out.println(mat.toString());
    }
}
