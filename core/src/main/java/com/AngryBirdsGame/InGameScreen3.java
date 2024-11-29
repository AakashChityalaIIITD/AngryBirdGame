package com.AngryBirdsGame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

class BirdFactory3 {
    public static AngryBird createBird(int type) {
        switch (type) {
            case 2: return new RedAngryBird();
            case 3: return new BlackAngryBird();
            case 1: return new BlueAngryBird();
            default: return new BlueAngryBird();
        }
    }
}
public class InGameScreen3 extends ApplicationAdapter implements Screen {
    private Main game;
    boolean chkNewGame;
    private final List<Body> bodiesToDestroy = new ArrayList<Body>();
    int black_bird_flight = 0;
    boolean isDragging = false; // Track if bird is being dragged
    private Stage stage;
    private World world;
    private int cntr = 2;
    private List<Pigs> pigs;

    boolean chk;

    private List<woodenBrick> woodenBlocks;
    private List<GlassBlock> glassBlocks;
    private List<SteelBlock> steelBlocks;
    private List<AngryBird> birds;
    private List<SquareGlasses> glasses;
    private SpriteBatch batch;
    private Sprite background;
    private Body groundBody; // Box2D body for ground
    private AngryBird bird_black;
    private ImageButton pause;
    int pig_cntr=3;
    int bird_cnt=3;
    GameHandler gameHandler;
    private final float PPM = 100f;
    private ShapeRenderer shapeRenderer; // For trajectory
    private Sprite groundSprite;// Pixels per meter scaling
    private Vector2 dragStartPosition = new Vector2();// Store the starting point of the drag
    private catapol cata;
    ArrayList<  SteelBlock> arr=new ArrayList<SteelBlock>();
    private SteelBlock squareGlasses1, squareGlasses2, squareGlasses3, squareGlasses4, squareGlasses5, squareGlasses6,squareGlasses7,squareGlasses8,squareGlasses9,squareGlasses10,squareGlasses11,squareGlasses12;
    private smallpig pig_smallpig;
    private kingpig pig_king;
    private fattyPig pig_fatty;

    public InGameScreen3(Main main, boolean chkNewGame) {
        this.game = main;
        this.chkNewGame = chkNewGame;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        glasses = new ArrayList<SquareGlasses>();
        pigs = new ArrayList<Pigs>();
        steelBlocks = new ArrayList<SteelBlock>();
        woodenBlocks = new ArrayList<woodenBrick>();
        glassBlocks = new ArrayList<GlassBlock>();
        birds = new ArrayList<AngryBird>();

        gameHandler = new GameHandler("gameSave3.dat");
        chk = gameHandler.loadGameState(pigs, woodenBlocks, glassBlocks, steelBlocks, birds);

        this.world = new World(new Vector2(0f, -9.8f), true);
        initializeContactListener();
        background = new Sprite(new Texture("GamePage.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        createGround();
        initializeCatapult(); // Create ground
        initializeBird();
        initializeObstacles(); // Initialize blocks and pigs
        // Initialize the Black Angry Bird
        initializeCatapult(); // Initialize the catapul// t
        groundSprite = new Sprite(new Texture("Stage.png"));
        groundSprite.setPosition(0, 0); // Position the ground at the bottom
        shapeRenderer = new ShapeRenderer();


        // Initialize the pause button
        pause = new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause.png"))));
        pause.setSize(50, 60);
        pause.setPosition(Gdx.graphics.getWidth() - pause.getWidth(), Gdx.graphics.getHeight() - pause.getHeight());
        stage.addActor(pause);
        final InGameScreen3 igScreen_temp=this;
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenuScreen(game,3, pigs,woodenBlocks,glassBlocks,glasses,steelBlocks, bird_cnt,null, igScreen_temp,null));
            }
        });

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (bird_black != null && bird_black.sprite != null && bird_black.sprite.getBoundingRectangle().contains(x, y)) {
                    isDragging = true;
                    bird_black.body.setAwake(false);
                    dragStartPosition.set(x, y);
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDragging && bird_black != null) {
                    bird_black.sprite.setPosition(x - bird_black.sprite.getWidth() / 2, y - bird_black.sprite.getHeight() / 2);
                    bird_black.body.setTransform(
                        bird_black.sprite.getX() / PPM + bird_black.sprite.getWidth() / (2 * PPM),
                        bird_black.sprite.getY() / PPM + bird_black.sprite.getHeight() / (2 * PPM),
                        bird_black.body.getAngle()
                    );
                    drawTrajectory(dragStartPosition.x, dragStartPosition.y, x, y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging && bird_black != null) {
                    isDragging = false;
                    Vector2 launchVelocity = calculateLaunchVelocity(x, y);
                    bird_black.body.setLinearVelocity(launchVelocity);
                    bird_black.body.setAwake(true);
                    black_bird_flight = 1;
                }
            }
        });
        System.out.println(steelBlocks.size());
        for(int i=0;i<12;i++){
            if(steelBlocks.get(i)==null){
                System.out.println("null");
            }
            if(steelBlocks.get(i)!=null){
                update_block(steelBlocks.get(i));
            }
        }
    }

    private void initializeContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                Object userDataA = bodyA.getUserData();
                Object userDataB = bodyB.getUserData();

                if (userDataA == null) {
                    System.out.println("Body A user data is null");
                } else {
                    System.out.println("Body A user data: " + userDataA);
                }

                if (userDataB == null) {
                    System.out.println("Body B user data is null");
                } else {
                    System.out.println("Body B user data: " + userDataB);
                }

                // Check if the contact is between a bird and a brick
                if (bodyA.getUserData() instanceof AngryBird && bodyB.getUserData() instanceof Blocks ||
                    (bodyA.getUserData() instanceof Blocks && bodyB.getUserData() instanceof AngryBird)) {
                    System.out.println("Bird hit a brick!");
                    // Handle bird and brick collision
                    handleBirdBrickCollision(bodyA, bodyB);
                }

                // Check if the contact is between a bird and a pig
                if ((bodyA.getUserData() instanceof AngryBird && bodyB.getUserData() instanceof Pigs) ||
                    (bodyA.getUserData() instanceof Pigs && bodyB.getUserData() instanceof AngryBird)) {
                    System.out.println("Bird hit a pig!");
                    // Handle bird and pig collision
                    handleBirdPigCollision(bodyA, bodyB);
                }

                // Check if the contact is between a bird and a pig
                if ((bodyA.getUserData() instanceof Blocks && bodyB.getUserData() instanceof Blocks)
                   ) {
                    System.out.println("Brick hit a brick!");
                    // Handle bird and pig collision
                    handleBrickBrickCollision(bodyA, bodyB);
                }

//                // Check if the contact is between a bird and a pig
//                if ((bodyA.getUserData() instanceof Blocks && bodyB.getUserData() instanceof Pigs) ||
//                    (bodyA.getUserData() instanceof Pigs && bodyB.getUserData() instanceof Blocks)) {
//                    System.out.println("Bricks hit a pig!");
//                    // Handle bird and pig collision
//                    handleBrickPigCollision(bodyA, bodyB);
//                }


            }

            @Override
            public void endContact(Contact contact) {
                // Optional: Add code here for when contacts end
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // Optional: Add code here for pre-solve handling
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // Optional: Add code here for post-solve handling
            }
        });
    }

    // Handle collision between bird and brick
    private void handleBirdBrickCollision(Body fixtureA, Body fixtureB) {
        AngryBird bird = null;
        Blocks brick = null;

        // Identify the bird
        if (fixtureA.getUserData() instanceof AngryBird) {
            bird = (AngryBird) fixtureA.getUserData();
        } else if (fixtureB.getUserData() instanceof AngryBird) {
            bird = (AngryBird) fixtureB.getUserData();
        }

        // Identify the brick
        if (fixtureA.getUserData() instanceof Blocks) {
            brick = (Blocks) fixtureA.getUserData();
        } else if (fixtureB.getUserData() instanceof Blocks) {
            brick = (Blocks) fixtureB.getUserData();
        }

        // Ensure both bird and brick are found before proceeding
        if (bird != null && brick != null) {
            // Handle the collision between bird and brick
            System.out.println("Bird hit a brick!");
            brick.takeDamage(bird.getImpactPower());
            if (brick.isDestroyed()) {
                bodiesToDestroy.add(brick.body);
                brick.sprite = null;
            }

            // Add any additional logic to handle this collision here
        } else {
            System.out.println("Collision not identified properly: Bird: " + (bird != null) + " Brick: " + (brick != null));
        }

        brick.takeDamage(bird.getImpactPower());
        if(brick.isDestroyed()){
            bodiesToDestroy.add(brick.body);
            brick.sprite=null;
        }

    }
    private void handleBrickBrickCollision(Body fixtureA, Body fixtureB) {
        Blocks brick1 = null;
        Blocks brick2 = null;

        // Identify the bird

        brick1 = (Blocks) fixtureA.getUserData();

        brick2 = (Blocks) fixtureB.getUserData();


        // Ensure both bird and brick are found before proceeding
        if (brick2 != null && brick1 != null) {
            // Handle the collision between bird and brick
            System.out.println("Bird hit a brick!");
            // Add any additional logic to handle this collision here
        } else {
            System.out.println("Collision not identified properly: Bird: " + (brick2 != null) + " Brick: " + (brick1 != null));
        }

         brick2.takeDamage(1);
        brick1.takeDamage(1);
        if(brick1.isDestroyed()){
            bodiesToDestroy.add(brick1.body);
            brick1.sprite=null;
        }
        if(brick2.isDestroyed()){
            bodiesToDestroy.add(brick2.body);
            brick2.sprite=null;
        }

    }
    private void handleBrickPigCollision(Body fixtureA, Body fixtureB) {
        Pigs pigs = null;
        Blocks brick = null;

        // Identify the bird
        if (fixtureA.getUserData() instanceof Pigs) {
            pigs = (Pigs) fixtureA.getUserData();
        } else if (fixtureB.getUserData() instanceof Pigs) {
            pigs = (Pigs) fixtureB.getUserData();
        }

        // Identify the brick
        if (fixtureA.getUserData() instanceof Blocks) {
            brick = (Blocks) fixtureA.getUserData();
        } else if (fixtureB.getUserData() instanceof Blocks) {
            brick = (Blocks) fixtureB.getUserData();
        }

        // Ensure both bird and brick are found before proceeding
        if (pigs != null && brick != null) {
            // Handle the collision between bird and brick
            System.out.println("Bird hit a brick!");
            // Add any additional logic to handle this collision here
        } else {
            System.out.println("Collision not identified properly: Bird: " + (pigs != null) + " Brick: " + (brick != null));
        }


         pigs.takeDamage(1);
        if(pigs.isDead()){
            bodiesToDestroy.add(pigs.body);
            pigs.sprite=null;
            pig_cntr--;
        }
    }

    private void processPendingDestructions() {
        for (Body body : bodiesToDestroy) {
            if (body != null) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();
    }

    // Handle collision between bird and pig
    private void handleBirdPigCollision(Body fixtureA, Body fixtureB) {
        AngryBird bird = (AngryBird) (fixtureA.getUserData() instanceof AngryBird ?
            fixtureA.getUserData() : fixtureB.getUserData());
        Pigs pig = (Pigs) (fixtureA.getUserData() instanceof Pigs ?
            fixtureA.getUserData() : fixtureB.getUserData());

        pig.takeDamage(bird.getImpactPower());
        if(pig.isDead()){
            bodiesToDestroy.add(pig.body);
            pig.sprite=null;
            pig_cntr--;
        }


    }


    private void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, 0); // Ground level at the bottom

        groundBody = world.createBody(groundBodyDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(Gdx.graphics.getWidth() / PPM, 120 / PPM); // Define size based on screen width

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }

    private void initializeObstacles() {

        if (chk && (!chkNewGame)) {
            bird_cnt = gameHandler.loadNoOfBirds();
            System.out.println("bird-cnt" + bird_cnt);
            bird_cnt++;
            swamp();
            List<GameState.PigState> p = gameHandler.loadPigs();
            System.out.println(p.size());
            List<GameState.BlockState> b = gameHandler.loadBlocks();
            int it=0;
            for (GameState.PigState pig : p) {
                if(it==0) {
                    if (pig.x == -1 && pig.y == -1) {
                        pig_smallpig = new smallpig();
                        pig_smallpig.sprite = null;
                        bodiesToDestroy.add(pig_smallpig.body);
                        pigs.add(pig_smallpig);
                    } else {
                        pig_smallpig = new smallpig();
                        pig_smallpig.setHealth(pig.durability);
                        pig_smallpig.sprite.setPosition(pig.x, pig.y);
                        pig_smallpig.sprite.setSize(65, 68);
                        pig_smallpig.body = createBody2(pig_smallpig, BodyDef.BodyType.DynamicBody);
                        pigs.add(pig_smallpig);
                    }
                }
                if (it==1){
                    if (pig.x == -1 && pig.y == -1) {
                        pig_fatty = new fattyPig();
                        pig_fatty.sprite = null;
                        bodiesToDestroy.add(pig_fatty.body);
                        pigs.add(pig_fatty);
                    } else {
                        pig_fatty = new fattyPig();
                        pig_fatty.setHealth(pig.durability);
                        pig_fatty.sprite.setPosition(pig.x, pig.y);
                        pig_fatty.sprite.setSize(65, 68);
                        pig_fatty.body = createBody2(pig_fatty, BodyDef.BodyType.DynamicBody);
                        pigs.add(pig_fatty);
                    }
                }
                if(it==2){
                    if (pig.x == -1 && pig.y == -1) {
                        pig_king = new kingpig();
                        pig_king.sprite = null;
                        bodiesToDestroy.add(pig_king.body);
                        pigs.add(pig_king);
                    } else {
                        pig_king = new kingpig();
                        pig_king.setHealth(pig.durability);
                        pig_king.sprite.setPosition(pig.x, pig.y);
                        pig_king.sprite.setSize(65, 68);
                        pig_king.body = createBody2(pig_king, BodyDef.BodyType.DynamicBody);
                        pigs.add(pig_king);
                    }
                }
                it++;
            }
            int iter=0;
            System.out.println(b.size());
            for(GameState.BlockState blck:b){
                if(iter==0){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses1 =new SteelBlock();
                        squareGlasses1.sprite=null;
                        bodiesToDestroy.add(squareGlasses1.body);
                        steelBlocks.add(squareGlasses1);
                        System.out.println("brick: " + squareGlasses1.getDurability());

                    }
                    else{
                        squareGlasses1 = new SteelBlock();
                        squareGlasses1.setDurability(blck.durability);
                        System.out.println("brick: " + squareGlasses1.getDurability());
                        squareGlasses1.sprite.setPosition(blck.x, blck.y);
                        squareGlasses1.sprite.setSize(50, 50);
                        squareGlasses1.body = createBody3(squareGlasses1, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses1);
                    }

                }
                if(iter==1){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses2=new SteelBlock();
                        squareGlasses2.sprite=null;
                        bodiesToDestroy.add(squareGlasses2.body);
                        steelBlocks.add(squareGlasses2);
                    }
                    else{
                        squareGlasses2 = new SteelBlock();
                        squareGlasses2.setDurability(blck.durability);
                        squareGlasses2.sprite.setPosition(blck.x, blck.y);
                        squareGlasses2.sprite.setSize(50, 50);
                        squareGlasses2.body = createBody3(squareGlasses2, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses2);
                    }

                }
                if(iter==2){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses3=new SteelBlock();
                        squareGlasses3.sprite=null;
                        bodiesToDestroy.add(squareGlasses3.body);
                        steelBlocks.add(squareGlasses3);
                    }
                    else{
                        squareGlasses3 = new SteelBlock();
                        squareGlasses3.setDurability(blck.durability);
                        squareGlasses3.sprite.setPosition(blck.x, blck.y);
                        squareGlasses3.sprite.setSize(50, 50);
                        squareGlasses3.body = createBody3(squareGlasses3, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses3);
                    }

                }
                if(iter==3){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses4=new SteelBlock();
                        squareGlasses4.sprite=null;
                        bodiesToDestroy.add(squareGlasses4.body);
                        steelBlocks.add(squareGlasses4);
                    }
                    else{
                        squareGlasses4 = new SteelBlock();
                        squareGlasses4.setDurability(blck.durability);
                        squareGlasses4.sprite.setPosition(blck.x, blck.y);
                        squareGlasses4.sprite.setSize(50, 50);
                        squareGlasses4.body = createBody3(squareGlasses4, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses4);
                    }

                }
                if(iter==4){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses5=new SteelBlock();
                        squareGlasses5.sprite=null;
                        bodiesToDestroy.add(squareGlasses5.body);
                        steelBlocks.add(squareGlasses5);
                    }
                    else{
                        squareGlasses5 = new SteelBlock();
                        squareGlasses5.setDurability(blck.durability);
                        squareGlasses5.sprite.setPosition(blck.x, blck.y);
                        squareGlasses5.sprite.setSize(50, 50);
                        squareGlasses5.body = createBody3(squareGlasses5, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses5);
                    }

                }
                if(iter==5){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses6=new SteelBlock();
                        squareGlasses6.sprite=null;
                        bodiesToDestroy.add(squareGlasses6.body);
                        steelBlocks.add(squareGlasses6);
                    }
                    else{
                        squareGlasses6 = new SteelBlock();
                        squareGlasses6.setDurability(blck.durability);
                        squareGlasses6.sprite.setPosition(blck.x, blck.y);
                        squareGlasses6.sprite.setSize(50, 50);
                        squareGlasses6.body = createBody3(squareGlasses6, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses6);
                    }

                }
                if(iter==6){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses7=new SteelBlock();
                        squareGlasses7.sprite=null;
                        bodiesToDestroy.add(squareGlasses7.body);
                        steelBlocks.add(squareGlasses7);
                    }
                    else{
                        squareGlasses7 = new SteelBlock();
                        squareGlasses7.setDurability(blck.durability);
                        squareGlasses7.sprite.setPosition(blck.x, blck.y);
                        squareGlasses7.sprite.setSize(50, 50);
                        squareGlasses7.body = createBody3(squareGlasses7, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses7);
                    }

                }
                if(iter==7){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses8=new SteelBlock();
                        squareGlasses8.sprite=null;
                        bodiesToDestroy.add(squareGlasses8.body);
                        steelBlocks.add(squareGlasses8);
                    }
                    else{
                        squareGlasses8 = new SteelBlock();
                        squareGlasses8.setDurability(blck.durability);
                        squareGlasses8.sprite.setPosition(blck.x, blck.y);
                        squareGlasses8.sprite.setSize(50, 50);
                        squareGlasses8.body = createBody3(squareGlasses8, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses8);
                    }

                }
                if(iter==8){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses9=new SteelBlock();
                        squareGlasses9.sprite=null;
                        bodiesToDestroy.add(squareGlasses9.body);
                        steelBlocks.add(squareGlasses9);
                    }
                    else{
                        squareGlasses9 = new SteelBlock();
                        squareGlasses9.setDurability(blck.durability);
                        squareGlasses9.sprite.setPosition(blck.x, blck.y);
                        squareGlasses9.sprite.setSize(50, 50);
                        squareGlasses9.body = createBody3(squareGlasses9, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses9);
                    }

                }
                if(iter==9){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses10=new SteelBlock();
                        squareGlasses10.sprite=null;
                        bodiesToDestroy.add(squareGlasses10.body);
                        steelBlocks.add(squareGlasses10);
                    }
                    else{
                        squareGlasses10 = new SteelBlock();
                        squareGlasses10.setDurability(blck.durability);
                        squareGlasses10.sprite.setPosition(blck.x, blck.y);
                        squareGlasses10.sprite.setSize(50, 50);
                        squareGlasses10.body = createBody3(squareGlasses10, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses10);
                    }

                }
                if(iter==10){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses11=new SteelBlock();
                        squareGlasses11.sprite=null;
                        bodiesToDestroy.add(squareGlasses11.body);
                        steelBlocks.add(squareGlasses11);
                    }
                    else{
                        squareGlasses11 = new SteelBlock();
                        squareGlasses11.setDurability(blck.durability);
                        squareGlasses11.sprite.setPosition(blck.x, blck.y);
                        squareGlasses11.sprite.setSize(50, 50);
                        squareGlasses11.body = createBody3(squareGlasses11, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses11);
                    }

                }
                if(iter==11){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses12=new SteelBlock();
                        squareGlasses12.sprite=null;
                        bodiesToDestroy.add(squareGlasses12.body);
                        steelBlocks.add(squareGlasses12);
                    }
                    else{
                        squareGlasses12 = new SteelBlock();
                        squareGlasses12.setDurability(blck.durability);
                        squareGlasses12.sprite.setPosition(blck.x, blck.y);
                        squareGlasses12.sprite.setSize(50, 50);
                        squareGlasses12.body = createBody3(squareGlasses12, BodyDef.BodyType.DynamicBody);

                        steelBlocks.add(squareGlasses12);
                    }

                }
                iter++;
            }
        }
        else{
       pig_smallpig = new smallpig();
       pig_smallpig.sprite.setPosition(790, 280); // Adjusted position
        pig_smallpig.sprite.setSize(45, 50);
     pig_smallpig.body = createBody2(pig_smallpig, BodyDef.BodyType.DynamicBody);
        pig_smallpig.body.setAwake(false);
        pigs.add(pig_smallpig);


        pig_fatty = new fattyPig();
        pig_fatty.sprite.setPosition(950, 280); // Above king pig
        pig_fatty.sprite.setSize(45, 50);
        pig_fatty.body = createBody2(pig_fatty, BodyDef.BodyType.DynamicBody);
        pig_fatty.body.setAwake(false);
        pigs.add(pig_fatty);

            pig_king = new kingpig();
            pig_king.sprite.setPosition(860, 100); // Above small pig
            pig_king.sprite.setSize(45, 50);
            pig_king.body = createBody2(pig_king, BodyDef.BodyType.DynamicBody);
            pig_king.body.setAwake(false);
            pigs.add(pig_king);

        squareGlasses1=new SteelBlock();
        squareGlasses2=new SteelBlock();
//        squareGlasses3=new SteelBlock();
        squareGlasses4=new SteelBlock();
        squareGlasses5=new SteelBlock();
//        squareGlasses6=new SteelBlock();
        squareGlasses7=new SteelBlock();
        squareGlasses8=new SteelBlock();
//        squareGlasses9=new SteelBlock();
        squareGlasses10=new SteelBlock();
        squareGlasses11=new SteelBlock();
//        squareGlasses12=new SteelBlock();
        steelBlocks.add(squareGlasses1);
        steelBlocks.add(squareGlasses2);
        steelBlocks.add(squareGlasses3);
        steelBlocks.add(squareGlasses4);
            steelBlocks.add(squareGlasses5);
            steelBlocks.add(squareGlasses6);
            steelBlocks.add(squareGlasses7);
            steelBlocks.add(squareGlasses8);
            steelBlocks.add(squareGlasses9);
            steelBlocks.add(squareGlasses10);
            steelBlocks.add(squareGlasses11);
            steelBlocks.add(squareGlasses12);
            System.out.println("brick: " + squareGlasses1.getDurability());


            for(int i=0;i<2;i++){
            SteelBlock x =steelBlocks.get(i);

            x.sprite.setPosition(850-50-30-10,100+50*i);
            x.sprite.setSize(50,50);
            x.body=createBody3(x, BodyDef.BodyType.DynamicBody);
            x.body.setAwake(false);

        }

        for(int i=3;i<5;i++){
            SteelBlock x=steelBlocks.get(i);
            x.sprite.setPosition(860-30,100+50*(i-3));
            x.sprite.setSize(50,50);
            x.body=createBody3(x, BodyDef.BodyType.DynamicBody);
            x.body.setAwake(false);
        }
        for(int i=6;i<8;i++){
            SteelBlock x=steelBlocks.get(i);
            x.sprite.setPosition(940,100+50*(i-6));
            x.sprite.setSize(50,50);
            x.body=createBody3(x, BodyDef.BodyType.DynamicBody);
            x.body.setAwake(false);
         }
          for(int i=9;i<11;i++){
            SteelBlock x=steelBlocks.get(i);
            x.sprite.setPosition(1000,100+50*(i-9));
            x.sprite.setSize(50,50);
            x.body=createBody3(x, BodyDef.BodyType.DynamicBody);
           x.body.setAwake(false);
       }

        }

    }
    private void initializeBird() {
        bird_black = new BlackAngryBird();
        bird_black.sprite.setSize(42, 51);
        bird_black.sprite.setPosition(250,180);
        System.out.println("Initialized Bird Sprite Position before: " + bird_black.sprite.getX() + ", " + bird_black.sprite.getY());
        bird_black.body = createBody(bird_black, BodyDef.BodyType.DynamicBody); // Create body first
        bird_black.body.setAwake(false); // Keep it from falling immediately
        System.out.println("Initialized Bird Sprite Position: " + bird_black.sprite.getX() + ", " + bird_black.sprite.getY());
        System.out.println("Initialized Bird Body Position (World): " + bird_black.body.getPosition().x + ", " + bird_black.body.getPosition().y);
    }

    private void initializeCatapult() {
        cata = new catapol();
        cata.sprite.setPosition(250, 108); // Set catapult position
    }



    private void drawTrajectory(float birdX, float birdY, float endX, float endY) {
        Vector2 launchVelocity = calculateLaunchVelocity(endX, endY);


        float g = world.getGravity().y;
        float step = 0.05f;
        int maxSteps = 12;
        float worldBirdX = birdX / PPM;
        float worldBirdY = birdY / PPM;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (int i = 0; i < maxSteps; i++) {
            float t = i * step;

            float x = worldBirdX + launchVelocity.x * t;
            float y = worldBirdY + launchVelocity.y * t + 0.5f * g * t * t;

            float screenX = Math.min(Math.max(x * PPM, 0), 1140); // Clamp between 0 and 1140
            float screenY = Math.min(Math.max(y * PPM, 0), 800);  // Clamp between 0 and 800

            if (screenX >= 0 && screenX <= 1140 && screenY >= 0 && screenY <= 800) {
                shapeRenderer.circle(screenX, screenY, 3); // Draw trajectory point
            } else {
                break;
            }
        }
        shapeRenderer.end();
    }

    private Vector2 calculateLaunchVelocity(float x, float y) {
        float catapultX = cata.sprite.getX() + cata.sprite.getWidth() / 2;
        float catapultY = cata.sprite.getY() + cata.sprite.getHeight() / 2;

        float deltaX = (catapultX - x) / PPM;
        float deltaY = (catapultY - y) / PPM;
        float power = 30f;

        Vector2 launchVelocity = new Vector2(deltaX * power, deltaY * power);// Adjust power to control launch strength
        System.out.println("Launch Velocity Calculation: " + launchVelocity.x + ", " + launchVelocity.y);
        return new Vector2((deltaX - 0.5f) * power, (deltaY + 0.5f) * power);
    }

    @Override
    public void show() {
        // Optional: Implement any initialization logic for when the screen is shown
    }

    @Override
    public void create() {

    }

    private void updateBirdPosition() {
        if (bird_black != null && bird_black.body != null) {
            Vector2 bodyPosition = bird_black.body.getPosition();
            // Set the sprite's position based on the body's position
            bird_black.sprite.setPosition(
                (bodyPosition.x * PPM - bird_black.sprite.getWidth() / 2),
                (bodyPosition.y * PPM - bird_black.sprite.getHeight() / 2)
            );
            if (bird_black instanceof BlueAngryBird && bird_black.body.getPosition().x >= Gdx.graphics.getWidth()/PPM/2 && !bird_black.isSplit()) {
                //Simulate split here.  This is a simplification because BlueAngryBird doesn't have a split() method.
                System.out.println("beginning");
                bird_black.setSplit(true); //Prevent repeated splits
                simulateSplit(bird_black);

            }

            // Check the horizontal velocity to determine when to switch birds
            if (bird_black != null && bird_black.body != null) {
                float horizontalVelocity = bird_black.body.getLinearVelocity().x;
                if ((horizontalVelocity == 0 || bird_black.sprite.getX() <= 0 || bird_black.body.getPosition().x > 12) && black_bird_flight == 1) {
                    black_bird_flight = 0; // Reset flight status
                    swamp(); // Switch to the next bird
                }
            }
        }
    }


    void update_pos(Pigs pig){
        if(pig!=null && pig.sprite!=null){
            Vector2 bodyPosition = pig.body.getPosition();
            pig.sprite.setPosition(
                (bodyPosition.x * PPM - pig.sprite.getWidth() / 2),
                bodyPosition.y * PPM - pig.sprite.getHeight() / 2
            );
            if(pig.sprite.getX()<=0 || pig.sprite.getY()<=0){
                pig_cntr--;
                pig.sprite=null;
                bodiesToDestroy.add(pig.body);
            }
        }
    }
    void update_new_bird_pos(AngryBird bird) {
        if(bird!=null && bird.sprite!=null){
            Vector2 bodyPosition = bird.body.getPosition();
            bird.sprite.setPosition(
                (bodyPosition.x * PPM - bird.sprite.getWidth() / 2),
                bodyPosition.y * PPM - bird.sprite.getHeight() / 2
            );
            if(bird.sprite.getX()<=0 || bird.sprite.getY()<=0){
                pig_cntr--;
                bird.sprite=null;
                bodiesToDestroy.add(bird.body);
            }
        }
    }
    void update_block(Blocks blc){
        if(blc!=null && blc.sprite!=null){
            Vector2 bodyPosition = blc.body.getPosition();
            blc.sprite.setPosition(
                (bodyPosition.x * PPM - blc.sprite.getWidth() / 2) ,
                (bodyPosition.y * PPM - blc.sprite.getHeight() / 2)
            );
        }
    }

    @Override
    public void render(float delta) {
        processPendingDestructions();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 100, 100);//// Step the physics simulation



        int pig_check=0;
        for(int i=0;i<pigs.size();i++){
            if(pigs.get(i).sprite==null){
                pig_check++;
            }
        }

        if(pig_check==3){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    FileOutputStream fileOut = null;
                    ObjectOutputStream out = null;
                    try {
                        fileOut = new FileOutputStream("gameSave3.dat");
                        System.out.println("Game state has been serialized and saved.");
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions appropriately
                    }
                    game.setScreen(new VictoryScreen(game)); // Change to your desired screen
                }
            },0.5f);  // 2-second delay

        }
        else if(bird_cnt==0){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    FileOutputStream fileOut = null;
                    ObjectOutputStream out = null;
                    try {
                        fileOut = new FileOutputStream("gameSave3.dat");
                        System.out.println("Game state has been serialized and saved.");
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions appropriately
                    }
                    game.setScreen(new LoseScreen(game,3)); // Change to your desired screen
                }
            }, 0.8f);  // 2-second delay
        }
        batch.begin();
        background.draw(batch);
        groundSprite.draw(batch);
        if (bird_black != null) {
            Vector2 bodyPosition = bird_black.body.getPosition();
            bird_black.sprite.setPosition(
                bodyPosition.x * PPM - bird_black.sprite.getWidth() / 2,
                bodyPosition.y * PPM - bird_black.sprite.getHeight() / 2
            );
            // Ensure sprite is drawn correctly
            bird_black.sprite.draw(batch);
        }
        update_pos(pig_smallpig);
        update_pos(pig_king);
        update_pos(pig_fatty);
        for(int i=0;i<12;i++){
            SteelBlock x=steelBlocks.get(i);
            update_block(x);
        }
        for (AngryBird bird : birds) {
            update_new_bird_pos(bird);
            if (bird!=null && bird.sprite!=null && bird.body != null) {
                bird.sprite.draw(batch);
            }
        }
        for (Pigs pig : pigs) {
            if (pig != null && pig.body != null && pig.sprite != null) { // Check for null
                pig.sprite.draw(batch);
            }
        }
        for(int i=0;i<12;i++) {
            SteelBlock y = steelBlocks.get(i);
            if (y == null) {
                System.out.println("null" + i);
            }
            if (y != null && y.sprite != null) {
                y.sprite.draw(batch);
            }
        }
        cata.sprite.draw(batch);
        batch.end();
        updateBirdPosition();


        // Draw trajectory if dragging
        if (isDragging && bird_black.sprite != null) {
            drawTrajectory(
                bird_black.sprite.getX() + bird_black.sprite.getWidth() / 2,
                bird_black.sprite.getY() + bird_black.sprite.getHeight() / 2,
                Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY()
            );
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
    }

    private void simulateSplit(AngryBird bird) {

        Vector2 velocity = bird.body.getLinearVelocity();
        int x = (int) bird.sprite.getX();
        int y = (int) bird.sprite.getY();
        System.out.println(x + "--" + y);

        float splitAngleOffset = 20f;
        float velocityScale = 0.7f;

        for (int i = 0; i < 3; i++) {
            Vector2 position = bird.body.getPosition();
            float angle = velocity.angle() + splitAngleOffset * (i - 1);
            Vector2 newVelocity = velocity.cpy().rotate(angle);
            SmallBlueBird newBird = new SmallBlueBird(x, y, world);
            newBird.sprite.setPosition(x,y);
            newBird.body = createBody(newBird, BodyDef.BodyType.DynamicBody);
            newBird.body.setLinearVelocity(newVelocity.scl(velocityScale));
            birds.add(newBird);
        }

//        world.destroyBody(bird.body);
//        bird_black = null;

    }

    @Override
    public void hide() {
    }
    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        stage.dispose();

        // Dispose textures if they are not automatically dealt with
        background.getTexture().dispose();
        groundSprite.getTexture().dispose();
        // You might want to loop through all birds, pigs, and blocks to dispose their textures
        for (AngryBird bird : birds) {
            bird.sprite.getTexture().dispose();
        }
        for (Pigs pig : pigs) {
            pig.sprite.getTexture().dispose();
        }
        for (Blocks block : woodenBlocks) {
            block.sprite.getTexture().dispose();
        }
        for (Blocks block : glassBlocks) {
            block.sprite.getTexture().dispose();
        }
        for (SteelBlock block : steelBlocks) {
            block.sprite.getTexture().dispose();
        }

        Gdx.input.setInputProcessor(null);
    }

    // Method to switch to the next bird after one is launched
    private void swamp() {
        if (bird_cnt > 0) {
            bird_cnt--;
            bodiesToDestroy.add(bird_black.body);
            bird_black = BirdFactory3.createBird(bird_cnt); // Create the next bird using factory method
            bird_black.sprite.setSize(42, 51);
            bird_black.sprite.setPosition(250, 108); // Reset position above the catapult
            bird_black.body = createBody(bird_black, BodyDef.BodyType.DynamicBody); // Create the new body
            bird_black.body.setAwake(false); // Keep it from falling immediately
        }
    }

    // Implementations for creating bodies for pigs and blocks
    private Body createBody2(Pigs gameObject, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            (gameObject.sprite.getX() + gameObject.sprite.getWidth() / 2) / PPM,
            (gameObject.sprite.getY() + gameObject.sprite.getHeight() / 2) / PPM
        );


        Body body = world.createBody(bodyDef);
        System.out.println(body.getPosition()+"HI-?blocks body");
        System.out.println(gameObject.sprite.getX()+" "+gameObject.sprite.getY());
        PolygonShape shape = new PolygonShape();
        Pigs block = (Pigs) gameObject;
        float halfWidth = (block.sprite.getWidth() / 2) / PPM; // Convert to meters
        float halfHeight = (block.sprite.getHeight() / 2) / PPM; // Convert to meters
        shape.setAsBox(halfWidth, halfHeight);



//        shape.setAsBox(1.4f,0.8f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);
        shape.dispose();
        return body;
    }
    private Body createBody(AngryBird gameObject, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            (gameObject.sprite.getX() + gameObject.sprite.getWidth() / 2) / PPM,
            (gameObject.sprite.getY() + gameObject.sprite.getHeight() / 2) / PPM
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        AngryBird block = (AngryBird) gameObject;
        float halfWidth = (block.sprite.getWidth() / 2) / PPM; // Convert to meters
        float halfHeight = (block.sprite.getHeight() / 2) / PPM; // Convert to meters
        shape.setAsBox(halfWidth, halfHeight);

        System.out.println(halfWidth+"angryWala "+halfHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.35f;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);

        shape.dispose();
        return body;
    }
    private Body createBody3(Blocks gameObject, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            (gameObject.sprite.getX() + gameObject.sprite.getWidth() / 2) / PPM,
            (gameObject.sprite.getY() + gameObject.sprite.getHeight() / 2) / PPM
        );


        Body body = world.createBody(bodyDef);
        System.out.println(body.getPosition()+"HI-?blocks body");
        System.out.println(gameObject.sprite.getX()+" "+gameObject.sprite.getY());
        PolygonShape shape = new PolygonShape();
        Blocks block = (Blocks) gameObject;
        float halfWidth = (block.sprite.getWidth() / 2) / PPM; // Convert to meters
        float halfHeight = (block.sprite.getHeight() / 2) / PPM; // Convert to meters
        shape.setAsBox(halfWidth, halfHeight);



//        shape.setAsBox(1.4f,0.8f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);
        shape.dispose();
        return body;
    }

    public Stage getStage(){
        return this.stage;
    }
}
