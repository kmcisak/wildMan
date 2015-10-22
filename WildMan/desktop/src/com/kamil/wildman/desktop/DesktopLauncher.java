package com.kamil.wildman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kamil.ads.IActivityRequestHandler;
import com.kamil.wildman.WMGame;

public class DesktopLauncher implements IActivityRequestHandler {
	private static DesktopLauncher application;

	public static void main(String[] arg) {
		if (application == null) {
			application = new DesktopLauncher();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wild Man";
		config.width = 816;
		config.height = 500;
		new LwjglApplication(new WMGame(null), config);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub

	}
}
