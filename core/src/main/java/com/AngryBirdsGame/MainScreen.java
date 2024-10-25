package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class MainScreen implements Screen {
    private final Main game;
    private Stage stage;

    private Sprite background;
    private Music backgroundMusic;

    private boolean musicShouldContinue;
    private SpriteBatch batch;
    private ImageButton newGameButton;
    private ImageButton loadSavedGameButton;
    private ImageButton exitButton;
    private Table table;

    private Texture newGameTexture;
    private Texture loadTexture;
    private Texture exitTexture;

    public MainScreen(final Main main) {
        this.game = main;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Theme.mp3"));
        backgroundMusic.setLooping(true);
        musicShouldContinue = true;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

        background = new Sprite(new Texture("Designer.jpeg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load textures for buttons
        newGameTexture = new Texture("newgame.png");
        loadTexture = new Texture("load.png");
        exitTexture = new Texture("exit.png");

        // Create ImageButtons
        newGameButton = new ImageButton(new SpriteDrawable(new Sprite(newGameTexture)));
        loadSavedGameButton = new ImageButton(new SpriteDrawable(new Sprite(loadTexture)));
        exitButton = new ImageButton(new SpriteDrawable(new Sprite(exitTexture)));

        // Adding UI elements to the table
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(newGameButton).size(200, 100).padBottom(50).row();
        table.add(loadSavedGameButton).size(200, 100).padBottom(50).row();
        table.add(exitButton).size(220, 100).row();

        stage.addActor(table);

        // Add click listeners
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new LevelScreen(game));
            }
        });

        loadSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SavedGameScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }



    @Override
    public void show() {
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.end();

        // Render the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        // Dispose of resources
        stage.dispose();
        batch.dispose();
        background.getTexture().dispose();
        newGameTexture.dispose();
        loadTexture.dispose();
        exitTexture.dispose();
    }
}
