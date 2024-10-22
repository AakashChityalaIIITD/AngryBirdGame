package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GlassBlock {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public GlassBlock() {
        this.sprite=new Sprite(new Texture("glassStraightBrick.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
