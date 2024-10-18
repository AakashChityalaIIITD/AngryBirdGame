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
        this.stage = new Stage(new FitViewport(1000, 650));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);

        // Initialize the batch for drawing
        batch = new SpriteBatch();

        // Background setup (proper scaling for full screen without distortion)
        background = new Sprite(new Texture("img2.png"));
        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Background button as an image to fill the screen
//        backpace = new ImageButton(new SpriteDrawable(new Sprite(new Texture("backpace.jpg"))));
//        backpace.setFillParent(true);  // Makes the backpace button fill the screen

        // Adding UI elements
        table = new Table();
        newGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("newgame.jpg"))));
        LoadSavedGameButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Load.jpg"))));
        ExitButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("exit.jpg"))));

        // Positioning buttons in a table
        table.setFillParent(true);  // Ensures the table is sized to the stage
        table.center();  // Centers the table on the stage
        table.add(newGameButton).size(300, 100).padBottom(50).row();  // Set size for New Game button
        table.add(LoadSavedGameButton).size(300, 100).padBottom(50).row();  // Set size for Load Saved Game button
        table.add(ExitButton).size(300, 100).row();

        // Adding everything to the stage
        //stage.addActor(backpace);  // Add background first so that it stays behind everything
        stage.addActor(table);// Table containing buttons

        // Button listeners
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to a new screen, e.g., NewGameScreen
                System.out.println("New Game Clicked!");
            }
        });

        LoadSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle loading a saved game
                System.out.println("Load Saved Game Clicked!");
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
        // Update the viewport to ensure proper scaling
        stage.getViewport().update(width, height, true);
//        background.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());  // Resize the background without distortion
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
