package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.*;


public class PauseMenuScreen implements Screen {
    private Main game;
    private Stage stage;
    private Sprite background;
    private SpriteBatch batch;
    private ImageButton ResumeButton;
    private ImageButton BackMainMenuButton;
    private ImageButton SaveButton;
    private Sprite backgroundTexture;
    private BitmapFont font;
    private String message;
    private boolean showMessage = false;
    private float messageDisplayTime = 2f;
    private float elapsedTime = 0;
    Table table;
    public PauseMenuScreen(Main main){
        this.game=main;
        this.font = new BitmapFont();
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        backgroundTexture = new Sprite(new Texture("pauseBackground.png"));
        background = new Sprite(new Texture("GamePage.jpg"));

        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table= new Table();
        table.setFillParent(true);
        table.center();

        SaveButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("SaveGame.png"))));
        BackMainMenuButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("mainmenu.png"))));
        ResumeButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("resume.png"))));

        table.add(ResumeButton).size(400,100).padBottom(25).row();
        table.add(SaveButton).size(400,100).padBottom(25).row();
        table.add(BackMainMenuButton).size(400,100).row();

        stage.addActor(table);

        ResumeButton.addListener(new ClickListener() {
            @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new InGameScreen(game));
           }
        });

        SaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AfterSaveScreen(game));
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
        font = new BitmapFont();
        message = "Game Saved Successfully";

        SaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Show the message when SaveButton is clicked
                showMessage = true;
                elapsedTime = 0;

                // Timer to switch screens after 2 seconds
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new MainScreen(game));
                    }
                }, messageDisplayTime);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backgroundTexture.setSize(350,428);
        backgroundTexture.setPosition(Gdx.graphics.getWidth()/2-backgroundTexture.getWidth()/2, Gdx.graphics.getHeight()/2 - backgroundTexture.getHeight()/2);
        batch.begin();
        background.draw(batch);
        backgroundTexture.draw(batch);
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
