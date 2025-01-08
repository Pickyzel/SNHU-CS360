package com.assignment.assignmentsix_tiffanymcdonnell;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.hardware.SensorEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private ImageView compassPNG;

    private float DegreeStart = 0f;

    TextView DegreeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DegreeTV = findViewById(R.id.DegreeTV);
        compassPNG = findViewById(R.id.compassPicture);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);

        DegreeTV.setText(getString(R.string.heading, Float.toString(degree)));

        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        ra.setFillAfter(true);

        ra.setDuration(210);

        compassPNG.startAnimation(ra);
        DegreeStart = -degree;

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    //tutorial from - https://www.androidcode.ninja/android-compass-code-example/
}