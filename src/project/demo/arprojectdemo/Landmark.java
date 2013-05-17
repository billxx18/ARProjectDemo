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
	private Context context; // �D�{��
	GeoPoint myGp;

	public Landmark(Drawable defaultMarker, GeoPoint gp) {
		super(defaultMarker);
		myGp = gp;
	}

	public Landmark(Drawable defaultMarker, Context context) {
		super(defaultMarker);
		this.context = context;

		// GeoPoint station_taipei = new GeoPoint((int) (25.047192 * 1000000),
		// (int) (121.516981 * 1000000)); // �]�w�a�Ϯy�Э�:�n��,�g��
		// GeoPoint nccu = new GeoPoint((int) (24.987597 * 1000000),
		// (int) (121.576060 * 1000000)); // �]�w�a�Ϯy�Э�:�n��,�g��

		// �[�J�a�Юy�Э�(Point)�B���D(Title)�B����(Snippet)
		// items.add(new OverlayItem(station_taipei, "�x�_����",
		// "�x�_�������P���K�B���B�T�K�@�c"));
		// items.add(new OverlayItem(nccu, "�F�v�j��", "NCCU"));
		if ((myGp != null)) {
			items.add(new OverlayItem(myGp, null, null));
		}

		populate(); // �۰ʧ����Ҧ��B�z�{�ǡA�I�screateItem���o�Ҧ��a��
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return items.get(i); // ���o��i�Ӧa��
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return items.size(); // �a���`�ƶq�A�|�Qpopulate()�I�s
	}

	@Override
	protected boolean onTap(int index) { // �I���a�Юɪ�����
		// TODO Auto-generated method stub
		// �u�X����
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // ��ܩ�D�{��main
		builder.setTitle(items.get(index).getTitle()); // �[�J���D
		builder.setMessage(items.get(index).getSnippet()); // �[�J����
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
		return super.onTap(index);
	}
}