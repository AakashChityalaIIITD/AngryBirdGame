package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

class LevelScreen implements Screen {
    private Main game;
    private Stage stage;
    private Sprite background;
    private SpriteBatch batch;
    private Table table;
    private ImageButton Level1;
    private ImageButton Level2;
    private ImageButton Level3;
    private ImageButton Level4;
    public LevelScreen(Main main){
        this.game=main;
        this.stage = new Stage(new FitViewport(1000, 650));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Sprite(new Texture("background.jpeg"));
        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table= new Table();
        table.setFillParent(true);
        table.center();

        Level1=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level1."))));
        Level2=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level2."))));
        Level3=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level3."))));
        Level4=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level4."))));
        table.add(Level1).size(300, 100).padBottom(25).row();
        table.add(Level2).size(300, 100).padBottom(25).row();
        table.add(Level3).size(300, 100).padBottom(25).row();
        table.add(Level4).size(300, 100).row();

        stage.addActor(table);

        Level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new IngameScreen(game));
            }
        });
        Level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new IngameScreen(game));
            }
        });
        Level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new IngameScreen(game));
            }
        });
        Level4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new IngameScreen(game));
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
        stage.dispose();
        batch.dispose();
        background.getTexture().dispose();

    }
}
