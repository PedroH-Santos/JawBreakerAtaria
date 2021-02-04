package com.grupo5.jawbreakerataria.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grupo5.jawbreakerataria.Game;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 700;
		config.height = 650;
		
		new LwjglApplication(new Game(), config);
	}
}
