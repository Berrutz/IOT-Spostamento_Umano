package com.example.spostamento_umano;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.ArrayList;

public class MoveDetector implements SensorEventListener{

    private TextView Doing=null;
    private TextView TextCount=null;
    private SensorManager sensormanager=null;
    private double PrevMagnitude = 0;
    private Integer counter=0;
    //private ArrayList<Float[]> accValues = null;
    // private ArrayList<Float[]> accValues = null;

    public MoveDetector(SensorManager sensorManager, TextView textCount){
        sensormanager=sensorManager;
        TextCount = textCount;
    }

    public MoveDetector(SensorManager sensorManager, TextView textCount, TextView doing) {
        sensormanager=sensorManager;
        TextCount = textCount;
        Doing = doing;
    }


    @Override
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

            TextCount.setText(counter.toString());
            if(Delta > 10){
                counter++;
                Doing.setText(R.string.Running);
            }else if(Delta > 6 ) {
                counter++;
                Doing.setText(R.string.Walking);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do something here if sensor accuracy changes.
    }


}
