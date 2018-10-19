package ltm.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/** Overview : classe d'implémentation dédiée au binding
 */
class LocalBinder extends Binder {

	String helloService() {
		return "hello service";
	}
}

public class ServiceLTM extends Service {
    
    private final LocalBinder _mBinder = new LocalBinder();

	@Override
	public int onStartCommand( Intent intent, int flags, int startId ) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();	
        
		Log.v( "ltm", "onStartCommand" );
		showNotification();
		
		return START_STICKY;	
	}

	private NotificationManager mNM;
	
	@Override
	public void onCreate() {
        mNM = (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
		
		Log.v("ltm", "onCreate" );
		
		super.onCreate();
	}

	@Override
	public void onDestroy() {
        mNM.cancel( 10000 );
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();	
        Log.v( "ltm", "onDestroy" );
    }

	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
		Log.v("ltm", "onBind" );


		return _mBinder;
	}
	
	private void showNotification() {
			
		final NotificationManager mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	
	    /*final Intent launchNotificationIntent = new Intent(this, ActivityLaunched.class);
		
	    final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
	    		launchNotificationIntent, PendingIntent.FLAG_ONE_SHOT);*/

		Notification.Builder builder = new Notification.Builder(this)
			.setWhen(System.currentTimeMillis())
			.setTicker("title")
			.setSmallIcon(R.drawable.icon)
			.setContentTitle("titre")
			.setContentText("desc")
			.setContentIntent(null);
		
		builder.setAutoCancel(true);
	
		mNotification.notify(10000, builder.build());
    }

}


































