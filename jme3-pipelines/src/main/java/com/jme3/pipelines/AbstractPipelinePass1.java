package com.jme3.pipelines;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPipelinePass1<A> implements PipelinePass1<A> {
    List<PipelinePass1<A>> successors = new ArrayList<>();

    /**
     * Implement this method to do the processing at this pipeline step.<br />
     * Remember that <code>first</code> may be shared between multiple pipeline steps,
     * so if it should be immutable, cloning may be required.<br />
     *
     * @param first the first argument of this pass
     * @return the processed value
     */
    public abstract A doProcess(A first);

    @Override
    public void process(A first) {
        A a = doProcess(first);
        for (var p : successors) {
            p.process(a);
        }
    }

    @Override
    public void onConnect(PipelinePass1<A> predecessor) { }

    @Override
    public PipelinePass1<A> then(PipelinePass1<A> nextPass) {
        successors.add(nextPass);
        nextPass.onConnect(this);
        return nextPass;
    }

    @Override
    public PipelinePass1<A> thenAdd(PipelinePass1<A> nextPass) {
        successors.add(nextPass);
        nextPass.onConnect(this);
        return this;
    }
}
