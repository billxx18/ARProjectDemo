package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class StageTwoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_two);
	}

	public void onReturnResultBtnClick(View v) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

}
