package com.example.spostamento_umano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor mSensor;
    private Button bttStart = null, bttStop = null;

    private TextView textCount = null;
    private SensorEventListener sensorListener = null;
    private TextView Doing=null;


    // NECESSITA' : Foreground Task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttStop = findViewById(R.id.bttStop);
        textCount = findViewById(R.id.Move);
        Doing = findViewById(R.id.Doing);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if ((mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) == null)
            Log.i(TAG, "Accellerometer not found");

        sensorListener = new MoveDetector(sensorManager,textCount,Doing);

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(sensorListener); // se attaccato a altri sensori posso specificare solo 1

            }
        });

        bttStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(sensorListener); // se attaccato a altri sensori posso specificare solo 1


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL); // ogni quanto prendere i dati
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }

}