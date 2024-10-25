package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class woodenBrick extends Blocks{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public woodenBrick() {
        super("woody",0,0,1);
        this.sprite=new Sprite(new Texture("woodenStraightBrick.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
