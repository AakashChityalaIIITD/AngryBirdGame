package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
    private SpriteBatch batch;
    private ImageButton newGameButton;
    private ImageButton LoadSavedGameButton;
    private ImageButton ExitButton;
    private ImageButton backpace;  // Acts as a background image now
    Table table;

    public MainScreen(final Main main) {
        this.game = main;

        // Initialize the stage and set it as the input processor
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

        background = new Sprite(new Texture("background.jpeg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        backpace = new ImageButton(new SpriteDrawable(new Sprite(new Texture("backpace.jpg"))));
        backpace.setHeight((float) (background.getHeight() * 0.75));

        // Center the backpace image on the stage
        backpace.setPosition((Gdx.graphics.getWidth() - backpace.getWidth()) / 2,
            (Gdx.graphics.getHeight() - backpace.getHeight()) / 2);

        // Adding UI elements
        table = new Table();
        newGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("newgame.png"))));
        LoadSavedGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("laod.png"))));
        ExitButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("exit.png"))));

        // Positioning buttons in a table
        table.setFillParent(true);  // Ensures the table is sized to the stage
        table.center();  // Centers the table on the stage
        table.add(newGameButton).size(800, 200).padBottom(-150).row();  // Set size for New Game button
        table.add(ExitButton).size(800, 200).padBottom(-150).row();  // Set size for Load Saved Game button
        table.add(LoadSavedGameButton).size(800, 200).row();

        // Adding everything to the stage
        //stage.addActor(backpace);  // Add backpace image first to act as the background
        stage.addActor(table);     // Then add the table containing buttons

        // Button listeners
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                game.setScreen(new LevelScreen(game));
            }
        });

        LoadSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle loading a saved game
                game.setScreen(new SavedGameScreen(game));
            }
        });

        ExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit the application
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        // This is called when the screen is set, no logic needed here for now
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the background
        batch.begin();
        background.draw(batch);
        batch.end();

        // Render the stage (e.g., if you add buttons or UI components to the stage)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport
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
        // Dispose of resources to avoid memory leaks
        stage.dispose();
        batch.dispose();
        background.getTexture().dispose();
    }
}
