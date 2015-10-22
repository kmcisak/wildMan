package com.kamil.gameworld;

import java.util.List;
import java.util.Random;

import TweenAccessor.Value;
import TweenAccessor.ValueAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kamil.gameobjects.BadMan;
import com.kamil.gameobjects.Bomb;
import com.kamil.gameobjects.Grass;
import com.kamil.gameobjects.Horse;
import com.kamil.gameobjects.ScrollHandler;
import com.kamil.gameobjects.Train;
import com.kamil.ui.SimpleButton;
import com.kamil.wmHelpers.AssetLoader;
import com.kamil.wmHelpers.InputHandler;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private Random rand = new Random();
	private int prob;

	private SpriteBatch batcher;

	private static int midPointY;
	// private int gameHeight;

	// Game Objects
	private Horse horse;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Train hurdle1, hurdle2, hurdle3;
	private Bomb bomb;
	private BadMan bandit;

	// Game Assets
	private TextureRegion bg, grass, birdDown, bum, ready, gameOver, highScore,
			scoreboard, star, noStar, retry, badman;
	private Animation birdAnimation, trainAnimation, trainAnimation2,
			witchTrain1, witchTrain2, witchTrain3;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private List<SimpleButton> retButtons;

	private Color transitionColor;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;

		// this.gameHeight = gameHeight;
		GameRenderer.setMidPointY(midPointY);

		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getMenuButtons();

		this.retButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getRetButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 408,
				(Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 408)));

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();

		transitionColor = new Color();
		prepareTransition(0, 0, 0, .5f);
	}

	private void initGameObjects() {
		horse = myWorld.getHorse();
		scroller = myWorld.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		hurdle1 = scroller.getHurdle1();
		hurdle2 = scroller.getHurdle2();
		hurdle3 = scroller.getHurdle3();
		bomb = scroller.getBomb();
		bandit = ScrollHandler.getBadMan();

	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.horseAnimation;
		trainAnimation = AssetLoader.trainAnimation;
		trainAnimation2 = AssetLoader.trainAnimation2;
		birdDown = AssetLoader.birdDown;
		bum = AssetLoader.bum;
		ready = AssetLoader.ready;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		scoreboard = AssetLoader.scoreboard;
		retry = AssetLoader.retry;
		star = AssetLoader.star;
		noStar = AssetLoader.noStar;
		badman = AssetLoader.badman;
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
				frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(),
				backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawBomb() {

		batcher.draw(bum, bomb.getX(), bomb.getY(), bomb.getWidth(),
				bomb.getHeight());

	}

	private void drawBadMan() {
		batcher.draw(badman, bandit.getX(), bandit.getY(), bandit.getWidth(),
				bandit.getHeight());
	}

	private Animation witchTrain1() {
		prob = (int) (100 * rand.nextDouble());
		if (prob < 30) {
			witchTrain1 = trainAnimation;
		} else
			witchTrain1 = trainAnimation2;
		return witchTrain1;
	}

	private Animation witchTrain2() {
		prob = (int) (100 * rand.nextDouble());
		if (prob < 30) {
			witchTrain2 = trainAnimation;
		} else
			witchTrain2 = trainAnimation2;
		return witchTrain2;
	}

	private Animation witchTrain3() {
		prob = (int) (100 * rand.nextDouble());
		if (prob < 30) {
			witchTrain3 = trainAnimation;
		} else
			witchTrain3 = trainAnimation2;
		return witchTrain3;
	}

	private void drawhurdles(float runTime) {

		if (hurdle1.getX() > 408) {
			witchTrain1();
		}

		batcher.draw(witchTrain1.getKeyFrame(runTime), hurdle1.getX(),
				hurdle1.getY(), hurdle1.getWidth(), hurdle1.getHeight());

		// batcher.draw(bar, hurdle1.getX(), hurdle1.getY() +
		// hurdle1.getHeight() + 45,
		// hurdle1.getWidth(), midPointY + 66 - (hurdle1.getHeight() + 45));

		if (hurdle2.getX() > 408) {
			witchTrain2();
		}

		batcher.draw(witchTrain2.getKeyFrame(runTime), hurdle2.getX(),
				hurdle2.getY(), hurdle2.getWidth(), hurdle2.getHeight());

		// batcher.draw(bar, hurdle2.getX(), hurdle2.getY() +
		// hurdle2.getHeight() + 45,
		// hurdle2.getWidth(), midPointY + 66 - (hurdle2.getHeight() + 45));

		if (hurdle3.getX() > 408) {
			witchTrain3();
		}
		batcher.draw(witchTrain3.getKeyFrame(runTime), hurdle3.getX(),
				hurdle3.getY(), hurdle3.getWidth(), hurdle3.getHeight());

		// batcher.draw(bar, hurdle3.getX(), hurdle3.getY() +
		// hurdle3.getHeight() + 45,
		// hurdle3.getWidth(), midPointY + 66 - (hurdle3.getHeight() + 45));
	}

	private void drawHorse(float runTime) {
		if (horse.shouldntFlap()) {
			batcher.draw(birdDown, horse.getX(), horse.getY(),
					horse.getWidth() / 2.0f, horse.getHeight() / 2.0f,
					horse.getWidth(), horse.getHeight(), 1, 1,
					horse.getRotation());

		} else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), horse.getX(),
					horse.getY(), horse.getWidth() / 2.0f,
					horse.getHeight() / 2.0f, horse.getWidth(),
					horse.getHeight(), 1, 1, horse.getRotation());
		}
	}

	// private void drawHorse(float runTime) {
	// if (horse.shouldntFlap()) {
	// batcher.draw(h3, horse.getX(), horse.getY(),
	// horse.getWidth() / 2.0f, horse.getHeight() / 2.0f,
	// horse.getWidth(), horse.getHeight(), 1, 1,
	// horse.getRotation());
	//
	// } else {
	// batcher.draw(horse2.getKeyFrame(runTime), horse.getX(),
	// horse.getY(), horse.getWidth() / 2.0f,
	// horse.getHeight() / 2.0f, horse.getWidth(),
	// horse.getHeight(), 1, 1, horse.getRotation());
	// }
	// }

	private void drawMenuUI() {
		batcher.draw(AssetLoader.zbLogo, 408 / 2 - 160, getMidPointY() - 80,
				AssetLoader.zbLogo.getRegionWidth(),
				AssetLoader.zbLogo.getRegionHeight());

		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}

	private void drawScoreboard() {
		batcher.draw(scoreboard, (408 / 2) - 97, getMidPointY() - 50, 194, 74);

		batcher.draw(noStar, 112, getMidPointY() - 20, 20, 20);
		batcher.draw(noStar, 137, getMidPointY() - 20, 20, 20);
		batcher.draw(noStar, 162, getMidPointY() - 20, 20, 20);
		batcher.draw(noStar, 187, getMidPointY() - 20, 20, 20);
		batcher.draw(noStar, 212, getMidPointY() - 20, 20, 20);

		if (myWorld.getScore() > 5) {
			batcher.draw(star, 112, getMidPointY() - 20, 20, 20);
		}

		if (myWorld.getScore() > 10) {
			batcher.draw(star, 137, getMidPointY() - 20, 20, 20);
		}

		if (myWorld.getScore() > 20) {
			batcher.draw(star, 162, getMidPointY() - 20, 20, 20);
		}

		if (myWorld.getScore() > 40) {
			batcher.draw(star, 187, getMidPointY() - 20, 20, 20);
		}

		if (myWorld.getScore() > 80) {
			batcher.draw(star, 212, getMidPointY() - 20, 20, 20);
		}

		int length = ("" + myWorld.getScore()).length();

		AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
				270 - (2 * length), getMidPointY() - 28);

		int length2 = ("" + AssetLoader.getHighScore()).length();
		AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
				270 - (3 * length2), getMidPointY() + 4);

	}

	private void drawRetry() {
		batcher.draw(retry, (408 / 2) - 33, getMidPointY() + 30, 66, 14);

		for (SimpleButton button1 : retButtons) {
			button1.draw(batcher);
		}
	}

	private void drawReady() {
		batcher.draw(ready, (408 / 2) - 34, getMidPointY() - 70, 68, 14);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, (408 / 2) - 46, getMidPointY() - 70, 92, 14);
	}

	private void drawScore() {
		int length = ("" + myWorld.getScore()).length();
		// Draw shadow first
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (408 / 2)
				- (4 * length), 35);
		// Draw text
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (408 / 2)
				- (4 * length - 1), 34);
	}

	private void drawHighScore() {
		batcher.draw(highScore, (408 / 2) - 46, getMidPointY() - 70, 92, 14);
	}

	// private void setupTweens() {
	// Tween.registerAccessor(Value.class, new ValueAccessor());
	// manager = new TweenManager();
	// Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
	// .start(manager);
	// }

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(22 / 255.0f, 216 / 255.0f, 251 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 408, getMidPointY() + 76);

		// Draw Grass
		// shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		// shapeRenderer.rect(0, midPointY + 66, 204, 11);

		// Draw Dirt
		shapeRenderer.setColor(222 / 255.0f, 216 / 255.0f, 149 / 255.0f, 1);
		shapeRenderer.rect(0, getMidPointY() + 123, 408, 37);

		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();

		batcher.draw(bg, 0, getMidPointY() + 67, 408, 43);

		batcher.enableBlending();

		drawBadMan();

		drawhurdles(runTime);

		drawGrass();

		drawBomb();

		if (myWorld.isRunning()) {
			drawHorse(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawHorse(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawHorse(runTime); // centered
			drawMenuUI();

		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawHorse(runTime);
			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawHorse(runTime);
			drawHighScore();
			drawRetry();
		}

		batcher.end();

		// shapeRenderer.begin(ShapeType.Filled);
		// shapeRenderer.setColor(Color.RED);
		// shapeRenderer.circle(horse.getBoundingCircle().x,
		// horse.getBoundingCircle().y, horse.getBoundingCircle().radius);
		// shapeRenderer.end();

		drawTransition(delta);

	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 1, alpha.getValue());
			shapeRenderer.rect(0, 0, 408, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

	public static int getMidPointY() {
		return midPointY;
	}

	public static void setMidPointY(int midPointY) {
		GameRenderer.midPointY = midPointY;
	}
}
