package com.kamil.wildman.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kamil.ads.IActivityRequestHandler;
import com.kamil.wildman.WMGame;

public class AndroidLauncher extends AndroidApplication implements
		IActivityRequestHandler {

	protected AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_ADS: {
				adView.setVisibility(View.VISIBLE);
				break;
			}
			case HIDE_ADS: {
				adView.setVisibility(View.GONE);
				break;
			}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new WMGame(this));

		// AndroidApplicationConfiguration config = new
		// AndroidApplicationConfiguration();
		// initialize(new WMGame(), config);
		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-1094487335636091/3341350160");

		AdRequest request = new AdRequest.Builder().build();

		adView.loadAd(request);

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
