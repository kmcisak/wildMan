package com.kamil.wmHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.kamil.gameobjects.BadMan;
import com.kamil.gameobjects.Horse;
import com.kamil.gameobjects.ScrollHandler;
import com.kamil.gameworld.GameWorld;
import com.kamil.ui.SimpleButton;

public class InputHandler implements InputProcessor {

	private Horse myHorse;
	private GameWorld myWorld;
	private BadMan myBadMan;

	private List<SimpleButton> menuButtons;
	
	private List<SimpleButton> retButtons;

	private SimpleButton playButton, retryButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public InputHandler(GameWorld myWorld, float scaleFactorX,
			float scaleFactorY) {
		this.myWorld = myWorld;
		myHorse = myWorld.getHorse();
		myBadMan = ScrollHandler.getBadMan();

		int midPointY = myWorld.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(
				408 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
				midPointY + 20, 104, 58, AssetLoader.playButtonUp,
				AssetLoader.playButtonDown);
		menuButtons.add(playButton);
		
		
		
		
		retButtons = new ArrayList<SimpleButton>();
		
		retryButton = new SimpleButton( (408 / 2) - 33, midPointY + 30, 66, 14, AssetLoader.retry, AssetLoader.retry);
		retButtons.add(retryButton);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		System.out.println(screenX + " " + screenY);

		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isReady()) {
			myWorld.start();
			myHorse.onClick();
		} else if (myWorld.isRunning()) {
			
			if (myBadMan.getBadManBound().contains(screenX, screenY)){
				myBadMan.onClick();
				myWorld.addScore(1);
				myBadMan.setScrolledLeft(true);
			}else myHorse.onClick();
			
		}

		else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			 if (retryButton.isTouchDown(screenX, screenY)){

			myWorld.restart();}
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.ready();
				return true;
			}
		}
		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.ready();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
	
	public List<SimpleButton> getRetButtons() {
		return retButtons;
	}

}
