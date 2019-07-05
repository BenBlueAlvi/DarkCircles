package com.qs.dc;

import com.badlogic.gdx.graphics.Texture;

public class Debris extends PhysicsActor{

	public Debris(int size) {
		super(AssetManager.getTexture("debris" + Integer.toString(size)), (size * 10 + 1) / 1000, 6 * size + 1);
		
		// TODO Auto-generated constructor stub
		
	}
	
}
