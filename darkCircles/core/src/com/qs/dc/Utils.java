package com.qs.dc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Utils {
	
	
	
	public static Vector2 polarToRect(int r, double angle, Vector2 origin) {
		Vector2 v = new Vector2();
		
		v.x = (float) (r * Math.cos(Math.toRadians(angle))) + origin.x;
		v.y = (float) (r * Math.sin(Math.toRadians(angle))) + origin.y;

		return v;
	}
	
	/**
	 * Performs an insertion sort on the given array
	 * @param ar the array to sort
	 */
	public static void insertionSort(int[] ar) {
		for (int i = 1; i < ar.length; i++) {
			int index = ar[i];
			int j = i;
			while (j > 0 && ar[j - 1] > index) {
				ar[j] = ar[j - 1];
				j--;
			}
			ar[j] = index;
		}

	}
	

	@Deprecated
	public static Texture loadTexture(String path) {
		try {
			Texture t = new Texture(Gdx.files.internal(path));
			
			String s = "hello";
		
			return t;

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(Gdx.files.internal("assets/textures/missingTex.png"));
			return t;
		}
	}
	
	/**
	 * Loads an animation from a path
	 * @param path
	 * @param cols
	 * @param rows
	 * @return
	 */
	@Deprecated
	public static Animation<TextureRegion> loadAnimation(String path, int cols, int rows){
		Texture sheet = Utils.loadTexture(path);
		TextureRegion[][] map = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = map[i][j];
			}
		}
		return new Animation<TextureRegion>(0.025f, frames);
	}

	
	/**
	 * calculates the angle between two points
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @return the angle between the points (x, y) and (x2, y2)
	 */
	public static float pointAt(Vector2 p1, Vector2 p2) {

		float yDiff = p1.y - p2.y;
		float xDiff = p1.x - p2.x;
		float newAngle = MathUtils.radiansToDegrees * MathUtils.atan2(yDiff, xDiff) + 180;


		return newAngle;

	}
	
	/**
	 * converts an angle into it's unit components
	 * @param angle the angle to convert, in degrees
	 * @return a vector containing the angle's unit components
	 */
	public static Vector2 convertVec(float angle) {
	

		return new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
	}
	
	/**
	 * calculates the distance between two points
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @return the distance between points (x, y) and (x2, y2)
	 */
	public static float distanceTo(Vector2 p1, Vector2 p2) {

		return  (float) Math.hypot(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
	}
	
	
	/**
	 * gets the number of digits in an integer
	 * @param i the integer to count digits of
	 * @return the number of digits in i
	 */
	public static int getDigits(int i) {
		int count = 0;
		while (i > 0) {
			i/= 10;
			count++;
		}
		
		return count;
	}
	
	
	public static String removeSpaces(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				s = s.substring(0, i) + s.substring(i+1);
			}
		}
		return s;
	}
}
