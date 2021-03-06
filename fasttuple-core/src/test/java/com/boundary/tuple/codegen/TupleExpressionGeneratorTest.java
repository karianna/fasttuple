package com.boundary.tuple.codegen;

import com.boundary.tuple.FastTuple;
import com.boundary.tuple.TupleSchema;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cliff on 5/12/14.
 */
public class TupleExpressionGeneratorTest {

    @Test
    public void testGetLongOut() throws Exception {
        TupleSchema schema = TupleSchema.builder().
                addField("a", Long.TYPE).
                addField("b", Long.TYPE).
                addField("c", Long.TYPE).
                heapMemory().
                build();

        FastTuple tuple = schema.createTuple();
        tuple.setLong(1, 100L);
        tuple.setLong(2, 600L);
        tuple.setLong(3, 1000L);

        TupleExpressionGenerator.LongTupleExpression eval = TupleExpressionGenerator.builder().expression("tuple.a + tuple.b + tuple.c").schema(schema).returnLong();
        assertEquals(1700L, eval.evaluate(tuple));
    }

    @Test
    public void testMultiExpr() throws Exception {
        TupleSchema schema = TupleSchema.builder().
                addField("a", Long.TYPE).
                addField("b", Long.TYPE).
                addField("c", Long.TYPE).
                heapMemory().
                build();

        FastTuple tuple = schema.createTuple();
        TupleExpressionGenerator.TupleExpression eval = TupleExpressionGenerator.builder().expression("tuple.a(100), tuple.b(200), tuple.c(300)").schema(schema).returnVoid();
        eval.evaluate(tuple);
        assertEquals(100L, tuple.getLong(1));
        assertEquals(200, tuple.getLong(2));
        assertEquals(300, tuple.getLong(3));
    }
}
