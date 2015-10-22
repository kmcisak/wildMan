package com.kamil.gameobjects;

public class Train extends Scrollable {

	private boolean isScored = false;

	public Train(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		//height = 36;
		isScored = false;
	}
	
	public void onRestart(float x, float scrollSpeed){
		velocity.x = scrollSpeed;
		reset(x);
	}



	public boolean isScored() {
		return isScored;
	}
	
	

	public void setScored(boolean b) {
		isScored = b;
	}
}
