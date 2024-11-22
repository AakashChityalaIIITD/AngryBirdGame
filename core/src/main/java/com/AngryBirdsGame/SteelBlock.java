package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SteelBlock extends Blocks {
    private int positionX;
    private int positionY;

    public SteelBlock() {
        super("stelly",0,0,3);
        this.sprite=new Sprite(new Texture("steelSqaure.png"));

    }
    public Sprite getSprite(){
        return sprite;
    }
}
