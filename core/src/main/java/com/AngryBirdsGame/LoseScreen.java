package com.AngryBirdsGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoseScreen extends ApplicationAdapter implements Screen {
    private Main game;
    private Stage stage;
    private Texture LoseTexture;
    private ImageButton retry;
    private ImageButton backButton;

    public LoseScreen(Main main) {
        this.game = main;
        backButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("back.png"))));
        backButton.setSize(Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/13);
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.retry = new ImageButton(new SpriteDrawable(new Sprite(new Texture("retry.png"))));
        Gdx.input.setInputProcessor(stage);
        LoseTexture = new Texture(Gdx.files.internal("lose.jpg"));

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InGameScreen(game));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
    }

    @Override
    public void show() {
        Image LoseImage = new Image(LoseTexture);
        LoseImage.setPosition(0, 0);
        LoseImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(LoseImage);
        stage.addActor(retry);
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        retry.setSize(260, 140);
        backButton.setSize(Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/13);
        retry.setPosition(Gdx.graphics.getWidth()/2 - retry.getWidth()/2, (float) (Gdx.graphics.getHeight()/5.9 - retry.getHeight()/2));
        backButton.setPosition((float) 0, (float) (Gdx.graphics.getHeight() * 0.9));
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        LoseTexture.dispose();
    }
}
