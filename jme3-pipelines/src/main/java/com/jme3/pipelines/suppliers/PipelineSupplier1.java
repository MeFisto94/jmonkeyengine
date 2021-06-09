package com.jme3.pipelines.suppliers;

import com.jme3.pipelines.AbstractPipelinePass1;

import java.util.function.Consumer;

public class PipelineSupplier1<A> extends AbstractPipelinePass1<A> implements Consumer<A> {
    @Override
    public A doProcess(A first) {
        return first;
    }

    @Override
    public void accept(A a) {
        process(a);
    }
}
