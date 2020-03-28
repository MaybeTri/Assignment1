package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {

    private MyGdxGame game;
    private SpriteBatch batch;
    private Texture img,logo;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private BitmapFont creditfont, uiFont;
    private Label title;
    private Stage stage;
    private TextButton playBtn, exitBtn;
    private OrthographicCamera camera;
    private TextureRegion mainBackground,logoRegion;
    private TextureAtlas buttonAtlas;
    private Table table;
    private Viewport viewport;
    final float WORLD_WIDTH = 800;
    final float WORLD_HEIGHT = 480;

    public MainMenu(MyGdxGame game) {
        this.game = game;

    }

    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(800, 800 * (h / w));
        viewport = new ScalingViewport(Scaling.stretch,WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(viewport); //Set up a stage for the ui

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        textButtonStyle = new TextButton.TextButtonStyle();
        skin = new Skin(Gdx.files.internal("textbutton.json"));

        img = new Texture("mainMenuBg.png");
        logo = new Texture("logo.png");
        Image imageActor = new Image(logo);
        mainBackground = new TextureRegion(img, 0, 0);
        img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        uiFont = new BitmapFont(Gdx.files.internal("good_neighbors.fnt"), Gdx.files.internal("good_neighbors.png"), false);
        uiFont.getData().setScale(2, 2);

        //Text Button Style
        textButtonStyle.font = uiFont;
        textButtonStyle.pressedOffsetY = -15;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("buttonPressed");
        textButtonStyle.over = skin.getDrawable("buttonOver");

        //Menu Buttons
        playBtn = new TextButton("Play", textButtonStyle);
        playBtn.setHeight(Gdx.graphics.getHeight() / 4);
        playBtn.setHeight(Gdx.graphics.getWidth() / 8);

        playBtn.setPosition(400 - playBtn.getWidth(), WORLD_HEIGHT / 2 - playBtn.getHeight());
        exitBtn = new TextButton("Exit", textButtonStyle);


        table = new Table();
        table.align(Align.center);
        table.setPosition(0,0);
        table.add(imageActor);
        table.row().padBottom(20).padTop(10);
        table.add(playBtn);
        table.row().padBottom(20);
        table.add(exitBtn);
        table.row().padBottom(20);
        table.setBackground(new TextureRegionDrawable(mainBackground));
        table.setFillParent(true);

        stage.addActor(table);



        //stage.addActor(exitBtn);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        batch.draw(logo, Gdx.graphics.getWidth()/2 - logo.getWidth()/2, Gdx.graphics.getHeight()-200);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        //Button Events

        if (playBtn.isPressed()) {
            game.setScreen(MyGdxGame.gameScreen);
            Gdx.app.log("MenuScreen: ","Button Clicked" );
        }

        if(exitBtn.isPressed()) {
            Gdx.app.exit();
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        logo.dispose();
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
    public void hide() {

    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ", "menuScreen show called");
        create();
    }
}