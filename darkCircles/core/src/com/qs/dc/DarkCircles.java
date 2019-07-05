package com.qs.dc;


import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class DarkCircles extends Game {
	public static final int viewWidth = 1280;
	public static final int viewHeight = 768;

	public static final float aspectRatio = (float) viewWidth / (float) viewHeight;
	
	public static LabelStyle font;
	public static World world;
	

	@Override
	public void create() {
		// viewBorder = new Rectangle(1, 0, PS.viewWidth-1, PS.viewHeight-220);
		/*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/AndromedaTV.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont theFont = generator.generateFont(parameter); // font size 12
																// pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!

		font = new LabelStyle(new BitmapFont(), Color.WHITE);*/

		Box2D.init();
		world = new World(new Vector2(0, 0), true); 
		AssetManager.loadTextures(new File("assets"));
		GameplayScreen ms = new GameplayScreen(this);
		
		setScreen(ms);
	}

	public static int getViewHeight() {
		return viewHeight;
	}

	public static int getViewWidth() {

		return viewWidth;
	}
}
