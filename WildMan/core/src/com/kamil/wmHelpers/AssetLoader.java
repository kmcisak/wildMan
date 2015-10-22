package com.kamil.wmHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, logoTexture;
	public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown,
			birdUp, skullUp, skullDown, carriage, carriage_2, carriage2_1,
			carriage2_2, bum, playButtonUp, playButtonDown, ready, gameOver,
			highScore, scoreboard, star, noStar, retry, h1, h2, h3, h4, badman;

	public static Animation horseAnimation, horse2, trainAnimation, trainAnimation2;

	public static BitmapFont font, shadow, whiteFont;
	public static Sound coin, shot;
	public static Preferences prefs;

	public static void load() {

		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		playButtonUp = new TextureRegion(texture, 7, 81, 104, 58);
		playButtonDown = new TextureRegion(texture, 116, 81, 104, 58);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		ready = new TextureRegion(texture, 400, 8, 68, 14);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 400, 62, 66, 14);
		retry.flip(false, true);

		gameOver = new TextureRegion(texture, 400, 26, 92, 14);
		gameOver.flip(false, true);

		scoreboard = new TextureRegion(texture, 0, 375, 194, 74);
		scoreboard.flip(false, true);

		star = new TextureRegion(texture, 4, 455, 20, 20);
		noStar = new TextureRegion(texture, 30, 455, 20, 20);

		star.flip(false, true);
		noStar.flip(false, true);

		highScore = new TextureRegion(texture, 400, 44, 96, 14);
		highScore.flip(false, true);

		zbLogo = new TextureRegion(texture, 12, 220, 318, 79);
		zbLogo.flip(false, true);

		bg = new TextureRegion(texture, 0, 8, 335, 43);
		bg.flip(false, true);

		grass = new TextureRegion(texture, 0, 51, 336, 23);
		grass.flip(false, true);

		birdDown = new TextureRegion(texture, 74, 302, 66, 66);
		birdDown.flip(false, true);

		bird = new TextureRegion(texture, 0, 302, 66, 66);
		bird.flip(false, true);

		birdUp = new TextureRegion(texture, 156, 302, 66, 66);
		birdUp.flip(false, true);

		TextureRegion[] birds = { birdUp, bird, birdDown };
		horseAnimation = new Animation(0.12f, birds);
		horseAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		h1 = new TextureRegion(texture, 5, 300, 84, 66);
		h1.flip(false, true);

		h2 = new TextureRegion(texture, 92, 300, 84, 66);
		h2.flip(false, true);

		h3 = new TextureRegion(texture, 178, 300, 84, 66);
		h3.flip(false, true);

		h4 = new TextureRegion(texture, 262, 300, 84, 66);
		h4.flip(false, true);

		TextureRegion[] horses = { h1, h2, h3, h4 };
		horse2 = new Animation(0.12f, horses);
		horse2.setPlayMode(Animation.PlayMode.LOOP);

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		// Create by flipping existing skullUp
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true);

		carriage = new TextureRegion(texture, 0, 617, 279, 102);
		carriage.flip(false, true);

		carriage_2 = new TextureRegion(texture, 284, 617, 279, 102);
		carriage_2.flip(false, true);

		TextureRegion[] bars = { carriage, carriage_2 };
		trainAnimation = new Animation(0.1f, bars);
		trainAnimation.setPlayMode(PlayMode.LOOP);
		
		
		
		carriage2_1 = new TextureRegion(texture, 0, 502, 279, 102);
		carriage2_1.flip(false, true);

		carriage2_2 = new TextureRegion(texture, 284, 502, 279, 102);
		carriage2_2.flip(false, true);

		TextureRegion[] bars2 = { carriage2_1, carriage2_2 };
		trainAnimation2 = new Animation(0.1f, bars2);
		trainAnimation2.setPlayMode(PlayMode.LOOP);
		
		
		

		bum = new TextureRegion(texture, 340, 47, 12, 13);
		bum.flip(false, true);

		badman = new TextureRegion(texture, 351, 74, 36, 50);
		badman.flip(false, true);

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.30f, -.30f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.30f, -.30f);

		whiteFont = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		whiteFont.setScale(.2f, -.2f);

		prefs = Gdx.app.getPreferences("Wild Man");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		shot = Gdx.audio.newSound(Gdx.files.internal("data/shot.mp3"));

	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		texture.dispose();
		logoTexture.dispose();

		font.dispose();
		shadow.dispose();

		coin.dispose();
	}

}
