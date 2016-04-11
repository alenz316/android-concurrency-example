package com.steadfatinnovation.androidconcurrency;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.widget.TextView;

public class CalcThreadedRightActivity extends AbstractCalcActivity {

    private static final String OUTPUT_KEY = "out_key";

    private ThreadHolder mThreadHolder;
    private TextView mPrimeOutput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrimeOutput = (TextView) findViewById(R.id.prime_output);
        if (savedInstanceState != null && mPrimeOutput != null) {
            mPrimeOutput.setText(savedInstanceState.getString(OUTPUT_KEY));
        }

        mThreadHolder = (ThreadHolder) getLastCustomNonConfigurationInstance();
        if (mThreadHolder != null) {
            mThreadHolder.attach(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OUTPUT_KEY, mPrimeOutput.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (mThreadHolder != null) {
            mThreadHolder.detach();
        }
        return mThreadHolder;
    }

    @MainThread
    protected void setOutputText(final String output) {
        mPrimeOutput.setText(output);
    }

    @Override
    protected void startCalc(int nthPrime, boolean pauseOnPrime) {
        mThreadHolder = new ThreadHolder(this, nthPrime, pauseOnPrime);
    }

    private static class ThreadHolder implements OnPrimeFoundListener {
        private CalcThreadedRightActivity mCurrentContext;

        private ThreadHolder(CalcThreadedRightActivity activity, final int nthPrime, final boolean pauseOnPrime) {
            this.mCurrentContext = activity;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    CalcThreadedRightActivity.doCalc(nthPrime, pauseOnPrime, ThreadHolder.this);
                }
            }, "Threader").start();
        }

        private void attach(CalcThreadedRightActivity activity) {
            mCurrentContext = activity;
        }

        private void detach() {
            mCurrentContext = null;
        }

        @Override
        public void foundPrime(final int count, final int prime) {
            // RACE CONDITION:
            // Since this is being called on a random thread, detach may have been called
            // resulting in a NullPointerException
            mCurrentContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCurrentContext.setOutputText("The " + count + "th prime is " + prime);
                }
            });
        }
    }
}
