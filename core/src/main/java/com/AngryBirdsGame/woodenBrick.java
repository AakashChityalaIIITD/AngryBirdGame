package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class woodenBrick extends Blocks{
    private int positionX;
    private int positionY;
    public woodenBrick() {
        super("woody",0,0,1);
        this.sprite=new Sprite(new Texture("woodenStraightBrick.png"));
        this.durability=1;
    }
}
