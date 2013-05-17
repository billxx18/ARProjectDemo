package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class StageOneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_one);
	}

	public void onReturnResultBtnClick(View v) {
		setResult(RESULT_OK);
		finish();
	}

}
