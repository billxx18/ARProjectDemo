package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class StageEightActivity extends Activity {
	Button onTakeEnergyBtnClick, onReturnResultBtnClick;
	TextView hint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_eight);

		Boolean state = getIntent().getExtras().getBoolean("state");
		//
		onTakeEnergyBtnClick = (Button) findViewById(R.id.onTakeEnergyBtnClick);
		onReturnResultBtnClick = (Button) findViewById(R.id.onReturnResultBtnClick);
		hint = (TextView) findViewById(R.id.hint);
		//
		if (state == true) {
			onTakeEnergyBtnClick.setVisibility(View.VISIBLE);
			onReturnResultBtnClick.setVisibility(View.GONE);
			hint.setText("Please press the button to take the energy ");
		} else {
			onTakeEnergyBtnClick.setVisibility(View.GONE);
			onReturnResultBtnClick.setVisibility(View.VISIBLE);
			hint.setText("Please turn on the switch to restore energy");
		}
	}

	public void onTakeEnergyBtnClick(View v) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void onReturnResultBtnClick(View v) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

}
