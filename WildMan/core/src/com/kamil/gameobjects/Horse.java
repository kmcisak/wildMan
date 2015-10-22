package com.kamil.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kamil.gameworld.GameRenderer;

public class Horse {

	public Vector2 position;
	public Vector2 velocity;

	private float rotation;
	private int width;
	private int height;

	private float gravity = -8;

	public boolean canJump = true;

	public float jumpVelocity;

	private Circle boundingCircle;

	private boolean isAlive;

	public Horse(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		boundingCircle = new Circle();
		isAlive = true;
	}

	public void update(float delta) {

		position.y += velocity.y * delta;
		boundingCircle.set(position.x + 33, position.y + 33, 32.5f);

		if (position.y < (GameRenderer.getMidPointY() + 45)) {
			velocity.y -= gravity;

		} else {
			position.y = (GameRenderer.getMidPointY() + 45);
			rotation = 0;
			canJump = true;
			velocity.y = 0;
		}

		// Rotate counterclockwise
		if (velocity.y < 0) {
			rotation -= 600 * delta;

			if (rotation < -20) {
				rotation = -20;
			}
		}

		// Rotate clockwise
		if (isFalling()) {
			rotation += 480 * delta;
			if (rotation > -5) {
				rotation = -5;
			}
		}
	}

	public void jump() {
		if (canJump) {
			velocity.y = -180;
			canJump = false;
		}
	}

	public void updateReady(float runTime) {
		rotation = 0;
		velocity.x = 0;
		velocity.y = 0;
		isAlive = true;
		canJump = false;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		isAlive = true;
		canJump = false;
	}

	public boolean isFalling() {
		return velocity.y > 170;
	}

	public boolean shouldntFlap() {
		return velocity.y > 0 || !isAlive;
	}

	public void die() {
		isAlive = false;
	}

	public void onClick() {
		if (isAlive) {
			jump();
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
