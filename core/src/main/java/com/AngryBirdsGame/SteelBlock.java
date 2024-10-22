package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SteelBlock {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public SteelBlock() {
        this.sprite=new Sprite(new Texture("steelSqaure.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
