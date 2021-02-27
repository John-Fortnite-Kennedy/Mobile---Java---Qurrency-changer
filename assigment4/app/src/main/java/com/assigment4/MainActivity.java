package com.assigment4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Spinner toCurrency;
    Spinner fromCurrency;
    TextView textView;
    TextView result;
    TextView time;
    TextView NYtime;
    EditText edittext;
    Spinner ftemp;
    Spinner stemp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toCurrency = findViewById(R.id.planets_spinner);
        fromCurrency = findViewById(R.id.currency_spinner);
        ftemp = findViewById(R.id.fTemp);
        stemp = findViewById(R.id.sTemp);
        final EditText edtEuroValue = findViewById(R.id.dropDown);
        final Button btnConvert = findViewById(R.id.button);
        Calendar calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        textView= findViewById(R.id.textView7);
        result= findViewById(R.id.result);
        time= findViewById(R.id.timeText);
        NYtime= findViewById(R.id.newYorktime);
        time.setText("Local time in Nur-Sultan: "+ getTime("GMT+6"));
        NYtime.setText("Time in New York: " + calNewYork.get(Calendar.HOUR_OF_DAY)+":"+calNewYork.get(Calendar.MINUTE));

        edittext = findViewById(R.id.temp);

        btnConvert.setOnClickListener(v -> {
            double output,variable = 0;
            if (edtEuroValue.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show();
            else {
                String toCurr = toCurrency.getSelectedItem().toString();
                String fromCurr = fromCurrency.getSelectedItem().toString();
                double euroVlaue = Double.parseDouble(edtEuroValue.getText().toString());
                if (toCurr==fromCurr){
                    textView.setText(String.valueOf(edtEuroValue));
                    variable=1;
                } else if (fromCurr.equals("Euro")){
                    if (toCurr.equals("Dollar")){
                        variable=1.21;
                    } else if (toCurr.equals("Tenge")){
                        variable=504.15;
                    } else if (toCurr.equals("Ruble")){
                        variable=90.04;
                    }
                } else if (fromCurr.equals("Tenge")) {
                    if (toCurr.equals("Dollar")){
                        variable=0.0024;
                    } else if (toCurr.equals("Euro")){
                        variable=0.0020;
                    } else if (toCurr.equals("Ruble")){
                        variable=0.18;
                    }
                } else if (fromCurr.equals("Ruble")) {
                    if (toCurr.equals("Dollar")){
                        variable=0.013;
                    } else if (toCurr.equals("Euro")){
                        variable=0.011;
                    } else if (toCurr.equals("Tenge")){
                        variable=5.60;
                    }
                } else if (fromCurr.equals("Dollar")) {
                    if (toCurr.equals("Ruble")){
                        variable=74.58;
                    } else if (toCurr.equals("Euro")){
                        variable=0.83;
                    } else if (toCurr.equals("Tenge")){
                        variable=417.62;
                    }
                }
                output = euroVlaue * variable;
                textView.setText(String.valueOf(output));
            }

        });

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                String inTemp = ftemp.getSelectedItem().toString();
                String outTemp = stemp.getSelectedItem().toString();
                double tmp = 0;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String temperatureIn = edittext.getText().toString();
                    Double n = Double.parseDouble(temperatureIn);
                    if (inTemp==outTemp){
                        result.setText(temperatureIn);
                    } else if (inTemp.equals("C")){
                        if (outTemp.equals("F")){
                            tmp=((n*9)/5)+32;
                        } else if (outTemp.equals("K")){
                            tmp=n+273.15;
                        }
                    } else if (inTemp.equals("F")) {
                        if (outTemp.equals("C")){
                            tmp=((n-32)*5)/9;
                        } else if (outTemp.equals("K")){
                            tmp=(((n-32)*5)/9) + 273.15;
                        }
                    } else if (inTemp.equals("K")) {
                        if (outTemp.equals("F")){
                            tmp=(((n-273.15)*9)/5)+32;
                        } else if (outTemp.equals("C")){
                            tmp=n-273.15;
                        }
                    }
                    String stringdouble= Double.toString(tmp);
                    result.setText(stringdouble);
                }
                return handled;
            }
        });
    }

    public String getTime(String timezone) {
        TimeZone tz = TimeZone.getTimeZone("GMT+05:30");
        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d" , c.get(Calendar.MINUTE))+":"+String.format("%02d" , c.get(Calendar.SECOND))+":"+String.format("%03d" , c.get(Calendar.MILLISECOND));
        return time;
    }
}