package ltm.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class ActivityLaunching extends Activity {
	private ServiceConnection _connection = null;
	private LocalBinder _interfaceWithService = null;
	
    /** Called when the activity is first created. */
    @SuppressLint("ClickableViewAccessibility")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		createNotificationChannel();
        
        final Button b_start = (Button)findViewById( R.id.Button01 );
        final Button b_stop = (Button)findViewById( R.id.Button02 );
        final Button b_bind = (Button)findViewById( R.id.button_bind_service );
        final Button b_method = (Button)findViewById( R.id.button_method_service );
        b_stop.setEnabled(false);
        b_bind.setEnabled(false);
        b_method.setEnabled(false);
        
        // Start service
        b_start.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View arg0 ) {
				Intent intent = new Intent( ActivityLaunching.this, ServiceLTM.class);
				ActivityLaunching.this.startService( intent );
				
				b_stop.setEnabled(true);
		        b_bind.setEnabled(true);
		        //b_method.setEnabled(true);
				b_start.setEnabled(false);
			}} );

        // Stop service
        b_stop.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View arg0 ) {
				Intent intent = new Intent( ActivityLaunching.this, ServiceLTM.class);
				//ActivityLaunching.this.unbindService(_connection);
				ActivityLaunching.this.stopService( intent );
				_connection = null;
				_interfaceWithService = null;
				
				b_stop.setEnabled(false);
		        b_bind.setEnabled(false);
		        b_method.setEnabled(false);
				b_start.setEnabled(true);
			}} );
        
         // Call Methode service
        b_method.setOnTouchListener( new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if( _connection == null )
					return false;
				
				if( _interfaceWithService != null && event.getAction() == MotionEvent.ACTION_UP ) {
					Log.v( "ltm",_interfaceWithService.helloService() ); // CALL METHOD
					Toast.makeText( ActivityLaunching.this, "Method Called",
							Toast.LENGTH_SHORT ).show();
				}
				
				return true;
			}
		});

		// Bind to service
		b_bind.setOnTouchListener( new OnTouchListener() {
			@Override
			public boolean onTouch( View v, MotionEvent event ) {
				if( event.getAction() == MotionEvent.ACTION_UP ) {
					Intent intent = new Intent( ActivityLaunching.this, ServiceLTM.class);

					if( _connection != null )
						return false;

					_connection = new MaConnection();

					ActivityLaunching.this.bindService( intent, _connection, 0 );
					b_method.setEnabled(true);
					b_bind.setEnabled(false);
					return true;
				}
				return false;
			}
		});
	}

    class MaConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			_interfaceWithService = (LocalBinder) service;
			Log.v("ltm", "onServiceConnected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			_interfaceWithService = null;
			Log.v("ltm", "onServiceDisconnected");
		}
	}

	public void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "channel1";
			String description = "description channel1";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("id1", name, importance);
			channel.setDescription(description);
			channel.setLightColor(Color.CYAN);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
}