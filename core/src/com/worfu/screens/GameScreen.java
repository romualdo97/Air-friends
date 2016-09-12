package com.worfu.screens;

import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.worfu.airfriends.AFriends;
import com.worfu.gameworld.GameModel;
import com.worfu.gameworld.GameView;
import com.worfu.helpers.AdActionResolver;
import com.worfu.helpers.Assets;
import com.worfu.helpers.GameScreenStates;
import com.worfu.helpers.GameScreenStates.Screens;
import com.worfu.helpers.GameStates;
import com.worfu.helpers.GameStates.States;
import com.worfu.helpers.ScreenTransitions;
import com.worfu.helpers.UI;
import com.worfu.inputs.HUDInputHandler;

public class GameScreen extends ScreenAdapter {
	
	// Input controller
	private Vector3 touchPoint;
	private InputAdapter hudInputHandler;
	private InputMultiplexer multiplexer;
	
	// logic properties
	private GameModel model;
	private GameView view;

	// transitions and time
	ScreenTransitions transition;
	float elapsedTime;
	
	// buttons
	private UI pauseButton;
	private UI resumeButton;
	private UI quitButton;
	private UI restartButton;
	private UI fuelHUD;
	private float onClickScale = 0.2f;
	
	// properties for show things and change screen	
	private AFriends game;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera guiCam;
	private Viewport viewport;
	
	// strings and values for show
	String readyText;
	String gameOverText;
	String finalScoreText;
	
	// misc
	NumberFormat numberFormat;
	
	// Ads object action resolver
	private AdActionResolver adResolver;
	private static boolean isNotShowedAd = true;
	
	private static int numTimesPlayed;
	
	public GameScreen(AFriends game, InputMultiplexer multiplexer, AdActionResolver adResolver){
		this.multiplexer = multiplexer;
		this.game = game;
		this.adResolver = adResolver;
		
		initSettinga();		
		
		Gdx.app.log("GameScreen", "game screen started");
	}

    // ========================================================================

	private void initSettinga() {
		numTimesPlayed += 1;
		
		if (!isNotShowedAd) {
			adResolver.showAds(false);
		}
		
		if (numTimesPlayed == 3) {
			adResolver.loadInterstitialAd();
		}
		
		if (numTimesPlayed == 7) {
			adResolver.startInterstitial();
			numTimesPlayed = 0;
		}
		
		isNotShowedAd = true;
	}

	private void initModel(){
		GameScreenStates.setCurrentScreen(Screens.GAME_SCREEN);
		GameStates.setCurrentGameState(States.READY);
		
		readyText = "Touch the sky!";
		gameOverText = "Game Over";
		finalScoreText = "Score: ";
		
		// transitions
		transition = new ScreenTransitions(0, 0, 0);
		transition.prepareIn(1);
		
		// misc
		numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(1);
		numberFormat.setMinimumFractionDigits(1);
		
		// game
		model = new GameModel();
	}
	
	private void initView(){
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		guiCam = new OrthographicCamera(400, 250);
		viewport = new StretchViewport(400, 250, guiCam);
		int margin = 8;
		
		resumeButton = new UI("ui/play");
		resumeButton.setPosition(200 - (resumeButton.getWidth()/2), 125 - (resumeButton.getHeight()/2));
		
		pauseButton = new UI("ui/pause");
		pauseButton.setWidth(22);
		pauseButton.setPosition((400 - pauseButton.getWidth()) - margin, (250 - pauseButton.getHeight()) - margin);
		pauseButton.setOriginCenter();
		
		quitButton = new UI("ui/backward");
		quitButton.setWidth(22);
		quitButton.setPosition(margin, margin);
		quitButton.setOriginCenter();
		
		restartButton = new UI("ui/restart");
		restartButton.setWidth(90);
		restartButton.setPosition(200 - (restartButton.getWidth()/2), (125 - (restartButton.getHeight()/2)) - 20);
		restartButton.setOriginCenter();
		
		fuelHUD = new UI("ui/fuel");
		fuelHUD.setWidth(22);
		fuelHUD.setPosition(margin, margin);
		
		
		Assets.gameTypography.setScale(.40f);
		Assets.redGameTypography.setScale(.40f);
		Assets.yellowGameTypography.setScale(.4f, .4f);
		Assets.mainMusic.setVolume(0.03f);
		
		// game view
		view = new GameView(model);
	}
	
	private void initController() {
		touchPoint = new Vector3();
		
		multiplexer.clear();
		hudInputHandler = new HUDInputHandler(this);
		multiplexer.addProcessor(hudInputHandler);
		Gdx.input.setInputProcessor(multiplexer);
		
	}
	
	private void update(float delta){
		switch (GameStates.getCurrentGameState()) {
			case READY:
				updateReady(delta);
				break;
			case RUNNING:
				updateRunning(delta);
				break;
			case PAUSED:
				updatePaused(delta);
				break;
			case GAME_OVER:
				updateGameOver(delta);
				break;
			default:
				break;
		}
	}
	
	private void draw(float delta){
		guiCam.update();
		
		batch.setProjectionMatrix(guiCam.combined);
		shapeRenderer.setProjectionMatrix(guiCam.combined);
		
		elapsedTime += delta;

		view.render(elapsedTime);
		
		batch.begin();
		switch (GameStates.getCurrentGameState()) {
			case READY:
				drawReady();
				break;
			case RUNNING:
				drawRunning();
				break;
			case PAUSED:
				drawPaused();
				break;
			case GAME_OVER:
				drawGameOver();
				break;
			default:
				break;
		}
		batch.end();
		
		transition.drawTransition(shapeRenderer, delta);
	}
	
	private void updateReady(float delta){}
	
	private void updateRunning(float delta){
		model.update(delta);
	}
	
	private void updatePaused(float delta){}
	
	private void updateGameOver(float delta){}
	
	private void drawReady(){
		float readyTextWidth = Assets.yellowGameTypography.getBounds(readyText).width;
		float readyTextHeight= Assets.yellowGameTypography.getBounds(readyText).height;
		Assets.yellowGameTypography.draw(batch, readyText, 200 - readyTextWidth/2, 125 + readyTextHeight/2);
	}
	
	private void drawRunning(){
		// show pause button
		pauseButton.draw(batch);
		
		// show points
		float scoreTextWidth = Assets.mainTypography.getBounds(model.score + "").width;
		float scoreTextHeight = Assets.mainTypography.getBounds(model.score + "").height;
		Assets.gameTypography.setScale(.5f, .5f);
		Assets.gameTypography.draw(batch, model.score + "", 200 - scoreTextWidth/2, (125 + scoreTextHeight/2) + 105);

		// show fuel
		fuelHUD.draw(batch);
		Assets.gameTypography.setScale(.2f, .2f);
		Assets.gameTypography.draw(batch, numberFormat.format(model.aircraft.fuel) + "", 30, 27f);
	}
	
	private void drawPaused(){
		resumeButton.draw(batch);
		quitButton.draw(batch);
	}
	
	private void drawGameOver(){
		// Show banner
		if (isNotShowedAd) {
			adResolver.showAds(true);
			isNotShowedAd = false;
		}
		
		// show game over
		float gameOverTextWidth = Assets.redGameTypography.getBounds(gameOverText).width;
		float gameOverTextHeight = Assets.redGameTypography.getBounds(gameOverText).height;
		Assets.redGameTypography.draw(batch, gameOverText, 200 - gameOverTextWidth/2, (125 + gameOverTextHeight/2) + 50);
		
		// show final score
		float finalScoreTextWidth = Assets.yellowGameTypography.getBounds(finalScoreText + model.score).width;
		Assets.yellowGameTypography.setScale(.3f, .3f);
		Assets.yellowGameTypography.draw(batch, finalScoreText + model.score, 200 - finalScoreTextWidth/2, 60);
		
		// show restart button
		restartButton.draw(batch);
		
		// show quit button
		quitButton.draw(batch);
	}
	
	public void onClick(int screenX, int screenY){
		guiCam.unproject(touchPoint.set(screenX, screenY, 0));

		if (GameStates.isCurrentState(States.READY)) {
			Assets.yellowGameTypography.setScale(.35f);
			Assets.playSound(Assets.click);
		}
		if (GameStates.isCurrentState(States.RUNNING)) {
			if (pauseButton.isPressed(touchPoint.x, touchPoint.y)) {
				pauseButton.scale(-onClickScale);
				Assets.playSound(Assets.click);
			}
		}
		if (GameStates.isCurrentState(States.PAUSED)) {
			if (resumeButton.isPressed(touchPoint.x, touchPoint.y)) {
				resumeButton.scale(-onClickScale);
				Assets.playSound(Assets.click);
			}
			if (quitButton.isPressed(touchPoint.x, touchPoint.y)) {
				quitButton.scale(-onClickScale);
				Assets.playSound(Assets.click);
			}
		}
		if (GameStates.isCurrentState(States.GAME_OVER)) {
			if (restartButton.isPressed(touchPoint.x, touchPoint.y)) {
				restartButton.scale(-onClickScale);
				Assets.playSound(Assets.click);
			}
			if (quitButton.isPressed(touchPoint.x, touchPoint.y)) {
				quitButton.scale(-onClickScale);
				Assets.playSound(Assets.click);
			}
		}
	}
	
	public void onClickReleased(){
		if (GameStates.isCurrentState(States.READY)) {
			Assets.yellowGameTypography.setScale(.40f);
			GameStates.setCurrentGameState(States.RUNNING);
		}
		if (GameStates.isCurrentState(States.RUNNING)){
			if (pauseButton.isUnpressed()) {
				pauseButton.scale(onClickScale);
				GameStates.setCurrentGameState(States.PAUSED);				
			}
		}
		if (GameStates.isCurrentState(States.PAUSED)) {
			if (resumeButton.isUnpressed()) {
				resumeButton.scale(onClickScale);
				GameStates.setCurrentGameState(States.RUNNING);
			}
			if (quitButton.isUnpressed()) {
				quitButton.scale(onClickScale);
				game.setScreen(new MainMenu(game, multiplexer, adResolver));
			}
		}
		if (GameStates.isCurrentState(States.GAME_OVER)) {
			if (restartButton.isUnpressed()) {
				restartButton.scale(onClickScale);
				game.setScreen(new GameScreen(game, multiplexer, adResolver));
			}
			if (quitButton.isUnpressed()) {
				quitButton.scale(onClickScale);
				game.setScreen(new MainMenu(game, multiplexer, adResolver));
			}
		}
	}
	
	// ========================================================
	
	@Override
	public void show(){		
		// Model
		initModel();
		
		// screen gui
		initView();
		
		// Controller
		initController();
		
	}
	
	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (delta > 0.1f) delta = 0.1f;
		
		update(delta);
		draw(delta);
	
	}
	
	@Override
	public void resize(int screenWidth, int screenHeight){
		viewport.update(screenWidth, screenHeight, true);
		view.resize(screenWidth, screenHeight);
	}

	@Override
	public void pause() {
		if ( GameStates.isCurrentState(States.RUNNING)) {
			GameStates.setCurrentGameState(States.PAUSED);			
		}
	}
	
	

}
