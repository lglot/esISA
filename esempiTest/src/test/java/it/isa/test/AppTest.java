package it.isa.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.TimeUnit;

// per usare berofeAll ci va questa annotazione di classe, per renderla statica
@TestInstance(Lifecycle.PER_CLASS)
@Disabled
public class AppTest {
    @BeforeAll
    public void execBeforeAll() {
        System.out.println("Before all");
    }

    @BeforeEach
    public void execBeforeEach() {
        System.out.println("Before each");
    }

    @Disabled
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Disabled
    @Test
    public void shouldAnswerWithFalse() {
        assertFalse(false);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    public void sleepForSomeTime() {
        System.out.println("sleeping...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {

        }
    }
}
