package com.AngryBirdsGame;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Trajectory {
    AngryBird angryBird;
    @BeforeEach
    void setUp() {
        angryBird=new AngryBird("ANGRY",15,15,null);
    }
    @Test
    void trajectory(){
        Vector2 v=angryBird.calculateLaunchVelocity(50,30,90,100);
        Assertions.assertNotNull(v, "The sprite checked.");
    }

}
