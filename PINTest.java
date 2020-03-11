import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PINTest {
    private static final int MAX_TRIES = 3;
    private int tryCounter = MAX_TRIES;
    private boolean access = false;
    private int securePin;

    @Test
    void testtruetruetrue() {
        tryCounter = 3;
        access = true;
        securePin = 8455;

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);
        
        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);
    }

    @Test
    void testfalsetruetrue() {
        tryCounter = 0;
        access = true;
        securePin = 8455;

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);
    }

    @Test
    void testtruefalsetrue() {
        tryCounter = 3;
        access = false;
        securePin = 8455;

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin));
        assertTrue(access);
        assert(tryCounter == 3);
    }

    @Test
    void testtruetruefalse() {
        tryCounter = 3;
        access = true;
        securePin = 8455;

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assert(tryCounter == 3);

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assert(tryCounter == 3);
    }

    @Test
    void testfalsefalsetrue() {
        tryCounter = 0;
        access = false;
        securePin = 8455;

        assertFalse(checkPin(securePin));
        assertFalse(access);
        assertEquals(tryCounter, -1);

        assertFalse(checkPin(securePin));
        assertFalse(access);
        assertEquals(tryCounter, -2);

        assertFalse(checkPin(securePin));
        assertFalse(access);
        assertEquals(tryCounter, -3);
    }

    @Test
    void testtruefalsefalse() {
        tryCounter = 3;
        access = false;
        securePin = 8455;

        assertFalse(checkPin(securePin+1337));
        assertFalse(access);
        assertEquals(tryCounter, 2);

        assertFalse(checkPin(securePin+1337));
        assertFalse(access);
        assertEquals(tryCounter, 1);

        assertFalse(checkPin(securePin+1337));
        assertFalse(access);
        assertEquals(tryCounter, 0);
    }

    @Test
    void testfalsetruefalse() {
        tryCounter = 0;
        access = true;
        securePin = 8455;

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assertEquals(tryCounter, 3);

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assertEquals(tryCounter, 3);

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assertEquals(tryCounter, 3);

        assertTrue(checkPin(securePin+1337));
        assertTrue(access);
        assertEquals(tryCounter, 3);
    }

    public boolean checkPin(int pin) {
        if(access || (tryCounter > 0 && pin == securePin)) {
            access = true;
            tryCounter = 3;
            return true;
        }else{
            tryCounter -= 1;
            return false;
        }
    }

}