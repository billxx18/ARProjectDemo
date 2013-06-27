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
	int score, ghost_random, ghost_location_random, lastTime;
	GhostShakeProgressTask Game10ProgressTask;
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private final float NOISE = (float) 2.0;
	int time = 0;
	ImageView[] img = new ImageView[5];
	FrameLayout frame;
	AnimationSet animationSet;
	Animation anim;
	TextView text;
	int ghost_move = 0;
	View ghost_viewimg;
	String text2;
	boolean state = true, index = false;
	int img_height, img_width;
	int[] location8 = new int[2];
	int[] location9 = new int[2];
	long INTERVAL = 50;
	final static long TIMEOUT = 0;
	long elapsed = 1500;
	Timer timer, timer2;
	TimerTask task;
	int newWidth = 0, newHeight = 0;
	ImageView ghost_img;
	private Rect mChangeImageBackgroundRect = null;
	int count = 0;
	int frame_width, frame_height;

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
		frame = (FrameLayout) findViewById(R.id.frame);
		text = (TextView) findViewById(R.id.text);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		for (int i = 1; i < 5; i++) {
			img[i].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					onclick_ghost_init(v);
					state = false;
					elapsed = 0;

					// shakeView();
				}
			});
		}

		Game10ProgressTask = new GhostShakeProgressTask();
		Game10ProgressTask.execute((Void) null);
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
			if (deltaZ > 5) {
				// iv.setImageResource(R.drawable.horizontal);

				if (time < 40 && index == false) {
					time = time + 1;

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

	private class GhostShakeProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (time = 0; time < 40;) {
				if (isCancelled()) {
					return (null);
				}
				INTERVAL = 50;
				elapsed = 1500;
				state = true;
				ghost_move = 0; // Message msg = new Message();
				count = 0;
				ghost_random = ((int) (Math.random() * 4) + 1);
				ghost_location_random = ((int) (Math.random() * (frame_height-70)) + 1);
				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							if (state) {
								time = time - 10;
							}
							ghost_init(img[ghost_random]);

							return;
						}
						// if(some other conditions)
						// this.cancel();
						// down2(img[random]);
						count = count + 1;
						if (ghost_random == 1 || ghost_random == 2) {
							ghost_left_move(img[ghost_random]);
						} else
							ghost_right_move(img[ghost_random]);
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
			game_finish();
		}

	}

	private void shakeView() {
		if (time < 10) {
			img[0].setBackgroundResource(R.drawable.castle);
			img_height = img[0].getHeight();
			img_width = img[0].getWidth();
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins((frame_width - img_width) / 2, 0, 0, 0);
			img[0].setLayoutParams(params);
			text.setText(String.valueOf(time));
		} else if (time >= 10 && time < 15) {
			img[0].setBackgroundResource(R.drawable.castle2);
			text.setText(String.valueOf(time));
		} else if (time >= 15 && time < 20) {
			img[0].setBackgroundResource(R.drawable.castle3);
			text.setText(String.valueOf(time));
		} else if (time >= 20 && time < 25) {
			img[0].setBackgroundResource(R.drawable.castle4);
			text.setText(String.valueOf(time));
		} else if (time >= 25 && time < 30) {
			img[0].setBackgroundResource(R.drawable.castle5);
			text.setText(String.valueOf(time));
		} else if (time >= 30 && time < 35) {
			img[0].setBackgroundResource(R.drawable.castle6);
			text.setText(String.valueOf(time));
		} else if (time >= 40) {
			img[0].setBackgroundResource(R.drawable.castle7);
			text.setText(String.valueOf(time));
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
		if ((Game10ProgressTask != null)
				&& (Game10ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			Game10ProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	public void ghost_left_move(ImageView img) {
		ghost_img = img;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ghost_img.setVisibility(View.VISIBLE);
				int ghost_img_height = ghost_img.getHeight();
				int ghost_img_width = ghost_img.getWidth();
				// int frame_width = frame.getWidth();
				// int frame_height = frame.getHeight();

				

				Log.e("time", Integer.toString(ghost_location_random));
				ghost_move = ghost_move + (frame_width / 2 - ghost_img_width)
						/ 29;

				shakeView();
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);

				params.setMargins(0 + ghost_move, ghost_location_random, 0, 0);
				ghost_img.setLayoutParams(params);
			}
		});

	}

	public void ghost_right_move(ImageView img) {
		ghost_img = img;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ghost_img.setVisibility(View.VISIBLE);
				int ghost_img_width = ghost_img.getWidth();
				int ghost_img_height = ghost_img.getHeight();
//				ghost_location_random = ((int) (Math.random() * (frame_height - ghost_img_height)) + 1);

				ghost_move = ghost_move - (frame_width / 2 - ghost_img_width)
						/ 29;
				shakeView();
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);

				params.setMargins((frame_width - ghost_img_width) + ghost_move,
						ghost_location_random, 0, 0);

				ghost_img.setLayoutParams(params);
			}
		});

	}

	public void ghost_init(ImageView img) {
		ghost_img = img;
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ghost_img.setVisibility(View.GONE);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				params.setMargins(0, 0, 0, 0);
				ghost_img.setLayoutParams(params);
				shakeView();
			}

		});

	}

	public void onclick_ghost_init(View img) {
		ghost_viewimg = img;
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ghost_viewimg.setVisibility(View.GONE);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				params.setMargins(0, 0, 0, 0);
				ghost_viewimg.setLayoutParams(params);
				shakeView();
			}
		});
	}

	public void game_finish() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
		Toast.makeText(this, "Mission Copmlete", Toast.LENGTH_SHORT).show();

	}

	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);
		img[0].setVisibility(View.VISIBLE);
		frame_width = frame.getWidth();
		frame_height = frame.getHeight();

	}

}