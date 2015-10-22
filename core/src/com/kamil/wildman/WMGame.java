package com.kamil.wildman;

import com.badlogic.gdx.Game;
import com.kamil.screens.SplashScreen;
import com.kamil.wmHelpers.AssetLoader;
import com.kamil.ads.IActivityRequestHandler;

public class WMGame extends Game {
	
	public WMGame (IActivityRequestHandler handler) {
    }

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
