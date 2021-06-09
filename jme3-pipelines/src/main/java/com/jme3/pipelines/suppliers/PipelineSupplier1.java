package com.jme3.pipelines.suppliers;

import com.jme3.pipelines.AbstractPipelinePass1;

import java.util.function.Consumer;

public class PipelineSupplier1<I> extends AbstractPipelinePass1<I, I> implements Consumer<I> {
    @Override
    public I doProcess(I first) {
        return first;
    }

    @Override
    public void accept(I a) {
        process(a);
    }
}
