package project.demo.arprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StageFiveActivity extends Activity {
	int text_number = 0;
	TextView speak_content;
	Button back;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_five);
		speak_content = (TextView) findViewById(R.id.speak_content);
		back = (Button) findViewById(R.id.finish);
	}

	public void text_speak(View view) {
		switch (text_number) {
		case 0:
			speak_content.setText("Hello,");
			text_number = text_number + 1;
			break;
		case 1:
			speak_content.setText("Nice to meet you.");
			text_number = text_number + 1;
			break;
		case 2:
			speak_content.setText("I am the priest.");
			text_number = text_number + 1;
			break;
		case 3:
			speak_content.setText("I hope you can save the world.");
			text_number = text_number + 1;
			break;
		case 4:
			speak_content
					.setText("Please collect four items to beat the BOSS.");
			text_number = text_number + 1;
			break;
		case 5:
			speak_content.setText("Go ahead.Move !! Move !! Move !!");
			text_number = text_number + 1;
			;
			break;
		case 6:
			speak_content.setVisibility(View.GONE);
			back.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

	public void back(View view) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
