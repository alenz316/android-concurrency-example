package com.steadfatinnovation.androidconcurrency;

import android.os.Bundle;
import android.widget.TextView;

public class CalcMainActivity extends AbstractCalcActivity {

    protected TextView mPrimeOutput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrimeOutput = (TextView) findViewById(R.id.prime_output);
    }

    protected void setOutputText(String output) {
        mPrimeOutput.setText(output);
    }

    @Override
    protected void startCalc(int nthPrime, boolean pauseOnPrime) {
        doCalc(nthPrime, pauseOnPrime);
    }

    @Override
    protected void foundPrime(int count, int prime) {
        setOutputText("The " + count + "th prime is " + prime);
    }
}
