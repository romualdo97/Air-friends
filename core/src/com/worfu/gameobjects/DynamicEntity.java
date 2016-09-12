package com.worfu.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * class for dynamic game objects, i.e. objects that move,
 * its properties are principally the "acceleration and velocity"
 * this class should be used for objects that move in the world
 */

public abstract class DynamicEntity extends Entity {

	// All Entity properties are public
	// this class has no getters and setters
	
	public Vector2 acceleration;
	public Vector2 velocity;
	
	public DynamicEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		// initialize objects
		acceleration = new Vector2();
		velocity = new Vector2();
	}
	
	/**
	 * apply a force to the entity with a vector
	 * @param force
	 * @return add force to acceleration and add acceleration to velocity
	 */
	public void applyForce(Vector2 force){
		acceleration.add(force.x/mass, force.y/mass);
	}
	
	/**
	 * apply a force to the entity specifying magnitude in x and y
	 * @param float x
	 * @param float y
	 * @return add force to acceleration and add acceleration to velocity*/
	public void applyForce(float x, float y){
		acceleration.add(x/mass, y/mass);
	}
	
	/**
	 * scroll a entity in the specified direction
	 * @param Vector2 direction
	 * @return add direction vector to position
	 */
	public void scroll(Vector2 direction){
		position.add(direction);
	}
	
	/**
	 * scroll a entity in the specified direction
	 * @param x steps in x
	 * @param y steps in y
	 * @return add x and y to position vector*/
	public void scroll(float x, float y){
		position.add(x, y);
	}
	
	/**
	 * Reset acceleration, this method should be written at the
	 * end of the game loop
	 * @return multiply acceleration by 0*/
	public void resetAcceleration(){
		acceleration.scl(0);
	}
	
	/**
	 * update entity bounds
	 * @return set the box bounding rectangle position to "position vector" values
	 */
	public void update(float delta){
		bounds.setPosition(position.x, position.y);
	}

}
