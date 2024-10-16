package com.AngryBirdsGame;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }

    @Override
    public void render() {
        System.out.println("hello");
    }
}
