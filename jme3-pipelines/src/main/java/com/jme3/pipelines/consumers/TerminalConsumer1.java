package com.jme3.pipelines.consumers;

import com.jme3.pipelines.PipelinePass1;

import java.util.function.Consumer;

public class TerminalConsumer1<I> implements PipelinePass1<I, Void> {
    Consumer<I> consumer;

    public TerminalConsumer1(Consumer<I> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void process(I first) {
        consumer.accept(first);
    }

    @Override
    public <T> PipelinePass1<Void, T> then(PipelinePass1<Void, T> nextPass) {
        throw new UnsupportedOperationException("Cannot attach another state to a terminal operation!");
    }

    @Override
    public PipelinePass1<I, Void> thenAdd(PipelinePass1<Void, ?> nextPass) {
        throw new UnsupportedOperationException("Cannot attach another state to a terminal operation!");
    }

    @Override
    public void onConnect(PipelinePass1<?, I> predecessor) { }
}
