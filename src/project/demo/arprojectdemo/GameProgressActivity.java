package project.demo.arprojectdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameProgressActivity extends MapActivity implements
		LocationListener {
	private boolean getService = false;
	private MapView map; // 宣告google map物件
	private MapController mapController; // 宣告google map控制物件
	String result = "no event";
	String record = "";
	String url;
	private boolean[] stageAvailability = { true, false, false };
	private boolean[] stageClear = { false, false, false, false, false, false,
			false, false, false, false, false, false };
	boolean stage_eight_state = false;
	LayoutInflater controlInflater = null;
	long INTERVAL = 1000;
	final static long TIMEOUT = 0;
	long elapsed = 25000;
	Timer timer, timer2;
	TimerTask task;
	int random, lastTime, emergeInterval;
	TextView time_text;
	Boolean time_state;
	View viewControl;
	ddsGameProgressTask gameProgressTask;
	int[] stagestate = { 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	int past_stage = 0;
	String putdata, user;
	SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_progress);

		map = (MapView) findViewById(R.id.mapView); // 載入google map物件
		mapController = map.getController(); // 設定控制的map物件

		LocationManager status = (LocationManager) (this
				.getSystemService(Context.LOCATION_SERVICE));
		if (status.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// 如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
			locationServiceInitial();
		} else {
			Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			// 開啟設定頁面
		}
		setupMap();
		String tMsg = "";
		try {
			// 指定向GPS裝置註冊要求取得地理資訊
			lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		} catch (Exception e) {
			tMsg = e.getMessage();
		}
		settings = getPreferences(MODE_PRIVATE);
		settings = getSharedPreferences("setting", 0);
		user = settings.getString("user", "");
		try {
			url = "http://140.119.19.15/download_user_progress.php";
			record = new PostTask().execute(user).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] record_array = record.split(",");
		Toast.makeText(this, record, Toast.LENGTH_LONG).show();
		int j = 0;
		for (int i = 1; i < 13; i++) {
			stagestate[i] = Integer.valueOf(record_array[j]);
			j = j + 1;
		}
	}

	public void onPassBtnClick(View v) {
		// Intent intent = new Intent();
		// intent.setClass(this, ResultActivity.class);
		// startActivity(intent);
		this.finish();
	}

	private LocationManager lms;
	private String bestProvider = LocationManager.GPS_PROVIDER;

	private void locationServiceInitial() {
		lms = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // 取得系統定位服務
		Location location = lms.getLastKnownLocation(bestProvider); // 使用GPS定位座標
		if (lms.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
			bestProvider = LocationManager.NETWORK_PROVIDER;
			location = lms.getLastKnownLocation(bestProvider);
		}
		location.getLongitude();
		location.getLatitude();
	}

	public void onLocationChanged(Location location) { // 當地點改變時
		getLocation(location);
		setupMap(location.getLongitude(), location.getLatitude());

	}

	public void onProviderDisabled(String arg0) { // 當GPS或網路定位功能關閉時
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String arg0) { // 當GPS或網路定位功能開啟
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) { // 定位狀態改變
		// TODO Auto-generated method stub

	}

	private void setupMap() {
		setupMap(lms.getLastKnownLocation(bestProvider).getLongitude(), lms
				.getLastKnownLocation(bestProvider).getLatitude()); // 設定地圖預設值
	}

	private MyLocationOverlay mylayer;

	private void setupMap(double longitude, double latitude) {
		// List<Overlay> overlays = map.getOverlays();
		// mylayer = new MyLocationOverlay(this, map);
		// mylayer.runOnFirstFix(new Runnable() {
		// public void run() {
		// // Zoom in to current location
		// mapController.setZoom(17);
		// mapController.animateTo(mylayer.getMyLocation());
		// }
		// });
		// overlays.add(mylayer);

		// GeoPoint center = new GeoPoint((int) (24.986009 * 1000000),
		// (int) (121.575136 * 1000000)); // 設定地圖座標值:緯度,經度
		GeoPoint center = new GeoPoint((int) (latitude * 1000000),
				(int) (longitude * 1000000));
		// List<Overlay> overlays = map.getOverlays();
		Drawable point_star = getResources().getDrawable(
				android.R.drawable.star_on);
		// 設定圖示大小
		point_star.setBounds(0, 0, point_star.getMinimumWidth(),
				point_star.getMinimumHeight());

		Landmark myLandmark = new Landmark(point_star, center);
		// if (center != null) {
		// overlays.add(myLandmark); // 將地標層加入地圖座標層中
		// }
		map.setSatellite(false);
		// .setTraffic(true)：一般地圖
		// .setSatellite(true)：衛星地圖
		// .setStreetView：街景圖

		mapController.setZoom(18); // 設定放大倍率1(地球)-21(街景)
		map.setBuiltInZoomControls(true);
		mapController.animateTo(center); // 指定地圖中央點
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void getLocation(Location location) { // 將定位資訊顯示在畫面中
		if (location != null) {

			TextView longitude_txt = (TextView) findViewById(R.id.longitude);
			TextView latitude_txt = (TextView) findViewById(R.id.latitude);

			Double longitude = location.getLongitude(); // 取得經度
			Double latitude = location.getLatitude(); // 取得緯度
			longitude_txt.setText(String.valueOf(longitude));
			latitude_txt.setText(Double.toString(latitude));
			String longitude_text = String.valueOf(longitude);
			String latitude_text = Double.toString(latitude);
			String msg = longitude_text + "," + latitude_text;

			try {
				url = "http://140.119.19.15/gpsfindevent.php";
				result = new PostTask().execute(msg).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (result.contains("eventA") && stagestate[1] != 1
					&& past_stage != 1) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 1,
						result);

			} else if (result.contains("eventB") && stagestate[2] != 1
					&& past_stage != 2) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 2,
						result);
			} else if (result.contains("eventC") && stagestate[3] != 1
					&& past_stage != 3) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 3,
						result);
			} else if (result.contains("eventD") && stagestate[4] != 1
					&& past_stage != 4) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 4,
						result);
			} else if (result.contains("eventE") && stagestate[5] != 1
					&& past_stage != 5) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 5,
						result);

			} else if (result.contains("eventF") && stagestate[6] != 1
					&& past_stage != 6) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 6,
						result);

			} else if (result.contains("eventG") && stagestate[7] != 1
					&& past_stage != 7) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 7,
						result);

			} else if (result.contains("eventH") && stagestate[8] != 1
					&& past_stage != 8) {

				if (stageClear[6] == true) {

					// if ((gameProgressTask != null)
					// && (gameProgressTask.getStatus() !=
					// AsyncTask.Status.FINISHED)) {
					// gameProgressTask.cancel(true);
					// }
					// removeView();
					project.demo.arprojectdemo.CallDialog.calldialog(this, 88,
							result);

				} else {
					project.demo.arprojectdemo.CallDialog.calldialog(this, 8,
							result);
				}

			} else if (result.contains("eventI") && stagestate[9] != 1
					&& past_stage != 9) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 9,
						result);

			} else if (result.contains("eventJ") && stagestate[10] != 1
					&& past_stage != 10) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 10,
						result);

			} else if (result.contains("eventK") && stagestate[11] != 1
					&& past_stage != 11) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 11,
						result);

			} else if (result.contains("eventL") && stagestate[12] != 1
					&& past_stage != 12) {
				project.demo.arprojectdemo.CallDialog.calldialog(this, 12,
						result);
			}
			// else
			// past_stage = 0;
		} else {
			Toast.makeText(this, "無法定位座標", Toast.LENGTH_SHORT).show();

		}
	}

	public String sendPostDataToInternet(String strText) {
		//
		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", strText));
		try {

			HttpClient httpclient = new DefaultHttpClient();
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpRequest);
			String strResult = EntityUtils.toString(response.getEntity());
			return strResult;

		} catch (Exception e) {
			// Toast.makeText(this, e.getMessage().toString(),
			// Toast.LENGTH_SHORT)
			// .show();
			e.printStackTrace();
		}
		return null;
	}

	public class PostTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return sendPostDataToInternet(params[0]);
		}

	}

	protected void onResume() {
		// // TODO Auto-generated method stub
		super.onResume();
		// mylayer.enableMyLocation();
		// 指定向GPS裝置註冊要求取得地理資訊
		lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		project.demo.arprojectdemo.lightpower.acquireWakeLock(this);
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.LeavingAlert)
				.setMessage(R.string.LeavingMessage)
				.setPositiveButton(R.string.Yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameProgressActivity.this.finish();
					}
				}).setNegativeButton(R.string.No, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setCancelable(true);

		builder.create().show();

	}

	protected void onPause() {
		super.onPause();
		// if (getService) {
		lms.removeUpdates(this); // 離開頁面時停止更新
		// mylayer.disableMyLocation();
		project.demo.arprojectdemo.lightpower.releaseWakeLock(this);
	}

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (elapsed > TIMEOUT) {
				if (isCancelled()) {
					return (null);

				}

				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							displayText("時間到，充能失敗，請重新操作");

							if ((gameProgressTask != null)
									&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
								gameProgressTask.cancel(true);
							}
							removeView();
							stageClear[6] = false;

							return;
						}
						// if(some other conditions)
						// this.cancel();
						displayText("剩餘時間: " + elapsed / 1000);
					}
				};
				timer = new Timer();
				timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);

				try {
					lastTime = 26000;
					Thread.sleep(lastTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			removeView();
			stageClear[6] = false;

		}
	}

	protected void onDestroy() {
		if ((gameProgressTask != null)
				&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
			gameProgressTask.cancel(true);
		}
		super.onDestroy();
	}

	private void displayText(final String text2) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				time_text = (TextView) findViewById(R.id.text);
				time_text.setText(text2);

			}
		});
	}

	public void removeView() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
				for (int i = 0; i < rootView.getChildCount(); i++) {
					if (rootView.getChildAt(i) == viewControl) {
						rootView.removeView(viewControl);
					}
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		settings = getPreferences(MODE_PRIVATE);
		settings = getSharedPreferences("setting", 0);
		user = settings.getString("user", "no user");

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				stagestate[1] = 3;
				stagestate[2] = 2;
				past_stage = 1;

				putdata = user + ",game1_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				putdata = user + ",game2_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 2:
				stagestate[2] = 3;
				stagestate[3] = 2;
				past_stage = 2;
				putdata = user + ",game2_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				putdata = user + ",game3_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				stagestate[3] = 3;
				stagestate[4] = 2;
				past_stage = 3;
				putdata = user + ",game3_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				putdata = user + ",game4_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				stagestate[4] = 3;
				stagestate[5] = 2;
				past_stage = 4;
				putdata = user + ",game4_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				putdata = user + ",game5_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				stagestate[5] = 3;
				past_stage = 5;
				stagestate[6] = 2;
				stagestate[7] = 2;
				stagestate[8] = 2;
				stagestate[9] = 2;
				stagestate[10] = 2;
				stagestate[11] = 2;
				putdata = user + ",game5_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game6_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game7_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game8_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game9_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game10_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game11_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				stagestate[6] = 3;
				putdata = user + ",game6_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				past_stage = 6;
				boss();
				break;
			case 7:
				stageClear[6] = data.getExtras().getBoolean("result");
				past_stage = 7;
				// controlInflater = LayoutInflater.from(getBaseContext());
				// viewControl = controlInflater.inflate(R.layout.timer, null);
				// LayoutParams layoutParamsControl = new LayoutParams(
				// LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				// this.addContentView(viewControl, layoutParamsControl);
				// gameProgressTask = new ddsGameProgressTask();
				// gameProgressTask.execute((Void) null);
				break;

			case 8:
				past_stage = 8;
				break;
			case 88:
				stagestate[8] = 3;
				past_stage = 8;
				stagestate[7] = 3;
				stageClear[6] = false;
				Toast.makeText(this, "Pass", Toast.LENGTH_SHORT).show();

				putdata = user + ",game7_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				putdata = user + ",game8_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boss();
				break;
			case 9:
				stagestate[9] = 3;
				past_stage = 9;
				putdata = user + ",game9_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 10:
				stagestate[10] = 3;
				past_stage = 10;
				putdata = user + ",game10_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boss();
				break;
			case 11:
				stagestate[11] = 3;
				past_stage = 11;
				putdata = user + ",game11_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boss();
				break;
			case 12:
				stagestate[12] = 3;
				past_stage = 12;
				putdata = user + ",game12_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		} else if (resultCode == RESULT_CANCELED) {
			switch (requestCode) {
			case 88:
				break;
			case 2:
				stagestate[2] = 3;
				stagestate[4] = 2;
				past_stage = 2;
				putdata = user + ",game2_state,3";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				putdata = user + ",game4_state,2";
				try {
					url = "http://140.119.19.15/update_user_progress.php";
					record = new PostTask().execute(putdata).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				past_stage = 2;
			default:
				break;
			}
		}

		else {
			switch (requestCode) {
			case 1:
				past_stage = 1;
				break;
			case 2:

				break;
			case 3:
				past_stage = 3;
				break;
			case 4:
				past_stage = 4;
				break;
			case 5:
				past_stage = 5;
				break;
			case 6:
				past_stage = 6;
				break;
			case 7:
				past_stage = 7;
				break;
			case 8:
				past_stage = 8;
				break;
			case 9:
				past_stage = 9;
				break;
			case 10:
				past_stage = 10;
				break;
			case 11:
				past_stage = 11;
				break;
			case 12:
				past_stage = 12;
				break;
			default:
				break;
			}
		}
	}

	public void event1(View view) {
		result = "eventA";
		if (result.contains("eventA") && stagestate[1] != 1 && past_stage != 1) {
			project.demo.arprojectdemo.CallDialog.calldialog(this, 1, result);

		}
	}

	public void event2(View view) {
		result = "eventB";
		if (result.contains("eventB") && stagestate[2] != 1 && past_stage != 2) {
			project.demo.arprojectdemo.CallDialog.calldialog(this, 2, result);
		}
	}

	public void event3(View view) {
		result = "eventC";
		if (result.contains("eventC") && stagestate[3] != 1 && past_stage != 3) {
			project.demo.arprojectdemo.CallDialog.calldialog(this, 3, result);
		}
	}

	public void boss() {
		if (stagestate[6] == 3 && stagestate[7] == 3 && stagestate[8] == 3
				&& stagestate[10] == 3 && stagestate[11] == 3) {
			stagestate[12] = 2;
			putdata = "game12_state,2";
			try {
				url = "http://140.119.19.15/update_user_progress.php";
				record = new PostTask().execute(putdata).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
