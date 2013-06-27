package project.demo.arprojectdemo;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class FindEventIntent {
	Intent intent;

	public static void intent(Activity con, Integer game_id, String result) {
		Toast.makeText(con, result, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(con, ARcamera.class);
		if (game_id == 1) {
			intent.putExtra("game", "1");
			con.startActivityForResult(intent, 1);
		}
		if (game_id == 2) {
			intent = new Intent(con, StageTwoActivity.class);
			intent.putExtra("game", "2");
			con.startActivityForResult(intent, 2);
		}
		if (game_id == 3) {
			intent.putExtra("game", "3");
			con.startActivityForResult(intent, 3);
		}
		if (game_id == 4) {
			intent.putExtra("game", "4");
			con.startActivityForResult(intent, 4);
		}
		if (game_id == 5) {
			intent.putExtra("game", "5");
			con.startActivityForResult(intent, 5);
		}
		if (game_id == 6) {
			intent.putExtra("game", "6");
			con.startActivityForResult(intent, 6);
		}
		if (game_id == 7) {
			intent.putExtra("game", "7");
			con.startActivityForResult(intent, 7);
		}
		if (game_id == 8) {
			intent.putExtra("game", "8");
			con.startActivityForResult(intent, 8);
		}
		if (game_id == 88) {
			intent.putExtra("game", "88");
			con.startActivityForResult(intent, 88);
		}
		if (game_id == 9) {
			intent.putExtra("game", "9");
			con.startActivityForResult(intent, 9);
		}
		if (game_id == 10) {
			intent.putExtra("game", "10");
			con.startActivityForResult(intent, 10);
		}
		if (game_id == 11) {
			intent.putExtra("game", "11");
			con.startActivityForResult(intent, 11);
		}
		if (game_id == 12) {
			intent.putExtra("game", "12");
			con.startActivityForResult(intent, 12);
		}

	}
}
