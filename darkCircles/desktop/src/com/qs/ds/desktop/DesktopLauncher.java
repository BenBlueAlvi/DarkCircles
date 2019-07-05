package com.qs.ds.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.qs.dc.DarkCircles;



public class DesktopLauncher {
	public static void main (String[] arg) {
		DarkCircles theGame = new DarkCircles();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dark Circles";
		cfg.height = DarkCircles.viewHeight;
		cfg.width =  DarkCircles.viewWidth;
	
		//cfg.addIcon("assets/icon.png", FileType.Internal);

		
	
		LwjglApplication launcher = new LwjglApplication(theGame, cfg);
	}
}
