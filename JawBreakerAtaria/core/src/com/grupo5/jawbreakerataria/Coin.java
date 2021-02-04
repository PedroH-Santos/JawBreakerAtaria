package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Coin {
	private int value;
	private Texture textureCoin;
	private Sprite spriteCoin;
	private int x;
	private int y;
	private Rectangle collision;
	private final int offsetCoin = 70;
	public Coin(int newX,int newY,int newValue) {
		textureCoin = new Texture(Gdx.files.internal("Coin.png"));
		spriteCoin = new Sprite(textureCoin,0,0,8,9);
		value = newValue;
		x = newX;
		y = newY;
		collision = new Rectangle(x,y + offsetCoin,8,9); //O Offset das moedas será 70 por causa do offset feito no mapa
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Sprite getSprite() {
		return spriteCoin;
	}
	public int getValue() {
		return value;
	}
	public Rectangle getCollision() {
		// TODO Auto-generated method stub
		return collision;
	}
}
