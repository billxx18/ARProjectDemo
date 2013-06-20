package project.demo.arprojectdemo;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class lightpower {
	private static WakeLock wakeLock;
	private static PowerManager pm;

	public static void acquireWakeLock(Context con) {
		
			Log.d("ddd", "Acquiring wake lock");
			pm = (PowerManager) con.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, con
					.getClass().getCanonicalName());
			wakeLock.acquire();
		
	}

	public  static void releaseWakeLock(Context con) {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
			wakeLock = null;
		}
	}
}
