package project.demo.arprojectdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StageThreeActivity extends Activity {

	LinearLayout ddsGameLayout, ddsStartLayout;
	ImageView ddsButton1, ddsButton2, ddsButton3, ddsButton4, ddsButton5,
			ddsButton6, ddsButton7, ddsButton8, ddsButton9;
	TextView scoreText;
	boolean ddsButtonHittable[] = new boolean[9];
	boolean isRecover = false;
	private Handler handler = new Handler();
	int score, random, lastTime, emergeInterval;
	ddsGameProgressTask gameProgressTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_three);
		ddsStartLayout = (LinearLayout) findViewById(R.id.ddsStartLayout);
		ddsGameLayout = (LinearLayout) findViewById(R.id.ddsGameLayout);
		scoreText = (TextView) findViewById(R.id.ddsScoreText);
		ddsButton1 = (ImageView) findViewById(R.id.ddsGameButton1_1);
		ddsButton2 = (ImageView) findViewById(R.id.ddsGameButton1_2);
		ddsButton3 = (ImageView) findViewById(R.id.ddsGameButton1_3);
		ddsButton4 = (ImageView) findViewById(R.id.ddsGameButton1_4);
		ddsButton5 = (ImageView) findViewById(R.id.ddsGameButton1_5);
		ddsButton6 = (ImageView) findViewById(R.id.ddsGameButton1_6);
		ddsButton7 = (ImageView) findViewById(R.id.ddsGameButton1_7);
		ddsButton8 = (ImageView) findViewById(R.id.ddsGameButton1_8);
		ddsButton9 = (ImageView) findViewById(R.id.ddsGameButton1_9);

		ddsButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[0]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[1]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[2]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[3]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[4]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[5]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[6]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[7]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});

		ddsButton9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ddsButtonHittable[8]) {
					score++;
					scoreText.setText(Integer.toString(score));
					Message msg = new Message();
					msg.what = random * 10 + 1;
					ddsHandler.sendMessage(msg);
					isRecover = true;
				}
			}
		});
	}

	public void onDdsStartButtonClick(View v) {
		ddsStartLayout.setVisibility(View.GONE);
		ddsGameLayout.setVisibility(View.VISIBLE);
		gameProgressTask = new ddsGameProgressTask();
		gameProgressTask.execute((Void) null);
	}

	private Handler ddsHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ddsButton1.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[0] = true;
				break;
			case 11:
				ddsButton1.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[0] = false;
				isRecover = false;
				break;

			case 2:
				ddsButton2.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[1] = true;
				break;
			case 21:
				ddsButton2.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[1] = false;
				isRecover = false;
				break;

			case 3:
				ddsButton3.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[2] = true;
				break;
			case 31:
				ddsButton3.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[2] = false;
				isRecover = false;
				break;

			case 4:
				ddsButton4.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[3] = true;
				break;
			case 41:
				ddsButton4.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[3] = false;
				isRecover = false;
				break;

			case 5:
				ddsButton5.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[4] = true;
				break;
			case 51:
				ddsButton5.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[4] = false;
				isRecover = false;
				break;

			case 6:
				ddsButton6.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[5] = true;
				break;
			case 61:
				ddsButton6.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[5] = false;
				isRecover = false;
				break;

			case 7:
				ddsButton7.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[6] = true;
				break;
			case 71:
				ddsButton7.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[6] = false;
				isRecover = false;
				break;

			case 8:
				ddsButton8.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[7] = true;
				break;
			case 81:
				ddsButton8.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[7] = false;
				isRecover = false;
				break;

			case 9:
				ddsButton9.setImageResource(R.drawable.yes_ds);
				ddsButtonHittable[8] = true;
				break;
			case 91:
				ddsButton9.setImageResource(R.drawable.no_ds);
				ddsButtonHittable[8] = false;
				isRecover = false;
				break;

			default:
				break;
			}
			return false;
		}
	});

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (score = 0; score < 30;) {
				if (isCancelled()) {
					return (null);
				}
				Message msg = new Message();
				random = ((int) (Math.random() * 9) + 1);
				msg.what = random;
				ddsHandler.sendMessage(msg);
				try {
					if (score > 10) {
						lastTime = 600;
					} else if (score > 20) {
						lastTime = 450;
					} else {
						lastTime = 750;
					}
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!isRecover) {
					Message msgResponse = new Message();
					msgResponse.what = (random * 10) + 1;
					ddsHandler.sendMessage(msgResponse);
				}
				try {
					if (score > 10) {
						emergeInterval = 450;
					} else if (score > 20) {
						emergeInterval = 600;
					} else {
						emergeInterval = 750;
					}
					Thread.sleep(emergeInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", true);
			setResult(RESULT_OK, returnIntent);
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		if ((gameProgressTask != null)
				&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			gameProgressTask.cancel(true);
		}
		super.onDestroy();
	}

}