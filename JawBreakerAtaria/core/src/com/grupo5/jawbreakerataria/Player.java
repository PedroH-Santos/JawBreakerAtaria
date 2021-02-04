package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
public class Player extends Characters implements InputProcessor {
	private Texture texturePlayer;
	private Sprite spritePlayer;
	private String sense; 
	private int points;
	private int levelPlayer;
	private int life;
	
	public Player(int newX, int newY,double newSpeed, int newLife, int newLevel) {
		super(newX,newY,32,32,newSpeed);
		texturePlayer = new Texture(Gdx.files.internal("Player.png"));
		spritePlayer = new Sprite(texturePlayer,0,0,32,32);
		Gdx.input.setInputProcessor(this);
		points = 0;
		life = newLife;
		levelPlayer = newLevel;
	}
	public void move(Map map) {
		
		collision.x = x; //Atualizando a posição do colissor do player
		collision.y = y;

		//No teste abaixo deve ser passado como parametro um novo retangulo pois deve ser testado a posição que o player pode adquirir e não a que ele está.
		//Pois caso seja testado a posição em que ele está esse ficará parado, já que a posição dele sempre estará enconstando na parede fazendo com que o player não possa se mover.	
		
		//Eixo Vertical
		if(sense == "UP" && y <  594) { //Limite do mapa em cima 
	
			if(testCollisionObstacle(new Rectangle(x, (float) (y + speed), collision.width,collision.height), map)) { //Caso o player colida com um obstaculo ele pode atravessar a parede
				y+= speed; 
			}else if(!testCollisionWall(new Rectangle(x, (float) (y + speed), collision.width,collision.height), map)) { //Caso o player esteja colidindo com a parede ele n se movimenta
				y+= speed;	
			} 
			if(testCollisionWall(collision, map)) { //Apenas verifica se o player está em cima de uma parede, caso ele esteja ele vai se movimentar para cima
				y+= 5; //O valor atribuido é fixo pois assim evita o player ficar preso no meio do movimento de atravessar a parede
			}
		} else if(sense == "DOWN" && y >  90) { //Limite do mapa em baixo
			if(testCollisionObstacle(new Rectangle(x, (float) (y - speed), collision.width,collision.height), map)) {
				y-= 3;
			}else  if(!testCollisionWall(new Rectangle(x, (float) (y - speed), collision.width,collision.height), map)) {
				y-= speed;
		
			}
			if(testCollisionWall(collision, map)) {
				y-= 5;
			}
		}
		
		//Eixo horizontal
		if(sense == "LEFT" && x > 15) { //Limite do mapa na esquerda
			if(!testCollisionWall(new Rectangle((float) (x - speed),y , collision.width,collision.height), map)) {
				x-= speed;	
			}
			if(testCollisionWall(collision, map)) {
				y+= 5;
			}
		}else if(sense == "RIGHT" && x < 648) { //Limite do baixo a direita
			if(!testCollisionWall(new Rectangle((float) (x + speed),y , collision.width,collision.height), map)) {
			
				x+= speed;
			}
			if(testCollisionWall(collision, map)) {
				y+= 5;
			}
		}

		
		//Testar colisao com as coins
		testCollisionCoin(collision, map);

	}
	public void testCollisionCoin(Rectangle character, Map map) {
		List<Coin> listOfCoinsCollected = new ArrayList<Coin>(); //Lista para pegar as moedas que foram coletadas
		
		for (Coin coin : map.getListOfCoin()) { //Caso algum personagem do game bata parede ele não pode atravessar a parede
			if(character.overlaps(coin.getCollision())) {
				
				points+= coin.getValue();
				listOfCoinsCollected.add(coin);
			}
		}
		
		//Deletando as moedas coletadas do jogo e da lista
		for (Coin coin : listOfCoinsCollected) {
			map.getListOfCoin().remove(coin);
			
		}
		listOfCoinsCollected.clear();
		
		
	}
	public void drawPlayer(SpriteBatch batch) {
		
		batch.draw(spritePlayer,x, y); 
		

	}
	public int getLevel(){
		return levelPlayer;
	}
	public int getLife(){
		return life;
	}
	public int getPoints(){
		return points;
	}
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		if(keycode ==  Input.Keys.W) {
			sense = "UP";

		}if(keycode ==  Input.Keys.A){
			sense = "LEFT";

		}
		if(keycode ==  Input.Keys.S) {
			sense = "DOWN";

		}
		if(keycode ==  Input.Keys.D) {
			sense = "RIGHT";

		}
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		
		//direction = ""; //Caso o player solte o botao a direção se torna nula
		
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub


		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		System.out.println("X :" + screenX + " Y: " + screenY);
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
}
