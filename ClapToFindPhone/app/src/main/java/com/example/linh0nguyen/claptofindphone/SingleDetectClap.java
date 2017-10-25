package com.example.linh0nguyen.claptofindphone;

import android.util.Log;

/**
 * Created by Linh(^0^)Nguyen on 10/25/2017.
 */

public class SingleDetectClap implements AmplitudeClipListener {
    public static final int AMPLITUDE_DIFF_LOW = 10000;
    public static final int AMPLITUDE_DIFF_MED = 18000;
    public static final int AMPLITUDE_DIFF_HIGH = 25000;

    public static final int DEFAULT_AMPLITUDE_DIFF = AMPLITUDE_DIFF_HIGH;
    public int maxAmp;
    public SingleDetectClap(int maxAmp){
        this.maxAmp = maxAmp;
    }
    @Override
    public boolean heard(int maxAmplitude) {
        boolean result = false;
        if(maxAmp >= maxAmplitude){
            Log.d("Clap", result+"");
            result = true;
        }
        return result;
    }
}
