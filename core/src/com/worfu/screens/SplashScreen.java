package com.worfu.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.worfu.airfriends.AFriends;
import com.worfu.helpers.AdActionResolver;
import com.worfu.helpers.Assets;
import com.worfu.helpers.ScreenTransitions;

public class SplashScreen extends ScreenAdapter {

	// controller
	private InputMultiplexer multiplexer;
	
	// tween stuff
	private ScreenTransitions transitions;
	
	// properties for show things and change screen
	private AFriends game;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera guiCam;
	private Viewport viewport;
	
	// Logo text
	private String logoText;
	
	// Ads object action resolver
	private AdActionResolver adResolver;
	
	public SplashScreen(AFriends game, InputMultiplexer multiplexer, AdActionResolver adResolver){
		this.multiplexer = multiplexer;
		this.game = game;
		this.adResolver = adResolver;
		
		Gdx.app.log("SplashScreen", "splashscreen started");
	}
	
	@Override
	public void show() {		
		// initialize logic properties
		initModel();
		
		// initialize stuff for view
		initView(game);
	}

	@Override
    public void render(float delta) {
    	draw(delta);
    }
    
    @Override
    public void resize(int screenWidth, int screenHeight){
    	viewport.update(screenWidth, screenHeight, true);
    }
	
    // ========================================================================
    
    private void initModel(){
    	logoText = "WORFU";        
    	
    	transitions = new ScreenTransitions(0, 0, 0);
		TweenCallback cb = new TweenCallback(){			  
			  @Override
			  public void onEvent(int type, BaseTween<?> source) {
			      game.setScreen(new MainMenu(game, multiplexer, adResolver));
			  }
		};
    	transitions.prepareOut(cb, 2);
    }
    
    private void initView(AFriends game){			
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		guiCam = new OrthographicCamera(400, 250);
		viewport = new StretchViewport(400, 250, guiCam);    	
    }
    
	private void draw(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
        guiCam.update();
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        batch.setProjectionMatrix(guiCam.combined);
        
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(30/255f, 30/255f, 30/255f, 1);
        shapeRenderer.rect(0, 0, guiCam.viewportWidth, guiCam.viewportHeight);
        shapeRenderer.end();
        
        batch.begin();
        
        float logoTextWidth = Assets.mainTypography.getBounds(logoText).width;
        float logoTextHeight = Assets.mainTypography.getBounds(logoText).height;
        
        Assets.mainTypography.setScale(.5f, .5f);
        Assets.mainTypography.draw(batch, logoText, 200 - (logoTextWidth/2), 125 + (logoTextHeight/2));
        batch.end();
        
        transitions.drawTransition(shapeRenderer, delta);        
	}

}
