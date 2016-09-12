package com.worfu.airfriends;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.worfu.helpers.AdActionResolver;
import com.worfu.helpers.Assets;
import com.worfu.helpers.Settings;
import com.worfu.screens.SplashScreen;

public class AFriends extends Game {
	private InputMultiplexer multiplexer;
	private AdActionResolver adResolver;
	
	public AFriends(AdActionResolver ar) {
		this.adResolver = ar;
	}	
	
	@Override
	public void create (){
		Assets.load();
		Settings.load();
		multiplexer = new InputMultiplexer();		

		setScreen(new SplashScreen(this, multiplexer, adResolver));
		
		Gdx.app.log("AFriends", "game started");
	}
	
	@Override
	public void dispose (){
		Assets.dispose();
	}

}
