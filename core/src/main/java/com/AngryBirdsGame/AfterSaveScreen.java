package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

public class AfterSaveScreen implements Screen {
    private final Main game; // Reference to the main game class
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;
    private Sprite background;
    private GlyphLayout layout; // Used to measure text dimensions

    public AfterSaveScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.stage = new Stage();

        // Load background texture
        background = new Sprite(new Texture(Gdx.files.internal("fullGameBackground.jpeg")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Make the background cover the screen

        font = new BitmapFont(); // Load your specific font here if needed
        layout = new GlyphLayout(); // Initialize the GlyphLayout

        // You don't need to add the background to the stage since you're rendering it in the SpriteBatch
    }

    @Override
    public void show() {
        // Schedule the screen to change back after a delay
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new InGameScreen(game)); // Change to your desired screen
            }
        }, 2);  // 2-second delay
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        batch.begin();
        background.draw(batch); // Draw the background
        batch.end();

        // Draw the message
        batch.begin();
        font.setColor(255 / 255f, 84 / 255f, 91 / 255f, 1); // Set font color to pink
        font.getData().setScale(3.7F); // Set font size scale

        String message = "Game Saved Successfully";
        layout.setText(font, message); // Set the text to measure its dimensions
        float textWidth = layout.width;
        float textHeight = layout.height;

        // Draw the message in the center of the screen
        font.draw(batch, message, (Gdx.graphics.getWidth() - textWidth) / 2, (Gdx.graphics.getHeight() + textHeight) / 2);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if necessary
        background.setSize(width, height); // Adjust background size
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
        batch.dispose();
        font.dispose(); // Dispose of the font when done
        background.getTexture().dispose(); // Dispose of the background texture
    }
}
