package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class catapol{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public catapol(){
        positionX = 0;
        positionY = 0;
        sprite = new Sprite(new Texture("Catapol.png"));
    }
}
