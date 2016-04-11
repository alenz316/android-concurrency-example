package com.steadfatinnovation.androidconcurrency;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.widget.TextView;

public class CalcThreadedActivity extends AbstractCalcActivity implements AbstractCalcActivity.OnPrimeFoundListener{

    private static final String OUTPUT_KEY = "out_key";

    protected TextView mPrimeOutput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrimeOutput = (TextView) findViewById(R.id.prime_output);
        if (savedInstanceState != null && mPrimeOutput != null) {
            mPrimeOutput.setText(savedInstanceState.getString(OUTPUT_KEY));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OUTPUT_KEY, mPrimeOutput.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @MainThread
    protected void setOutputText(final String output) {
        mPrimeOutput.setText(output);
    }

    @Override
    protected void startCalc(final int nthPrime, final boolean pauseOnPrime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doCalc(nthPrime, pauseOnPrime, CalcThreadedActivity.this);
            }
        }).start();

    }

    @Override
    public void foundPrime(int count, int prime) {
        setOutputText("The " + count + "th prime is " + prime);
    }
}
