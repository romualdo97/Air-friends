package com.worfu.airfriends.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.worfu.airfriends.AFriends;
import com.worfu.helpers.AdActionResolver;

public class AndroidLauncher extends AndroidApplication implements AdActionResolver {
	
	protected View gameView;
	protected AdView adView;
	protected InterstitialAd interstitialAd;
	
	// Constants
	protected final int SHOW_ADS = 1;
	protected final int HIDE_ADS = 0;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useImmersiveMode = true;
		
		windowConfigurations();
		
		RelativeLayout layout = new RelativeLayout(this);
		
		
		gameView = initializeForView(new AFriends(this), config);
		
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        interstitialAd = initInterstitialAd(AdsID.INTERSTITIAL_DEMO);
        interstitialAd.setAdListener(new AdListener() {

        	@Override
        	public void onAdLoaded() {
        		super.onAdLoaded();
        		Toast.makeText(getApplicationContext(), "Interstitial loaded!!!", Toast.LENGTH_SHORT).show();
        	}
        	
			@Override
			public void onAdClosed() {
				super.onAdClosed();
				Toast.makeText(getApplicationContext(), "Interstitial closed", Toast.LENGTH_SHORT).show();
			}
			
		});
        
        adView = initAdView(AdsID.MENU_PRINCIPAL);
        adView.setAdListener(new AdListener() {

			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Toast.makeText(getApplicationContext(), "Banner add loaded", Toast.LENGTH_SHORT).show();
			}        	
        	
		});
		
		layout.addView(gameView);
		layout.addView(adView, adParams);
		
		setContentView(layout);
		
	}
	
	private InterstitialAd initInterstitialAd(String adUnitId) {
		InterstitialAd interstitial = new InterstitialAd(getApplicationContext());
		interstitial.setAdUnitId(adUnitId);
		return interstitial;
	}

	private AdView initAdView(String adUnitId) {
		AdView initAdView = new AdView(this);
        initAdView.setAdSize(AdSize.SMART_BANNER);
        initAdView.setAdUnitId(adUnitId);
        return initAdView;        
	}
	
	private AdRequest loadAdRequest() {
		return new AdRequest.Builder()
							.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
							.addTestDevice(TestDevices.ILIUMPAD_E10SI)
							.addTestDevice(TestDevices.SAMSUMG_GALAXY_TAB_2)
							.build();	
	}
	
	private void windowConfigurations() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);		
	}
		
	private Handler handler = new Handler(Looper.getMainLooper()) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
				case SHOW_ADS:{
					adView.loadAd( loadAdRequest() );
					adView.setVisibility(View.VISIBLE);
					break;
				}
				
				case HIDE_ADS: {
					adView.destroy();
					adView.setVisibility(View.GONE);
					break;
				}
	
				default: {
					break;
				}
			}
			
		}
		
	};
	
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void startInterstitial () {
		
		try {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (interstitialAd.isLoaded()) {
						interstitialAd.show();
						Toast.makeText(getApplicationContext(), "Showing interstitial!!!", Toast.LENGTH_SHORT).show();
					} else {
						interstitialAd.loadAd( loadAdRequest() );
						Toast.makeText(getApplicationContext(), "Loading interstitial", Toast.LENGTH_SHORT).show();
					}
				}
				
			});
		} catch (Exception e) {			
			Log.e("Exception", e.toString());			
		}
		
	}
	
	
	@Override
	public void loadInterstitialAd() {
		try {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (!interstitialAd.isLoaded()) {
						interstitialAd.loadAd( loadAdRequest() );
						Toast.makeText(getApplicationContext(), "Loading interstitial", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}
	}

	@Override
	protected void onPause() {
		if (adView != null) adView.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (adView != null) adView.resume();
	}

	@Override
	protected void onDestroy() {
		if (adView != null) adView.destroy();
		super.onDestroy();
	}
	
}
