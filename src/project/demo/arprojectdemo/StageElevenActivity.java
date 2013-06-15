package project.demo.arprojectdemo;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class StageElevenActivity extends Activity {
	private static final Integer[] goal = new Integer[] { 3, 2, 1, 0 };
	private ArrayList<Integer> column1 = new ArrayList<Integer>();
	private ArrayList<Integer> column2 = new ArrayList<Integer>();
	private ArrayList<Integer> column3 = new ArrayList<Integer>();
	ImageView[] column1_img = new ImageView[3];
	ImageView[] column2_img = new ImageView[3];
	ImageView[] column3_img = new ImageView[3];
	boolean moveAvailable = false;
	int moveItem, sum;
	ImageView path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_eleven);
		column1_img[2] = (ImageView) findViewById(R.id.column1_layer3);
		column1_img[1] = (ImageView) findViewById(R.id.column1_layer2);
		column1_img[0] = (ImageView) findViewById(R.id.column1_layer1);
		column2_img[2] = (ImageView) findViewById(R.id.column2_layer3);
		column2_img[1] = (ImageView) findViewById(R.id.column2_layer2);
		column2_img[0] = (ImageView) findViewById(R.id.column2_layer1);
		column3_img[2] = (ImageView) findViewById(R.id.column3_layer3);
		column3_img[1] = (ImageView) findViewById(R.id.column3_layer2);
		column3_img[0] = (ImageView) findViewById(R.id.column3_layer1);

		this.column2.add(0);
		this.column3.add(0);

		this.column1.add(0, 3);
		this.column1.add(1, 2);
		this.column1.add(2, 1);
		this.column1.add(3, 0);
		setBackground();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void column_1_OnClick(View view) {
		if (moveAvailable == false) {
			if (column1.get(0) != 0) {
				moveAvailable = true;
				moveItem = column1.get(0);
				column1.remove(0);
				if (moveItem == 1) {
					column1_img[column1.size() - 1]
							.setBackgroundResource(R.drawable.layer3_move);

				}
				if (moveItem == 2) {
					column1_img[column1.size() - 1]
							.setBackgroundResource(R.drawable.layer2_move);
				}
				if (moveItem == 3) {
					column1_img[column1.size() - 1]
							.setBackgroundResource(R.drawable.layer1_move);
				}

				path = column1_img[column1.size() - 1];
				setBackground();
			}
		} else {
			if (moveItem > column1.get(0)) {
				path.setBackgroundResource(android.R.color.transparent);
				column1.add(0, moveItem);
				setBackground();
				moveAvailable = false;
			}
		}
	}

	public void column_2_OnClick(View view) {
		if (moveAvailable == false) {
			if (column2.get(0) != 0) {
				moveAvailable = true;
				moveItem = column2.get(0);
				column2.remove(0);
				if (moveItem == 1) {
					column2_img[column2.size() - 1]
							.setBackgroundResource(R.drawable.layer3_move);

				}
				if (moveItem == 2) {
					column2_img[column2.size() - 1]
							.setBackgroundResource(R.drawable.layer2_move);
				}
				if (moveItem == 3) {
					column2_img[column2.size() - 1]
							.setBackgroundResource(R.drawable.layer1_move);
				}

				path = column2_img[column2.size() - 1];

				// System.arraycopy(column1,1,column1,0,n);

				setBackground();
			}
		} else {
			if (moveItem > column2.get(0)) {
				path.setBackgroundResource(android.R.color.transparent);
				column2.add(0, moveItem);
				setBackground();
				moveAvailable = false;
			}
		}
	}

	public void column_3_OnClick(View view) {
		if (moveAvailable == false) {
			if (column3.get(0) != 0) {
				moveAvailable = true;
				moveItem = column3.get(0);
				column3.remove(0);
				if (moveItem == 1) {
					column3_img[column3.size() - 1]
							.setBackgroundResource(R.drawable.layer3_move);

				}
				if (moveItem == 2) {
					column3_img[column3.size() - 1]
							.setBackgroundResource(R.drawable.layer2_move);
				}
				if (moveItem == 3) {
					column3_img[column3.size() - 1]
							.setBackgroundResource(R.drawable.layer1_move);
				}

				path = column3_img[column3.size() - 1];
				setBackground();
			}
		} else {
			if (moveItem > column3.get(0)) {
				path.setBackgroundResource(android.R.color.transparent);
				column3.add(0, moveItem);
				setBackground();
				moveAvailable = false;
				sum = 0;
				if (column3.size() == 4) {
					Toast.makeText(this, "Mission Copmlete", Toast.LENGTH_SHORT)
							.show();
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result", true);
					setResult(RESULT_OK, returnIntent);
					finish();
				}

			}
		}
	}

	public void setBackground() {
		column1.size();
		for (int i = 0; i < column1.size() - 1; i++) {
			if (column1.get(i) == 1) {
				column1_img[column1.size() - i - 2]
						.setBackgroundResource(R.drawable.layer3);
			}
			if (column1.get(i) == 2) {
				column1_img[column1.size() - i - 2]
						.setBackgroundResource(R.drawable.layer2);
			}
			if (column1.get(i) == 3) {
				column1_img[column1.size() - i - 2]
						.setBackgroundResource(R.drawable.layer1);
			}
		}
		column2.size();
		for (int i = 0; i < column2.size() - 1; i++) {
			if (column2.get(i) == 1) {
				column2_img[column2.size() - i - 2]
						.setBackgroundResource(R.drawable.layer3);
			}
			if (column2.get(i) == 2) {
				column2_img[column2.size() - i - 2]
						.setBackgroundResource(R.drawable.layer2);
			}
			if (column2.get(i) == 3) {
				column2_img[column2.size() - i - 2]
						.setBackgroundResource(R.drawable.layer1);
			}
		}
		column3.size();
		for (int i = 0; i < column3.size() - 1; i++) {
			if (column3.get(i) == 1) {
				column3_img[column3.size() - i - 2]
						.setBackgroundResource(R.drawable.layer3);
			}
			if (column3.get(i) == 2) {
				column3_img[column3.size() - i - 2]
						.setBackgroundResource(R.drawable.layer2);
			}
			if (column3.get(i) == 3) {
				column3_img[column3.size() - i - 2]
						.setBackgroundResource(R.drawable.layer1);
			}
		}

	}

}
