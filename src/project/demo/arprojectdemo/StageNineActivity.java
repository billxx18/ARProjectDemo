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
	AnimationSet animationSet;
	int score, random, lastTime, emergeInterval;
	ddsGameProgressTask gameProgressTask;
	private Rect mChangeImageBackgroundRect = null;
	ImageView[] img = new ImageView[6];
	LinearLayout[] linear = new LinearLayout[6];
	FrameLayout frame;
	private boolean mRegisteredSensor;
	// 定义SensorManager
	private SensorManager mSensorManager;
	boolean state = false;
	int[] location8 = new int[2];
	long INTERVAL = 50;
	final static long TIMEOUT = 0;
	long elapsed = 3000;
	Timer timer, timer2;
	TimerTask task;
	int newWidth = 0, newHeight = 0;
	ImageView img2;
	int index = 0;
	
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
		linear[0] = (LinearLayout) findViewById(R.id.column1);
		linear[1] = (LinearLayout) findViewById(R.id.column2);
		linear[2] = (LinearLayout) findViewById(R.id.column3);
		linear[3] = (LinearLayout) findViewById(R.id.column4);
		linear[4] = (LinearLayout) findViewById(R.id.column5);
		linear[5] = (LinearLayout) findViewById(R.id.window);
		frame = (FrameLayout) findViewById(R.id.frame);
		int width3 = frame.getWidth();
		int height3 = frame.getHeight();
		int height4 = img[0].getHeight();
		int width4 = img[0].getWidth();
		
		img[0].layout(width3 / 2, height3-height4, width3 / 2 + img[0].getWidth(),
				height3-height4 + img[0].getHeight());
		img[0].setVisibility(View.VISIBLE);

		img[1].getLocationInWindow(location8);
		// FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
		// FrameLayout.LayoutParams.WRAP_CONTENT,
		// FrameLayout.LayoutParams.WRAP_CONTENT);
		// int[] location7 = new int[2];
		// img[0].getLocationInWindow(location7);
		// params2.setMargins(location7[0], location7[1], 0, 0);
		// img[0].setVisibility(View.VISIBLE);
		// // OR
		// // params.topMargin= 100;
		//
		// img[0].setLayoutParams(params2);
		gameProgressTask = new ddsGameProgressTask();
		gameProgressTask.execute((Void) null);

		// int width = frame.getWidth();
		// int height = frame.getHeight();
		// int height2 = img[1].getHeight();
		// int width2 = img[1].getWidth();
		// for(int i=0;i<=(height - height2);i++)
		// {
		//
		// LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT);
		// int[] location = new int[2];
		// img[1].getLocationInWindow(location);
		// params2.setMargins(location[0], location[1] + (height - height2),0 ,
		// 0);
		// // OR
		// // params.topMargin= 100;
		//
		// img[1].setLayoutParams(params2);
		// // ddsHandler.sendMessage(hit);
		// try {
		// lastTime = 3000/(height - height2);
		// Thread.sleep(lastTime);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
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

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (score = 0; score < 30;) {
				if (isCancelled()) {
					return (null);
				}
				INTERVAL = 50;
				elapsed = 3000;
				newWidth = 0;
				newHeight = 0;
				// Message msg = new Message();
				random = ((int) (Math.random() * 5) + 1);
				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							sssss(img[random]);
							return;
						}
						// if(some other conditions)
						// this.cancel();
						down2(img[random]);
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
					lastTime = 4000;
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
			StageNineActivity.this.finish();
		}

	}
	
	private void change(int index) {
		final int index2 = index;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				img[index2].setVisibility(View.VISIBLE);
				int width = frame.getWidth();
				int height = frame.getHeight();
				int[] location = new int[2];
				frame.getLocationInWindow(location);

				img[index2].setBackgroundResource(R.drawable.b);
				animationSet = new AnimationSet(true);
				// 创建一个RotateAnimation对象（从某个点移动到另一个点）
				TranslateAnimation translateAnimation = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, height);
				// 设置动画执行的时间（单位：毫秒）
				translateAnimation.setDuration(1000);

				// 将TranslateAnimation对象添加到AnimationSet当中
				animationSet.addAnimation(translateAnimation);
				// 使用ImageView的startAnimation方法开始执行动画
				img[index2].startAnimation(animationSet);

			}
		});
	}

	private void change2(int index) {
		final int index2 = index;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				img[index2].setBackgroundResource(android.R.color.transparent);

				img[index2].setVisibility(View.GONE);
			}
		});
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
			int width = frame.getWidth();
			int height = frame.getHeight();
			int height4 = img[0].getHeight();
			int width4 = img[0].getWidth();
			
			img[0].layout(width / 2 - y1, height - height4, (width / 2 - y1)
					+ img[0].getWidth(), (height - height4) + img[0].getHeight());
			
			int[] location7 = new int[2];
			LayoutParams params3 = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			img[0].getLocationInWindow(location7);
			params3.setMargins(location7[0], location7[1], 0, 0);
			img[0].setLayoutParams(params3);
			// Log.e("img", location7[0] + "~~" + location7[1]); // 記錄目前位置

			for (int i = 1; i <= 5; i++) {
				state = isInChangeImageZone(img[i], location7[0], location7[1]);
				if (state == true) {
					// Toast.makeText(MainActivity.this, "Oops",
					// Toast.LENGTH_SHORT).show();// toast有显示时间延迟
					StageNineActivity.this.finish();
					state = false;
					break;
				}
			}
		}
		// int x = 200, e = 100;
		// LayoutParams mparam = new LayoutParams(
		// (int) (LayoutParams.FILL_PARENT),
		// (int) (LayoutParams.FILL_PARENT));
		// img[0].setLayoutParams(mparam);
		// img[0].setPadding(x, e, 0, 0);
		// int y1 = (int) (y);
		// int d1 = x - 5 * y1;
		// if (d1 < 350 && d1 > 40) {
		// img[0].setPadding(d1, 100, 0, 0);
		// } else if (d1 >= 350) {
		// img[0].setPadding(360, 100, 0, 0);
		// } else {
		// img[0].setPadding(40, 100, 0, 0);
		// }

		// 使用ImageView的startAnimation方法开始执行动画
		// img[0].startAnimation(animationSet);

	}

	protected void onDestroy() {
		if ((gameProgressTask != null)
				&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			gameProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	public void down(View view) {
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int width = frame.getWidth();
		int height = frame.getHeight();
		int height2 = img[1].getHeight();
		int width2 = img[1].getWidth();
		img[1].getLocationInWindow(location8);

		params2.setMargins(location8[0], location8[1] + 5, 0, 0);
		// OR
		// params.topMargin= 100;

		img[1].setLayoutParams(params2);

	}

	public void down2(ImageView img) {
		img2 = img;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				img2.setVisibility(View.VISIBLE);
				int width = linear[5].getWidth();
				int height = linear[5].getHeight();
				int height2 = img2.getHeight();
				int width2 = img2.getWidth();
				newHeight = newHeight + (height) / 60;

				img2.layout(location8[0], location8[1] + newHeight,
						location8[0] + width2, location8[1] + newHeight
								+ height2);

				// params2.setMargins(location8[0], location8[1]
				// + (height - height2) / 50, 0, 0);
				// OR
				// params.topMargin= 100;

				// img[1].setLayoutParams(params2);
			}
		});
	}

	public void sssss(ImageView img) {
		img2 = img;
		this.runOnUiThread(new Runnable() {
			int height2 = img2.getHeight();
			int width2 = img2.getWidth();

			@Override
			public void run() {
				img2.setVisibility(View.GONE);
				img2.layout(location8[0], location8[1], location8[0] + width2,
						location8[1] + height2);
			}
		});
	}
}
