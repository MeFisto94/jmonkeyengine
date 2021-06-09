package com.jme3.pipelines;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPipelinePass1<I, O> implements PipelinePass1<I, O> {
    protected List<PipelinePass1<O, ?>> successors = new ArrayList<>();

    /**
     * Implement this method to do the processing at this pipeline step.<br />
     * Remember that <code>first</code> may be shared between multiple pipeline steps,
     * so if it should be immutable, cloning may be required.<br />
     *
     * @param first the first argument of this pass
     * @return the processed value
     */
    public abstract O doProcess(I first);

    @Override
    public void process(I first) {
        O o = doProcess(first);
        for (var p : successors) {
            p.process(o);
        }
    }

    @Override
    public void onConnect(PipelinePass1<?, I> predecessor) { }

    @Override
    public <T> PipelinePass1<O, T> then(PipelinePass1<O, T> nextPass) {
        successors.add(nextPass);
        nextPass.onConnect(this);
        return nextPass;
    }

    @Override
    public PipelinePass1<I, O> thenAdd(PipelinePass1<O, ?> nextPass) {
        successors.add(nextPass);
        nextPass.onConnect(this);
        return this;
    }
}
