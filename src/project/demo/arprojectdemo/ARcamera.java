package project.demo.arprojectdemo;

import java.io.IOException;
import java.util.List;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ARcamera extends Activity implements SurfaceHolder.Callback,
SensorEventListener {
private ImageView img;
Camera camera;
SurfaceView surfaceView;
SurfaceHolder surfaceHolder;
boolean previewing = false;
LayoutInflater controlInflater = null;
private SensorManager mSensorManager;
private boolean mRegisteredSensor;
View viewControl;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_arcamera);

getWindow().setFormat(PixelFormat.UNKNOWN);
surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
surfaceHolder = surfaceView.getHolder();
surfaceHolder.addCallback(this);
surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
// 取得系統定位服務
}

public void surfaceChanged(SurfaceHolder holder, int format, int width,
	int height) {
// TODO Auto-generated method stub
if (previewing) {
	camera.stopPreview();
	previewing = false;
}

if (camera != null) {
	try {
		camera.setPreviewDisplay(surfaceHolder);
		camera.startPreview();
		previewing = true;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

@Override
public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
camera = Camera.open();
}

@Override
public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
camera.stopPreview();
camera.release();
camera = null;
previewing = false;
}

public void onSensorChanged(SensorEvent event) {
if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
	// 这里我们可以得到数据，然后根据需要来处理
	float x = event.values[SensorManager.DATA_X];
	float y = event.values[SensorManager.DATA_Y];
	float z = event.values[SensorManager.DATA_Z];

	TextView tv1 = (TextView) findViewById(R.id.x);
	TextView tv2 = (TextView) findViewById(R.id.y);
	TextView tv3 = (TextView) findViewById(R.id.z);
	// TextView msg = (TextView) findViewById(R.id.msg);
	String x1 = Float.toString(x);
	String y1 = Float.toString(y);
	String z1 = Float.toString(z);
	tv1.setText(x1);
	tv2.setText(y1);
	tv3.setText(z1);
	// controlInflater = LayoutInflater.from(getBaseContext());
	// View viewControl = controlInflater.inflate(R.layout.control2,
	// null);
	if (x > 200 && x < 250 && y < 5 && y > -5 && z > 70 && z < 90) {
//
//		 controlInflater = LayoutInflater.from(getBaseContext());
//		 viewControl = controlInflater.inflate(R.layout.event1,
//		 null);
//		 LayoutParams layoutParamsControl = new LayoutParams(
//		 LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		 this.addContentView(viewControl, layoutParamsControl);
	}
}
}

public void onAccuracyChanged(Sensor sensor, int accuracy) {

}

protected void onResume() {
// // TODO Auto-generated method stub
super.onResume();
List<Sensor> sensors = mSensorManager
		.getSensorList(Sensor.TYPE_ORIENTATION);

if (sensors.size() > 0) {
	Sensor sensor = sensors.get(0);
	// // 注册SensorManager
	// // this->接收sensor的实例
	// // 接收传感器类型的列表
	// // 接受的频率
	mRegisteredSensor = mSensorManager.registerListener(this, sensor,
			SensorManager.SENSOR_DELAY_FASTEST);
	// }
}
}

// @Override
protected void onPause() {

if (mRegisteredSensor) {
	// 如果调用了registerListener
	// 这里我们需要unregisterListener来卸载/取消注册
	mSensorManager.unregisterListener(this);
	mRegisteredSensor = false;
}
super.onPause();
}
}