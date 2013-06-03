package project.demo.arprojectdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class StageSevenActivity extends Activity {

	ImageView img, dog;
	Button start;
	TextView text;
	boolean state = false, timeup = false;
	static boolean state2 = false;;
	private Rect mChangeImageBackgroundRect = null;
	int score = 5, random, lastTime, emergeInterval;
	ddsGameProgressTask gameProgressTask;
//	private MyCount mc;
	long INTERVAL = 1000;
	final static long TIMEOUT = 0;
	long elapsed = 10000;
	Timer timer, timer2;
	TimerTask task;
	int[] location = new int[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_seven);
		dog = (ImageView) findViewById(R.id.dogframe);
		img = (ImageView) findViewById(R.id.img);
		img.getLocationInWindow(location);
		start = (Button) findViewById(R.id.start);
		text = (TextView) findViewById(R.id.text);
		
		gameProgressTask = new ddsGameProgressTask();
		gameProgressTask.execute((Void) null);
		img.setOnTouchListener(imgListener);
		// while()

		// task = new TimerTask() {
		// @Override
		// public void run() {
		// elapsed = elapsed - INTERVAL;
		// if (elapsed <= TIMEOUT) {
		// this.cancel();
		// displayText("ATTACK");
		// return;
		// }
		// // if(some other conditions)
		// // this.cancel();
		// displayText("seconds elapsed: " + elapsed / 1000);
		// }
		// };
		// timer = new Timer();
		// // while (timeup == false) {
		// timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);
		// mc = new MyCount(5000, 1000);
		// mc.start();
		// }
	}


	private OnTouchListener imgListener = new OnTouchListener() {
		private float x, y; // 原本圖片存在的X,Y軸位置
		private int mx, my; // 圖片被拖曳的X ,Y軸距離長度

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			// Log.e("View", v.toString());
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
				LayoutParams params2 = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				int[] location7 = new int[2];
				v.getLocationInWindow(location7);
				params2.setMargins(location7[0], location7[1], 0, 0);
				// OR
				// params.topMargin= 100;

				v.setLayoutParams(params2);
				break;

			}

			// target.getLocationInWindow(location);// 获取在整个屏幕内的绝对坐标
			int[] location2 = new int[2];
			// target.getLocationInWindow(location);// 获取在整个屏幕内的绝对坐标
			img.getLocationOnScreen(location2);
			if (event.getAction() == MotionEvent.ACTION_UP) {
				Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my)); // 記錄目前位置
				Log.i("image", location2[0] + "~~" + location2[1]);
				state = isInChangeImageZone(dog, location2[0], location2[1]);

				if (state == true) {
					// text.setText("FINISH");
					dog.setVisibility(View.GONE);
					img.setVisibility(View.GONE);
					start.setVisibility(View.VISIBLE);
					// mc.cancel();
					// state2 = true;
					timer.cancel();
					elapsed = 4000;
					INTERVAL = 1000;
					if ((gameProgressTask != null)
							 && (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
							 gameProgressTask.cancel(true);
					}
					TimerTask task2 = new TimerTask() {

						@Override
						public void run() {

							elapsed = elapsed - INTERVAL;
							if (elapsed <= TIMEOUT) {
								this.cancel();
								displayText("狗狗出現了");
								change();
								return;
							}
							// if(some other conditions)
							// this.cancel();
							displayText("剩餘時間： " + elapsed / 1000);
						}
					};
					timer2 = new Timer();
					timer2.scheduleAtFixedRate(task2, INTERVAL, INTERVAL);

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

	}

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (score = 0; score < 10;) {
				if (isCancelled()) {
					return (null);
				}
				// mc = new MyCount(5000, 1000);
				// mc.start();
				
				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							displayText("攻擊!!!");
							return;
						}
						// if(some other conditions)
						// this.cancel();
						displayText("剩餘時間： " + elapsed / 1000);
					}
				};
				timer = new Timer();
				// while (timeup == false) {
				timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);
				elapsed = 10000;
				INTERVAL = 1000;

				try {
					lastTime = 11000;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
		}
	}
	 protected void onDestroy() {
		 if ((gameProgressTask != null)
		 && (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
		 gameProgressTask.cancel(true);
		 }
		 super.onDestroy();
		 }
//	class MyCount extends CountDownTimer {
//		public MyCount(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);
//		}
//
//		@Override
//		public void onFinish() {
//			text.setText("finish");
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {
//			text.setText("请等待10秒(" + millisUntilFinished / 1000 + ")...");
//			// Toast.makeText(MainActivity.this, millisUntilFinished / 1000 +
//			// "",
//			// Toast.LENGTH_LONG).show();// toast有显示时间延迟
//		}
//	}

	private void displayText(final String text2) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				text.setText(text2);
			}
		});
	}

	private void change() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dog.setVisibility(View.VISIBLE);
				start.setVisibility(View.GONE);
				img.setVisibility(View.VISIBLE);
				LayoutParams params2 = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params2.setMargins(location[0], location[1], 0, 0);
				// OR
				// params.topMargin= 100;

				img.setLayoutParams(params2);
				gameProgressTask = new ddsGameProgressTask();
				gameProgressTask.execute((Void) null);
				// img.setVisibility(View.VISIBLE);
			}
		});
	}

	public void start(View view) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				timer2.cancel();
				displayText("開始充能，請盡快到達目的地");
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result", true);
				setResult(RESULT_OK, returnIntent);
				finish();
				
				// img.setVisibility(View.VISIBLE);
			}
		});
	}



}
