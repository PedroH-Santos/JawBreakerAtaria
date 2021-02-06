package com.grupo5.jawbreakerataria;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	
	private SpriteBatch batch;
	private Map map;
	private OrthographicCamera camera;
	public final  int offset = 50; //Variavel constantes que determina a variação da imagem do mapa em relação a camera (Isso é util para deixar um espaço em baixo do mapa para os pontos)
	private Player player;
	private Ui ui;
	@Override
	public void create () {
		
		
		batch = new SpriteBatch();
		player = new Player(350,290,3,3,1);
		map = new Map(6);
		ui = new Ui(player);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 700, 600 + offset); //A camera é incrementada com o offset para que a janela apareça a parte do mapa que poderia ser cortada com o incremento do offset


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
		
		/*map.drawRectagleWall(batch);
		map.drawRectagleObstacle(batch);
		map.drawRectagleCoins(batch);*/
		
		ui.drawUi(batch, player);
		batch.end();
		map.moveObstacle();
		player.move(map); 
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
		
	}


}
