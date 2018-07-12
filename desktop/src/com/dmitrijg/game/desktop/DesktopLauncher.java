package com.dmitrijg.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dmitrijg.game.LonelyHuman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "user");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LonelyHuman(), config);
		config.addIcon("icon.png", Files.FileType.Local);
		config.title = "Lonely Man";
	}
}
