package com.grupo5.jawbreakerataria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class Map {
	private int limitOfSpeedOfObstacle; // É a variavel que apresenta a máxima velocidade que os obstaculos podem obter
	private Texture textureOfMap;
	private Sprite mapSprite;
	private List<Obstacle> listOfObstacle; //Lista de obstaculos
	private List<Coin> listOfCoins; //Lista de moedas do jogo
	private List<Wall> listOfWall; //Lista das paredes do jogo
	
	
	private Random random; //Objeto utilizada para tirar um numero aleatorio
	public Map(int  newLimitOfSpeedOfObstacle) {
		textureOfMap = new Texture(Gdx.files.internal("Map.png"));
		mapSprite = new Sprite(textureOfMap,0,0,700,600);
		limitOfSpeedOfObstacle = newLimitOfSpeedOfObstacle;
		
		createWall();
		createObstacle();
		createCoins();
	}
	public Sprite getSprite() {
		return mapSprite;
	}
	
	//Wall
	
	public void createWall() {
		listOfWall = new ArrayList<Wall>();
		//As paredes que ficam dentro do JawBreaker possui 574px de largura e 7px de altura. Além disso, são 8 paredes
		for (int i = 0; i < 8; i++) {  
			Wall wall = new Wall(63 , 69 + 60*i,574,7); //É o mesmo calculo para 
			listOfWall.add(wall); //Esses valores representam a posição da parede. 63 é fixo pois todas as paredes iniciam nessa posição. A outra expressão é pq cada parede está a 60 de distância uma da outra e a primeira está na posição 69, ou seja, deve incrementar 69 + 60 * i (até 7).
		}
	}
	public List<Wall> getListOfWall(){
		return listOfWall;
	}
	
	//Obstacles
	
	public void  createObstacle() {
		random = new Random();
		listOfObstacle = new ArrayList<Obstacle>();
		int indexOfWall = 0;
		for (Wall wall : listOfWall) {
			Obstacle obstacle = new Obstacle(wall.getX() ,wall.getY(), random.nextDouble()  * limitOfSpeedOfObstacle + 1); //Como os obstaculos devem estar em cima das paredes eles pegam a mesma posição que essa. A velocidade é gerada aleatoriamente em um intervalo de 1 a 5. 
			listOfObstacle.add(obstacle);
			indexOfWall++;
		}

		
		
	}
	public void drawObstacle(SpriteBatch batch, int offset) {

	
		
		for (Obstacle obstacle : listOfObstacle) {
		
			batch.draw(obstacle.getSprite(), obstacle.getX(), obstacle.getY() ); //É necessário incremetar o offset realizado no mapa par que a posição dos obstaculos calculada anteriormente consiga se relacionar com as posições do mapa. Além disso, é necessário incrementar + 33 posições para que os obstaculos fiquem na posição exata da parede.
		}
		
	
	}
	
	
	//Essas três funções apenas são para testar as colisões
	/*public void drawRectagleObstacle(SpriteBatch batch) {
		batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeType.Filled);
        for (Obstacle obstacle : listOfObstacle) {
            
           shapeRenderer.setColor(Color.RED);
           shapeRenderer.rect(obstacle.getX(), obstacle.getY(), obstacle.collision.width, obstacle.collision.height);

		}
        shapeRenderer.end();
        batch.begin();
	}
	public void drawRectagleWall(SpriteBatch batch) {
		batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeType.Filled);
        for (Wall wall : listOfWall) {
            
           shapeRenderer.setColor(Color.WHITE);
           shapeRenderer.rect(wall.getX(), wall.getY(), wall.getCollision().width, wall.getCollision().height);

		}
        shapeRenderer.end();
        batch.begin();
	}
	public void drawRectagleCoins(SpriteBatch batch) {

		batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeType.Filled);
        for (Coin coin : listOfCoins) {
            
           shapeRenderer.setColor(Color.WHITE);
           shapeRenderer.rect(coin.getCollision().x, coin.getCollision().y, coin.getCollision().width, coin.getCollision().height);

		}
        shapeRenderer.end();
        batch.begin();
	}*/
	
	
	
	public void moveObstacle() {
		for (Obstacle obstacle : listOfObstacle) {
			obstacle.move();
		}
	}
	public List<Obstacle> getListOfObstacle(){
		return listOfObstacle;
	}
	
	//Coins
	public void createCoins() {
		listOfCoins = new ArrayList<Coin>();
		for (int indexOfWall = 0; indexOfWall < 9; indexOfWall++) { //Quantidade de locais que devem receber moedas
			for (int i = 0; i < 20; i++) { //Dentro do JawBreaker há 9 fileiras com moedas, sendo que cada fileira possui 15 moedas
				Coin coin = new Coin(63 + 30 * i ,50 + 60 * indexOfWall,10); //Esses valores representam a posição da moeda. A posição X é sempre 63
															 //(posição original das paredes) + 15 (distancia de cada coin) * i.
															// A posição Y é sempre 45 + 60 (distancia entre paredes) * index do obstaculo (Assim, sempre terá 15 moedas para cada parede). E por fim há o valor de cada moeda
				listOfCoins.add(coin);
			
			}
		}
	}
	public void drawCoins(SpriteBatch batch, int offset) {


		for (Coin coin : listOfCoins) {
	
			batch.draw(coin.getSprite(), coin.getX(), coin.getY()  + offset + 20); //É necessário incremetar o offset realizado no mapa par que a posição dos obstaculos calculada anteriormente consiga se relacionar com as posições do mapa. Além disso, é necessário incrementar + 33 posições para que os obstaculos fiquem na posição exata da parede.
		}
		
	
	}
	public List<Coin> getListOfCoin(){
		return listOfCoins;
	}
	

}
