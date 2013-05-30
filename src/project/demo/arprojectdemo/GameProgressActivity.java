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
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
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
	private MapView map; // �ŧigoogle map����
	private MapController mapController; // �ŧigoogle map�����
	String result = "no event";
	String url = "http://140.119.19.15/gpsfindevent.php";
	private boolean[] stageAvailability = { true, false, false };
	private boolean[] stageClear = { false, false, false, false, false, false,
			false, false, false };
	boolean stage_eight_state = false;
	LayoutInflater controlInflater = null;
	long INTERVAL = 1000;
	final static long TIMEOUT = 0;
	long elapsed = 10000;
	Timer timer, timer2;
	TimerTask task;
	int random, lastTime, emergeInterval;
	TextView time_text;
	Boolean time_state;
	View viewControl;
	ddsGameProgressTask gameProgressTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_progress);

		 map = (MapView) findViewById(R.id.mapView); // ���Jgoogle map����
		 mapController = map.getController(); // �]�w���map����
		 LocationManager status = (LocationManager) (this
		 .getSystemService(Context.LOCATION_SERVICE));
		 if (status.isProviderEnabled(LocationManager.GPS_PROVIDER)
		 || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
		 // �p�GGPS�κ����w��}�ҡA�I�slocationServiceInitial()��s��m
		 locationServiceInitial();
		 } else {
		 Toast.makeText(this, "�ж}�ҩw��A��", Toast.LENGTH_LONG).show();
		 startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		 // �}�ҳ]�w����
		 }
		 setupMap();
		 String tMsg = "";
		 try {
		 // ���w�VGPS�˸m���U�n�D���o�a�z��T
		 lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		 } catch (Exception e) {
		 tMsg = e.getMessage();
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
		lms = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // ���o�t�Ωw��A��
		Location location = lms.getLastKnownLocation(bestProvider); // �ϥ�GPS�w��y��
		if (lms.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
			bestProvider = LocationManager.NETWORK_PROVIDER;
			location = lms.getLastKnownLocation(bestProvider);
		}
		location.getLongitude();
		location.getLatitude();
	}

	public void onLocationChanged(Location location) { // ��a�I���ܮ�
		getLocation(location);
		setupMap(location.getLongitude(), location.getLatitude());

	}

	public void onProviderDisabled(String arg0) { // ��GPS�κ����w��\��������
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String arg0) { // ��GPS�κ����w��\��}��
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) { // �w�쪬�A����
		// TODO Auto-generated method stub

	}

	private void setupMap() {
		setupMap(lms.getLastKnownLocation(bestProvider).getLongitude(), lms
				.getLastKnownLocation(bestProvider).getLatitude()); // �]�w�a�Ϲw�]��
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
		// (int) (121.575136 * 1000000)); // �]�w�a�Ϯy�Э�:�n��,�g��
		GeoPoint center = new GeoPoint((int) (latitude * 1000000),
				(int) (longitude * 1000000));
		List<Overlay> overlays = map.getOverlays();
		Drawable point_star = getResources().getDrawable(
				android.R.drawable.star_on);
		// �]�w�ϥܤj�p
		point_star.setBounds(0, 0, point_star.getMinimumWidth(),
				point_star.getMinimumHeight());

		Landmark myLandmark = new Landmark(point_star, center);
		if (center != null) {
			overlays.add(myLandmark); // �N�a�мh�[�J�a�Ϯy�мh��
		}
		map.setSatellite(false);
		// .setTraffic(true)�G�@��a��
		// .setSatellite(true)�G�ìP�a��
		// .setStreetView�G�󴺹�

		mapController.setZoom(18); // �]�w��j���v1(�a�y)-21(��)
		map.setBuiltInZoomControls(true);
		mapController.animateTo(center); // ���w�a�Ϥ����I
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void getLocation(Location location) { // �N�w���T��ܦb�e����
		if (location != null) {

			TextView longitude_txt = (TextView) findViewById(R.id.longitude);
			TextView latitude_txt = (TextView) findViewById(R.id.latitude);

			Double longitude = location.getLongitude(); // ���o�g��
			Double latitude = location.getLatitude(); // ���o�n��
			longitude_txt.setText(String.valueOf(longitude));
			latitude_txt.setText(Double.toString(latitude));
			String longitude_text = String.valueOf(longitude);
			String latitude_text = Double.toString(latitude);
			String msg = longitude_text + "," + latitude_text;

			try {
				result = new PostTask().execute(msg).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (result.contains("eventA")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "1");
				startActivityForResult(intent, 1);

			}
			if (result.contains("eventB")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						StageTwoActivity.class);
				intent.putExtra("game", "2");
				startActivityForResult(intent, 2);
			}
			if (result.contains("eventC")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "3");
				startActivityForResult(intent, 3);
			}
			if (result.contains("eventD")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "4");
				startActivityForResult(intent, 4);
			}
			if (result.contains("eventE")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "5");
				startActivityForResult(intent, 5);

			}
			if (result.contains("eventF")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "6");
				startActivityForResult(intent, 6);

			}
			if (result.contains("eventG")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);
				intent.putExtra("game", "7");
				startActivityForResult(intent, 7);

			}
			if (result.contains("eventH")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						ARcamera.class);

				if (stageClear[6] == true) {

					if ((gameProgressTask != null)
							&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
						gameProgressTask.cancel(true);
					}
					removeView();
					intent.putExtra("game", "88");
					startActivityForResult(intent, 88);

				} else {
					intent.putExtra("game", "8");
					startActivityForResult(intent, 8);
				}

			}
			if (result.contains("eventI")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();

			}
			if (result.contains("eventz")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();

			}
		} else {
			Toast.makeText(this, "�L�k�w��y��", Toast.LENGTH_LONG).show();

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
		// ���w�VGPS�˸m���U�n�D���o�a�z��T
		 lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

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
		 lms.removeUpdates(this); // ���}�����ɰ����s
		// mylayer.disableMyLocation();

	}

	private class ddsGameProgressTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (elapsed > TIMEOUT) {
				if (isCancelled()) {
					return (null);
				}
				// elapsed = 10000;
				// INTERVAL = 1000;

				task = new TimerTask() {

					@Override
					public void run() {

						elapsed = elapsed - INTERVAL;
						if (elapsed <= TIMEOUT) {
							this.cancel();
							displayText("Time Out");
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
						displayText("seconds elapsed: " + elapsed / 1000);
					}
				};
				timer = new Timer();
				timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);

				try {
					lastTime = 10000;
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
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				stageClear[0] = data.getExtras().getBoolean("result");
				break;
			case 2:
				stageClear[1] = data.getExtras().getBoolean("result");
				break;
			case 3:

				stageClear[2] = data.getExtras().getBoolean("result");
				if (stageClear[2]) {
			
				}

				// stageAvailability[2] = false;
				// Toast.makeText(this, "All Stages Completed!",
				// Toast.LENGTH_LONG)
				// .show();
				// Intent toResultIntent = new Intent();
				// toResultIntent.setClass(GameProgressActivity.this,
				// ResultActivity.class);
				// startActivity(toResultIntent);
				// GameProgressActivity.this.finish();

				break;
			case 4:
				stageClear[3] = data.getExtras().getBoolean("result");
				break;
			case 5:
				stageClear[4] = data.getExtras().getBoolean("result");
				break;
			case 6:
				stageClear[5] = data.getExtras().getBoolean("result");
				break;
			case 7:
				stageClear[6] = data.getExtras().getBoolean("result");
				controlInflater = LayoutInflater.from(getBaseContext());
				viewControl = controlInflater.inflate(R.layout.timer, null);
				LayoutParams layoutParamsControl = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				this.addContentView(viewControl, layoutParamsControl);
				gameProgressTask = new ddsGameProgressTask();
				gameProgressTask.execute((Void) null);
				break;
			case 8:
				// stageClear[7] = data.getExtras().getBoolean("result");

				break;
			case 88:
				stageClear[7] = data.getExtras().getBoolean("result");
				if (stageClear[7]) {
					Toast.makeText(this, "Pass", Toast.LENGTH_LONG).show();
				}
				break;
			case 9:
				stageClear[8] = data.getExtras().getBoolean("result");
				break;
			default:
				break;
			}
		}
		if (resultCode == RESULT_CANCELED) {
			switch (requestCode) {
			case 88:
				Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
				break;
			}

		}
	}

	public void event8(View view) {

		Intent intent = new Intent(GameProgressActivity.this,
				ARcamera.class);

		if (stageClear[6] == true) {

			if ((gameProgressTask != null)
					&& (gameProgressTask.getStatus() != AsyncTask.Status.FINISHED)) {
				gameProgressTask.cancel(true);
			}
			removeView();
			intent.putExtra("game", "88");
			startActivityForResult(intent, 88);

		} else {
			intent.putExtra("game", "8");
			startActivityForResult(intent, 8);
		}

	}

	public void event7(View view) {
		Intent intent = new Intent(GameProgressActivity.this,
				ARcamera.class);
		intent.putExtra("game", "7");
		startActivityForResult(intent, 7);

	}
}
