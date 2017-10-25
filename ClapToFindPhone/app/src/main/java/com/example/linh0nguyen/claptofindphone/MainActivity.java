package com.example.linh0nguyen.claptofindphone;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public String FILE_PATH = Environment.getExternalStorageDirectory() + "/audio.3gp";

    TextView tvResult;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RecordAmplitudeTask().execute(new SingleDetectClap(SingleDetectClap.DEFAULT_AMPLITUDE_DIFF));
            }
        });
    }

    private void addControls() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnStart = (Button) findViewById(R.id.btnStart);
        File file = new File(FILE_PATH);
        if(file.exists()){
            file.delete();
        }
    }




    public class RecordAmplitudeTask extends AsyncTask<AmplitudeClipListener, Void, Boolean> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvResult.setText("Detecting clap .....");
        }

        @Override
        protected Boolean doInBackground(AmplitudeClipListener... listeners) {
            if(listeners.length == 0){
                return false;
            }
            AmplitudeClipListener listener = listeners[0];

            MaxAmplitudeRecoder recorder = new MaxAmplitudeRecoder(MaxAmplitudeRecoder.DEFAULT_CLIP_TIME, FILE_PATH, listener, this, getApplicationContext());

            boolean heard = false;
            try{

                heard = recorder.startRecording();

            } catch (IllegalStateException is){
                Log.e("IS", is + "");
                heard = false;
            }catch (RuntimeException re){
                Log.e("RE", re + "");
                heard = false;
            }



            return heard;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Log.d("result", result+"");
            if(result){
                tvResult.setText("Heard Clap");
            }
        }

        @Override
        protected void onCancelled() {
            setDoneMessage();
            super.onCancelled();
        }

        private void setDoneMessage() {
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
        }
    }


}
