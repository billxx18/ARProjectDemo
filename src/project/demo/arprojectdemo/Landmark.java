package project.demo.arprojectdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

class Landmark extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private Context context; // 主程式
	GeoPoint myGp;

	public Landmark(Drawable defaultMarker, GeoPoint gp) {
		super(defaultMarker);
		myGp = gp;
	}

	public Landmark(Drawable defaultMarker, Context context) {
		super(defaultMarker);
		this.context = context;

		// GeoPoint station_taipei = new GeoPoint((int) (25.047192 * 1000000),
		// (int) (121.516981 * 1000000)); // 設定地圖座標值:緯度,經度
		// GeoPoint nccu = new GeoPoint((int) (24.987597 * 1000000),
		// (int) (121.576060 * 1000000)); // 設定地圖座標值:緯度,經度

		// 加入地標座標值(Point)、標題(Title)、說明(Snippet)
		// items.add(new OverlayItem(station_taipei, "台北車站",
		// "台北火車站與高鐵、捷運三鐵共構"));
		// items.add(new OverlayItem(nccu, "政治大學", "NCCU"));
		if ((myGp != null)) {
			items.add(new OverlayItem(myGp, null, null));
		}

		populate(); // 自動完成所有處理程序，呼叫createItem取得所有地標
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return items.get(i); // 取得第i個地標
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return items.size(); // 地標總數量，會被populate()呼叫
	}

	@Override
	protected boolean onTap(int index) { // 點擊地標時的反應
		// TODO Auto-generated method stub
		// 彈出視窗
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // 顯示於主程式main
		builder.setTitle(items.get(index).getTitle()); // 加入標題
		builder.setMessage(items.get(index).getSnippet()); // 加入說明
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
		return super.onTap(index);
	}
}