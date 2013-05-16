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
	private MapView map; // 宣告google map物件
	private MapController mapController; // 宣告google map控制物件
	String result = "no event";
	String url = "http://140.119.19.15/gpsfindevent.php";

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
			Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); // 開啟設定頁面
		}
		setupMap();
		String tMsg = "";
		try {
			// 指定向GPS裝置註冊要求取得地理資訊
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

	private void setupMap(double longitude, double latitude) {
		GeoPoint center = new GeoPoint((int) (24.986009 * 1000000),
				(int) (121.575136 * 1000000)); // 設定地圖座標值:緯度,經度

		List<Overlay> overlays = map.getOverlays();
		Drawable point_star = getResources().getDrawable(
				android.R.drawable.star_on);
		// 設定圖示大小
		point_star.setBounds(0, 0, point_star.getMinimumWidth(),
				point_star.getMinimumHeight());

		Landmark myLandmark = new Landmark(point_star, this);
		overlays.add(myLandmark); // 將地標層加入地圖座標層中
		map.setSatellite(false);
		// .setTraffic(true)：一般地圖
		// .setSatellite(true)：衛星地圖
		// .setStreetView：街景圖

		mapController.setZoom(18); // 設定放大倍率1(地球)-21(街景)
		mapController.animateTo(center); // 指定地圖中央點
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void getLocation(Location location) { // 將定位資訊顯示在畫面中
		if (location != null) {

			Double longitude = location.getLongitude(); // 取得經度
			Double latitude = location.getLatitude(); // 取得緯度
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
			Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
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

		// 指定向GPS裝置註冊要求取得地理資訊
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
		lms.removeUpdates(this); // 離開頁面時停止更新
		super.onPause();
	}
}
