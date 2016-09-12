package com.worfu.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Aircraft extends DynamicEntity {

	// states machine
	
	public enum AircraftStates {
		FLYING, DESTROYED
	}

	private AircraftStates currenAircraftState;
	public void setCurrentState(AircraftStates state) {
		currenAircraftState = state;
	}
	public boolean isCurrentState(AircraftStates state){
		return state == currenAircraftState;
	}
	
	// ===================================================================================
	
	public float rotation;
	public float fuel;
	public boolean isAlive;
	
	private float quicknessLimit;
	private float rotationSpeed;
	
	private Vector2 firstPoint;
	private Vector2 secondPoint;
	private Vector2 resultantVector;
	
	public Aircraft(float x, float y, float width, float height, float fuel) {
		super(x, y, width, height);
		this.fuel = fuel;
		initModel();
	}
	
	private void initModel(){
		setCurrentState(AircraftStates.FLYING);
		isAlive = true;
		rotation = 0;
		rotationSpeed = 180;
		quicknessLimit = 4;
		
		firstPoint = new Vector2();
		secondPoint = new Vector2();
		resultantVector = new Vector2();
	}

	public void moveAngularly(float angle) {
		
		firstPoint.x = (MathUtils.cosDeg(angle));
		firstPoint.y = (MathUtils.sinDeg(angle));
		
		secondPoint.x = (MathUtils.cosDeg(angle)) * 2;
		secondPoint.y = (MathUtils.sinDeg(angle)) * 2;
		
		resultantVector.x = firstPoint.x - secondPoint.x;
		resultantVector.y = firstPoint.y - secondPoint.y;
		
		resultantVector.nor();
		resultantVector.scl(-1);
		
	}
		
	public void burnFuel(float delta, float extraConsumption){
		fuel -= delta + extraConsumption;
	}
	
	public void refuel(float fuelAmount){
		fuel += fuelAmount;
	}
	
	public boolean isThereNotFuel(){
		return fuel <= 0;
	}
	
	public void setQuicknessLimit(float limit){
		quicknessLimit = limit;
		if (velocity.x >= limit) {
			velocity.x = limit;
		}
		if (velocity.x <= -limit) {
			velocity.x = -limit;
		}
		if (velocity.y >= limit) {
			velocity.y = limit;
		}
		if (velocity.y <= -limit) {
			velocity.y = -limit;
		}
	}
	
	private void move(float delta){
		
		if (velocity.x > 0) {
			rotation -= 180 * delta;
		}
		if (velocity.x < 0) {
			rotation += 180 * delta;
		}
		
		moveAngularly(rotation);

		position.add(resultantVector.x/8, resultantVector.y/8);
				
	}
	
	@Override
	public void update(float delta) {
		
		move(delta);
		
		burnFuel(delta, 0);
		
		super.update(delta);
	}

}