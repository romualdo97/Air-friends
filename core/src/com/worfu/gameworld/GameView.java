package com.worfu.gameworld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.worfu.gameobjects.Aircraft;
import com.worfu.helpers.Assets;
import com.worfu.helpers.GameStates;
import com.worfu.helpers.GameStates.States;

public class GameView {
	
	// properties that store world objects// properties needed for show the world(third-party properties)
	private GameModel world;
	private Aircraft aircraft;
	
	// sprites and textures
	private AtlasRegion bg;
	
	// properties needed for show the world(third-party properties)
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	
	public GameView(GameModel model){
		
		System.out.println("game view started");
		initModel(model);
		initView();
		
	}
	
	// ======================================================================================

	// game objects are the world entities, e.g, a character, a wall etc...
	private void initModel(GameModel world){
		this.world = world;
		aircraft = world.aircraft;
	}
	
	// viewers are all properties needed for show the game
	// e.g, a batch, a camera etc...
	private void initView(){
		// initialize renderers
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		// initialize viewers
		camera = new OrthographicCamera(WorldProperities.WIDTH, WorldProperities.HEIGHT);
		viewport = new StretchViewport(WorldProperities.WIDTH, WorldProperities.HEIGHT, camera);
		
		// textures and sprites
		bg = Assets.atlas.findRegion("large-images/in-game-background");
	}
	
	private void drawBg(){
		batch.draw(bg, 0, 0, WorldProperities.WIDTH, WorldProperities.HEIGHT);
	}
	
	private void drawCoin(float elapsedTime){
//		batch.draw(Assets.coinAnimation.getKeyFrame(elapsedTime, true), 4, 4, .6f, .6f);
//		System.out.println(world.coins.size);
		for (int i = 0; i < world.coins.size; i++) {
			batch.draw(Assets.coinAnimation.getKeyFrame(elapsedTime, true), world.coins.get(i).getX(), world.coins.get(i).getY(), world.coins.get(i).getWidth(), world.coins.get(i).getHeight());
		}
	}

	private void drawExplosion(float elapsedTime){
		if (GameStates.isCurrentState(States.GAME_OVER)) {
			batch.draw(Assets.explosionAnimation.getKeyFrame(elapsedTime, true), aircraft.getX() - .5f, aircraft.getY() - .5f, 2, 2);			
		}
	}
	
	private void drawAircraft(float elapsedTime){
//		batch.draw(Assets.redAircraft.getKeyFrame(elapsedTime, true), aircraft.getX(), aircraft.getY(), aircraft.getWidth(), aircraft.getHeight());
		batch.draw(Assets.redAircraft.getKeyFrame(elapsedTime, true),
				aircraft.getX(), aircraft.getY(), // position
				aircraft.getWidth()/2, aircraft.getHeight()/2, // origin(pivot point)
				aircraft.getWidth(), aircraft.getHeight(), // (size)
				1, 1, // scale
				aircraft.rotation // rotation
			);
	}
	
	private void drawDebug(){
		shapeRenderer.setColor(0, 0, 1, 1);
		shapeRenderer.rect(aircraft.position.x, aircraft.position.y, aircraft.bounds.width, aircraft.bounds.height);
		
		shapeRenderer.setColor(0, 1, 0, 1);
		for (int i = 0; i < world.coins.size; i++) {
			shapeRenderer.rect(world.coins.get(i).position.x, world.coins.get(i).position.y, world.coins.get(i).bounds.width, world.coins.get(i).bounds.height);
		}
	}
	
	// ======================================================================================
	
	public void render(float elapsedTime){
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(135/255f, 211/255f, 124/255f, 1);
		shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
		shapeRenderer.end();
		
		batch.begin();
		drawBg();
		drawCoin(elapsedTime);
		drawAircraft(elapsedTime);
		drawExplosion(elapsedTime);
		batch.end();
		
//		shapeRenderer.begin(ShapeType.Filled);
//		drawDebug();
//		shapeRenderer.end();
	}
	
	public void resize(int screenWidth, int screenHeight){
		viewport.update(screenWidth, screenHeight, true);
	}
	
}
