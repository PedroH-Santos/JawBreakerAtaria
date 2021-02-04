package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.math.Rectangle;

public class Wall {
	
	private int x;
	private int y;
	private Rectangle collision;
	private final int offsetWall = 83;
	
	public Wall(int newX, int newY,int width, int height) {
		x = newX;
		y = newY + offsetWall; //Deve-se fixar 83 por causa do offset feito no mapa 
		
		collision = new Rectangle(x ,y,width ,height);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Rectangle getCollision() {
		return collision;
	}
}
