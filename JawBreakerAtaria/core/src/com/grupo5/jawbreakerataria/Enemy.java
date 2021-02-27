package com.grupo5.jawbreakerataria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
	

public class Enemy extends Characters {
	
	public boolean dead = false;
	public boolean newPositionDead = false;
	
	private Texture EnemyTexture;
	private Texture EnemyTexture2;
	private Texture EnemyTexture3;
	private Texture EnemyTexture4;
	private boolean controle=false;
	private Random random=new Random();
	private int sorteio;
	private boolean imagem=false;
	private ArrayList<Texture> textura = new ArrayList<Texture>();
	private Texture textureEnemyCanDead;
	
	private int timeForEndDead = 300;
	private int currentTime;
	
	public Enemy(int newX, int newY, int height, int width, double newSpeed,String spritepath) {
		super(newX, newY, 33,33, newSpeed);
		System.out.println(speed);
		if(imagem==false) {
			LoadImages();
			imagem=true;
		}
		
		
		
	}
	public void LoadImages() {
		textura = new ArrayList<Texture>();
		EnemyTexture = new Texture(Gdx.files.internal("Enemy1.png"));
		EnemyTexture2 = new Texture(Gdx.files.internal("Enemy2.png"));
		EnemyTexture3 = new Texture(Gdx.files.internal("Enemy3.png"));
		EnemyTexture4 = new Texture(Gdx.files.internal("Enemy4.png"));
		textureEnemyCanDead = new Texture(Gdx.files.internal("EnemyCanDead.png"));
		textura.add(EnemyTexture);
		textura.add(EnemyTexture2);
		textura.add(EnemyTexture3);
		textura.add(EnemyTexture4);
		
		
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void move(Map map) {
		collision.x = x; 
		collision.y = y;
		if(x<-30) {
			sorteio=random.nextInt(2);
			if(sorteio==1) {
				y=GeneratePositionEnemyY();
			}
			controle=true;
			
		}else if(x>730) {
			sorteio=random.nextInt(2);
			if(sorteio==1) {
				y=GeneratePositionEnemyY();
			}
			controle=false;
		}
		if(controle==false) {
			x-= speed;
		}else {
			x+= speed;
		}
		
		

	}
	public void resetDead() {
		currentTime++;
		if(currentTime == timeForEndDead) {
			currentTime = 0;
			dead = false;
			
		}
	}
	public void Render(SpriteBatch batch,int posX,int posY,Texture texture, Player player) {
		if(player.canDestroyEnemy) {
			batch.draw(textureEnemyCanDead, posX, posY);
		}else {
			batch.draw(texture, posX, posY);
		}
		
	}
	public void setX(int value) {
		x = value;
	}
	public void setY(int value) {
		y = value;
	}

	public static int GeneratePositionEnemyY() {
		Random sorteio= new Random();
		int posicao,valor;
		valor=sorteio.nextInt(9);
		posicao=(110+(valor*60));
		return posicao;
	}
	public static int GeneratePositionEnemyX() {
		Random sorteio= new Random();
		int posicao;
		posicao=sorteio.nextInt(700);
		return posicao;
	}
	public Rectangle getCollision() {
		return collision;
	}
	public Texture getTexture(int indice){
		return textura.get(indice);
	}

}
