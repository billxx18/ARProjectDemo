package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class ExtraGamesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extra_games);
	}

	public void onBackBtnClick(View v) {
		this.finish();
	}

}
