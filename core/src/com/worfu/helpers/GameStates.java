package com.worfu.helpers;

public class GameStates {
	
	public static enum States{
		READY, RUNNING, GAME_OVER, PAUSED
	} 

	private static States currentGameState;
	
	public static boolean isCurrentState(States state){
		return state == currentGameState;
	}

	public static States getCurrentGameState() {
		return currentGameState;
	}

	public static void setCurrentGameState(States currentGameState) {
		GameStates.currentGameState = currentGameState;
	}
	
	

}
