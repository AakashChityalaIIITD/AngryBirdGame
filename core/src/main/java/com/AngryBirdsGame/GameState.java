package com.AngryBirdsGame;

import com.badlogic.gdx.utils.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Class to represent the game's state for serialization
public class GameState implements Serializable {
    public int score; // Player's score
    public int level; // Current level
    public int remainingBirds; // Number of birds remaining
    public List<PigState> pigs = new ArrayList<PigState>(); // State of pigs
    public List<BlockState> blocks = new ArrayList<BlockState>(); // State of blocks (wood, glass)
    public List<BirdState> birds = new ArrayList<BirdState>(); // Store states for all active birds

    // Inner class to represent the state of a pig
    public static class PigState implements Serializable {
        public int x; // X position
        public int y; // Y position
        public int durability; // Durability of the pig

        public PigState(int x, int y, int durability) {
            this.x = x;
            this.y = y;
            this.durability = durability;
        }

        public PigState() {
        }
    }

    // Inner class to represent the state of a block
    public static class BlockState implements Serializable {
        public int x; // X position
        public int y; // Y position
        public String type; // Type of block (e.g., "Wood", "Glass")
        public int durability; // Durability of the block

        public BlockState(int x, int y, String type, int durability) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.durability = durability;
        }

        public BlockState() {
        }
    }

    // Inner class to represent the state of a bird
    public static class BirdState implements Serializable {
        public String type; // Type of bird (e.g., "RedBird")
        public int x; // X position
        public int y; // Y position

        public BirdState(String type, int x, int y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        public BirdState() {
        }
    }

    // Method to serialize the GameState object to JSON
    public String serialize() {
        Json json = new Json(); // Create a new Json object
        return json.toJson(this); // Convert the current GameState to JSON string
    }

    // Method to deserialize a JSON string back into a GameState object
    public static GameState deserialize(String jsonString) {
        Json json = new Json(); // Create a new Json object
        return json.fromJson(GameState.class, jsonString); // Convert JSON string back to GameState
    }
}
