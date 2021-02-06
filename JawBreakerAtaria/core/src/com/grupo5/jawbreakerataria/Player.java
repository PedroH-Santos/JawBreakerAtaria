package com.grupo5.jawbreakerataria;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class Player extends Characters implements InputProcessor {
	private Texture idleAnimationHorizontal; //Responsavel por pegar separadamente cada anima��o do player (cima,baixo e horizontal)
	private Texture idleAnimationUp;
	private Texture idleAnimationDown;
	private Animation<TextureRegion> idleAnimation; //Variavel resposanvel por juntar e realizar as anima��es
	private static final int COLUNA_SPRITESHEET = 2, LINHAS_SPRITESHEET = 1; //Constantes que representam as colunas e as linhas de cada sprite do personagem (no caso � apenas 2 imagens logo 2 colunas)
	private float stateTime; //Tempo para iniciar a anima��o
	private String sense; 
	private int points;
	private int levelPlayer;
	private int life;
	
	public Player(int newX, int newY,double newSpeed, int newLife, int newLevel) {
		super(newX,newY,32,32,newSpeed);
		idleAnimationHorizontal = new Texture(Gdx.files.internal("PlayerHorizontal.png")); //Pega as texturas
		idleAnimationUp = new Texture(Gdx.files.internal("PlayerUp.png"));
		idleAnimationDown = new Texture(Gdx.files.internal("PlayerDown.png"));





	
		idleAnimation = new Animation<TextureRegion>(0.2f, createIdleFrames(idleAnimationHorizontal)); //instancia a anima��o do player na horizontal e limita o tempo de troca de anima��o em 0.2
		stateTime = 0;


		

		
		Gdx.input.setInputProcessor(this);
		points = 0;
		life = newLife;
		levelPlayer = newLevel;
		
	}
	public TextureRegion[] createIdleFrames(Texture texture) {
		TextureRegion[][] getAllSprites = TextureRegion.split(texture,idleAnimationHorizontal.getWidth() / COLUNA_SPRITESHEET,idleAnimationHorizontal.getHeight() / LINHAS_SPRITESHEET); //A fun��o split basicamente separa cada sprite dentro da imagem, permitindo acessar cada pixel dessa.
		TextureRegion[] getPixelsVector = new TextureRegion[COLUNA_SPRITESHEET * LINHAS_SPRITESHEET]; //Pega todos os frames e insere em um vetor, pois na instancia do idleAnimation apenas � aceito um vetor de textureRegion.
		int indexPixel = 0;
		for (int i = 0; i < LINHAS_SPRITESHEET; i++) {
			for (int j = 0; j < COLUNA_SPRITESHEET; j++) {
				getPixelsVector[indexPixel++] = getAllSprites[i][j];
			}
		}
		return  getPixelsVector;
	}
	public void move(Map map) {
		
		collision.x = x; //Atualizando a posi��o do colissor do player
		collision.y = y;

		//No teste abaixo deve ser passado como parametro um novo retangulo pois deve ser testado a posi��o que o player pode adquirir e n�o a que ele est�.
		//Pois caso seja testado a posi��o em que ele est� esse ficar� parado, j� que a posi��o dele sempre estar� enconstando na parede fazendo com que o player n�o possa se mover.	
		
		//Eixo Vertical
		if(sense == "UP" && y <  594) { //Limite do mapa em cima 
	
			if(testCollisionObstacle(new Rectangle(x, (float) (y + speed), collision.width,collision.height), map)) { //Caso o player colida com um obstaculo ele pode atravessar a parede
				y+= speed; 
			}else if(!testCollisionWall(new Rectangle(x, (float) (y + speed), collision.width,collision.height), map)) { //Caso o player esteja colidindo com a parede ele n se movimenta
				y+= speed;	
			} 
			if(testCollisionWall(collision, map)) { //Apenas verifica se o player est� em cima de uma parede, caso ele esteja ele vai se movimentar para cima
				y+= 5; //O valor atribuido � fixo pois assim evita o player ficar preso no meio do movimento de atravessar a parede
			}
			idleAnimation = new Animation<TextureRegion>(0.2f, createIdleFrames(idleAnimationUp)); //Atualiza a anima��o do player
		} else if(sense == "DOWN" && y >  90) { //Limite do mapa em baixo
			if(testCollisionObstacle(new Rectangle(x, (float) (y - speed), collision.width,collision.height), map)) {
				y-= speed;
			}else  if(!testCollisionWall(new Rectangle(x, (float) (y - speed), collision.width,collision.height), map)) {
				y-= speed;
		
			}
			if(testCollisionWall(collision, map)) {
				y-= 5;
			}
			idleAnimation = new Animation<TextureRegion>(0.2f, createIdleFrames(idleAnimationDown));
		}
		
		//Eixo horizontal
		if(sense == "LEFT" && x > 15) { //Limite do mapa na esquerda
			if(!testCollisionWall(new Rectangle((float) (x - speed),y , collision.width,collision.height), map)) {
				x-= speed;	
			}
			if(testCollisionWall(collision, map)) {
				y+= 5;
			}
			idleAnimation = new Animation<TextureRegion>(0.2f, createIdleFrames(idleAnimationHorizontal));
		}else if(sense == "RIGHT" && x < 648) { //Limite do baixo a direita
			if(!testCollisionWall(new Rectangle((float) (x + speed),y , collision.width,collision.height), map)) {
			
				x+= speed;
			}
			if(testCollisionWall(collision, map)) {
				y+= 5;
			}
			idleAnimation = new Animation<TextureRegion>(0.2f, createIdleFrames(idleAnimationHorizontal));
		}

		
		//Testar colisao com as coins
		testCollisionCoin(collision, map);

	}
	public void testCollisionCoin(Rectangle character, Map map) {
		List<Coin> listOfCoinsCollected = new ArrayList<Coin>(); //Lista para pegar as moedas que foram coletadas
		
		for (Coin coin : map.getListOfCoin()) { //Caso algum personagem do game bata parede ele n�o pode atravessar a parede
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
		stateTime += Gdx.graphics.getDeltaTime(); //O tempo deve ser atualizado a todo loop, pois assim � poss�vel pegar o frame exato para a anima��o ocorrer
		TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime,true);  //pega o frame exato da anima��o
		batch.draw(currentFrame,x, y); 
		

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
		
		//direction = ""; //Caso o player solte o botao a dire��o se torna nula
		
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
