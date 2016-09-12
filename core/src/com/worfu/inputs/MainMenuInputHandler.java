package com.worfu.inputs;

import com.badlogic.gdx.InputAdapter;
import com.worfu.helpers.GameScreenStates;
import com.worfu.helpers.GameScreenStates.Screens;
import com.worfu.screens.MainMenu;


public class MainMenuInputHandler extends InputAdapter {
	
	MainMenu mainMenu;
	
	public MainMenuInputHandler(MainMenu mainMenu){
		this.mainMenu = mainMenu;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (GameScreenStates.isScreenActive(Screens.MAINMENU_SCREEN)){
			mainMenu.onClick(screenX, screenY);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (GameScreenStates.isScreenActive(Screens.MAINMENU_SCREEN)) {
			mainMenu.onClickReleased();
			return true;
		}
		return false;		
	}	
	
}