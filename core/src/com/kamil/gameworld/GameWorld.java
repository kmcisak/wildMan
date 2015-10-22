package com.kamil.gameworld;

import com.kamil.gameobjects.Horse;
import com.kamil.gameobjects.ScrollHandler;
import com.kamil.wmHelpers.AssetLoader;

public class GameWorld {
	private Horse horse;
	private ScrollHandler scroller;
	private static int score = 0;
	private float runTime = 0;
	private int midPointY;
	private GameRenderer renderer;

	public static GameState currentState;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midPointY) {
		currentState = GameState.MENU;
		this.midPointY = midPointY;
		horse = new Horse(33, midPointY + 45, 66, 66);
		horse.canJump = false;
		scroller = new ScrollHandler(this, midPointY + 110);
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;

		case RUNNING:
		default:

			updateRunning(delta);
			break;
			
			

		}
	}

	private void updateReady(float delta) {

		horse.updateReady(runTime);
		scroller.updateReady(delta);

	}

	public void updateRunning(float delta) {

		// Add a delta cap so that if our game takes too long
		// to update, we will not break our collision detection.

		if (delta > .15f) {
			delta = .15f;
		}

		horse.update(delta);
		scroller.update(delta);

		if ((horse.isAlive() && scroller.collides(horse)) || scroller.collides()) {
			scroller.stop();
			horse.die();
			renderer.prepareTransition(255, 255, 255, .3f);
			currentState = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}

	}

	public Horse getHorse() {
		return horse;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void ready() {
		currentState = GameState.READY;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	public void restart() {
		//currentState = GameState.READY;
		score = 0;
		horse.onRestart(midPointY + 45);
		scroller.onRestart();
		ready();
	}
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}
	
	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
	
	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public int getMidPointY() {
		return midPointY;
	}

	

}
