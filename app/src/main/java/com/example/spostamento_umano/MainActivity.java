package com.example.spostamento_umano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mSensor;
    private Button bttStart =null , bttStop=null;


    // NECESSITA' : Foreground Task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttStop = findViewById(R.id.bttStop);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if ((mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) != null){
            List<Sensor> gravSensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER); // lista sensori di tipo
            for(int i=0; i<gravSensors.size(); i++) {
                if ((gravSensors.get(i).getVendor().contains("Google LLC")) && (gravSensors.get(i).getVersion() == 3)){
                    // Use the version 3 gravity sensor.
                    mSensor = gravSensors.get(i);
                    String vendor = mSensor.getVendor();
                }
            }
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float val1 = event.values[0];
        float val2 = event.values[1];
        float val3 = event.values[2];

        // Do something with this sensor value.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL); // ogni quanto prendere i dati
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}