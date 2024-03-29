package com.qs.dc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class BaseActor extends Group {
	public TextureRegion region;
	public Rectangle boundary;
	public Vector2 vel;
	protected Polygon boundingPolygon;
	public Vector2 center;

	protected float elapsedTime;
	private String path;
	
	private Texture texture;


	/**
	 * Creates a base actor with the texture given
	 * @param t the texture
	 */

	public BaseActor(Texture t) {
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		vel = new Vector2(0, 0);
		elapsedTime = 0;
		this.center = new Vector2();
		this.setTexture(t);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
		this.setRectangleBoundary();


	}
	/**
	 * creates a base actor with no texture
	 * (set texture before rendering)
	 */
	
	public BaseActor() {
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		vel = new Vector2(0, 0);
		elapsedTime = 0;
		this.center = new Vector2();
		this.setRectangleBoundary();
	}
	
	/**
	 * Sets the base actor's texture to the given texture
	 * Also update's its size parameters
	 * @param t the texture
	 */

	public void setTexture(Texture t) {
		texture = t;
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth(w);
		setHeight(h);
		region.setRegion(t);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
		

	}
	
	/**
	 * Sets the BaseActor's texture to the texture at the given path
	 *
	 * @param path to the texture
	 */

	public void genTexture(String path) {
		try {
			Texture t = new Texture(Gdx.files.internal(path));
			setTexture(t);

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(Gdx.files.internal("assets/missingTex.png"));
			setTexture(t);
		}
	}

	// Currently broken
	public void genAnimation(String path, int frames) {
		TextureRegion[] frameImgs = new TextureRegion[frames];
		for (int n = 0; n < frames; n++) {
			Texture tex = new Texture(Gdx.files.internal(path + n + ".png"));
			tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			frameImgs[n] = new TextureRegion(tex);
		}
		Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
		Animation anim = new Animation(0.1f, framesArray, Animation.PlayMode.LOOP_PINGPONG);
		// Texture baseTex = anim.getKeyFrame(0);
	}
	/**
	 * Gets the BaseActor's bounding rectangle
	 * @return Rectangle bouding rectangle
	 */
	
	public Rectangle getBoundingRectangle() {
		boundary.set(getX(), getY(), getWidth(), getHeight());
		return boundary;

	}
	

	private void setRectangleBoundary() {
		float w = getWidth();
		float h = getHeight();
		float[] vertices = { 0, 0, w, 0, w, h, 0, h };
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin(getOriginX(), getOriginY());
	}
	/**
	 * Gets the BaseActor's bounding polygon
	 * equivilent to getBoundingRectangle() currently
	 * @return
	 */
	public Polygon getBoundingPolygon() {
		boundingPolygon.setPosition(getX(), getY());
		boundingPolygon.getRotation();
		return boundingPolygon;
	}
	
	
	/**
	 * Checks to see if the BaseActor is overlapping another BaseActor	
	 * @param other the other BaseActor
	 * @param resolve True to move the baseActors out of each other if they are overlapping
	 * @return Boolean weather or not they are overlapping
	 */
	public boolean overlaps(BaseActor other, boolean resolve) {
		Polygon poly1 = this.getBoundingPolygon();
		Polygon poly2 = other.getBoundingPolygon();

		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return false;

		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polyOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

		if (polyOverlap && resolve) {
			this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		}
		float significant = 0.5f;
		return (polyOverlap && (mtv.depth > significant));

	}
	
	/**
	 * Function that acts as an update loop
	 */
	public void act(float dt) {
		super.act(dt);
		moveBy(vel.x * dt, vel.y * dt);
		elapsedTime += dt;
		center.x = this.getX() + this.getWidth() / 2;
		center.y = this.getY() + this.getHeight() / 2;

	}
	/**
	 * draws the BaseActor
	 */

	public void draw(Batch batch, float parentAlpha) {
		// region.setRegion(anim.getKeyFrame(elapsedTime));

		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if (isVisible()) {
			batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

		}
		super.draw(batch, parentAlpha);
	}

	/**
	 * Sets this BaseActor to copy the given BaseActor
	 * @param orig the BaseActor to copy
	 */
	public void copy(BaseActor orig) {
		this.region = new TextureRegion(orig.region);
		if (orig.boundingPolygon != null) {
			this.boundingPolygon = new Polygon(orig.boundingPolygon.getVertices());
			this.boundingPolygon.setOrigin(orig.getOriginX(), orig.getOriginY());

		}
		this.setPosition(orig.getX(), orig.getY());
		this.setOriginX(orig.getOriginX());
		this.setOriginY(orig.getOriginY());
		this.setWidth(orig.getWidth());
		this.setHeight(orig.getHeight());
		this.setColor(orig.getColor());
		this.setVisible(orig.isVisible());

	}
	
	
	/**
	 * Returns a copy of this BaseActor
	 */
	public BaseActor clone() {
		BaseActor newbie = new BaseActor(this.texture);
		newbie.copy(this);
		return newbie;
	}
	/**
	 * Tells the BaseActor to point at the given point
	 * @param x the x coord of the point to point at
	 * @param y the y coord of the point to point at
	 * @param speed the speed at which the BaseActor should rotate
	 * @param rotate whether or not the BaseActor's image should be rotated to point at the given point
	 * @return the angle between the BaseActor and the given point
	 */
	public double pointAt(double x, double y, float speed, boolean rotate) {

		double yDiff = y - this.center.y;
		double xDiff = x - this.center.x;
		double newAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));

		if (rotate) {
			this.addAction(Actions.rotateTo((float) newAngle, speed));
		}
		return newAngle;
		


	}
	

	/**
	 * Calculates the distance between the center of this BaseActor and the given coords
	 * @param x the x coord
	 * @param y the y coord
	 * @return Double the distance
	 */
	public double distanceTo(double x, double y) {

		return Math.hypot(Math.abs(this.center.x - x), Math.abs(this.center.y - y));
	}
	
	/**
	 * Moves the BaseActor's center to the given coords
	 * @param x the x coord
	 * @param y the y coord
	 */

	public void setCenter(float x, float y) {
		this.setPosition(x - this.getWidth() / 2, y - this.getHeight() / 2);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
		this.center.set(x, y);
	
	}
	/**
	 * gets the texture of this BaseActor
	 * @return Texture the texture of the BaseActor
	 */
	public Texture getTexture() {
		return this.texture;
	}

}
