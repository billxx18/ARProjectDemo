package project.demo.arprojectdemo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

public class StageNineActivity extends Activity implements SensorEventListener {
	int score, fish_random, fish_location_random, lastTime, emergeInterval;
	TunnelProgressTask Game9ProgressTask;
	private Rect mChangeImageBackgroundRect = null;
	ImageView[] img = new ImageView[6];
	FrameLayout frame;
	private boolean mRegisteredSensor;
	// 定义SensorManager
	private SensorManager mSensorManager;
	boolean match_state = false;
	int[] fish_location = new int[2];
	long INTERVAL = 50;
	final static long TIMEOUT = 0;
	long elapsed = 3000;
	Timer fish_emerge_timer, timer2;
	TimerTask fish_emerge_task;
	int moveHeight = 0;
	ImageView fish_img;
	int index = 0;
	int img_height = 500;
	int time = 0;
	int frame_width, frame_height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_nine);
		img[0] = (ImageView) findViewById(R.id.img0);
		img[1] = (ImageView) findViewById(R.id.img1);
		img[2] = (ImageView) findViewById(R.id.img2);
		img[3] = (ImageView) findViewById(R.id.img3);
		img[4] = (ImageView) findViewById(R.id.img4);
		img[5] = (ImageView) findViewById(R.id.img5);
		frame = (FrameLayout) findViewById(R.id.frame);
		frame_width = frame.getWidth();
		frame_height = frame.getHeight();

		img_height = img[0].getHeight();
		int img_width = img[0].getWidth();

		img[0].layout(frame_width / 2, frame_height - img_width, frame_width
				/ 2 + img[0].getWidth(),
				frame_height - img_height + img[0].getHeight());
		img[0].setVisibility(View.VISIBLE);
		// img[1].getLocationInWindow(fish_location);
		Game9ProgressTask = new TunnelProgressTask();
		// gameProgressTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		// gameProgressTask2 = new ddsGameProgressTask2();
		Game9ProgressTask.execute((Void) null);
		mRegisteredSensor = false;
		// 取得SensorManager实例
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class TunnelProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			for (score = 0; score < 30;) {
				if (isCancelled()) {
					return (null);
				}
				
				moveHeight=0;
				INTERVAL = 50;
				elapsed = 3000;
				fish_random = ((int) (Math.random() * 5) + 1);
				int frame_width = frame.getWidth();
				int fish_img_height = img[fish_random].getHeight();
				int fish_img_width = img[fish_random].getWidth();
				fish_location_random = ((int) (Math.random() * (frame_width-fish_img_width)) + 1);
				Log.e("img",  Integer.toString(frame_width) ); // 記錄目前位置
				
				fish_emerge_task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							fish_location_init(img[fish_random]);
							return;
						}
						fish_down(img[fish_random]);
					}
				};
				fish_emerge_timer = new Timer();
				// while (timeup == false) {
				fish_emerge_timer.scheduleAtFixedRate(fish_emerge_task,
						INTERVAL, INTERVAL);
				try {
					lastTime = 4000;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			StageNineActivity.this.finish();
		}

	}

	private boolean isInChangeImageZone(View view, int x, int y) {

		if (null == mChangeImageBackgroundRect) {
			mChangeImageBackgroundRect = new Rect();
		}
		view.getDrawingRect(mChangeImageBackgroundRect);

		int[] location_view = new int[2];
		view.getLocationOnScreen(location_view);

		mChangeImageBackgroundRect.left = location_view[0];
		mChangeImageBackgroundRect.top = location_view[1];
		mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right
				+ location_view[0];
		mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom
				+ location_view[1];

		return mChangeImageBackgroundRect.contains(x + 50, y + 50);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 接受SensorManager的一个列表(Listener)
		// 这里我们指定类型为TYPE_ORIENTATION(方向感应器)
		List<Sensor> sensors = mSensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);

		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			// 注册SensorManager
			// this->接收sensor的实例
			// 接收传感器类型的列表
			// 接受的频率
			mRegisteredSensor = mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
		}
	}

	protected void onPause() {
		if (mRegisteredSensor) {
			// 如果调用了registerListener
			// 这里我们需要unregisterListener来卸载/取消注册
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		super.onPause();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		// 接受方向感应器的类型
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			// 这里我们可以得到数据，然后根据需要来处理
			float z = event.values[SensorManager.DATA_Y];

			img[0].bringToFront();
			int y1 = (int) (z) * 10;
			int frame_width = frame.getWidth();
			int frame_height = frame.getHeight();
			int img_height = img[0].getHeight();
			int img_width = img[0].getWidth();

			img[0].layout(frame_width / 2 - y1, frame_height - img_height,
					(frame_width / 2 - y1) + img[0].getWidth(),
					(frame_height - img_height) + img[0].getHeight());

			int[] img_location = new int[2];
			LayoutParams params3 = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			img[0].getLocationInWindow(img_location);
			params3.setMargins(img_location[0], img_location[1], 0, 0);
			img[0].setLayoutParams(params3);
			// Log.e("img", location7[0] + "~~" + location7[1]); // 記錄目前位置

			// for (int i = 1; i <= 5; i++) {
			match_state = isInChangeImageZone(img[fish_random],
					img_location[0], img_location[1]);
			if (match_state == true) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result", true);
				setResult(RESULT_OK, returnIntent);
				finish();
				// break;
				// }
			}
		}

	}

	protected void onDestroy() {
		if ((Game9ProgressTask != null)
				&& (Game9ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			Game9ProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	public void fish_down(ImageView img) {
		fish_img = img;

		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				fish_img.setVisibility(View.VISIBLE);
				int frame_height = frame.getHeight();
				int fish_img_height = fish_img.getHeight();
				int fish_img_width = fish_img.getWidth();

				moveHeight = moveHeight + (frame_height)/55 ;
				fish_img.layout(fish_location_random, moveHeight, 0, 0);
				FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				params2.setMargins(fish_location_random, moveHeight, 0, 0);
				fish_img.setLayoutParams(params2);

			}
		});
	}

	public void fish_location_init(ImageView img) {
		fish_img = img;
		this.runOnUiThread(new Runnable() {
			int fish_img_height = fish_img.getHeight();
			int fish_img_width = fish_img.getWidth();
			int linear_height = frame.getHeight();

			@Override
			public void run() {

				// fish_img.layout(fish_location[0], 0,
				// fish_location[0] +
				// fish_img_width,linear_height-fish_img_height);
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				//
				params.setMargins(0, 0, 0, 0);
				//
				fish_img.setLayoutParams(params);
				// fish_img2.setLayoutParams(params);
				// fish_img3.setLayoutParams(params);
				// fish_img4.setLayoutParams(params);
				// fish_img5.setLayoutParams(params);
				fish_img.setVisibility(View.GONE);

			}
		});
	}

}
