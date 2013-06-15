package project.demo.arprojectdemo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

public class StageSixCamera extends Activity {
	Uri outputFileUri;
	Bitmap bmp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_six_camera);

	}

	public void go(View view) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);// �Q��intent�h�}��android�������Ӭۤ���

		// �]�w�Ϥ����x�s��m�A�H���ɦW

		// File tmpFile = new File(
		//
		// Environment.getExternalStorageDirectory(), "image.jpg");
		//
		// outputFileUri = Uri.fromFile(tmpFile);

		/*
		 * 
		 * ��W�z���]�wput�i�h�I�M��startActivityForResult,
		 * 
		 * �O��A�]���O��ForResult�A�ҥH�b�����ۤv��acitivy�̭������n�ƼgonActivityResult
		 * 
		 * �y��A����onActivityResult
		 */

		// intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				// bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); //
				// �Q��BitmapFactory�h���o����Ӫ��Ϲ�
				bmp = (Bitmap) data.getExtras().get("data");
				Intent intent = new Intent(StageSixCamera.this,
						StageSixActivity.class);
				intent.putExtra("bmp", bmp);
				startActivityForResult(intent, 1);
				break;
			case 1:
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result", true);
				setResult(RESULT_OK, returnIntent);
				finish();
				break;
			}
		}
	}

}
