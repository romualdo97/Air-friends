package com.worfu.airfriends.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.worfu.airfriends.AFriends;
import com.worfu.helpers.AdActionResolver;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Air friends";
		config.width = 800;
		config.height = 500;
		
		new LwjglApplication(new AFriends(new AdActionResolver() {
			
			@Override
			public void startInterstitial() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void showAds(boolean show) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadInterstitialAd() {
				// TODO Auto-generated method stub
				
			}
			
		}), config);
	}
}
