package project.demo.arprojectdemo;

import java.io.IOException;
import java.util.List;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	boolean state, index = false, index4 = false;
	private Rect mChangeImageBackgroundRect = null;

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
			String x1 = Float.toString(x);
			String y1 = Float.toString(y);
			String z1 = Float.toString(z);
			tv1.setText(x1);
			tv2.setText(y1);
			tv3.setText(z1);
			Intent intent = getIntent();
			String game = intent.getStringExtra("game");
			int games = Integer.parseInt(game);
			switch (games) {
			case 1:
				if (x > 200 && x < 250 && y < 5 && y > -5 && z > 70 && z < 90
						&& index == false) {
					//
					controlInflater = LayoutInflater.from(getBaseContext());
					viewControl = controlInflater.inflate(
							R.layout.activity_stage_one, null);
					LayoutParams layoutParamsControl = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					this.addContentView(viewControl, layoutParamsControl);
					index = true;
					img = (ImageView) findViewById(R.id.image);
					img.setOnTouchListener(imgListener);
					break;
				}
			case 4:
				if (x > 200 && x < 250 && y < 5 && y > -5 && z > 70 && z < 90
						&& index4 == false) {
					Intent intent4 = new Intent(ARcamera.this,
							StageFourActivity.class);
					startActivityForResult(intent4, 4);
					index4 = true;
					ARcamera.this.finish();

				}

			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	private OnTouchListener imgListener = new OnTouchListener() {
		private float x, y; // 原本圖片存在的X,Y軸位置
		private int mx, my; // 圖片被拖曳的X ,Y軸距離長度

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			// 按下圖片時
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				// 移動圖片時
			case MotionEvent.ACTION_MOVE:
				mx = (int) (event.getRawX() - x);
				my = (int) (event.getRawY() - y); // 50應該是標題框長度
				v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
				break;
			}
			ImageView target = (ImageView) findViewById(R.id.target);
			int[] location2 = new int[2];
			img.getLocationOnScreen(location2);
			if (event.getAction() == MotionEvent.ACTION_UP) {

				Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my)); // 記錄目前位置
				// 获取在当前窗口内的绝对坐标
				Log.i("image", location2[0] + "~~" + location2[1]);
				state = isInChangeImageZone(target, location2[0], location2[1]);
				if (state == true) {
					ARcamera.this.finish();
				}
			}
			return true;
		}
	};

	private boolean isInChangeImageZone(View view, int x, int y) {

		if (null == mChangeImageBackgroundRect) {
			mChangeImageBackgroundRect = new Rect();
		}
		view.getDrawingRect(mChangeImageBackgroundRect);

		int[] location = new int[2];
		view.getLocationInWindow(location);

		mChangeImageBackgroundRect.left = location[0];
		mChangeImageBackgroundRect.top = location[1];
		mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right
				+ location[0];
		mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom
				+ location[1];
		Log.e("view", location[0] + "~~" + location[1]); // 記錄目前位置

		return mChangeImageBackgroundRect.contains(x + 50, y + 50);
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