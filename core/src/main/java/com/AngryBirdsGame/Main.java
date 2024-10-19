package com.AngryBirdsGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    private FitViewport viewport;
    private Texture texture;
    private SpriteBatch batch;


    @Override
    public void create() {
        // Use FitViewport to maintain aspect ratio and avoid distortion
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Set your virtual world size
        viewport.apply();

        texture = new Texture("image.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        // Check if the right arrow key is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            setScreen(new MainScreen(this)); // Switch to MainScreen
        }

        // If the screen is not switched, render the current screen (default)
        if (getScreen() != null) {
            super.render();  // Calls the render method of the currently active screen
        } else {
            // Clear the screen with white color
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Set viewport to handle proper scaling
            viewport.apply();
            batch.setProjectionMatrix(viewport.getCamera().combined);

            // Render the texture
            batch.begin();
            batch.draw(texture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport on resize to adjust screen size
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose of resources
        texture.dispose();
        batch.dispose();
    }
}
