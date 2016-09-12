package com.worfu.helpers;


public class GameScreenStates {

	// screen states(model)
	public static enum Screens{
		MAINMENU_SCREEN, GAME_SCREEN
	};
	
	private static Screens currentScreen;

	public static boolean isScreenActive(Screens screen){
		return screen == currentScreen;
	}

	public static Screens getCurrentScreen() {
		return currentScreen;
	}

	public static void setCurrentScreen(Screens currentScreen) {
		GameScreenStates.currentScreen = currentScreen;
	}
	
	
}
