package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
        MyGdxGame game; // Note it’s "MyGdxGame" not "Game"
        private SpriteBatch batch;
        private Stage stage;
        private OrthographicCamera camera;
        private TiledMap tiledMap;
        private TiledMapRenderer tiledMapRenderer;
        private Viewport viewport;
        final float WORLD_WIDTH = 800;
        final float WORLD_HEIGHT = 480;
    //Player Character
    Texture characterTexture;
    int characterX;
    int characterY;
    float movementCooldown;
    //Game clock
    long lastTime;
    float elapsedTime;


        public GameScreen(MyGdxGame game) {
            this.game = game;
        }

        public void create() {
            batch = new SpriteBatch();
            tiledMap = new TmxMapLoader().load("street.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            characterTexture = new Texture("froggy.png");

            Gdx.app.log("GameScreen: ", "GameScreen create");
            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();
            batch = new SpriteBatch();
            camera = new OrthographicCamera(800, 800 * (h / w));
            camera.setToOrtho(false, w / 2, h / 2);

            viewport = new ScalingViewport(Scaling.stretch,WORLD_WIDTH, WORLD_HEIGHT, camera);
            stage = new Stage(viewport); //Set up a stage for the ui



            camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
            camera.update();

        }

    private void newGame() {

        //Translate camera to center of screen
        camera.position.x = 16; //The 16 is half the size of a tile
        camera.position.y = 16;

        lastTime = System.currentTimeMillis();
        elapsedTime = 0.0f;

        //Player start location, you can have this stored in the tilemaze using an object layer.
        characterX = 1;
        characterY = 18;
        movementCooldown = 0.0f;

        camera.translate(characterX * 32, characterY * 32);
    }

        public void render(float f) {

            //Update game clock first
            long currentTime = System.currentTimeMillis();
            //Divide by a thousand to convert from milliseconds to seconds
            elapsedTime = (currentTime - lastTime) / 1000.0f;
            lastTime = currentTime;

            //TODO Render Map Here
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();

            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            camera.update();
            batch.draw(characterTexture, characterX * 32, characterY * 32, 32, 32);
            batch.end();

            //stage.act(Gdx.graphics.getDeltaTime());
            //stage.draw();

        }

        @Override
        public void dispose() {
            batch.dispose();
        }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800f;
        camera.viewportHeight = 800f * height/width;
        camera.update();
        stage.getViewport().update(width,height,false);

        Gdx.app.log("MenuScreen", "Resizing screen to: " + width + " x " + height);
    }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void show() {
            Gdx.app.log("GameScreen: ", "GameScreen show called");
            create();
        }

        @Override
        public void hide() {
            Gdx.app.log("GameScreen: ", "GameScreen hide called");
        }
    }

