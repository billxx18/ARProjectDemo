package project.demo.arprojectdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class StageTenActivity extends Activity implements SensorEventListener {
	private float xCurrentPos, yCurrentPos;
	int score, random, lastTime;
	ddsGameProgressTask gameProgressTask;
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private final float NOISE = (float) 2.0;
	int time = 0;
	ImageView[] img = new ImageView[5];
	LinearLayout frame;
	AnimationSet animationSet;
	Animation anim;
	TextView text;
	int go = 0;
	View img3;
	String text2;
	boolean state = true, index = false;

	int[] location8 = new int[2];
	int[] location9 = new int[2];
	long INTERVAL = 50;
	final static long TIMEOUT = 0;
	long elapsed = 1000;
	Timer timer, timer2;
	TimerTask task;
	int newWidth = 0, newHeight = 0;
	ImageView img2;
	private Rect mChangeImageBackgroundRect = null;
	FrameLayout row1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_ten);

		mInitialized = false;

		img[0] = (ImageView) findViewById(R.id.castle);
		img[1] = (ImageView) findViewById(R.id.ghost_left_1);
		img[2] = (ImageView) findViewById(R.id.ghost_left_2);
		img[3] = (ImageView) findViewById(R.id.ghost_right_1);
		img[4] = (ImageView) findViewById(R.id.ghost_right_2);
		frame = (LinearLayout) findViewById(R.id.frame);
		row1 = (FrameLayout) findViewById(R.id.row1);
		text = (TextView) findViewById(R.id.text);
		int height = frame.getHeight();
		int width = frame.getWidth();
		int height2 = img[3].getHeight();
		int width2 = img[3].getWidth();

		img[1].getLocationOnScreen(location8);
		img[3].getLocationOnScreen(location9);
		
		// random = ((int) (Math.random() * 2) + 1);
		// task = new TimerTask() {
		//
		// @Override
		// public void run() {
		//
		// elapsed = elapsed - INTERVAL;
		// if (elapsed <= TIMEOUT) {
		// this.cancel();
		// // sssss(img[random]);
		//
		// return;
		// }
		// // if(some other conditions)
		// // this.cancel();
		// // down2(img[random]);
		// change(img[random]);
		// }
		// };
		// timer = new Timer();
		// // while (timeup == false) {
		// timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		for (int i = 1; i < 4; i++) {
			img[i].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(random==1 ||random==2)
					{
					sssss(v);
					}
					else
					{
						sssss2(v);
					}
					state = false;
					elapsed = 0;

					// shakeView();
				}
			});
		}

		gameProgressTask = new ddsGameProgressTask();
		gameProgressTask.execute((Void) null);
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// can be safely ignored for this demo
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		TextView tvX = (TextView) findViewById(R.id.x_axis);
		TextView tvY = (TextView) findViewById(R.id.y_axis);
		TextView tvZ = (TextView) findViewById(R.id.z_axis);
		text = (TextView) findViewById(R.id.text);
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			// tvX.setText("0.0");
			// tvY.setText("0.0");
			// tvZ.setText("0.0");
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			tvX.setText(Float.toString(deltaX));
			tvY.setText(Float.toString(deltaY));
			tvZ.setText(Float.toString(deltaZ));
			if (deltaZ > 0) {
				// iv.setImageResource(R.drawable.horizontal);

				if (time < 40 && index == false) {
					time = time + 1;
					text.setText(String.valueOf(time));
				} else
					index = true;

			} else if (deltaY > deltaX) {
				// iv.setImageResource(R.drawable.vertical);
			} else {
				// iv.setVisibility(View.INVISIBLE);
			}
			shakeView();
		}
	}

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (time = 0; time < 40;) {
				if (isCancelled()) {
					return (null);
				}
				INTERVAL = 50;
				elapsed = 1500;
				state = true;
				go = 0; // Message msg = new Message();
				random = ((int) (Math.random() * 4) + 1);
				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							if (state) {
								time = time - 10;
							}
							sssss(img[random]);

							return;
						}
						// if(some other conditions)
						// this.cancel();
						// down2(img[random]);
						if (random == 1 || random == 2) {
							change(img[random]);
						} else
							change2(img[random]);
						;
					}
				};
				timer = new Timer();
				// while (timeup == false) {
				timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);

				// change(random);

				// msg.what = random;
				// ddsHandler.sendMessage(msg);

				// try {
				// lastTime = 1000;
				// Thread.sleep(lastTime);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }

				// Message hit = new Message();
				// hit.what = random * 1000 + 1;
				// ddsHandler.sendMessage(hit);
				try {
					lastTime = 2000;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// int[] location7 = new int[2];
				// img[0].getLocationInWindow(location7);
				// for (int i = 1; i <= 5; i++) {
				// state = isInChangeImageZone(img[i], location7[0], 0);
				// if (state == true) {
				// // Toast.makeText(MainActivity.this, "Oops",
				// // Toast.LENGTH_SHORT).show();// toast有显示时间延迟
				// MainActivity.this.finish();
				// state = false;
				// }
				// }

				// change2(random);
				// Message msgResponse = new Message();
				// msgResponse.what = random * 100;
				// ddsHandler.sendMessage(msgResponse);
				// try {
				// emergeInterval = 600;
				// Thread.sleep(emergeInterval);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			finish2();
		}

	}

	private void shakeView() {
		if (time < 10) {
			img[0].setBackgroundResource(R.drawable.castle);
		} else if (time >= 10 && time < 15) {
			img[0].setBackgroundResource(R.drawable.castle2);
		} else if (time >= 15 && time < 20) {
			img[0].setBackgroundResource(R.drawable.castle3);
		} else if (time >= 20 && time < 25) {
			img[0].setBackgroundResource(R.drawable.castle4);
		} else if (time >= 25 && time < 30) {
			img[0].setBackgroundResource(R.drawable.castle5);
		} else if (time >= 30 && time < 35) {
			img[0].setBackgroundResource(R.drawable.castle6);
		} else if (time >= 40) {
			img[0].setBackgroundResource(R.drawable.castle7);
		}

	}

	private boolean isInChangeImageZone(View view, int x, int y) {

		if (null == mChangeImageBackgroundRect) {
			mChangeImageBackgroundRect = new Rect();
		}
		view.getDrawingRect(mChangeImageBackgroundRect);

		int[] location = new int[2];
		view.getLocationOnScreen(location);

		mChangeImageBackgroundRect.left = location[0];
		mChangeImageBackgroundRect.top = location[1];
		mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right
				+ location[0];
		mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom
				+ location[1];
		Log.e("view", location[0] + "~~" + location[1]); // 記錄目前位置

		return mChangeImageBackgroundRect.contains(x + 50, y + 50);
	}

	protected void onDestroy() {
		if ((gameProgressTask != null)
				&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			gameProgressTask.cancel(true);
		}
		super.onDestroy();
	}


	public void change(ImageView img) {
		img2 = img;

		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				img2.setVisibility(View.VISIBLE);
				img2.setBackgroundResource(R.drawable.ghost_left);
				int height2 = img2.getHeight();
				int width2 = img2.getWidth();
				int height = frame.getHeight();
				int width = frame.getWidth();

				go = go + (width / 2 - width2) / 30;
//				img2.layout(0 + go, 0, 0 + go + width2, 0 + height2);
				shakeView();
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

				params.setMargins(0 + go, 0, 0, 0);
				// OR
				// params.topMargin = 100;
				img2.setLayoutParams(params);
			}
		});

	}

	public void change2(ImageView img) {

		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				img2.setVisibility(View.VISIBLE);
				img2.setBackgroundResource(R.drawable.ghost_right);
				int height2 = img2.getHeight();
				int width2 = img2.getWidth();
				int height = frame.getHeight();
				int width = frame.getWidth();

				go = go - (width / 2 - width2) / 30;
//				img2.layout((width-width2) + go, 0, (width-width2) + go + width2, 0 + height2);
				shakeView();
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

				params.setMargins((width-width2) + go, 0, 0, 0);
				// OR
				// params.topMargin = 100;
				img2.setLayoutParams(params);
			}
		});

	}

	public void sssss(View img) {
		img3 = img;
		this.runOnUiThread(new Runnable() {
			int height2 = img3.getHeight();
			int width2 = img3.getWidth();
			int height = frame.getHeight();
			int width = frame.getWidth();

			@Override
			public void run() {
				img3.setVisibility(View.GONE);
				img3.layout(location8[0], location8[1], location8[0] + width2,
						location8[1] + height2);
				text.setText(String.valueOf(time));
				shakeView();
			}

		});

	}

	public void sssss2(View img) {
		img3 = img;
		this.runOnUiThread(new Runnable() {
			int height2 = img3.getHeight();
			int width2 = img3.getWidth();
			int height = frame.getHeight();
			int width = frame.getWidth();
			
			@Override
			public void run() {
				img3.setVisibility(View.GONE);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

				params.setMargins((width-width2) + go, 0, 0, 0);
				// OR
				// params.topMargin = 100;
				img2.setLayoutParams(params);
				shakeView();
			}
		});
	}

	public void finish2() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
		Toast.makeText(this, "Mission Copmlete", Toast.LENGTH_SHORT).show();

	}
}