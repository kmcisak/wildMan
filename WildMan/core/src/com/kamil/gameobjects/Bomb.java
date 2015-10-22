package com.kamil.gameobjects;

import com.badlogic.gdx.math.Circle;

public class Bomb extends Scrollable {

	private Circle bombBounding;
	private boolean isScored = false;

	public Bomb(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		bombBounding = new Circle();

	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		isScored = false;
	}
	
	public void onRestart(float x, float scrollSpeed){
		velocity.x = scrollSpeed;
		reset(x);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		bombBounding.set(position.x + 6, position.y + 6.5f, 6.5f);
	}

	public boolean collides(Horse horse) {
		
		if (position.x < horse.getX() + horse.getWidth()){
			return bombBounding.overlaps(horse.getBoundingCircle());
		}
		return false;
	}
	
	

	public Circle getBombBound() {
		return bombBounding;
	}
	
	public boolean isScored() {
	    return isScored;
	}

	public void setScored(boolean b) {
	    isScored = b;
	}

}
