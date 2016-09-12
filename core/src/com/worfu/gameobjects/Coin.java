package com.worfu.gameobjects;

import java.util.Random;

import com.worfu.gameworld.WorldProperities;

public class Coin extends Entity {
	
	// state machine
	
	public enum CoinStates {
		LIFETIME_RUNNING, LIFETIME_EXPIRED
	}
	
	private CoinStates currentState;
	
	public void setCurrentState(CoinStates state){
		currentState = state;
	}
	
	public boolean isCurrentState(CoinStates state){
		return state == currentState;
	}
	
	// =============================================================================

	public final int SCORE = 1;
	public float lifeTime;
//	private boolean isNotScored = true;
	private Random r;
	
	public Coin(float width, float height, float lifeTime) {
		super(0, 0, width, height);
		this.lifeTime = lifeTime;
		r = new Random();
		spawn();
	}
	
	private void spawn(){
		position.x = r.nextInt((int) (WorldProperities.WIDTH - getWidth()));
		position.y = r.nextInt((int) (WorldProperities.HEIGHT - getHeight()));
		bounds.setPosition(position);
	}
	
	public void update(float delta){
		lifeTime -= delta;
		if (lifeTime <= 0) {
			setCurrentState(CoinStates.LIFETIME_EXPIRED);
		}
		
	}
	
}
