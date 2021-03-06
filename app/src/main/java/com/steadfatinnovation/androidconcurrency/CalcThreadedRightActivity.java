package com.steadfatinnovation.androidconcurrency;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;
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
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            if (mThreadHolder != null) {
                mThreadHolder.detach();
            }
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
        private final Handler mainHandler;

        private ThreadHolder(CalcThreadedRightActivity activity, final int nthPrime, final boolean pauseOnPrime) {
            this.mCurrentContext = activity;
            this.mainHandler = new Handler(Looper.getMainLooper());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    CalcThreadedRightActivity.doCalc(nthPrime, pauseOnPrime, CalcThreadedRightActivity.ThreadHolder.this);
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
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mCurrentContext != null) {
                        if (mCurrentContext.isDestroyed()) {
                            throw new IllegalStateException("This should never happen if attach and detach are being called in the appropriate places.");
                        }
                        mCurrentContext.setOutputText("The " + count + "th prime is " + prime);
                    } else {
                        // Ignore the Activity is finished
                        Log.e("Missed Events", "These missing events should only happen when the activity was finished");
                    }
                }
            });
        }
    }
}
