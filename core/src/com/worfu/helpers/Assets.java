package com.worfu.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	
	// textures
	public static Texture badlogic;
	public static TextureAtlas atlas;
		
	// fonts
	public static BitmapFont mainTypography;
	public static BitmapFont gameTypography;
	public static BitmapFont redGameTypography;
	public static BitmapFont yellowGameTypography;
	
	// sound
	public static Sound airplaneCrash;
	public static Sound airplaneMotor;
	public static Sound airplaneShot;
	public static Sound coin;
	public static Sound click;
	
	// Music
	public static Music ambient;
	public static Music mainMusic;
	
	// animations
	public static Animation mainBGAnimation;
	public static Animation redAircraft;
	public static Animation coinAnimation;
	public static Animation explosionAnimation;
	
	public static void load(){
		// initialize textures
		atlas = new TextureAtlas(Gdx.files.internal("data/graphics/packaged.atlas"));

		// initialize animations
		mainBGAnimation = new Animation(1/2f, atlas.findRegions("large-images/background-image"));
		redAircraft = new Animation(1/32f, atlas.findRegions("airplanes/red-aircraft"), PlayMode.LOOP_PINGPONG);
		coinAnimation = new Animation(1/10f, atlas.findRegions("misc/coin"), PlayMode.LOOP_PINGPONG);
		explosionAnimation = new Animation(1/10f, atlas.findRegions("misc/explosion"));
		
		// initialize fonts
		mainTypography = new BitmapFont(Gdx.files.internal("data/fonts/jaapokki-substract.fnt"));
		gameTypography = new BitmapFont(Gdx.files.internal("data/fonts/bob.fnt"));
		redGameTypography = new BitmapFont(Gdx.files.internal("data/fonts/bobRed.fnt"));
		yellowGameTypography = new BitmapFont(Gdx.files.internal("data/fonts/bobYellow.fnt"));
		
		// initialize audio
		airplaneCrash = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.ogg"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/sounds/coin.ogg"));
		click = Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.wav"));
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/casual-game-track.mp3"));
	}
	
	public static void dispose(){}
	
	public static void playSound(Sound sound){
		if(Settings.getSoundState()) sound.play();
	}
	
	public static void playSound(Sound sound, float volume){		
		if(Settings.getSoundState()) sound.setVolume( sound.play(), volume );
	}
	
	
	public static void playMusic(Music music) {
		if (Settings.getSoundState()) music.play();
	}
	
	public static void playMusic(Music music, float volume) {
		music.setLooping(true);
		music.setVolume(volume);
		if (Settings.getSoundState()) music.play();
	}
	
}
