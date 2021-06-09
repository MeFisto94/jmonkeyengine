package com.jme3.pipelines.consumers;

import com.jme3.pipelines.PipelinePass1;

import java.util.function.Consumer;

public class TerminalConsumer1<A> implements PipelinePass1<A> {
    Consumer<A> consumer;

    public TerminalConsumer1(Consumer<A> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void process(A first) {
        consumer.accept(first);
    }

    @Override
    public PipelinePass1<A> then(PipelinePass1<A> nextPass) {
        throw new UnsupportedOperationException("Cannot attach another state to a terminal operation!");
    }

    @Override
    public PipelinePass1<A> thenAdd(PipelinePass1<A> nextPass) {
        throw new UnsupportedOperationException("Cannot attach another state to a terminal operation!");
    }

    @Override
    public void onConnect(PipelinePass1<A> predecessor) { }
}
