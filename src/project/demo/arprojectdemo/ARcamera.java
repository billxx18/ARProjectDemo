package project.demo.arprojectdemo;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
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
	boolean state, index = false;
	private Rect mChangeImageBackgroundRect = null;
	long INTERVAL = 1000;
	final static long TIMEOUT = 0;
	long elapsed = 10000;
	Timer timer, timer2;
	TimerTask task;
	int random, lastTime, emergeInterval;
	TextView time_text;
	Boolean time_state;

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
			String game = getIntent().getStringExtra("game");
			// Boolean stage_eight_state = getIntent().getExtras().getBoolean(
			// "state");
			int games = Integer.parseInt(game);
			// Toast.makeText(this, game, Toast.LENGTH_SHORT).show();
			switch (games) {
			case 1:
				if (x > 17 && x < 77 && (y < -170 || y > 170) && z > 20
						&& z < 70 &&index==false) {
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

				}
				break;
			case 3:
				if (x > 75 && x < 125 && (y < 5 || y > 170) && z > 60 && z < 90
						&& index == false) {
					Intent intent3 = new Intent(ARcamera.this,
							StageThreeActivity.class);
					startActivityForResult(intent3, 3);
					index = true;

				}
				break;
			case 4:
				if (x > 256 && x < 316 && (y < 5 || y > 170) && z > 60
						&& z < 80 && index == false) {
					Intent intent4 = new Intent(ARcamera.this,
							StageFourActivity.class);
					startActivityForResult(intent4, 4);
					index = true;

				}
				break;
			case 5:
				if (x > 61 && x < 126 && (y < 5 || y > 170) && z > 53
						&& z < 113 && index == false) {
					Intent intent5 = new Intent(ARcamera.this,
							StageFiveActivity.class);
					startActivityForResult(intent5, 5);
					index = true;
				}
				break;
			case 6:
				if (x > 254 && x < 314 && (y < 5 || y > 170) && z > 56
						&& z < 116 && index == false) {
					Intent intent6 = new Intent(ARcamera.this,
							StageSixCamera.class);
					startActivityForResult(intent6, 6);
					index = true;
				}
				break;
			case 7:
				if (x > 59 && x < 119 && y < 10 && y > -10 && z > 20 && z < 55
						&& index == false) {

					Intent intent7 = new Intent(ARcamera.this,
							StageSevenActivity.class);
					startActivityForResult(intent7, 7);
					index = true;
				}
				break;

			case 8:
				if (((x > 74 && x < 114) || (x > 177 && x < 237))
						&& (y < 5 || y > 170) && z > 65 && z < 90
						&& index == false) {
					Intent intent8 = new Intent(ARcamera.this,
							StageEightActivity.class);
					intent8.putExtra("state", false);
					startActivityForResult(intent8, 8);
					index = true;
				}
				break;
			case 88:
				if (((x > 74 && x < 114) || (x > 177 && x < 237))
						&& (y < 5 || y > 170) && z > 65 && z < 90
						&& index == false) {
					Intent intent8 = new Intent(ARcamera.this,
							StageEightActivity.class);
					intent8.putExtra("state", true);
					startActivityForResult(intent8, 8);
					index = true;
				}
				break;
			case 9:

				break;
			case 10:
				if (x > 28 && x < 88 && (y < 5 || y > 170) && z > 51 && z < 111
						&& index == false) {
					Intent intent10 = new Intent(ARcamera.this,
							StageTenActivity.class);
					startActivityForResult(intent10, 10);
					index = true;
				}
				break;
			case 11:
				if (((x > 235 && x < 295) && (y < 5 || y > 170) && (z > 47 && z < 107))
						|| (x > 250 && x < 310 && (y < 5 || y > 170) && z > 25 && z < 60)
						&& index == false) {
					Intent intent11 = new Intent(ARcamera.this,
							StageElevenActivity.class);
					startActivityForResult(intent11, 11);
					index = true;
				}
				break;
			case 12:
				if (x > 46 && x < 86 && (y < 5 || y > 170) && z > 31 && z < 91
						&& index == false) {
					Intent intent12 = new Intent(ARcamera.this,
							StageTwelveActivity.class);
					startActivityForResult(intent12, 12);
					index = true;
				}
				break;
			default:
				break;
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

					Intent returnIntent = new Intent();
					returnIntent.putExtra("result", true);
					setResult(RESULT_OK, returnIntent);
					finish();
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

	@Override
	protected void onPause() {

		if (mRegisteredSensor) {
			// 如果调用了registerListener
			// 这里我们需要unregisterListener来卸载/取消注册
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		super.onDestroy();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				break;
			case 2:
				break;
			case 3:

				Intent returnIntent3 = new Intent();
				returnIntent3.putExtra("result", true);
				setResult(RESULT_OK, returnIntent3);
				finish();

				// stageAvailability[2] = false;
				// Toast.makeText(this, "All Stages Completed!",
				// Toast.LENGTH_SHORT)
				// .show();
				// Intent toResultIntent = new Intent();
				// toResultIntent.setClass(GameProgressActivity.this,
				// ResultActivity.class);
				// startActivity(toResultIntent);
				// GameProgressActivity.this.finish();

				break;
			case 4:
				Intent returnIntent4 = new Intent();
				returnIntent4.putExtra("result", true);
				setResult(RESULT_OK, returnIntent4);
				finish();
				break;
			case 5:
				Intent returnIntent5 = new Intent();
				returnIntent5.putExtra("result", true);
				setResult(RESULT_OK, returnIntent5);
				finish();
				break;
			case 6:
				Intent returnIntent6 = new Intent();
				returnIntent6.putExtra("result", true);
				setResult(RESULT_OK, returnIntent6);
				finish();
				break;
			case 7:
				// stage_eight_state = true;
				// Intent intent = new Intent(GameProgressActivity.this,
				// ARcamera.class);
				// intent.putExtra("game", "8");
				// intent.putExtra("state", stage_eight_state);
				// startActivityForResult(intent, 8);
				Intent returnIntent7 = new Intent();
				returnIntent7.putExtra("result", true);
				setResult(RESULT_OK, returnIntent7);
				finish();
				// if (resultCode == RESULT_OK) {
				// Boolean stage_eight_state = data.getExtras().getBoolean(
				// "result");
				// }
				break;
			case 8:
				Intent returnIntent8 = new Intent();
				returnIntent8.putExtra("result", true);
				setResult(RESULT_OK, returnIntent8);
				finish();
				break;
			case 88:
				Intent returnIntent88 = new Intent();
				returnIntent88.putExtra("result", true);
				setResult(RESULT_OK, returnIntent88);
				finish();
				break;
			case 9:
				break;
			case 10:
				Intent returnIntent10 = new Intent();
				returnIntent10.putExtra("result", true);
				setResult(RESULT_OK, returnIntent10);
				finish();
				break;
			case 11:
				Intent returnIntent11 = new Intent();
				returnIntent11.putExtra("result", true);
				setResult(RESULT_OK, returnIntent11);
				finish();
				break;
			case 12:
				Intent returnIntent12 = new Intent();
				returnIntent12.putExtra("result", true);
				setResult(RESULT_OK, returnIntent12);
				finish();
				break;
			default:

				break;
			}
		}
		index = false;
	}
}