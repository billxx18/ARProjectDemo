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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageButton Games, GamesNew, GamesContinue, Settings, Rankings,
			ExtrasGames, About, leaveBtn;
	LinearLayout menu, load_games;
	ImageView logo;
	int menuStair;
	String url;
	SharedPreferences settings;
	int user_id ;
	String user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewInitial();
		menuInitial();

		
		
		
		user = getIntent().getStringExtra("user_id");
//		user_id = Integer.parseInt(user);
//		 String user = Integer.toString(user_id);
		 Log.e("sss", user );
		Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
		try {
			url = "http://140.119.19.15/update_fetch_user_id.php";
			new PostTask().execute(user).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void viewInitial() {
		menu = (LinearLayout) findViewById(R.id.menu);
		logo = (ImageView) findViewById(R.id.menu_logo);
		load_games = (LinearLayout) findViewById(R.id.load_games);
		Games = (ImageButton) findViewById(R.id.menu_games);
		GamesNew = (ImageButton) findViewById(R.id.menu_game_new);
		GamesContinue = (ImageButton) findViewById(R.id.menu_game_continue);
		ExtrasGames = (ImageButton) findViewById(R.id.menu_extrasgames);
		Settings = (ImageButton) findViewById(R.id.menu_settings);
		Rankings = (ImageButton) findViewById(R.id.menu_rankings);
		About = (ImageButton) findViewById(R.id.menu_about);
		leaveBtn = (ImageButton) findViewById(R.id.game_bk);
	}

	private void menuInitial() {
		leaveBtn.setVisibility(View.VISIBLE);
		menu.setVisibility(View.VISIBLE);
		GamesNew.setVisibility(View.GONE);
		GamesContinue.setVisibility(View.GONE);
		ExtrasGames.setVisibility(View.GONE);

		Games.setVisibility(View.VISIBLE);
		About.setVisibility(View.VISIBLE);
		Settings.setVisibility(View.VISIBLE);
		Rankings.setVisibility(View.VISIBLE);
		menuStair = 0;
	}

	public void onFirstStairMenuBtnClick(View v) {
		switch (v.getId()) {
		case R.id.menu_games:
			menu.setVisibility(View.GONE);
			load_games.setVisibility(View.VISIBLE);
			GamesNew.setVisibility(View.VISIBLE);
			GamesContinue.setVisibility(View.VISIBLE);
			ExtrasGames.setVisibility(View.VISIBLE);
			menuStair = 1;
			break;
		case R.id.menu_rankings:
			Intent rankingsIntent = new Intent();
			rankingsIntent.setClass(MainActivity.this, RankingsActivity.class);
			startActivity(rankingsIntent);
			break;
		case R.id.menu_settings:
			Intent settingsIntent = new Intent();
			settingsIntent.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(settingsIntent);
			break;
		case R.id.menu_about:
			Intent AboutIntent = new Intent();
			AboutIntent.setClass(MainActivity.this, AboutActivity.class);
			startActivity(AboutIntent);
			break;
		case R.id.game_bk:
			leavingAlert(this);
			break;
		}
	}

	public void onSecondStairMenuBtnClick(View v) {
		switch (v.getId()) {
		case R.id.menu_game_new:
			Intent newGamesIntent = new Intent();
			newGamesIntent.setClass(MainActivity.this,
					GameProgressActivity.class);
			startActivity(newGamesIntent);
			break;
		case R.id.menu_game_continue:
			break;
		case R.id.menu_extrasgames:
			Intent extraGamesIntent = new Intent();
			extraGamesIntent.setClass(MainActivity.this,
					ExtraGamesActivity.class);
			startActivity(extraGamesIntent);
			break;
		}
	}

	public void leavingAlert(Context con) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle(R.string.LeavingAlert)
				.setMessage(R.string.LeavingMessage)
				.setPositiveButton(R.string.Yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.finish();
					}
				}).setNegativeButton(R.string.No, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setCancelable(true);

		builder.create().show();
	}

	@Override
	public void onBackPressed() {
		switch (menuStair) {
		case 0:
			leavingAlert(this);
			break;
		case 1:
			menuInitial();
			break;
		}
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		project.demo.arprojectdemo.lightpower.acquireWakeLock(this);

	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		project.demo.arprojectdemo.lightpower.releaseWakeLock(this);

	}

	public class PostTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			settings = getPreferences(MODE_PRIVATE);
			settings = getSharedPreferences("setting", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("user", params[0]);  
			settings = getSharedPreferences("setting", 0);
			editor.commit();
			return sendPostDataToInternet(params[0]); 
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
}
