package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	ImageButton Games, GamesNew, GamesContinue, Settings, Rankings,
			ExtrasGames, About, leaveBtn;
	LinearLayout menu, load_games;
	ImageView logo;
	int menuStair;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewInitial();
		menuInitial();
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

}
