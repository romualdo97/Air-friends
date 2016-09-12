package com.worfu.gameworld;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.worfu.gameobjects.Aircraft;
import com.worfu.gameobjects.Aircraft.AircraftStates;
import com.worfu.gameobjects.Coin;
import com.worfu.gameobjects.Coin.CoinStates;
import com.worfu.helpers.Assets;
import com.worfu.helpers.GameStates;
import com.worfu.helpers.GameStates.States;

public class GameModel {
	// game objects
	public Aircraft aircraft;
	public Array<Coin> coins;
	public int score;
	
	private Random r;
	// misc
	private float accelX;
	
	public GameModel(){
		aircraft = new Aircraft(WorldProperities.WIDTH/2, WorldProperities.HEIGHT/2, 1, 4/7f, 12);
		coins = new Array<Coin>();
		r = new Random();
		System.out.println("game model started");
	}
	
	// =========================================================================================
	
	private void generateCoins(){
		if (coins.size == 0) {
			// pseudo-random number between 0(inclusive) and 4(exclusive)
			int numCoinsForCreate = r.nextInt(4);
			// exclude 0
			numCoinsForCreate = numCoinsForCreate == 0 ? 1 : numCoinsForCreate;
			for (int i = 0; i < numCoinsForCreate; i++) {
				coins.add(new Coin(.6f, .6f, 10));
			}
		}
	}

	private void updateAircraft(float delta, float accelX){
		if (aircraft.isCurrentState(AircraftStates.FLYING)){
			aircraft.setQuicknessLimit(0.1f);
			aircraft.velocity.add(accelX * delta, 0);
		}
		boolean isGameOver = aircraft.isCurrentState(AircraftStates.DESTROYED) || aircraft.isThereNotFuel(); 
		if (isGameOver && aircraft.isAlive) {
			aircraft.isAlive = false;
			Assets.playSound(Assets.airplaneCrash);
			GameStates.setCurrentGameState(States.GAME_OVER);
		}
		aircraft.update(delta);
	}

	private void updateCoins(float delta){
		generateCoins();
//		Gdx.app.log("num coins", coins.size + "");
		if (coins.size > 0) {
//			Gdx.app.log("first coin lifetime", coins.get(0).lifeTime + "");
			coins.get(0).update(delta);
			if (coins.get(0).isCurrentState(CoinStates.LIFETIME_EXPIRED)) {
//				Gdx.app.log("first coin lifetime", "expired");
				coins.removeIndex(0);
			}
		}
	}
	
	private void checkWorldLimitCollisions(){
		// check in X
		if (aircraft.getRightSide() > WorldProperities.WIDTH + .5f) {
			aircraft.setCurrentState(AircraftStates.DESTROYED);
		}
		if (aircraft.getX() < -.5f) {
			aircraft.setCurrentState(AircraftStates.DESTROYED);
		}
		// check in Y
		if (aircraft.getTopSide() > WorldProperities.HEIGHT + .5f) {
			aircraft.setCurrentState(AircraftStates.DESTROYED);
		}
		if (aircraft.getY() < -.5f) {
			aircraft.setCurrentState(AircraftStates.DESTROYED);
		}
	}
	
	private void checkCoinCollision(){
		for (int i = 0; i < coins.size; i++) {
			if (Intersector.overlaps(aircraft.bounds, coins.get(i).bounds)){
				Gdx.app.log("Intersects", "coin " + i + " intersects the aircraft");
				score += coins.get(i).SCORE;
				coins.removeIndex(i);
				Assets.playSound(Assets.coin, 0.4f);
				aircraft.refuel(2);
			}
		}
	}
	
	private void checkCollisions(){
		checkWorldLimitCollisions();
		checkCoinCollision();
	}
	
	// =========================================================================================
	
	public void update(float delta){
		
//		if (!Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) return;
		
		accelX = Gdx.input.getAccelerometerY();
				
		updateAircraft(delta, accelX);
		updateCoins(delta);
		checkCollisions();
		
	}
}