package com.jme3.material.gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Map;

public class MaterialPOJO {
    String name;

    @SerializedName(value = "extends")
    String[] extending;

    Map<String, MatParamPOJO> params;
    Map<String, TechniquePOJO> techniques;

    protected MaterialPOJO() {
    }

    @Override
    public String toString() {
        return "MaterialPOJO{" +
                "name='" + name + "'" +
                ",\n extending=" + Arrays.toString(extending) +
                ",\n params=" + params +
                ",\n techniques=" + techniques +
                '}';
    }
}
