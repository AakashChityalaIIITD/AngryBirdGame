package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    private Music backgroundMusic;
    private Sprite background;
    private SpriteBatch batch;
    private Table table;
    private ImageButton Level1;
    private ImageButton Level2;
    private ImageButton Level3;
    private ImageButton backButton;
    public LevelScreen(Main main){
        this.game=main;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Theme.mp3")); // Replace with your MP3 file path
        backgroundMusic.setLooping(true); // Music will loop continuously
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Sprite(new Texture("Designer.jpeg"));
        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table= new Table();
        table.setFillParent(true);
        table.center();

        backButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("back.png"))));

        backButton.setSize(Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/13);
//        backButton.setPosition(0, Gdx.graphics.getHeight());

        Level1=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level1.png"))));
        Level2=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level2.png"))));
        Level3=new ImageButton(new SpriteDrawable(new Sprite(new Texture("Level3.png"))));
        table.add(Level1).size(400, 100).padBottom(25).row();
        table.add(Level2).size(400, 100).padBottom(25).row();
        table.add(Level3).size(400, 100).row();
        stage.addActor(table);
        stage.addActor(backButton);

        Level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new InGameScreen1(game, true));
            }
        });
        Level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new InGameScreen2(game, true));
            }
        });
        Level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new InGameScreen3(game, true));
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });


    }
    @Override
    public void show() {
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backButton.setSize(Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/13);
        backButton.setPosition((float) 0, (float) (Gdx.graphics.getHeight() * 0.9));
        batch.begin();
        background.draw(batch);
        float x=(float)2;
        backButton.draw(batch,x);
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
        backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.getTexture().dispose();

    }
}
