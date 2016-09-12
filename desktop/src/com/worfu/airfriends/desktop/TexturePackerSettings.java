package com.worfu.airfriends.desktop;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TexturePackerSettings {

	public static void main(String[] args) throws Exception {		
		// Dir resources
		// NOTE: Only change the input dir. 
		String imputDir = "/home/romualdo/projects/air-friends-project/air-friends_assets/graphics/";
		String ouputDir = "/home/romualdo/projects/air-friends-project/air-friends/android/assets/data/graphics";
		String packFileName = "packaged";
		
		// Config object
		Settings settings = new TexturePacker.Settings();
		settings.combineSubdirectories = false;
		settings.pot = true;
		settings.paddingX = 0;
		settings.paddingY = 0;
		
		TexturePacker.processIfModified(settings, imputDir, ouputDir, packFileName);		
	}

}
