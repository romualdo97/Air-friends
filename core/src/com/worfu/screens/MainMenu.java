package com.worfu.screens;

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
import com.worfu.helpers.AdActionResolver;
import com.worfu.helpers.Assets;
import com.worfu.helpers.GameScreenStates;
import com.worfu.helpers.GameScreenStates.Screens;
import com.worfu.helpers.ScreenTransitions;
import com.worfu.helpers.Settings;
import com.worfu.helpers.UI;
import com.worfu.inputs.MainMenuInputHandler;

public class MainMenu extends ScreenAdapter {

	// inputs(controller)
	private Vector3 touchPoint;
	InputAdapter MainMenuInputHandler;
	InputMultiplexer multiplexer;

	// sprites for gui(view)
	private UI playButtonSkin;
	private UI soundButtonSkin;
	private UI soundDisabledButtonSkin;
	private UI attributtionsButtonSkin;
	private float onClickScale = 0.2f;
	
	// tween stuff(model-view)
	ScreenTransitions transition;
	private float elapsedTime;

	// properties for show things and change screen(view)
	private AFriends game;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Viewport viewport;
	private OrthographicCamera guiCam;
	
	// Ads object action resolver
	private AdActionResolver adResolver;

	public MainMenu(AFriends game, InputMultiplexer multiplexer, AdActionResolver adResolver) {
		this.multiplexer = multiplexer;
		this.game = game;
		this.adResolver = adResolver;

		Gdx.app.log("MainMenu", "main menu started");
	}

	// ========================================================================

	private void initModel() {
		GameScreenStates.setCurrentScreen(Screens.MAINMENU_SCREEN);
		
		// transitions
		transition = new ScreenTransitions(0, 0, 0);
		transition.prepareIn(1);
	}

	private void initView(AFriends game) {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		guiCam = new OrthographicCamera(400, 250);
		viewport = new StretchViewport(400, 250, guiCam);
		int margin = 8;

	soundButtonSkin = new UI("ui/sound-on");
		soundButtonSkin.setPosition(margin, margin);
		soundButtonSkin.setWidth(22);

		soundDisabledButtonSkin = new UI("ui/sound-off");
		soundDisabledButtonSkin.setPosition(margin, margin);
		soundDisabledButtonSkin.setWidth(22);

		attributtionsButtonSkin = new UI("ui/more");
		attributtionsButtonSkin.setWidth(22);
		attributtionsButtonSkin.setPosition((400 - 22) - margin, margin);
		attributtionsButtonSkin.setOriginCenter();

		playButtonSkin = new UI("ui/play");
		playButtonSkin.setPosition(200 - (playButtonSkin.getWidth() / 2),
				125 - (playButtonSkin.getHeight() / 2));

		Assets.playMusic(Assets.mainMusic, 0.12f);
		Assets.mainMusic.setLooping(true);
	}

	private void initController() {
		touchPoint = new Vector3();

		Gdx.app.log("GameScreen", "Initialized inputs");

		multiplexer.clear();
		MainMenuInputHandler = new MainMenuInputHandler(this);
		multiplexer.addProcessor(MainMenuInputHandler);
		Gdx.input.setInputProcessor(multiplexer);

	}

	private void update() {}

	private void draw(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		elapsedTime += delta;
		
		guiCam.update();
		shapeRenderer.setProjectionMatrix(guiCam.combined);
		batch.setProjectionMatrix(guiCam.combined);

		batch.begin();
		drawUI(elapsedTime);
		batch.end();

		transition.drawTransition(shapeRenderer, delta);
	}

	private void drawUI(float elapsedTime) {
		// System.out.println(Settings.getSoundState());

		
		batch.draw(Assets.mainBGAnimation.getKeyFrame(elapsedTime, true), 0, 0);
		
		if (Settings.getSoundState()) {
			soundButtonSkin.draw(batch);
		} else {
			soundDisabledButtonSkin.draw(batch);
		}

		attributtionsButtonSkin.draw(batch);

		playButtonSkin.draw(batch);
	}

	public void onClick(int screenX, int screenY) {
		guiCam.unproject(touchPoint.set(screenX, screenY, 0));
		if (playButtonSkin.isPressed(touchPoint.x, touchPoint.y)) {
			playButtonSkin.scale(-onClickScale);
			Assets.playSound(Assets.click);
		}
		if (soundButtonSkin.isPressed(touchPoint.x, touchPoint.y)) {
			Assets.playSound(Assets.click);
			Settings.toggleSoundState();
			if (Settings.getSoundState()) {
				Assets.mainMusic.play();
			} else {
				Assets.mainMusic.pause();
			}
		}
		if (attributtionsButtonSkin.isPressed(touchPoint.x, touchPoint.y)) {
			attributtionsButtonSkin.scale(-onClickScale);
			Assets.playSound(Assets.click);
		}
	}

	public void onClickReleased() {
		if (playButtonSkin.isUnpressed()) {
			playButtonSkin.scale(onClickScale);
			game.setScreen(new GameScreen(game, multiplexer, adResolver));
		}
		if (attributtionsButtonSkin.isUnpressed()) {
			attributtionsButtonSkin.scale(onClickScale);
		}
	}

	// ========================================================

	@Override
	public void show() {	
		// initialize logic properties
		initModel();

		// initialize stuff for view things
		initView(game);

		// initialize inputs
		initController();

	}

	@Override
	public void render(float delta) {
		update();
		draw(delta);
	}

	@Override
	public void resize(int screenWidth, int screenHeight) {
		viewport.update(screenWidth, screenHeight, true);
	}

}