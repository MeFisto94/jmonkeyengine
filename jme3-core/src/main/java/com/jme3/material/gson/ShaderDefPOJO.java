package com.jme3.material.gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Map;

public class ShaderDefPOJO {
    @SerializedName(value = "extends")
    String[] extending;
    Map<String, String> define;
    ShaderPOJO shaders;

    public ShaderDefPOJO(String[] extending, Map<String, String> define, ShaderPOJO shaders) {
        this.extending = extending;
        this.define = define;
        this.shaders = shaders;
    }

    protected ShaderDefPOJO() {
    }

    @Override
    public String toString() {
        return "ShaderDefPOJO{" +
                "extending=" + Arrays.toString(extending) +
                ",\n define=" + define +
                ",\n shaders=" + shaders +
                '}';
    }
}
