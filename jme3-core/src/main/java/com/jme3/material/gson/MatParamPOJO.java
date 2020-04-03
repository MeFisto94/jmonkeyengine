package com.jme3.material.gson;

import com.jme3.material.MatParam;
import com.jme3.math.Vector2f;
import com.jme3.shader.VarType;

import java.util.List;

public class MatParamPOJO {
    MatParamType type;
    Object value;

    protected MatParamPOJO() {
    }

    @Override
    public String toString() {
        return "MatParamPOJO{" +
                "type='" + type + '\'' +
                ", value=" + toMatParam() +
                "}\n";
    }

    public MatParam toMatParam() {
        switch (type) {
            case Float: // Cast is not redundant: We want an Exception when value is no float, but the type is.
                return new MatParam(VarType.Float, "lol", (float)value);
            case Vector2:
                List<Double> dbls = (List<Double>)value;
                return new MatParam(VarType.Vector2, "lol", new Vector2f((float)(double)dbls.get(0), (float)(double)dbls.get(1)));
            case Texture2D:
                return null;

            case UniformBufferObject:
                return new MatParam(VarType.BufferObject, "lol", null);

            default:
                throw new IllegalStateException("Unknown MatParam Type");
        }
    }
}
