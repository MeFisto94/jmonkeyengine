package com.jme3.pipelines.suppliers;

import com.jme3.util.functional.Function;

public class LambdaIterableSupplierPass1<I, O> extends IterableSupplierPass1<I, O> {
    private final Function<Iterable<O>, I> internalFunction;

    public LambdaIterableSupplierPass1(Function<Iterable<O>, I> internalFunction) {
        this.internalFunction = internalFunction;
    }

    @Override
    public Iterable<O> processIterator(I first) {
        return internalFunction.eval(first);
    }
}
