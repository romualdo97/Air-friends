package com.worfu.inputs;

import com.badlogic.gdx.InputAdapter;
import com.worfu.helpers.GameScreenStates;
import com.worfu.helpers.GameScreenStates.Screens;
import com.worfu.screens.GameScreen;

public class HUDInputHandler extends InputAdapter {

	private GameScreen gameScreen;

	public HUDInputHandler(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (GameScreenStates.isScreenActive(Screens.GAME_SCREEN)){
			gameScreen.onClick(screenX, screenY);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (GameScreenStates.isScreenActive(Screens.GAME_SCREEN)){
			gameScreen.onClickReleased();
			return true;
		}
		return false;
	}
	
		
	
}
