package com.jme3.material.gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Map;

public class TechniquePOJO {
    @SerializedName(value = "extends")
    String[] extending;
    Map<String, ShaderDefPOJO> shaderDefs;

    protected TechniquePOJO() {
    }

    @Override
    public String toString() {
        return "TechniquePOJO{" +
                "extending=" + Arrays.toString(extending) +
                ",\n shaderDef=" + shaderDefs +
                "\n}";
    }
}
