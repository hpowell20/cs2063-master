package mobiledev.unb.ca.labexam.helper;

public class SharedPreferencesEntry {
    private final String number;
    private final boolean checked;

    public SharedPreferencesEntry(String number, boolean checked) {
        this.number = number;
        this.checked = checked;
    }

    public String getNumber() { return number; }

    public boolean getChecked() { return checked; }
}
