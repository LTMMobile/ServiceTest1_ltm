package ltm.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

class LocalBinder extends Binder {
	String helloService() {
		return "hello binding Service";
	}
}

public class ServiceLTM extends Service {
    private final LocalBinder _mBinder = new LocalBinder();
	NotificationManager mNotification = null;

	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
		Log.v("ltm", "onBind" );

		showNotification();

		return _mBinder;
	}

	@Override
	public int onStartCommand( Intent intent, int flags, int startId ) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();	
        
		Log.v( "ltm", "onStartCommand" );
		
		return START_STICKY;	
	}

	private NotificationManager mNM;
	
	@Override
	public void onCreate() {
		mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		Log.v("ltm", "onCreate" );
		Toast.makeText(this, "onCreate", Toast.LENGTH_LONG ).show();
		
		super.onCreate();
	}

	@Override
	public void onDestroy() {
        //mNM.cancel( 1 );
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();	
        Log.v( "ltm", "onDestroy" );
    }

	private void showNotification() {
		Intent intent = new Intent(this, ActivityLaunched.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id1")
				.setSmallIcon(R.drawable.icon)
				.setContentTitle("Titre notif")
				.setContentText("binding .....")
				.setContentIntent(pendingIntent)
				.setStyle(new NotificationCompat.BigTextStyle().bigText("big text"))
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setAutoCancel(true);

		mNotification.notify(1000, builder.build());
    }
}
