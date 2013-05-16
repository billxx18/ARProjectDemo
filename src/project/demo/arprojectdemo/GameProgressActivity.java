package project.demo.arprojectdemo;

import java.util.ArrayList;
import java.util.List;
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
import android.view.View;
import android.widget.Toast;

public class GameProgressActivity extends MapActivity implements
		LocationListener {
	private boolean getService = false;
	private MapView map; // �ŧigoogle map����
	private MapController mapController; // �ŧigoogle map�����
	String result = "no event";
	String url = "http://140.119.19.15/gpsfindevent.php";

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
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); // �}�ҳ]�w����
		}
		setupMap();
		String tMsg = "";
		try {
			// ���w�VGPS�˸m���U�n�D���o�a�z��T
			lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0,
					this);
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

	private void setupMap(double longitude, double latitude) {
		GeoPoint center = new GeoPoint((int) (24.986009 * 1000000),
				(int) (121.575136 * 1000000)); // �]�w�a�Ϯy�Э�:�n��,�g��

		List<Overlay> overlays = map.getOverlays();
		Drawable point_star = getResources().getDrawable(
				android.R.drawable.star_on);
		// �]�w�ϥܤj�p
		point_star.setBounds(0, 0, point_star.getMinimumWidth(),
				point_star.getMinimumHeight());

		Landmark myLandmark = new Landmark(point_star, this);
		overlays.add(myLandmark); // �N�a�мh�[�J�a�Ϯy�мh��
		map.setSatellite(false);
		// .setTraffic(true)�G�@��a��
		// .setSatellite(true)�G�ìP�a��
		// .setStreetView�G�󴺹�

		mapController.setZoom(18); // �]�w��j���v1(�a�y)-21(��)
		mapController.animateTo(center); // ���w�a�Ϥ����I
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void getLocation(Location location) { // �N�w���T��ܦb�e����
		if (location != null) {

			Double longitude = location.getLongitude(); // ���o�g��
			Double latitude = location.getLatitude(); // ���o�n��
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
						StageOneActivity.class);
				startActivityForResult(intent, 1);
				GameProgressActivity.this.finish();
			}
			if (result.contains("eventB")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventC")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();

				Intent intent = new Intent(GameProgressActivity.this,
						StageOneActivity.class);
				startActivityForResult(intent, 1);
				GameProgressActivity.this.finish();
			}
			if (result.contains("eventD")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventE")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventF")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventG")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventH")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				// Intent intent =new Intent(MainActivity.this,event1.class);
				// startActivity(intent);
			}
			if (result.contains("eventz")) {
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(GameProgressActivity.this,
						StageOneActivity.class);
				startActivityForResult(intent, 1);
				GameProgressActivity.this.finish();
			}
		} else {
			Toast.makeText(this, "�L�k�w��y��", Toast.LENGTH_LONG).show();
			// Intent intent =new Intent(this,event1.class);
			// startActivity(intent);
		}
		// Intent intent =new Intent(this,event1.class);
		// startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			GameProgressActivity.this.finish();
			break;
		default:
			break;
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

		// ���w�VGPS�˸m���U�n�D���o�a�z��T
		lms.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);

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
		super.onPause();
	}
}
