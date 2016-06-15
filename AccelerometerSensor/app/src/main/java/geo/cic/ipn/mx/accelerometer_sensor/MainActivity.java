package geo.cic.ipn.mx.accelerometer_sensor;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends Activity implements SensorEventListener {
    private boolean color = false;
    private int minAcceleration = 2;
    private int minTimeUpdate = 200;
    private SensorManager sensorManager;
    private long lastUpdate;
    private View view;
    private boolean firstUpdate = true;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.textView);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];


        long tiempo = System.currentTimeMillis();

        if(tiempo-lastUpdate > 1000) {
            lastUpdate = System.currentTimeMillis();
            Log.i("muestra: ", "X: " + x * 1000 + " Y:" + y * 1000 + " Z:" + z * 1000 + " clase: " + RestApi.obtenerClase(x, y, z, 7));


            String texto = "";
            switch (RestApi.obtenerClase(x, y, z, 7)) {
                case "3":
                    texto = "parado";
                    break;
                case "4":
                    texto = "caminando";
                    break;
                case "5":
                    texto = "subiendo o bajando escaleras";
                    break;
                case "6":
                    texto = "caminando y hablando con alguien";
                    break;
                case "7":
                    texto = "hablandomientras estas parado";
                    break;
                case "-1":
                    texto = "sin servicio de internet";
                    break;

            }
            ((TextView) view).setText(texto);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
