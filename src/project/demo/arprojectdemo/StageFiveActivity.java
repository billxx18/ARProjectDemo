package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class StageFiveActivity extends Activity {
	TextView text_content;
	String[] text = {"a","b","c","d","e","f","g","h","finish"};
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_five);
		text_content = (TextView) findViewById(R.id.text_content);
	}

	public void onReturnResultBtnClick(View v) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	public void textchange(View view)
	{
		if(i<9)
		{
		text_content.setText(text[i]);
		i=i+1;
		}
	}
}
