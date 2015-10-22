package com.kamil.gameobjects;


import com.badlogic.gdx.math.Rectangle;
import com.kamil.wmHelpers.AssetLoader;

public class BadMan extends Scrollable{
	
	private Rectangle badManBounding;
	private boolean isScored = false;

	public BadMan(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		badManBounding = new Rectangle();
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

		badManBounding.set(position.x, position.y, getWidth(), getHeight());
	}

	public boolean collides() {
		
		return(position.x < 150);
	}
	
	

	public Rectangle getBadManBound() {
		return badManBounding;
	}
	
	public boolean isScored() {
	    return isScored;
	}

	public void setScored(boolean b) {
	    isScored = b;
	}

	public void onClick() {
		if(!isScored()){
			AssetLoader.shot.play();
			
			//GameWorld.addScore(1);
			//setScored(true);
		}
	}

}
