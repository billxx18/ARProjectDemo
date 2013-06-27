package project.demo.arprojectdemo;

import java.util.ArrayList;
import java.util.Collections;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StageSixActivity extends Activity {
	int smallimage_Numbers = 9;
	private TextView moveCounter;
	private TextView feedbackText;
	private Button[] buttons;
	private Boolean bad_move = false;
	private static final Integer[] goal = new Integer[] { 0, 1, 2, 3, 4, 5, 6,
			7, 8 };
	Button[] b = new Button[9];
	ArrayList<Bitmap> blockimages;
	private ArrayList<Integer> cells = new ArrayList<Integer>();
	Uri outputFileUri;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_six);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		Bitmap picture_bmp = (Bitmap) extras.get("bmp");
		ImageView image = (ImageView) findViewById(R.id.source_image);

		image.setImageBitmap(picture_bmp);
		buttons = findButtons();

		for (int i = 0; i < 9; i++) {
			this.cells.add(i);
		}
		Collections.shuffle(this.cells); // random cells array

		fill_grid();

		moveCounter = (TextView) findViewById(R.id.MoveCounter);
		feedbackText = (TextView) findViewById(R.id.FeedbackText);

		for (int i = 0; i < 9; i++) {
			buttons[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					makeMove((Button) v);
				}
			});
		}
		moveCounter.setText("0");
		feedbackText.setText(R.string.game_feedback_text);
		/*
		 * 
		 * 把上述的設定put進去！然後startActivityForResult,
		 * 
		 * 記住，因為是有ForResult，所以在本身自己的acitivy裡面等等要複寫onActivityResult
		 * 
		 * 稍後再說明onActivityResult
		 */

	}

	public Button[] findButtons() {

		ImageView image = (ImageView) findViewById(R.id.source_image);
		blockimages = splitImage(image, smallimage_Numbers);

		b[0] = (Button) findViewById(R.id.Button00);
		b[0].setBackgroundResource(android.R.color.transparent);

		b[1] = (Button) findViewById(R.id.Button01);
		BitmapDrawable drawable1 = new BitmapDrawable(blockimages.get(1));
		b[1].setBackgroundDrawable(drawable1);

		b[2] = (Button) findViewById(R.id.Button02);
		BitmapDrawable drawable2 = new BitmapDrawable(blockimages.get(2));
		b[2].setBackgroundDrawable(drawable2);

		b[3] = (Button) findViewById(R.id.Button03);
		BitmapDrawable drawable3 = new BitmapDrawable(blockimages.get(3));
		b[3].setBackgroundDrawable(drawable3);

		b[4] = (Button) findViewById(R.id.Button04);
		BitmapDrawable drawable4 = new BitmapDrawable(blockimages.get(4));
		b[4].setBackgroundDrawable(drawable4);

		b[5] = (Button) findViewById(R.id.Button05);
		BitmapDrawable drawable5 = new BitmapDrawable(blockimages.get(5));
		b[5].setBackgroundDrawable(drawable5);

		b[6] = (Button) findViewById(R.id.Button06);
		BitmapDrawable drawable6 = new BitmapDrawable(blockimages.get(6));
		b[6].setBackgroundDrawable(drawable6);

		b[7] = (Button) findViewById(R.id.Button07);
		BitmapDrawable drawable7 = new BitmapDrawable(blockimages.get(7));
		b[7].setBackgroundDrawable(drawable7);

		b[8] = (Button) findViewById(R.id.Button08);
		BitmapDrawable drawable8 = new BitmapDrawable(blockimages.get(8));
		b[8].setBackgroundDrawable(drawable8);
		return b;
	}

	public void makeMove(final Button b) {
		bad_move = true;
		int b_text, b_pos, zuk_pos;
		b_text = Integer.parseInt((String) b.getText());
		b_pos = find_pos(b_text);
		zuk_pos = find_pos(0);
		switch (zuk_pos) {
		case (0):
			if (b_pos == 1 || b_pos == 3)
				bad_move = false;
			break;
		case (1):
			if (b_pos == 0 || b_pos == 2 || b_pos == 4)
				bad_move = false;
			break;
		case (2):
			if (b_pos == 1 || b_pos == 5)
				bad_move = false;
			break;
		case (3):
			if (b_pos == 0 || b_pos == 4 || b_pos == 6)
				bad_move = false;
			break;
		case (4):
			if (b_pos == 1 || b_pos == 3 || b_pos == 5 || b_pos == 7)
				bad_move = false;
			break;
		case (5):
			if (b_pos == 2 || b_pos == 4 || b_pos == 8)
				bad_move = false;
			break;
		case (6):
			if (b_pos == 3 || b_pos == 7)
				bad_move = false;
			break;
		case (7):
			if (b_pos == 4 || b_pos == 6 || b_pos == 8)
				bad_move = false;
			break;
		case (8):
			if (b_pos == 5 || b_pos == 7)
				bad_move = false;
			break;
		}

		if (bad_move == true) {
			feedbackText.setText("Move Not Allowed");
			return;
		}
		feedbackText.setText("Move OK");
		cells.remove(b_pos);
		cells.add(b_pos, 0);
		cells.remove(zuk_pos);
		cells.add(zuk_pos, b_text);

		fill_grid();
		moveCounter.setText(Integer.toString(Integer
				.parseInt((String) moveCounter.getText()) + 1));

		for (int i = 0; i < 9; i++) {
			if (cells.get(i) != goal[i]) {
				return;
			}
		}
		feedbackText.setText("we have a winner");
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void fill_grid() {

		for (int i = 0; i < 9; i++) {
			int text = cells.get(i);
			switch (i) {
			case (0):
				b[0].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable = new BitmapDrawable(
							blockimages.get(text));
					b[0].setBackgroundDrawable(drawable);
				} else
					b[0].setBackgroundResource(android.R.color.transparent);
				break;

			case (1):
				b[1].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable1 = new BitmapDrawable(
							blockimages.get(text));
					b[1].setBackgroundDrawable(drawable1);
				} else
					b[1].setBackgroundResource(android.R.color.transparent);
				break;
				
			case (2):
				b[2].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable2 = new BitmapDrawable(
							blockimages.get(text));
					b[2].setBackgroundDrawable(drawable2);
				} else
					b[2].setBackgroundResource(android.R.color.transparent);
				break;

			case (3):
				b[3].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable3 = new BitmapDrawable(
							blockimages.get(text));
					b[3].setBackgroundDrawable(drawable3);
				} else
					b[3].setBackgroundResource(android.R.color.transparent);
				break;

			case (4):
				b[4].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable4 = new BitmapDrawable(
							blockimages.get(text));
					b[4].setBackgroundDrawable(drawable4);
				} else
					b[4].setBackgroundResource(android.R.color.transparent);
				break;

			case (5):
				b[5].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable5 = new BitmapDrawable(
							blockimages.get(text));
					b[5].setBackgroundDrawable(drawable5);
				} else
					b[5].setBackgroundResource(android.R.color.transparent);
				break;
				
			case (6):
				b[6].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable6 = new BitmapDrawable(
							blockimages.get(text));
					b[6].setBackgroundDrawable(drawable6);
				} else
					b[6].setBackgroundResource(android.R.color.transparent);
				break;

			case (7):
				b[7].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable7 = new BitmapDrawable(
							blockimages.get(text));
					b[7].setBackgroundDrawable(drawable7);
				} else
					b[7].setBackgroundResource(android.R.color.transparent);
				break;

			case (8):
				b[8].setText(String.valueOf(text));
				if (text != 0) {

					BitmapDrawable drawable8 = new BitmapDrawable(
							blockimages.get(text));
					b[8].setBackgroundDrawable(drawable8);
				} else
					b[8].setBackgroundResource(android.R.color.transparent);
				break;

			}

		}

	}

	public int find_pos(int element) {
		int i = 0;
		for (i = 0; i < 9; i++) {
			if (cells.get(i) == element) {
				break;
			}
		}
		return i;
	}

	private ArrayList<Bitmap> splitImage(ImageView image, int smallimage_Numbers) {

		// For the number of rows and columns of the grid to be displayed

		int rows, cols;

		// For height and width of the small image smallimage_s

		int smallimage_Height, smallimage_Width;

		// To store all the small image smallimage_s in bitmap format in this
		// list

		ArrayList<Bitmap> smallimages = new ArrayList<Bitmap>(
				smallimage_Numbers);

		// Getting the scaled bitmap of the source image

		BitmapDrawable mydrawable = (BitmapDrawable) image.getDrawable();

		Bitmap bitmap = mydrawable.getBitmap();

		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
				bitmap.getWidth(), bitmap.getHeight(), true);

		rows = cols = (int) Math.sqrt(smallimage_Numbers);

		smallimage_Height = bitmap.getHeight() / rows;

		smallimage_Width = bitmap.getWidth() / cols;

		// xCo and yCo are the pixel positions of the image smallimage_s

		int yCo = 0;

		for (int x = 0; x < rows; x++) {

			int xCo = 0;

			for (int y = 0; y < cols; y++) {

				smallimages.add(Bitmap.createBitmap(scaledBitmap, xCo, yCo,
						smallimage_Width, smallimage_Height));

				xCo += smallimage_Width;

			}

			yCo += smallimage_Height;

		}
		return smallimages;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			Bitmap bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); // 利用BitmapFactory去取得剛剛拍照的圖像

			ImageView image = (ImageView) findViewById(R.id.source_image);

			image.setImageBitmap(bmp);
			buttons = findButtons();

			for (int i = 0; i < 9; i++) {
				this.cells.add(i);
			}
			Collections.shuffle(this.cells); // random cells array

			fill_grid();

			moveCounter = (TextView) findViewById(R.id.MoveCounter);
			feedbackText = (TextView) findViewById(R.id.FeedbackText);

			for (int i = 0; i < 9; i++) {
				buttons[i].setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						makeMove((Button) v);
					}
				});
			}
			moveCounter.setText("0");
			feedbackText.setText(R.string.game_feedback_text);

		}

	}

	public void finish(View view) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", true);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

}