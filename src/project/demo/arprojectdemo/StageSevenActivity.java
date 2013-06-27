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
	Button startBtn;
	TextView text;
	boolean match_state = false, timeup = false;
	static boolean state2 = false;;
	private Rect mChangeImageBackgroundRect = null;
	int score = 5, random, lastTime, emergeInterval;
	PutBoneProgressTask Game7ProgressTask;
	long INTERVAL = 1000;
	final static long TIMEOUT = 0;
	long elapsed = 10000;
	Timer putbone_timer, pressbtn_timer;
	TimerTask putbone_task, pressbtn_task;
	int[] location_img = new int[2];
	boolean stage1 = false;
	int progress=10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_seven);
		dog = (ImageView) findViewById(R.id.dogframe);
		img = (ImageView) findViewById(R.id.img);
		img.getLocationInWindow(location_img);
		startBtn = (Button) findViewById(R.id.start);
		text = (TextView) findViewById(R.id.text);

		Game7ProgressTask = new PutBoneProgressTask();
		Game7ProgressTask.execute((Void) null);
		img.setOnTouchListener(imgListener);

	}

	private OnTouchListener imgListener = new OnTouchListener() {
		private float x, y; // ���摮�,Y頠訾�蝵�
		private int mx, my; // ��鋡急��喟�X ,Y頠貉��ａ摨�

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			// ������
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				// 蝘餃�����
			case MotionEvent.ACTION_MOVE:
				mx = (int) (event.getRawX() - x);
				my = (int) (event.getRawY() - y); // 50�府�舀�憿��瑕漲
				v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				int[] location_img_move = new int[2];
				v.getLocationInWindow(location_img_move);
				params.setMargins(location_img_move[0], location_img_move[1],
						0, 0);
				v.setLayoutParams(params);
				break;

			}

			int[] location_img_stop = new int[2];

			img.getLocationOnScreen(location_img_stop);
			if (event.getAction() == MotionEvent.ACTION_UP) {
				Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my)); // 閮��桀�雿蔭
				Log.i("image", location_img_stop[0] + "~~"
						+ location_img_stop[1]);
				match_state = isInChangeImageZone(dog, location_img_stop[0],
						location_img_stop[1]);

				if (match_state == true) {

					dog.setVisibility(View.GONE);
					img.setVisibility(View.GONE);
					startBtn.setVisibility(View.VISIBLE);
					if ((Game7ProgressTask != null)
							&& (Game7ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
						Game7ProgressTask.cancel(true);
					}
					elapsed = 4000;
					INTERVAL = 1000;

					pressbtn_task = new TimerTask() {

						@Override
						public void run() {

							elapsed = elapsed - INTERVAL;
							
							if (elapsed < TIMEOUT) {
								this.cancel();
								dog_emerge();
								return;
							}
							if (elapsed ==0) {
								displayText("狗狗出現了");
							}
							else
							displayText("�拚���嚗�" + elapsed / 1000);
						}
					};
					pressbtn_timer = new Timer();
					pressbtn_timer.scheduleAtFixedRate(pressbtn_task, INTERVAL,
							INTERVAL);

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

		int[] location_view = new int[2];
		view.getLocationInWindow(location_view);

		mChangeImageBackgroundRect.left = location_view[0];
		mChangeImageBackgroundRect.top = location_view[1];
		mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right
				+ location_view[0];
		mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom
				+ location_view[1];
		Log.e("view", location_view[0] + "~~" + location_view[1]); // 閮��桀�雿蔭

		return mChangeImageBackgroundRect.contains(x + 50, y + 50);
	}

	protected void onResume() {
		// // TODO Auto-generated method stub
		super.onResume();

	}

	
	private class PutBoneProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			progress=10;
			for (score = 0; score < 10;) {
				
				if (isCancelled()) {
					return (null);
				}
			if(progress==0)
			{
				displayText("攻擊！！！");
				progress=11;
			}
			else
			{
				displayText("�拚���嚗�" +progress);
			}
				try {
					lastTime = 1000 ;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progress= progress-1;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}
	}
	protected void onDestroy() {
		if ((Game7ProgressTask != null)
				&& (Game7ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			Game7ProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	private void displayText(final String display_text) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				text.setText(display_text);
			}
		});
	}

	private void dog_emerge() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dog.setVisibility(View.VISIBLE);
				startBtn.setVisibility(View.GONE);
				img.setVisibility(View.VISIBLE);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(location_img[0], location_img[1], 0, 0);
				img.setLayoutParams(params);
				Game7ProgressTask = new PutBoneProgressTask();
				Game7ProgressTask.execute((Void) null);
			}
		});
	}

	public void switch_press(View view) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				pressbtn_timer.cancel();
				displayText("開始充能，請盡快到達目的地");

				Intent returnIntent = new Intent();
				returnIntent.putExtra("result", true);
				setResult(RESULT_OK, returnIntent);
				finish();

			}
		});
	}

}
