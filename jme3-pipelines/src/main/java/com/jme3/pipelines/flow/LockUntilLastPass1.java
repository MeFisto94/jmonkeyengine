package com.jme3.pipelines.flow;

import com.jme3.pipelines.AbstractPipelinePass1;
import com.jme3.pipelines.PipelinePass1;

public class LockUntilLastPass1<A> extends AbstractPipelinePass1<A> {
    int nbOfConnections = 0;
    int nbOfCalls = 0;

    @Override
    public void onConnect(PipelinePass1<A> predecessor) {
        nbOfConnections++;
    }

    @Override
    public A doProcess(A first) {
        return first;
    }

    @Override
    public void process(A first) {
        nbOfCalls++;
        if (nbOfCalls == nbOfConnections) {
            super.process(first);
        }
    }
}
