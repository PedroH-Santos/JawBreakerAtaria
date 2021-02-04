package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle extends Characters{

	private Texture textureObstacle;
	private Sprite spriteObstacle;

	public Obstacle(int newX,int newY, double newSpeed){
		super(newX,newY,9,52,newSpeed);
		speed = newSpeed;
		textureObstacle = new Texture(Gdx.files.internal("Obstacle.png"));
		spriteObstacle = new Sprite(textureObstacle,0,0,52,9);
		directionX = 1;
		
	}
	public void move() {
		
		if(directionX == 1) { //Caso o obstaculo esteja indo para a direita ele vira para a esquerda
			 //
			if(x >= 574) {//574 é a posição X máxima das paredes, ou seja, é onde a parede termina
				directionX = -1;
			}
		}else {//Caso o obstaculo esteja indo para a esquerda ele vira para a direita
			if(x <= 63) { //63 é a posição X de origem das paredes
				directionX = 1;
			}
		}

		x+= speed * directionX;
		collision.x = x; //atualiza colizor
		
	}
	
	public Sprite getSprite() {
		return spriteObstacle;
	}
	public Rectangle getCollision() {
		return collision;
	}
	
}
