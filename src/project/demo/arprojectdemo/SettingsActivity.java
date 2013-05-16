package project.demo.arprojectdemo;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class SettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}
	
	public void onBackBtnClick(View v){
		this.finish();
	}
}
