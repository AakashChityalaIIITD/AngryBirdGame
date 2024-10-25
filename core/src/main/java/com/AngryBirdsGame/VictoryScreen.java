package com.AngryBirdsGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class VictoryScreen extends ApplicationAdapter implements Screen {
    private Main game;
    private Stage stage;
    private Texture victoryTexture;
    private ImageButton backButton;
    public VictoryScreen(Main main) {
        this.game = main;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        victoryTexture = new Texture(Gdx.files.internal("Victory.jpeg"));
        this.backButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("back.png"))));


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
    }

    @Override
    public void show() {
        Image victoryImage = new Image(victoryTexture);
        victoryImage.setPosition(0, 0);
        victoryImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(victoryImage);
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        backButton.setSize(Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/13);
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
        victoryTexture.dispose();
    }
}
