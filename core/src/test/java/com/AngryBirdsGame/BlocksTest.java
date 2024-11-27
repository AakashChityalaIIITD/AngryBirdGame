package com.AngryBirdsGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlocksTest {
    Blocks blocks;
    @BeforeEach
    void setUp() {
        blocks=new Blocks("blocky",25,25,5);
    }
    @Test
    void blockTes(){
        Assertions.assertEquals("blocky", this.blocks.name, "The name checked.");
        assertEquals(25,blocks.getPosX(), "The posX checked.");
        assertEquals(25, blocks.getPosY(), "The posY checked.");
        assertEquals(15,blocks.durability, "The durability checked.");

    }

}
