package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GlassBlock extends Blocks {
    private int positionX;
    private int positionY;

    public GlassBlock() {
        super("Glassy",0,0,2);
        this.sprite=new Sprite(new Texture("glassStraightBrick.png"));
        this.durability=1;
    }
}
