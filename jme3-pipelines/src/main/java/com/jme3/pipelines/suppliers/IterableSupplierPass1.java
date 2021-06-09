package com.jme3.pipelines.suppliers;

import com.jme3.pipelines.AbstractPipelinePass1;

/**
 * A helper class to trigger one downstream event per element in an iterator.<br />
 * This is comparable to flatMap() in streams.<br />
 *
 * @param <I> The input datatype
 * @param <O> the output iterable datatype
 */
public abstract class IterableSupplierPass1<I, O> extends AbstractPipelinePass1<I, O> {

    @Override
    public O doProcess(I first) {
        return null;
    }

    public abstract Iterable<O> processIterator(I first);

    @Override
    public void process(I first) {
        Iterable<O> it = processIterator(first);

        for (O o: it) {
            for (var p: successors) {
                p.process(o);
            }
        }
    }
}
