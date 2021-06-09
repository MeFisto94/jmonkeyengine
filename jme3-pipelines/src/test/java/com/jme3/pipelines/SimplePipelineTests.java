package com.jme3.pipelines;

import com.jme3.pipelines.consumers.TerminalConsumer1;
import com.jme3.pipelines.flow.LockUntilLastPass1;
import com.jme3.pipelines.suppliers.PipelineSupplier1;
import org.junit.Assert;
import org.junit.Test;

public class SimplePipelineTests {
    private static class StringReversePass extends AbstractPipelinePass1<String, String> {
        @Override
        public String doProcess(String first) {
            return new StringBuilder(first).reverse().toString();
        }
    }

    @Test
    public void testPipelineConstruction_thenAdd() {
        var supplier = new PipelineSupplier1<String>();
        supplier.thenAdd(
            new StringReversePass()
                .thenAdd(new TerminalConsumer1<>(first -> Assert.assertEquals("dlroW olleH", first)))
                .thenAdd(new StringReversePass()
                    .thenAdd(new TerminalConsumer1<>(first -> Assert.assertEquals("Hello World", first))))
        );

        supplier.accept("Hello World");
    }

    @Test
    public void testPipelineConstruction_then() {
        // Here it's important to keep in mind that foo.then(bar) returns bar, so you can easily chain calls.
        // When you, however need to add the existing chain to something else, you need to capture "foo", as shown here.
        // thenAdd can bypass that problem, but will lead to increased nesting, as foo.then(bar).then(baz) becomes
        // foo.thenAdd(bar.thenAdd(baz))

        var foo = new StringReversePass();
        foo.then(new TerminalConsumer1<>(first -> Assert.assertEquals("Hello World", first)));

        var supplier = new PipelineSupplier1<String>();
        supplier.then(
            new StringReversePass()
                .thenAdd(new TerminalConsumer1<>(first -> Assert.assertEquals("dlroW olleH", first)))
                .thenAdd(foo)
        );

        supplier.accept("Hello World");
    }

    @Test
    public void testPipelineFlow_LockUntilLast() {
        var lockedSection = new LockUntilLastPass1<String>()
                .thenAdd(new TerminalConsumer1<>(a -> Assert.assertEquals("Hello World", a)));

        var supplier = new PipelineSupplier1<String>();
        supplier
            .thenAdd(new StringReversePass().thenAdd(lockedSection))
            .thenAdd(new StringReversePass().thenAdd(new StringReversePass().thenAdd(lockedSection)));

        // The supplier will first execute the reverse-once part, and thus when locking is correct, the string should
        // appear in the correct order again (double reversed), because only then will the lock not terminate processing.
        supplier.accept("Hello World");
    }

    @Test
    public void testPipelineTypeConversion() {
        var supplier = new PipelineSupplier1<Integer>();
        supplier
                .then(new LambdaPipelinePass1<>(i -> i + 1))
                .then(new LambdaPipelinePass1<>(i -> "" + i))
                .then(new TerminalConsumer1<>(s -> Assert.assertEquals(s, "1337")));

        supplier
                .then(new LambdaPipelinePass1<>(i -> "" + i))
                .thenAdd(new LambdaPipelinePass1<>(i -> i + 1)) // thenAdd -> bound to supplier
                .then(new LambdaPipelinePass1<>(i -> "" + i))
                .then(new LambdaPipelinePass1<>(Integer::parseInt))
                .then(new TerminalConsumer1<>(i -> Assert.assertEquals(i, (Integer)1336)));

        supplier.accept(1336);
    }
}
