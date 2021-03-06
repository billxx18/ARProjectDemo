package project.demo.arprojectdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class StageFourActivity extends Activity {
	LinearLayout Button1, Button2, Button3, Button4, Button5, Button6, Button7,
			Button8, Button9, Button10, Button11, Button12, Button13, Button14,
			Button15, Button16;
	TextView score_text;
	ImageView Boss, img;
	int score, random, lastTime;
	WaterDodgeProgressTask Game4ProgressTask;
	boolean isRecover = false, match_state = false;
	LinearLayout[] Button = new LinearLayout[16];
	private Rect mChangeImageBackgroundRect = null;
	FrameLayout game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// WaterDodgeActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stage_four);

		// getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
		// WindowManager.LayoutParams. FLAG_FULLSCREEN);
		img = (ImageView) findViewById(R.id.icon);
		game = (FrameLayout) findViewById(R.id.game);
		Boss = (ImageView) findViewById(R.id.Boss);
		Button[0] = (LinearLayout) findViewById(R.id.Button1);
		Button[1] = (LinearLayout) findViewById(R.id.Button2);
		Button[2] = (LinearLayout) findViewById(R.id.Button3);
		Button[3] = (LinearLayout) findViewById(R.id.Button4);
		Button[4] = (LinearLayout) findViewById(R.id.Button5);
		Button[5] = (LinearLayout) findViewById(R.id.Button6);
		Button[6] = (LinearLayout) findViewById(R.id.Button7);
		Button[7] = (LinearLayout) findViewById(R.id.Button8);
		Button[8] = (LinearLayout) findViewById(R.id.Button9);
		Button[9] = (LinearLayout) findViewById(R.id.Button10);
		Button[10] = (LinearLayout) findViewById(R.id.Button11);
		Button[11] = (LinearLayout) findViewById(R.id.Button12);
		Button[12] = (LinearLayout) findViewById(R.id.Button13);
		Button[13] = (LinearLayout) findViewById(R.id.Button14);
		Button[14] = (LinearLayout) findViewById(R.id.Button15);
		Button[15] = (LinearLayout) findViewById(R.id.Button16);
		score_text = (TextView) findViewById(R.id.Score);
		score_text.setText(Integer.toString(score));
		Boss.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				score = score + 1;
				score_text.setText(Integer.toString(score));
				if (score == 30) {
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result", true);
					setResult(RESULT_OK, returnIntent);
					if ((Game4ProgressTask != null)
							&& (Game4ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
						Game4ProgressTask.cancel(true);
					}
					finish();
				}
			}
		});
		img.setOnTouchListener(imgListener);
		Game4ProgressTask = new WaterDodgeProgressTask();
		Game4ProgressTask.execute((Void) null);
	}

	private Handler WaterDodgeHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			int[] location_img = new int[2];
			// target.getLocationInWindow(location);// 获取在整个屏幕内的绝对坐标
			img.getLocationInWindow(location_img);
			switch (msg.what) {
			case 1:
				Button[0].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 100:
				Button[0].setBackgroundResource(android.R.color.transparent);
				break;
			case 1001:
				Button[0].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[0], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 2:
				Button[1].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 200:
				Button[1].setBackgroundResource(android.R.color.transparent);
				break;
			case 2001:
				Button[1].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[1], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 3:
				Button[2].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 300:
				Button[2].setBackgroundResource(android.R.color.transparent);
				break;
			case 3001:
				Button[2].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[2], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}

				break;

			case 4:
				Button[3].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 400:
				Button[3].setBackgroundResource(android.R.color.transparent);
				break;
			case 4001:
				Button[3].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[3], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 5:
				Button[4].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 500:
				Button[4].setBackgroundResource(android.R.color.transparent);
				break;
			case 5001:
				Button[4].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[4], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 6:
				Button[5].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 600:
				Button[5].setBackgroundResource(android.R.color.transparent);
				break;
			case 6001:
				Button[5].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[5], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 7:
				Button[6].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 700:
				Button[6].setBackgroundResource(android.R.color.transparent);
				break;
			case 7001:
				Button[6].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[6], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 8:
				Button[7].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 800:
				Button[7].setBackgroundResource(android.R.color.transparent);
				break;
			case 8001:
				Button[7].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[7], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 9:
				Button[8].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 900:
				Button[8].setBackgroundResource(android.R.color.transparent);
				break;
			case 9001:
				Button[8].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[8], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 10:
				Button[9].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1000:
				Button[9].setBackgroundResource(android.R.color.transparent);
				break;
			case 10001:
				Button[9].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[9], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 11:
				Button[10].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1100:
				Button[10].setBackgroundResource(android.R.color.transparent);
				break;
			case 11001:
				Button[10].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[10], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 12:
				Button[11].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1200:
				Button[11].setBackgroundResource(android.R.color.transparent);
				break;
			case 12001:
				Button[11].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[11], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 13:
				Button[12].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1300:
				Button[12].setBackgroundResource(android.R.color.transparent);
				break;
			case 13001:
				Button[12].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[12], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 14:
				Button[13].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1400:
				Button[13].setBackgroundResource(android.R.color.transparent);
				break;
			case 14001:
				Button[13].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[13], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			case 15:
				Button[14].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1500:
				Button[14].setBackgroundResource(android.R.color.transparent);
				break;
			case 15001:
				Button[14].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[14], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}

				break;

			case 16:
				Button[15].setBackgroundResource(R.drawable.yes_ds);
				break;
			case 1600:
				Button[15].setBackgroundResource(android.R.color.transparent);
				break;
			case 16001:
				Button[15].setBackgroundResource(R.drawable.bomb);
				match_state = isInChangeImageZone(Button[15], location_img[0],
						location_img[1]);
				if (match_state == true) {
					score = score - 10;
					score_text.setText(Integer.toString(score));
				}
				break;

			default:
				break;
			}
			return false;
		}
	});

	private class WaterDodgeProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (score = 0; score < 30;) {
				if (isCancelled()) {
					return (null);
				}

				Message msg = new Message();
				random = ((int) (Math.random() * 16) + 1);
				msg.what = random;
				WaterDodgeHandler.sendMessage(msg);
				try {
					lastTime = 1000;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Message hit = new Message();
				hit.what = random * 1000 + 1;
				WaterDodgeHandler.sendMessage(hit);
				try {
					lastTime = 500;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Message msgResponse = new Message();
				msgResponse.what = random * 100;
				WaterDodgeHandler.sendMessage(msgResponse);
				try {
					lastTime = 600;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			StageFourActivity.this.finish();
		}

	}

	protected void onDestroy() {
		if ((Game4ProgressTask != null)
				&& (Game4ProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			Game4ProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	private OnTouchListener imgListener = new OnTouchListener() {
		private float x, y; // 原本圖片存在的X,Y軸位置
		private int mx, my; // 圖片被拖曳的X ,Y軸距離長度

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub

			game.measure(0, 0);
			int game_width = game.getMeasuredWidth();
			int game_height = game.getMeasuredHeight();
			switch (event.getAction()) {
			// 按下圖片時
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();

				// 移動圖片時
			case MotionEvent.ACTION_MOVE:
				mx = (int) (event.getRawX() - x);
				my = (int) (event.getRawY() - y); // 50應該是標題框長度
				if (mx > 0 && mx < game_width && my > 0 && my < game_height) {
					v.layout(mx - 20, my - 20, mx + v.getWidth(),
							my + v.getHeight());
					LayoutParams params = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					int[] location_img_move = new int[2];
					v.getLocationInWindow(location_img_move);
					params.setMargins(location_img_move[0], location_img_move[1], 0, 0);
					v.setLayoutParams(params);
					break;
				}

			case MotionEvent.ACTION_UP:
				int[] location_img_stop = new int[2];
				img.getLocationInWindow(location_img_stop);
				for (int i = 0; i < 16; i++) {
					match_state = isInChangeImageZone(Button[i], location_img_stop[0],
							location_img_stop[1]);

					if (match_state == true) {
						int[] location_button = new int[2];
						Button[i].getLocationInWindow(location_button);

						LayoutParams params = new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);

						params.setMargins(location_button[0],location_button[1], 0, 0);
						// OR
						// params.topMargin= 100;

						img.setLayoutParams(params);

					}
				}
			}
			Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my)
					+ match_state); // 記錄目前位置

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
		Log.e("view", location_view[0] + "~~" + location_view[1]); // 記錄目前位置

		return mChangeImageBackgroundRect.contains(x + 50, y + 50);
	}
}