package project.demo.arprojectdemo;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class StageTwoActivity extends Activity {

	ImageView skull;
	int skullCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_two);
		skull = (ImageView) findViewById(R.id.skull);
		skullCount = 3;

		skull.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (skullCount > 0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
						StageTwoActivity.this);
					builder.setTitle("遊戲提示")
							.setMessage("撿到骨頭了")
							.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();

											if (skullCount == 0) {
												skull.setImageResource(R.drawable.nccu_logo_png);
											}
										}
									});
					builder.create().show();
				}
				if (skullCount > -1) {
					skullCount--;
				}
				if (skullCount == -1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							StageTwoActivity.this);
					builder.setTitle("遊戲提示")
							.setMessage("出現拿不完[暫]的藍寶石了")
							.setPositiveButton("不知道該怎麼報答您的大恩大德",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									});
					builder.create().show();
				}

			}
		});
	}

	public void onPathInstructionYesClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Path Selected")
				.setMessage("往武器庫前進")
				.setPositiveButton("知道了",
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								Intent returnIntent = new Intent();
								returnIntent.putExtra("result", true);
								setResult(RESULT_OK, returnIntent);
								finish();							}
						});
		builder.create().show();
	}

	public void onPathInstructionNoClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Path Selected")
				.setMessage("往化骨潭前進")
				.setPositiveButton("知道了",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								Intent returnIntent = new Intent();
								setResult(RESULT_CANCELED, returnIntent);
								finish();
							}
						});
		builder.create().show();
	}

	public void onPauseClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("遊戲提示")
				.setMessage("暫停還沒實作")
				.setPositiveButton("知道了",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
		builder.create().show();
	}
}
