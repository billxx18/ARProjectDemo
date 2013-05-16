package project.demo.arprojectdemo;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	public void onBackBtnClick(View v) {
		this.finish();
	}
}
