package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class PauseMenuScreen implements Screen {
    private Main game;
    private Stage stage;
    private Sprite background;
    private SpriteBatch batch;
    private ImageButton ResumeButton;
    private ImageButton BackMainMenuButton;
    private ImageButton SaveButton;
    Table table;
    public PauseMenuScreen(Main main){
        this.game=main;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Sprite(new Texture("Designer.jpeg"));
        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table= new Table();
        table.setFillParent(true);
        table.center();

        SaveButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("SaveGame.png"))));
        BackMainMenuButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("SaveGame.png"))));
        ResumeButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("SaveGame.png"))));

        table.add(ResumeButton).size(400,100).padBottom(25).row();
        table.add(SaveButton).size(400,100).padBottom(25).row();
        table.add(BackMainMenuButton).size(400,100).row();

        stage.addActor(table);

        ResumeButton.addListener(new ClickListener() {
            @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new IngameScreen(game));
           }
        });

        SaveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IngameScreen(game));
            }
        });

        BackMainMenuButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
             game.setScreen(new MainScreen(game));
           }
        });


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
        background.setPosition(0, 0);

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
