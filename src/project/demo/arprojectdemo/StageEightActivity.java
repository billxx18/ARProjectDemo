package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StageEightActivity extends Activity {
	Button onTakeEnergyBtnClick, onReturnResultBtnClick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_eight);

		Boolean stage_eight_state = getIntent().getExtras().getBoolean("state");
		onTakeEnergyBtnClick = (Button) findViewById(R.id.onTakeEnergyBtnClick);
		onReturnResultBtnClick = (Button) findViewById(R.id.onReturnResultBtnClick);
		if (stage_eight_state == true) {
			onTakeEnergyBtnClick.setVisibility(View.VISIBLE);
			onReturnResultBtnClick.setVisibility(View.GONE);
		} else {
			onTakeEnergyBtnClick.setVisibility(View.GONE);
			onReturnResultBtnClick.setVisibility(View.VISIBLE);
		}
	}

	public void onTakeEnergyBtnClick(View v) {
		finish();

	}

	public void onReturnResultBtnClick(View v) {
		finish();

	}

}
