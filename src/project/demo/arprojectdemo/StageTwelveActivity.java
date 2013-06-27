package project.demo.arprojectdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StageTwelveActivity extends Activity {
	TextView enemyHp, yourHp;
	ImageView enemyFirstStatus, enemyStatusPoint1, enemyStatusPoint2,
			enemyStatusPoint3, enemyStatusPoint4, enemyStatusPoint5,
			enemyStatusPoint6, enemyStatusPoint7;
	RelativeLayout secondThirdStatusLayout;
	Button laserButton;
	int enemyHpCount = 0;
	int yourHpCount = 0;
	int atkInterval = 0;
	int enemyAtkDamage = 0;
	int yourAtkDamage = 20;
	int stageStatus = 0;
	static String endGameMessage[] = { "You Win!", "You Lose!" };
	enemyAttackProgressTask enemyAtkTsk;
	enemyStageStatusProgressTask enemyStgStsTsk;
	final int PLAYER_ATTACK = 0;
	final int ENEMY_ATTACK = 1;
	final int SECOND_STATUS_LAYOUT = 2;
	final int SET_HP_CHANGE = 4;
	final int INVIS_VIEW_1 = 5;
	final int INVIS_VIEW_2 = 6;
	final int INVIS_VIEW_3 = 7;
	final int INVIS_VIEW_4 = 8;
	final int INVIS_VIEW_5 = 9;
	final int INVIS_VIEW_6 = 10;
	final int INVIS_VIEW_7 = 11;
	final int VIS_VIEW_ALL = 12;
	boolean pointHitStatus[] = { false, false, false, false, false, false,
			false };
	int statusPointHitTimes[] = { 10, 10, 10, 10, 10, 10, 10 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_twelve);
		initialHp();
		initialEnemyStatus();

		enemyAtkTsk = new enemyAttackProgressTask();
		enemyAtkTsk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		enemyStgStsTsk = new enemyStageStatusProgressTask();
		enemyStgStsTsk.execute((Void) null);
		// handle stage process
	}

	private void initialEnemyStatus() {
		enemyFirstStatus = (ImageView) findViewById(R.id.dagaforce);
		enemyStatusPoint1 = (ImageView) findViewById(R.id.imageView1);
		enemyStatusPoint2 = (ImageView) findViewById(R.id.imageView2);
		enemyStatusPoint3 = (ImageView) findViewById(R.id.imageView3);
		enemyStatusPoint4 = (ImageView) findViewById(R.id.imageView4);
		enemyStatusPoint5 = (ImageView) findViewById(R.id.imageView5);
		enemyStatusPoint6 = (ImageView) findViewById(R.id.imageView6);
		enemyStatusPoint7 = (ImageView) findViewById(R.id.imageView7);
		secondThirdStatusLayout = (RelativeLayout) findViewById(R.id.secondThirdStatusLayout);

		stageStatus = 1;
	}

	private void initialHp() {
		enemyHpCount = 2000;
		yourHpCount = 200;
		enemyHp = (TextView) findViewById(R.id.enemyHp);
		yourHp = (TextView) findViewById(R.id.yourHp);
		setHpChange();
		laserButton = (Button) findViewById(R.id.laserAttack);
	}

	private void setHpChange() {
		Message msg = new Message();
		msg.what = SET_HP_CHANGE;
		hitPointHandler.sendMessage(msg);
	}

	private void initialPointHitStatus() {
		for (int count = 0; count < 7; count++) {
			pointHitStatus[count] = false;
		}
		Message msg = new Message();
		msg.what = VIS_VIEW_ALL;
		hitPointHandler.sendMessage(msg);

		enemyStatusPoint1.setClickable(true);
		enemyStatusPoint2.setClickable(true);
		enemyStatusPoint3.setClickable(true);
		enemyStatusPoint4.setClickable(true);
		enemyStatusPoint5.setClickable(true);
		enemyStatusPoint6.setClickable(true);
		enemyStatusPoint7.setClickable(true);
	}

	public void onLaserButtonClick(View v) {
		enemyHpCount -= 200;
		setHpChange();
		v.setClickable(false);
	}

	private class enemyAttackProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (yourHpCount > 0) {
				if (isCancelled()) {
					return (null);
				}
				Message msg = new Message();
				msg.what = ENEMY_ATTACK;
				hitPointHandler.sendMessage(msg);

				try {
					if (enemyHpCount > 900) {
						atkInterval = 3000;
						enemyAtkDamage = 20;
					} else if (enemyHpCount > 400) {
						atkInterval = 3000;
						enemyAtkDamage = 0;
					} else {
						atkInterval = 3000;
						enemyAtkDamage = 10;
					}

					Thread.sleep(atkInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(Void result) {
//			if ((enemyStgStsTsk != null)
//					&& (enemyStgStsTsk.getStatus() != AsyncTask.Status.FINISHED)) {
//				enemyStgStsTsk.cancel(true);
//			}
			Intent returnIntent = new Intent();
			setResult(RESULT_CANCELED, returnIntent);
			showLoseGameDialog(StageTwelveActivity.this);
			// handle end game (Lose)
		}

	}

	private class enemyStageStatusProgressTask extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (enemyHpCount > 0) {
				if (isCancelled()) {
					return (null);
				}
				try {
					if (enemyHpCount > 900) {
						enemyFirstStatus.setClickable(true);
						laserButton.setClickable(true);
						stageStatus = 1;
						for (; enemyHpCount > 900;) {
							enemyFirstStatus
									.setOnClickListener(new View.OnClickListener() {

										public void onClick(View v) {
											enemyHpCount -= yourAtkDamage;
											setHpChange();
										}
									});
						}
					} else if (enemyHpCount > 400) {
						enemyFirstStatus.setClickable(false);
						laserButton.setClickable(false);
						Message msg = new Message();
						msg.what = SECOND_STATUS_LAYOUT;
						hitPointHandler.sendMessage(msg);
						enemyStatusPoint1.setClickable(true);
						enemyStatusPoint2.setClickable(true);
						enemyStatusPoint3.setClickable(true);
						enemyStatusPoint4.setClickable(true);
						enemyStatusPoint5.setClickable(true);
						enemyStatusPoint6.setClickable(true);
						enemyStatusPoint7.setClickable(true);
						stageStatus = 2;
						for (; enemyHpCount > 400;) {
							enemyStatusPoint1
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[0] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_1;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint2
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[1] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_2;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint3
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[2] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_3;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint4
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[3] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_4;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint5
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[4] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_5;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint6
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[5] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_6;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});
							enemyStatusPoint7
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											pointHitStatus[6] = true;
											Message msg = new Message();
											msg.what = INVIS_VIEW_7;
											hitPointHandler.sendMessage(msg);
											v.setClickable(false);
										}
									});

							Thread.sleep(atkInterval);
							int subEnemiesCount = checkSecondStatusUnbrokenSubs();
							if (subEnemiesCount == 7) {
								// enemy hp reduce
								enemyHpCount -= 50;
								setHpChange();
								initialPointHitStatus();
							} else {
								// player hp reduce
								yourHpCount -= (subEnemiesCount * 5);
								setHpChange();
								initialPointHitStatus();
							}
						}
					} else {
						stageStatus = 3;
						initialPointHitStatus();

						final int tempArray[] = statusPointHitTimes;
						for (; enemyHpCount > 0;) {
							enemyStatusPoint1
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[0] > 0) {
												statusPointHitTimes[0]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_1;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint2
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[1] > 0) {
												statusPointHitTimes[1]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_2;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint3
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[2] > 0) {
												statusPointHitTimes[2]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_3;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint4
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[3] > 0) {
												statusPointHitTimes[3]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_4;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint5
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[4] > 0) {
												statusPointHitTimes[4]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_5;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint6
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[5] > 0) {
												statusPointHitTimes[5]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_6;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
							enemyStatusPoint7
									.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											if (tempArray[6] > 0) {
												statusPointHitTimes[6]--;
											} else {
												Message msg = new Message();
												msg.what = INVIS_VIEW_7;
												hitPointHandler
														.sendMessage(msg);
												v.setClickable(false);
												enemyHpCount -= 58;
												setHpChange();
											}
										}
									});
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			if (StageTwelveActivity.this!=null) {
				enemyAtkTsk.cancel(true);
			}
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", true);
			setResult(RESULT_OK, returnIntent);
			showWinGameDialog(StageTwelveActivity.this);

			// handle end game (Win)
		}

	}

	private int checkSecondStatusUnbrokenSubs() {
		int aliveSubsNum = 0;
		for (int count = 0; count < 7; count++) {
			if (pointHitStatus[count] == true) {
				aliveSubsNum++;
			}
		}
		return aliveSubsNum;
	}

	private Handler hitPointHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case ENEMY_ATTACK:
				yourHpCount -= enemyAtkDamage;
				setHpChange();
				break;
			case SECOND_STATUS_LAYOUT:
				secondThirdStatusLayout.setVisibility(View.VISIBLE);
				break;
			case SET_HP_CHANGE:
				if (enemyHpCount >= 0) {
					enemyHp.setText(Integer.toString(enemyHpCount));
				} else {
					enemyHp.setText("0");
				}
				if (yourHpCount >= 0) {
					yourHp.setText(Integer.toString(yourHpCount));
				} else {
					yourHp.setText("0");
				}
				break;
			case INVIS_VIEW_1:
				enemyStatusPoint1.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_2:
				enemyStatusPoint2.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_3:
				enemyStatusPoint3.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_4:
				enemyStatusPoint4.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_5:
				enemyStatusPoint5.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_6:
				enemyStatusPoint6.setVisibility(View.INVISIBLE);
				break;
			case INVIS_VIEW_7:
				enemyStatusPoint7.setVisibility(View.INVISIBLE);
				break;
			case VIS_VIEW_ALL:
				enemyStatusPoint1.setVisibility(View.VISIBLE);
				enemyStatusPoint2.setVisibility(View.VISIBLE);
				enemyStatusPoint3.setVisibility(View.VISIBLE);
				enemyStatusPoint4.setVisibility(View.VISIBLE);
				enemyStatusPoint5.setVisibility(View.VISIBLE);
				enemyStatusPoint6.setVisibility(View.VISIBLE);
				enemyStatusPoint7.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			return false;
		}
	});

	
	public static void showWinGameDialog(final Context con) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("遊戲結束").setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(endGameMessage[0])
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						((Activity) con).finish();
					}
				}).setCancelable(false);
		builder.create().show();
	}

	public static void showLoseGameDialog(final Context con) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("遊戲結束").setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(endGameMessage[1])
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
						((Activity) con).finish();
						
					}
				}).setCancelable(false);
		builder.create().show();
	}

	@Override
	protected void onDestroy() {
		if ((enemyAtkTsk != null)
				&& (enemyAtkTsk.getStatus() != AsyncTask.Status.FINISHED)) {
			enemyAtkTsk.cancel(true);
		}
		if ((enemyStgStsTsk != null)
				&& (enemyStgStsTsk.getStatus() != AsyncTask.Status.FINISHED)) {
			enemyStgStsTsk.cancel(true);
		}
		super.onDestroy();
		// handle task cancel
	}

}
