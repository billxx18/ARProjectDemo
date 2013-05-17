package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
	}

	public void onFinishBtnClick(View v) {
		this.finish();
	}
}
