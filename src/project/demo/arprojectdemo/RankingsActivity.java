package project.demo.arprojectdemo;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class RankingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rankings);
	}

	public void onBackBtnClick(View v) {
		this.finish();
	}
}
