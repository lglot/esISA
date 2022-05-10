package it.isa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OperationTest {

    // Operation op;

    // public OperationTest() {
    // op = new Operation();
    // }

    // testiamo metodo sum
    @Test
    public void testSum() {
        Operation op = new Operation();
        float result = op.sum(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testGetCount() {
        Operation op = new Operation(1);
        op.sum(1, 2);
        assertEquals(2, op.getCount());
    }

}
