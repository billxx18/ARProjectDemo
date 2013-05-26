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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); // �Q��BitmapFactory�h���o����Ӫ��Ϲ�
			Intent intent2 = new Intent(StageSixCamera.this, StageSixActivity.class);
			intent2.putExtra("bmp", bmp);
			startActivity(intent2);
			StageSixCamera.this.finish();
		}
	}

	public void go(View view) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);// �Q��intent�h�}��android�������Ӭۤ���

		// �]�w�Ϥ����x�s��m�A�H���ɦW

		File tmpFile = new File(

		Environment.getExternalStorageDirectory(),

		"image.jpg");

		outputFileUri = Uri.fromFile(tmpFile);

		/*
		 * 
		 * ��W�z���]�wput�i�h�I�M��startActivityForResult,
		 * 
		 * �O��A�]���O��ForResult�A�ҥH�b�����ۤv��acitivy�̭������n�ƼgonActivityResult
		 * 
		 * �y��A����onActivityResult
		 */

		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 0);

	}
}
