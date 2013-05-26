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

			bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); // 利用BitmapFactory去取得剛剛拍照的圖像
			Intent intent2 = new Intent(StageSixCamera.this, StageSixActivity.class);
			intent2.putExtra("bmp", bmp);
			startActivity(intent2);
			StageSixCamera.this.finish();
		}
	}

	public void go(View view) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);// 利用intent去開啟android本身的照相介面

		// 設定圖片的儲存位置，以及檔名

		File tmpFile = new File(

		Environment.getExternalStorageDirectory(),

		"image.jpg");

		outputFileUri = Uri.fromFile(tmpFile);

		/*
		 * 
		 * 把上述的設定put進去！然後startActivityForResult,
		 * 
		 * 記住，因為是有ForResult，所以在本身自己的acitivy裡面等等要複寫onActivityResult
		 * 
		 * 稍後再說明onActivityResult
		 */

		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 0);

	}
}
