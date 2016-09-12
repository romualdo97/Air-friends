package com.worfu.helpers;

import tweenaccesors.Value;
import tweenaccesors.ValueAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.worfu.airfriends.AFriends;
import com.worfu.screens.MainMenu;

public class ScreenTransitions {
	
	private Value alpha;
	private TweenManager manager;
	private Color transitionColor;
	
	public ScreenTransitions(float r, float g, float b){
		transitionColor = new Color();
		manager = new TweenManager();
		alpha = new Value();
		
		alpha.setValue(1);
		transitionColor.set(r/255f, g/255f, b /255f, 1);
	}
	
	public void prepareIn(float duration){
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		
		Tween.to(alpha, -1, duration).target(0)
		.ease(TweenEquations.easeInOutQuad)
		.start(manager);
	}
	
	//private Screen newScreen;
	public void prepareOut(TweenCallback cb, float duration){
		alpha.setValue(1);
    	
    	Tween.registerAccessor(Value.class, new ValueAccessor());
    	manager = new TweenManager();
    	
    	Tween.to(alpha, -1, duration).target(0)
      .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, duration/2f)
      .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
      .start(manager);
	}
	
    public void drawTransition(ShapeRenderer shapeRenderer, float delta){
		if (alpha.getValue() < 0) return;
		
		manager.update(delta);
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getValue());
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();		
		Gdx.gl.glDisable(GL20.GL_BLEND);
    }
	
}
