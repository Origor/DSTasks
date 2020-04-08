import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PINTest {
    @Test
    void test110() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        int pin = 1234;
        PIN pinInstance = new PIN(pin);

        Field accessField = pinInstance.getClass().getDeclaredField("access");
        Field tryCounterField = pinInstance.getClass().getDeclaredField("tryCounter");
        Field securePinField = pinInstance.getClass().getDeclaredField("securePin");

        accessField.setAccessible(true);
        tryCounterField.setAccessible(true);
        securePinField.setAccessible(true);

        // set access to true
        accessField.set(pinInstance, true);

        int tryCounter = tryCounterField.getInt(pinInstance);
        boolean access = accessField.getBoolean(pinInstance);
        int securePin = securePinField.getInt(pinInstance);

        // base case for variables
        assert tryCounter == 3 && access && securePin != pin + 1;

        // run function
        pinInstance.checkPin(pin + 1);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        // Should grant access by entering the wrong pin since access variable is set to true
        assertNotEquals(pin + 1, securePin);
        assertEquals(3, tryCounter);
        assertTrue(access);
    }

    @Test
    void test010() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        int pin = 1234;
        PIN pinInstance = new PIN(pin);

        Field accessField = pinInstance.getClass().getDeclaredField("access");
        Field tryCounterField = pinInstance.getClass().getDeclaredField("tryCounter");
        Field securePinField = pinInstance.getClass().getDeclaredField("securePin");

        accessField.setAccessible(true);
        tryCounterField.setAccessible(true);
        securePinField.setAccessible(true);

        int tryCounter = tryCounterField.getInt(pinInstance);
        boolean access = accessField.getBoolean(pinInstance);
        int securePin = securePinField.getInt(pinInstance);

        assert tryCounter == 3 && !access && securePin != pin + 1;

        // run function with wrong pin
        pinInstance.checkPin(pin + 1);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        // should not grant access when only tryCounter is filling the requirements
        assertNotEquals(pin + 1, securePin);
        assertEquals(2, tryCounter);
        assertFalse(access);
    }

    @Test
    void test011() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        int pin = 1234;
        PIN pinInstance = new PIN(pin);

        Field accessField = pinInstance.getClass().getDeclaredField("access");
        Field tryCounterField = pinInstance.getClass().getDeclaredField("tryCounter");
        Field securePinField = pinInstance.getClass().getDeclaredField("securePin");

        accessField.setAccessible(true);
        tryCounterField.setAccessible(true);
        securePinField.setAccessible(true);

        int tryCounter = tryCounterField.getInt(pinInstance);
        boolean access = accessField.getBoolean(pinInstance);
        int securePin = securePinField.getInt(pinInstance);

        assert tryCounter == 3 && !access && securePin == pin;

        // run function with correct pin
        pinInstance.checkPin(pin);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        // should grant access when inserted pin is same as the one given in constructor within try limits
        assertEquals(pin, securePin);
        assertEquals(3, tryCounter);
        assertTrue(access);
    }

    @Test
    void test001() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        int pin = 1234;
        PIN pinInstance = new PIN(pin);

        Field accessField = pinInstance.getClass().getDeclaredField("access");
        Field tryCounterField = pinInstance.getClass().getDeclaredField("tryCounter");
        Field securePinField = pinInstance.getClass().getDeclaredField("securePin");

        accessField.setAccessible(true);
        tryCounterField.setAccessible(true);
        securePinField.setAccessible(true);

        // run function with incorrect pin
        pinInstance.checkPin(pin+1);
        pinInstance.checkPin(pin+1);
        pinInstance.checkPin(pin+1);
        pinInstance.checkPin(pin+1);

        int tryCounter = tryCounterField.getInt(pinInstance);
        boolean access = accessField.getBoolean(pinInstance);
        int securePin = securePinField.getInt(pinInstance);

        assert tryCounter < 0 && !access && securePin == pin;

        // run function with correct pin
        pinInstance.checkPin(pin);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        // should not grant access when the correct pin is given outside of the try limits
        assertEquals(pin, securePin);
        assertTrue(tryCounter < 0);
        assertFalse(access);
    }
}