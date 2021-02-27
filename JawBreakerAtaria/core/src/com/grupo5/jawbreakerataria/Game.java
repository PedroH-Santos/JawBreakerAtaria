package com.grupo5.jawbreakerataria;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	public static int currentLevelPlayer;
	
	
	
	
	private Texture GameOver;
	private ArrayList<Enemy> listEnemy;
	private SpriteBatch batch;
	private Map map;
	private OrthographicCamera camera;
	public final  int offset = 50; //Variavel constantes que determina a variação da imagem do mapa em relação a camera (Isso é util para deixar um espaço em baixo do mapa para os pontos)
	private Player player;
	private Ui ui;
	ScreenOfEndPhase screenEndPhase;
	
	
	private int currentTimeSpawnItem = 0;
	private int timeForSpawn = 400;
	private Item item = null;
	
	@Override
	public void create () {

		listEnemy = new ArrayList<Enemy>();
		Random rand = new Random();
		currentLevelPlayer++;
		System.out.println(currentLevelPlayer);
		for(int i=0;i<4;i++) {
			Enemy e= new Enemy(Enemy.GeneratePositionEnemyX(),Enemy.GeneratePositionEnemyY(),7,7,2 + currentLevelPlayer,"Enemy"+(i+1)+".png");
			listEnemy.add(e);
		}
		batch = new SpriteBatch();
		player = new Player(350,290,3,3);
		map = new Map(6);
		ui = new Ui(player,this);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 700, 600 + offset); //A camera é incrementada com o offset para que a janela apareça a parte do mapa que poderia ser cortada com o incremento do offset
		screenEndPhase=new ScreenOfEndPhase(this);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		
		batch.draw(map.getSprite(),0,offset); //Com o offset o mapa sobe um pouco e permite com que os pontos sejam colocados em baixo dele
		
		map.drawObstacle(batch,offset);
		map.drawCoins(batch, offset);
		player.drawPlayer(batch);
		

		for (int i =0; i < listEnemy.size(); i++) {
			Enemy enemy = listEnemy.get(i);
			enemy.Render(batch, enemy.getX(), enemy.getY(),enemy.getTexture(i),player);
			
		}
		ui.drawUi(batch, player);
		if(item != null) {
			item.drawItem(batch);
		}
		batch.end();
		map.moveObstacle();
		player.move(map); 
		player.callColisionEnemy(this);
		
		for (int i =0; i < listEnemy.size(); i++) {
			Enemy enemy = listEnemy.get(i);
			if(enemy.dead){ //Caso o inimigo esteja morto ele passa por essa condição
				enemy.resetDead(); //Função chamada para voltar o inimigo ao jogo depois de certo tempo 
				if(!enemy.newPositionDead){ //Testa se a nova posição do inimigo já foi determinada
					enemy.setX(731); //Coloca o inimigo na borda do mapa para setar uma nova posição x e y
					enemy.newPositionDead = true;
				}
			}else {
				enemy.newPositionDead = false;
				enemy.move(map);
			}
			
		}
		
		
		
		if(item == null) { //Caso não tenha item no mapa entra na condição. Assim, em um determinado tempo spawna um item no centro do mapa
			currentTimeSpawnItem++;
			if(currentTimeSpawnItem == timeForSpawn) { 
				currentTimeSpawnItem = 0;
				item = new Item(350,290);
				
				
			}
			
		}else {
			if(player.testCollisionSolidCharacter(player.collision,item.getCollision())) { //Caso o player colida com o item esse recebe o poder de comer os inimigos
				player.canDestroyEnemy = true;
				item = null;
			}
		}

		if(map.getListOfCoin().size() <= 0 ) { //Player venceu
			player.setX(350);
			player.setY(290);
			screenEndPhase.renderGameWin(batch);
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				player = null;
				listEnemy.clear();
				map = null;
				
				this.create();
			}
			return;
			
		}
		if(player.getLife()==0) {
			screenEndPhase.renderGameOver(batch);
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				currentLevelPlayer = 1;
				this.create();
				
				
			}
			return;
		}
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
		
	}

	public List<Enemy> getListEnemy(){
		return listEnemy;
	}
}
