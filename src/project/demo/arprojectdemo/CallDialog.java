package project.demo.arprojectdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CallDialog extends Activity {
	private static ImageView scene_img;
	private static TextView scene_intr;
	private static boolean decide;

	public static boolean calldialog(final Activity con, final Integer game_id,
			final String result) {
		LayoutInflater inflater = con.getLayoutInflater();
		View layout = inflater.inflate(R.layout.activity_dialog, null);
		scene_img = (ImageView) layout.findViewById(R.id.scene_img);
		scene_intr = (TextView) layout.findViewById(R.id.scene_intr);
		if (game_id == 1) {
			scene_img.setBackgroundResource(R.drawable.scene1);
			scene_intr.setText("這是第一關");
		}
		if (game_id == 2) {
			scene_img.setBackgroundResource(R.drawable.skeleton_button);
			scene_intr.setText("這是第二關");
		}
		if (game_id == 3) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第三關");
		}
		if (game_id == 4) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第四關");
		}
		if (game_id == 5) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第五關");
		}
		if (game_id == 6) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第六關");
		}
		if (game_id == 7) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第七關");
		}
		if (game_id == 8) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第八關");
		}
		if (game_id == 9) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第九關");
		}
		if (game_id == 10) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第十關");
		}
		if (game_id == 11) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是第十一關");
		}
		if (game_id == 12) {
			scene_img.setBackgroundResource(R.drawable.scene3);
			scene_intr.setText("這是最後一關");
		}

		new AlertDialog.Builder(con)
				.setTitle("關卡遭遇")
				.setView(layout)
				.setView(layout)
				.setPositiveButton("接受", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						decide = true;
						project.demo.arprojectdemo.FindEventIntent.intent(con,
								game_id, result);
						// TODO Auto-generated method stub

					}
				})
				.setNegativeButton("放棄", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						decide = false;
					}
				}).setCancelable(false).show();

		return decide;

	}
}
