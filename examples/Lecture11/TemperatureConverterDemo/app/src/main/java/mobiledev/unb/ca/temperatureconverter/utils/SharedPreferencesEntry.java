package mobiledev.unb.ca.temperatureconverter.utils;

public class SharedPreferencesEntry {
    private final float celsius;
    private final float fahrenheit;

    public SharedPreferencesEntry(float celsius, float fahrenheit) {
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
    }

    public float getCelsius() {
        return celsius;
    }

    public float getFahrenheit() {
        return fahrenheit;
    }
}
