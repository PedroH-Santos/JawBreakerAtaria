package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ui {
	private int pointsOfPlayer;
	private int levelOfPlayer;
	private int lifeOfPlayer;
	
	private Texture textureOfLife;
	private Sprite spriteOfLife;
	
	public Ui(Player player ) {
		pointsOfPlayer = player.getPoints();
		levelOfPlayer = player.getLevel();
		lifeOfPlayer = player.getLife();
		
		textureOfLife = new Texture(Gdx.files.internal("Life.png"));
		spriteOfLife = new Sprite(textureOfLife,0,0,16,16);
	}

	public void drawUi(SpriteBatch batch, Player player) {
		BitmapFont font = new BitmapFont();
		//Atualizando os valores do player
		pointsOfPlayer = player.getPoints();
		levelOfPlayer = player.getLevel();
		lifeOfPlayer = player.getLife();
		//Points
		font.setColor(Color.YELLOW);
		font.getData().setScale(3,3);
		font.draw(batch, "" + pointsOfPlayer, 330, 40);
		
		//Level
		font.setColor(Color.WHITE);
		font.getData().setScale(2,2);
		font.draw(batch, "" + levelOfPlayer, 650, 80);
		//Life
		for(int i =0; i < lifeOfPlayer ; i++) {
			batch.draw(spriteOfLife, 30 + 30 * i,60);
		}
	}
}
