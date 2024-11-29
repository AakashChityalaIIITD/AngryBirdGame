package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SmallBlueBird extends AngryBird {
    public SmallBlueBird(int x, int y, World world) {
        super("SmallBlue", x, y, new Sprite(new Texture("blue.png")));
        this.sprite = new Sprite(new Texture("blue.png"));
        this.speed=1.5f;
        this.impact=3;
        sprite.setSize(35, 43);
        System.out.println("SmallBlueBird constructor: World is null? " + (world == null)); //Null check
    }

}
