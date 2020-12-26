package mobiledev.unb.ca.temperatureconverter.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConverterUtilsTest {
    // convertFahrenheitToCelsius tests
    @Test(expected = IllegalArgumentException.class)
    public void testConvertFahrenheitToCelsius_WhenFahrenheitIsNull() {
        ConverterUtils.convertFahrenheitToCelsius(null);
    }

    @Test
    public void testConvertFahrenheitToCelsius_WhenFahrenheitIsLessThanZero() {
        float expected = -3.88f;
        float actual = ConverterUtils.convertFahrenheitToCelsius(25.0f);
        assertEquals(actual, expected, 0.01);
    }

    @Test
    public void testConvertFahrenheitToCelsius_WhenFahrenheitIsZero() {
        float expected = 0.0f;
        float actual = ConverterUtils.convertFahrenheitToCelsius(32.0f);
        assertEquals(actual, expected, 0.01);
    }

    @Test
    public void testConvertFahrenheitToCelsius_WhenFahrenheitIsGreaterThanZero() {
        float expected = 4.44f;
        float actual = ConverterUtils.convertFahrenheitToCelsius(40.0f);
        assertEquals(actual, expected, 0.01);
    }

    // convertCelsiusToFahrenheit tests
    @Test(expected = IllegalArgumentException.class)
    public void testConvertCelsiusToFahrenheit_WhenCelsiusIsNull() {
        ConverterUtils.convertCelsiusToFahrenheit(null);
    }

    @Test
    public void testConvertCelsiusToFahrenheit_WhenCelsiusIsLessThanZero() {
        float expected = 25.016f;
        float actual = ConverterUtils.convertCelsiusToFahrenheit(-3.88f);
        assertEquals(actual, expected, 0.01);
    }

    @Test
    public void testConvertCelsiusToFahrenheit_WhenCelsiusIsZero() {
        float expected = 32.0f;
        float actual = ConverterUtils.convertCelsiusToFahrenheit(0.0f);
        assertEquals(actual, expected, 0.01);
    }

    @Test
    public void testConvertCelsiusToFahrenheit_WhenCelsiusIsGreaterThanZero() {
        float expected = 40.0f;
        float actual = ConverterUtils.convertCelsiusToFahrenheit(4.44f);
        assertEquals(actual, expected, 0.01);
    }
}
