package com.AngryBirdsGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public OrthographicCamera camera;
    public SpriteBatch batch;
    @Override
    public void create() {
        camera=new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch=new SpriteBatch();
        setScreen(new OngoingScreen());
    }

    @Override
    public void render() {
        System.out.println("hello");
    }
}
