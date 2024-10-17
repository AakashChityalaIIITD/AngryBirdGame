package com.AngryBirdsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.awt.*;


public class MainScreen implements Screen {
    private final LoadScreen game;
    private Stage stage;
    private Sprite background;
    private ImageButton newGameButton;
    private ImageButton LoadSavedGameButton;
    private ImageButton ExitButton;

    public MainScreen(final LoadScreen main) {
        this.game = main;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background=new Sprite(new Texture("img.png"));
        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        newGameButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("img.png"))));
        LoadSavedGameButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("img.png"))));
        ExitButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("img.png"))));
        stage.addActor(newGameButton);
        stage.addActor(LoadSavedGameButton);
        stage.addActor(ExitButton);

    }
    public void update(float delta) {
        this.stage.act(delta);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       update(delta);
       stage.draw();
    }

    @Override
    public void resize(int width, int height) {
             stage.getViewport().update(width, height, false);
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
    public void dispose() {

    }
}
