package com.jme3.material.gson;

public class ShaderPOJO {
    String fragment;
    String vertex;

    public ShaderPOJO(String fragment, String vertex) {
        this.fragment = fragment;
        this.vertex = vertex;
    }

    protected ShaderPOJO() {
    }

    @Override
    public String toString() {
        return "ShaderPOJO{" +
                "fragment='" + fragment + '\'' +
                ",\n vertex='" + vertex + '\'' +
                '}';
    }
}
