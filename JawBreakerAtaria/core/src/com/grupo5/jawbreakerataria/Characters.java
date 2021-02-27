package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Characters {
	protected int x;
	protected double speed;
	protected int y;
	protected Rectangle collision; 
	protected int directionX; //Determina a dire��o de movimenta��o, direita ou esquerda.
	protected int directionY;
	public Characters(int newX,int newY,int height, int width,double newSpeed) {
		x = newX;
		y = newY;
		speed = newSpeed;
		collision = new Rectangle(x + 83,y,width,height); //O rectagle tem uma fun��o chamada Overlaps que verifica a colisao isso ser� utilzado para o player atravessar os obstaculos.
													//Lembra que o player tamb�m vai ter um rectagle, assim como os inimigos. 
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void move() {
		
	}
	public void setX(int value) {
		x = value;
	}
	public void setY(int value) {
		y = value;
	}
	//colis�o com os inimigos
	public boolean testCollisionSolidCharacter(Rectangle firstCharacter, Rectangle secondCharacter) { //Fun��o que testa se um character est� colidindo com outro
		
		if(firstCharacter.overlaps(secondCharacter)) {
			return true;
		}
		
		return false;
	}
	
	//Colis�o com elementos do mapa
	public boolean testCollisionWall(Rectangle character, Map map) {
		for (Wall wall : map.getListOfWall()) { //Caso algum personagem do game bata parede ele n�o pode atravessar a parede
			if(character.overlaps(wall.getCollision())) {
				return true;
			}
		}
		return false;
		
	}
	public boolean testCollisionObstacle(Rectangle character, Map map) {

		for (Obstacle obstacle : map.getListOfObstacle()) { //Caso algum personagem do game bata parede ele n�o pode atravessar a parede
			if(character.overlaps(obstacle.getCollision())) {
				
				return true;
			}
		}
		return false;
		
	}

}

