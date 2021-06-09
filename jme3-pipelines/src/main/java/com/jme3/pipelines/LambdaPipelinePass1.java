package com.jme3.pipelines;

import com.jme3.util.functional.Function;

/**
 * This extends {@link AbstractPipelinePass1} with a way to construct the abstract class without the need for an
 * anonymous class and instead passing a {@link java.util.function.Function}
 * @param <I> The input data type
 * @param <O> The output data type
 */
public class LambdaPipelinePass1<I, O> extends AbstractPipelinePass1<I, O> {
    private final  Function<O, I> internalFunction;

    public LambdaPipelinePass1(Function<O, I> lambda) {
        this.internalFunction = lambda;
    }

    @Override
    public O doProcess(I first) {
        return internalFunction.eval(first);
    }
}
