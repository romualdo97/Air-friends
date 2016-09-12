package com.worfu.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	
	public static Preferences prefs;
	private static boolean soundState = true;
	
	public static void load(){
		prefs = Gdx.app.getPreferences("preferences");
		
		// provide a default value for sound
		if (!prefs.contains("sound")){
			prefs.putBoolean("sound", true);
		}
		
	}
	
	private static void setSoundState(boolean state){
		prefs.putBoolean("sound", state);
		prefs.flush();
	}
	
	public static void toggleSoundState(){
		soundState = !soundState;
		setSoundState(soundState);
	}
	
	public static boolean getSoundState(){
		return prefs.getBoolean("sound");
	}
	
}
