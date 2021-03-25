package mobiledev.unb.ca.temperatureconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mobiledev.unb.ca.temperatureconverter.utils.ConverterUtils;

public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editText);
    }

    public void btnCalculate_onClickHandler(View view) {
        switch (view.getId()) {
            case R.id.btnCalculate:
                String textStr = text.getText().toString();

                if (textStr.length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                }

                float inputValue = getInputValue(textStr);

                RadioButton celsiusButton = findViewById(R.id.rbCelcius);
                RadioButton fahrenheitButton =findViewById(R.id.rbFahrenhiet);

                if (celsiusButton.isChecked()) {
                    text.setText(String.valueOf(ConverterUtils.convertCelsiusToFahrenheit(inputValue)));
                    celsiusButton.setChecked(false);
                    fahrenheitButton.setChecked(true);
                } else {
                    text.setText(String.valueOf(ConverterUtils.convertFahrenheitToCelsius(inputValue)));
                    fahrenheitButton.setChecked(false);
                    celsiusButton.setChecked(true);
                }

                break;
        }
    }

    private float getInputValue(String textStr) {
        return Float.parseFloat(textStr);
    }
}
