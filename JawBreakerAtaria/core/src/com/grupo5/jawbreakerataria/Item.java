package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item {
	private Texture textureItem;
	private Sprite spriteItem;
	private int x;
	private int y;
	private Rectangle collision;
	private final int offsetItem = 70;
	

	
	public Item(int newX,int newY) {
		// TODO Auto-generated constructor stub
		textureItem = new Texture(Gdx.files.internal("Item.png"));
		spriteItem = new Sprite(textureItem,0,0,8,9);
		x = newX;
		y = newY;
		collision = new Rectangle(x,y + offsetItem,8,9); //O Offset das moedas será 70 por causa do offset feito no mapa

	}
	public void drawItem(SpriteBatch batch) {
		batch.draw(spriteItem, x ,y + offsetItem);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Sprite getSprite() {
		return spriteItem;
	}

	public Rectangle getCollision() {
		// TODO Auto-generated method stub
		return collision;
	}

	
}
