package com.worfu.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/*
 * This class is the base for every game object,
 * its properties are principally the "position, bounds and mass"
 */
public abstract class Entity {
	// All Entity properties are public
	// this class also has getters and setters for ease
	
	// box bounding by default
	public Rectangle bounds;
	public Vector2 position;
	public float mass = 1;
	
	/**
	 * set game object position and bounds for collisions
	 * @param float x
	 * @param float y
	 * @param float width
	 * @param float height
	 */
	public Entity(float x, float y, float width, float height){
		// initialize objects
		bounds = new Rectangle();
		position = new Vector2();

		// set object properties
		position.set(x, y);
		bounds.setPosition(x, y);
		bounds.setSize(width, height);
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	
	public float getWidth(){
		return bounds.width;
	}
	
	public float getHeight(){
		return bounds.height;
	}
	
	public float getRightSide(){
		return position.x + bounds.width;
	}
	
	public float getTopSide(){
		return position.y + bounds.height;
	}
	
}
