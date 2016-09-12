package com.worfu.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class UI extends Sprite {
	
	private boolean isTouched;
	private boolean isUntouched;
	
	public UI(String regionName) {
		super(Assets.atlas.createSprite(regionName));
	}
	
	public void setWidth(float deciredWidth){
		float height = (getHeight() * deciredWidth) / getWidth();
		setSize(deciredWidth, height);		
	}
	
	public boolean isPressed(float x, float y){
		if (getBoundingRectangle().contains(x, y)){
			isTouched = true;
			isUntouched = false;
			return isTouched;
		}
		return false;
	}
	
	public boolean isUnpressed(){
		if (isTouched){
			isUntouched = true;
			isTouched = false;
			return isUntouched;
		}
		return false;
	}	
	
}
