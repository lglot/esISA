package it.isa.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.Property;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class OperationTest {

    // testiamo metodo sum
    @Test
    public void testSum() {
        Operation op = new Operation();
        float result = op.sumInt(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testGetCount() {
        Operation op = new Operation(1);
        op.sum(1, 2);
        assertEquals(2, op.getCount());
    }

    /*
     * Ora con mockito testiamo La classe Operation che usa l'interfaccia
     * ServerInterface, senza avere un implementazione di questa
     */
    @Test
    public void testServerInterfaceOperation() {
        ServerInterface si = mock(ServerInterface.class);
        Operation op = new Operation(si);

        // quando viene chiamato si.getStatus() restituisci 2

        when(si.getStatus()).thenReturn(2);

        assertEquals(2, op.getServerStatus());

        // controlla che ci sia stata interazioni con l'oggetto si
        verify(si).getStatus();

        // anyString() -> accetto qualsiasi stringa
        when(si.getUrl(anyString(), anyString())).thenReturn("prova");

        assertEquals("prova", op.getUrl("ciao", "bel"));
        assertEquals("prova", op.getUrl("come", "va"));

        // quando voglio specificare un paramentro, cioè fare un argumento match
        // dobbiamo usare eq()
        when(si.getUrl(anyString(), eq("italia"))).thenReturn("prova");

    }

    // @Property
    // public void alwaysHoldSum(int a, int b) {
    // Operation op = new Operation();
    // int result = op.sumInt(a, b);
    // if (a > 0 && b > 0) {
    // assertTrue(result >= a);
    // // questo può non essere sempre vero, perchè può esserci overflow
    // }
    // }

    @Property
    public void alwaysHoldDivisione(int a, int b) {
        Operation op = new Operation();
        float result = op.divisione(a, b);

        if (a > b && b >= 0 && a >= 0) {
            assertTrue(result > 1);
            // il codice della divisione non è corretto, ma non sempre falliscono i test,
            // perchè i numeri
            // sono generati casualmente
        }
    }
}
