import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PINTest {
    // if (A Il
    @Test
    void test100() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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

        assert tryCounter == 3 && access && securePin == pin;

        // run function
        pinInstance.checkPin(pin + 1);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        //Should not grant access
        assertEquals(securePin, pin);
        assertEquals(tryCounter, 3);
        assertTrue(access);
    }

    @Test
    void test000() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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

        // run function
        pinInstance.checkPin(pin + 1);
        pinInstance.checkPin(pin + 1);
        pinInstance.checkPin(pin + 1);
        pinInstance.checkPin(pin + 1);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        //Should not grant access
        assertEquals(securePin, pin);
        assertTrue(tryCounter < 0);
        assertFalse(access);
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

        assert tryCounter == 3 && !access && securePin == pin;

        // run function
        pinInstance.checkPin(pin + 1);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        //Should not grant access
        assertEquals(securePin, pin);
        assertTrue(tryCounter > 0);
        assertNotEquals(true, access);
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

        // run function
        pinInstance.checkPin(pin);

        tryCounter = tryCounterField.getInt(pinInstance);
        access = accessField.getBoolean(pinInstance);
        securePin = securePinField.getInt(pinInstance);

        //Should not grant access
        assertEquals(securePin, pin);
        assertEquals(tryCounter, 3);
        assertTrue(access);
    }
}