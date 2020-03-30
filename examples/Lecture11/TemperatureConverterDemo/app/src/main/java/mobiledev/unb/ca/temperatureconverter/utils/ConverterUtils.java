package mobiledev.unb.ca.temperatureconverter.utils;

public class ConverterUtils {
    public static float convertFahrenheitToCelsius(Float fahrenheit) {
        if (null == fahrenheit) {
            throw new IllegalArgumentException("fahrenheit is missing");
        }

        return ((fahrenheit - 32) * 5 / 9);
    }

    public static float convertCelsiusToFahrenheit(Float celsius) {
        if (null == celsius) {
            throw new IllegalArgumentException("celsius is missing");
        }

        return ((celsius * 9) / 5) + 32;
    }
}
