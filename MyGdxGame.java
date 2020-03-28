package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends Game implements ApplicationListener {
	// The class with the menu
	public static MainMenu mainMenu;
	// The class with the game
	public static GameScreen gameScreen;
	@Override
	public void create() {
		Gdx.app.log("MyGdxGame: "," create");
		gameScreen = new GameScreen(this);
		mainMenu = new MainMenu(this);
		Gdx.app.log("MyGdxGame: ","about to change screen to mainMenu");
// Change screens to the menu
		setScreen(mainMenu);
		Gdx.app.log("MyGdxGame: ","changed screen to mainMenu");
	}
	@Override
	public void dispose() {super.dispose();}
	@Override
// this method calls the super class render
// which in turn calls the render of the actual screen being used
	public void render() {super.render();}
	@Override
	public void resize(int width, int height) { super.resize(width, height);}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
}
