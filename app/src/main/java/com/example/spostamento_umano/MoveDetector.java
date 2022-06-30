package com.example.spostamento_umano;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

public class MoveDetector implements SensorEventListener{

    private final String TAG = "MoveDetector";
    private TextView Doing=null;
    private TextView TextCount=null;
    private SensorManager sensormanager=null;
    private double PrevMagnitude = 0;
    private Integer counter=0;
    //private ArrayList<Float[]> accValues = null;
    // private ArrayList<Float[]> accValues = null;

    /****** new *******/

    private float[] mGravity;
    private double mAccel= 0.00f;;
    private double mAccelCurrent=SensorManager.GRAVITY_EARTH;
    private double mAccelLast= SensorManager.GRAVITY_EARTH;

    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private final int SAMPLE_SIZE = 25; // change this sample size as you want, higher is more precise but slow measure.
    private final double THRESHOLD_WALKING = 0.3; // change this threshold as you want, higher is more spike movement
    private final double THRESHOLD_RUNNING = 2.2; // change this threshold as you want, higher is more spike movement


    public MoveDetector(SensorManager sensorManager, TextView textCount){
        sensormanager=sensorManager;
        TextCount = textCount;
    }

    public MoveDetector(SensorManager sensorManager, TextView textCount, TextView doing) {
        sensormanager=sensorManager;
        TextCount = textCount;
        Doing = doing;
    }


    /*@Override
    public void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        if(event != null) {
            float _x = event.values[0];
            float _y = event.values[1];
            float _z = event.values[2];

            double Magnitude = Math.sqrt( Math.pow(_x, 2) +  Math.pow(_y, 2) +  Math.pow(_z, 2));
            double Delta = Magnitude - PrevMagnitude;
            PrevMagnitude = Magnitude;

            Log.i(TAG,"Changing TextCount");
            TextCount.setText(counter.toString());
            if(Delta > 10){
                counter++;
                Doing.setText(R.string.Running);
            }else if(Delta > 6 ) {
                counter++;
                Doing.setText(R.string.Walking);
            }
        }

    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            double x = mGravity[0];
            double y = mGravity[1];
            double z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
                //TextCount.setText(counter.toString());
                Log.i(TAG, "Sampling information");
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                Log.i(TAG, String.valueOf(hitResult));
                TextCount.setText(String.valueOf(hitResult));

                if(hitResult > THRESHOLD_RUNNING){
                    Log.i(TAG, "Running");
                    Doing.setText(R.string.Running);
                }else if (hitResult > THRESHOLD_WALKING) {
                    Log.i(TAG, "Walking");
                    Doing.setText(R.string.Walking);
                } else {
                    Log.i(TAG, "Stop Walking");
                    Doing.setText(R.string.Sitting);
                }

                hitCount = 0;
                hitSum = 0;
                hitResult = 0;

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do something here if sensor accuracy changes.
    }


}
