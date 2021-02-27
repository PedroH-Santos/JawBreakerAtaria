package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenOfEndPhase {
	Game game;
	private Texture GameOver;
	private Texture GameWin;
	private BitmapFont font;
	public ScreenOfEndPhase(Game game) {
		this.game=game;
		GameOver = new Texture(Gdx.files.internal("game_over.png"));
		GameWin = new Texture(Gdx.files.internal("GameWin.png"));
		font = new BitmapFont();
	}

	public void renderGameOver(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.WHITE);
		font.getData().setScale(2,2);
		batch.begin();
			batch.draw(GameOver, 0, 0);
			font.draw(batch,"PRESS ENTER TO TRY AGAIN", 140, 100);
		batch.end();
		
	}
	public void renderGameWin(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.WHITE);
		font.getData().setScale(2,2);
		batch.begin();
			batch.draw(GameWin, 0, 0);
			font.draw(batch,"PRESS ENTER TO TRY AGAIN", 140, 100);
		batch.end();
		
	}

	
}