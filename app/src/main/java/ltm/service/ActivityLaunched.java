package ltm.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class ActivityLaunched extends Activity {
	private Handler _handler = null;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Toast.makeText( this, "ActivityLaunched", Toast.LENGTH_LONG ).show();

		_handler = new Handler(Looper.myLooper()) {
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
				Thread.sleep( 5000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			_handler.sendEmptyMessage(0);
			super.run();
		}
	}
}
