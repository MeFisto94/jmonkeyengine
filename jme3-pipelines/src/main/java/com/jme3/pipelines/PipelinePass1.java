package com.jme3.pipelines;

/**
 * The interface that represents a pass in the pipeline.<br />
 * This represents the main element of the whole system.<br />
 * Take a look at {@link AbstractPipelinePass1} for an easy-to-subclass element.<br />
 * The suffix <b>1</b> in the relevant classes denotes the number of arguments of the central process() call.<br />
 * Due to java not supporting multiple return types, the overhead of automatic wrapping and issues when combining
 * multiple numbers of arguments (e.g. 1 to 2 and 2 to 1), we will see how sufficient one argument is, especially
 * when using Java 14+ records.
 *
 * @param <A> The data type
 */
public interface PipelinePass1<A> {

    /**
     * This is called when the previous pass has finished processing and thus invokes this.<br />
     * It's the implementers responsibility to propagate this change (if desired) to the successors.<br />
     * @param first the processing output of the previous step
     */
    void process(A first);

    /**
     * Adds a new pass as successor to this.<br />
     * Implementers should call {@link #onConnect(PipelinePass1)} on <code>nextPass</code> passing this.<br />
     * @param nextPass the pass to add as successor.
     * @return the freshly added pass(!)
     * @see #thenAdd(PipelinePass1)
     */
    PipelinePass1<A> then(PipelinePass1<A> nextPass);

    /**
     * Adds a new pass as successor to this.<br />
     * Implementers should call {@link #onConnect(PipelinePass1)} on <code>nextPass</code> passing this.<br />
     * @param nextPass the pass to add as successor.
     * @return this(!)
     * @see #then(PipelinePass1)
     */
    PipelinePass1<A> thenAdd(PipelinePass1<A> nextPass);

    /**
     * This is called when <code>this</code> has been added to the corresponding predecessor.<br />
     * This may be useful to count the ingoing passes, but DO NOT use this for processing of data.
     * @param predecessor the predecessor pass
     */
    void onConnect(PipelinePass1<A> predecessor);
}
