package com.qs.dc;




import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class GameplayScreen implements Screen {

	public Stage mainStage;
	public Stage uiStage;
	
	private Camera camera;
	
	private Game game;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	private PhysicsActor test = new PhysicsActor(AssetManager.getTexture("eye"), 0.1f, 10);
	
	
	private ArrayList<Debris> circles = new ArrayList<Debris>();
	

	
	private BaseActor center = new BaseActor(AssetManager.getTexture("eye"));
	
	private ShapeRenderer renderer = new ShapeRenderer();
	
	public GameplayScreen(Game g) {
		game = g;
		create();
	}

	public void create() {

		camera = new OrthographicCamera();

		mainStage = new Stage(new ScreenViewport(camera));
	
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		
		mainStage.addActor(test);
		
		test.body.setTransform(DarkCircles.viewWidth/2, DarkCircles.viewHeight/2, 0);
		center.setCenter(DarkCircles.viewWidth/2, DarkCircles.viewHeight/2);
		mainStage.addActor(center);
		
		for (int i = 0 ; i < 100; i++) {
			Debris d = new Debris(1);
			d.body.setTransform(MathUtils.random(0, DarkCircles.viewWidth), MathUtils.random(0, DarkCircles.viewHeight), 0);
			mainStage.addActor(d);
			circles.add(d);
		}
		
		
	
		
		
		for (int i = -100; i < 100; i++) {
			BaseActor b = new BaseActor(AssetManager.getTexture("e"));
			b.setCenter(i * 10, 0);
			mainStage.addActor(b);
		}
	}

	public void render(float dt) {

		mainStage.act(dt);
		
		
		

	
		camera.position.x = test.center.x;
		camera.position.y = test.center.y;
		//gravityUpdate();

		Gdx.gl.glClearColor(0.0F, 0.0F, 0, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyPressed(Keys.Z)) {
			
			applyForces(-100000);
			
		} else if (Gdx.input.isKeyPressed(Keys.X)) {
			applyForces(100000);
		}
		
		gravityUpdate();
		mainStage.draw();
		
		
		DarkCircles.world.step(1/60, 6, 2);
	}
	
	
	public void gravityUpdate() {
		for(PhysicsActor c : circles) {
			for(PhysicsActor c2 : circles) {
				if (!c.equals(c2)) {
					Vector2 force = Utils.convertVec(Utils.pointAt(
							c2.body.getPosition(), c.body.getPosition()
					));
					float dist = Utils.distanceTo(c2.body.getPosition(), c.body.getPosition());
					System.out.println(dist);
					
					force.x *= -100 * c.body.getMass();
					force.y *= -100 * c.body.getMass();
					if (dist > 0) {
						force.x /= dist;
						force.y /= dist;
					}
					
					c.body.applyForceToCenter(force, true);
				}
				
			}
			
		}
		
		//forces on player
		for(PhysicsActor c : circles) {
			Vector2 force = Utils.convertVec(Utils.pointAt(
					test.body.getPosition(), c.body.getPosition()
			));
			float dist = Utils.distanceTo(test.body.getPosition(), c.body.getPosition());
			
			
			renderer.end();
			force.x *= 100;
			force.y *= 100;
			if (dist > 0) {
				force.x /= dist;
				force.y /= dist;
			}
			
			test.body.applyForceToCenter(force, true);
		}
	}
	
	public void applyForces(int amt) {
		for(PhysicsActor c : circles) {
			Vector2 force = Utils.convertVec(Utils.pointAt(
					test.body.getPosition(), 
					c.body.getPosition()
			));
			float dist = Utils.distanceTo(test.body.getPosition(), c.body.getPosition());
			System.out.println(dist);
		
			
			force.x *= -amt;
			force.y *= -amt;
			if (dist > 0 && dist < 1000) {
				force.x /= dist;
				force.y /= dist;
			}
			
			test.body.applyForceToCenter(force, true);
		}
	}
	
	@Override
	public void show() {
																// Ratio
																		// Maintenance

	}

	@Override
	public void resize(int width, int height) {
		//mainStage.getViewport().update(width, height, false);
		//uiStage.getViewport().update(width, height, false);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		
	}

	

}
