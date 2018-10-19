package ltm.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class ActivityLaunched extends Activity {
	private Handler _handler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Toast.makeText( this, "ActivityLaunched", Toast.LENGTH_LONG ).show();

		_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				finish();
			}
		};
		new Minuterie().start();
	}
	
	private class Minuterie extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep( 3000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			_handler.sendMessage( _handler.obtainMessage( 0, null ) );
			super.run();
		}
	}
}
