package edu.ggc.epatter1.morsecodetransmitter;

import android.graphics.Color;
import android.hardware.Camera;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by epatter1 on 4/7/2016.
 */
public class TransmitTask extends AsyncTask<String, Integer, Integer>
{
   private TextView textView;
    private String stringToTransmit;
//    private Camera camera = Camera.open();

    TransmitTask(TextView _t) {
        textView = _t;
    }
    @Override
    protected Integer doInBackground(String... params) {
        stringToTransmit = params[0];
        LinkedList<Signal> signals = MorseCode.genOnOffSchedule(stringToTransmit, 2000);

        for (int i = 0; i < signals.size(); i++) {
            Signal signal = signals.get(i);
            Log.d("TransmitTask", "Audio Duration: " + signal.getDuration());

            if(signal.isOn()) {
                publishProgress(1);
                AudioTrack tone = AudioUtils.generateTone(440, signal.getDuration());
                tone.play();
            } else {
                publishProgress(0);
            }

            try {
                Thread.sleep(signal.getDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return 1;
    }


    //    @Override
//    protected Void doInBackground(String... msg) {
//
//        Log.v(TAG, "running doInBackground() ....");
//        if (hasFlash) camera = Camera.open();
//        stringToTransmit = msg[0];
//        int defer = 500;
//        LinkedList<Signal> schedule = MorseCode.genOnOffSchedule(stringToTransmit, defer);
//        long begin = System.currentTimeMillis();
//
//        while (!schedule.isEmpty()) {
//            Signal signal = schedule.removeFirst();
//            boolean arrival = false;
//            while(!arrival)
//                arrival = (System.currentTimeMillis() - begin) > signal.getOnset();
//            updateUI(signal);
//        }
//        return null;
//    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        int textViewOn = progress[0];

        if(textViewOn == 1) {
            textView.setBackgroundColor(Color.WHITE);
        } else {
            textView.setBackgroundColor(Color.BLACK);
        }

    }

    private void turnOnCameraFlash() {

//        Parameters p = cam.getParameters();
//        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
//        cam.setParameters(p);
//        cam.startPreview();
    }

    private void turnOffCameraFlash() {

    }
}
