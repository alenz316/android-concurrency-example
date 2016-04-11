package com.steadfatinnovation.androidconcurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

abstract public class AbstractCalcActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        View v = findViewById(R.id.start_calc);
        final EditText et = (EditText) findViewById(R.id.number_picker);
        final CheckBox cb = (CheckBox) findViewById(R.id.pause_on_prime);

        if (v != null && et != null && cb != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCalc(Integer.parseInt(et.getText().toString()), cb.isChecked());
                }
            });
        }
    }

    abstract protected void startCalc(int nthPrime, boolean pauseOnPrime);

    protected interface OnPrimeFoundListener {
     void foundPrime(int count, int prime);
    }

    protected static void doCalc(int nthPrime, boolean pauseOnPrime, OnPrimeFoundListener listener) {
        int count = 0;

        int number = 2;
        while (count < nthPrime){
            if (isPrime(number)) {
                count++;
//                Log.e("MATHS", "doCalc: " + number);
                listener.foundPrime(count, number);
                if (pauseOnPrime) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            number++;
        }
    }

    private static boolean isPrime(int number) {
        if (number == 0) {
            return false;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
