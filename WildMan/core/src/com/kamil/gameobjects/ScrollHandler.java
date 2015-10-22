package com.kamil.gameobjects;

import java.util.Random;

import com.kamil.gameworld.GameWorld;
import com.kamil.wmHelpers.AssetLoader;

public class ScrollHandler {

	private Grass frontGrass, backGrass;
	private Train train1, train2, train3;
	private GameWorld gameWorld;
	private Bomb bomb;
	private Random r;
	private static BadMan badman;

	public static final int SCROLL_SPEED = -200;
	public static final int TRAIN_SCROLL_SPEED = -150;

	public static final int TRAIN_GAP = 279;

	public ScrollHandler(GameWorld gameWorld, float yPos) {
		r = new Random();
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0, yPos, 336 + 91, 23, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 336 + 91, 23,
				SCROLL_SPEED);

		train1 = new Train(420, yPos - 102, 279, 102, TRAIN_SCROLL_SPEED);
		train2 = new Train(train1.getTailX(), yPos - 102, 279, 102,
				TRAIN_SCROLL_SPEED);
		train3 = new Train(train2.getTailX(), yPos - 102, 279, 102,
				TRAIN_SCROLL_SPEED);

		bomb = new Bomb(460, yPos - 13, 11, 13, SCROLL_SPEED);
		
		badman = new BadMan(train1.getX()+320, yPos-85, 36, 50, TRAIN_SCROLL_SPEED);
		

	}

	public void updateReady(float delta) {

		frontGrass.update(delta);
		backGrass.update(delta);

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}

	}

	public void update(float delta) {
		// Update our objects
		frontGrass.update(delta);
		backGrass.update(delta);
		train1.update(delta);
		train2.update(delta);
		train3.update(delta);
		bomb.update(delta);
		badman.update(delta);

		// Check if any of the hurdles are scrolled left,
		// and reset accordingly

		if (train1.isScrolledLeft()) {
			train1.reset(train3.getTailX());
		} else if (train2.isScrolledLeft()) {
			train2.reset(train1.getTailX());

		} else if (train3.isScrolledLeft()) {
			train3.reset(train2.getTailX());
		}

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}

		if (bomb.isScrolledLeft()) {
			bomb.reset(r.nextInt(100) + 430);
		}
		
		if (badman.isScrolledLeft()) {
			badman.reset( 410 + r.nextInt(100));
		}
		
		
	}

	public boolean collides(Horse horse) {
		if (!bomb.isScored()
				&& ((bomb.getX() + (bomb.getWidth() / 2)) + 30) < horse.getX()
						+ horse.getWidth()) {
			AssetLoader.coin.play();
			addScore(1);
			bomb.setScored(true);
		}
		return bomb.collides(horse);
	}
	
	public boolean collides() {
		if (badman.getX() < 150) {
			AssetLoader.shot.play();
			
		}
		return badman.collides();
	}

	public void stop() {
		frontGrass.stop();
		backGrass.stop();

		train1.velocity.x = 100;
		train2.velocity.x = 100;
		train3.velocity.x = 100;
		bomb.stop();
		badman.velocity.x = 100;
	}

	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}

	public void onRestart() {
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		train1.onRestart(420, TRAIN_SCROLL_SPEED);
		train2.onRestart(train1.getTailX(), TRAIN_SCROLL_SPEED);
		train3.onRestart(train2.getTailX(), TRAIN_SCROLL_SPEED);
		bomb.onRestart(430, SCROLL_SPEED);
		badman.onRestart(train1.getX()+50, TRAIN_SCROLL_SPEED);
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}

	public Train getHurdle1() {
		return train1;
	}

	public Train getHurdle2() {
		return train2;
	}

	public Train getHurdle3() {
		return train3;
	}

	public Bomb getBomb() {
		return bomb;
	}
	
	public static  BadMan getBadMan() {
		return badman;
	}

}
