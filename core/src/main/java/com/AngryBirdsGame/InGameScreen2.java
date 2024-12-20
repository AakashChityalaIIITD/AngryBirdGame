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

//Factory Class Design pattern for creating bird
class BirdFactory {
    public static AngryBird createBird(int type) {
        switch (type) {
            case 2: return new RedAngryBird();
            case 3: return new BlackAngryBird();
            case 1: return new BlueAngryBird();
            default: return new BlueAngryBird();
        }
    }
}


public class InGameScreen2 extends ApplicationAdapter implements Screen {
    private Main game;
    private final List<Body> bodiesToDestroy = new ArrayList<Body>();
    boolean chkNewGame;

    GameHandler gameHandler;
    int black_bird_flight = 0;
    boolean isDragging = false; // Track if bird is being dragged
    private Stage stage;
    private World world;
    private int cntr = 2;
    private SpriteBatch batch;
    private Sprite background;
    private Body groundBody; // Box2D body for ground
    private AngryBird bird_black;
    private ImageButton pause;

    private List<Pigs> pigs;
    boolean chk;
    private List<woodenBrick> woodenBlocks;
    private List<GlassBlock> glassBlocks;
    private List<SteelBlock> steelBlocks;
    private List<AngryBird> birds;
    private List<SquareGlasses> glasses;

    private final float PPM = 100f;
    private ShapeRenderer shapeRenderer; // For trajectory
    private Sprite groundSprite;// Pixels per meter scaling
    private Vector2 dragStartPosition = new Vector2();// Store the starting point of the drag
    private catapol cata;
    int pig_cntr=2;
    int bird_cnt=3;
    ArrayList<SquareGlasses> arr=new ArrayList<SquareGlasses>();
    private SquareGlasses squareGlasses1, squareGlasses2, squareGlasses3, squareGlasses4, squareGlasses5, squareGlasses6;
    private smallpig pig_smallpig;
    private kingpig pig_king;
    private fattyPig pig_fatty;

    public InGameScreen2(Main main, boolean chkNewGame) {
        gameHandler = new GameHandler("gameSave2.dat");
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

        gameHandler = new GameHandler("gameSave2.dat");
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
        final InGameScreen2 ig2=this;
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenuScreen(game,2, pigs,woodenBlocks,glassBlocks,glasses,steelBlocks, bird_cnt,null,null,ig2));
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
        for(int i=0;i<6;i++){
            if(glasses.get(i)==null){
                System.out.println("null");
            }
            if(glasses.get(i)!=null){
                System.out.println(glasses.size());
                update_block(glasses.get(i));
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
                    System.out.println("Bird hit a pig!");
                    // Handle bird and pig collision
                    handleBrickBrickCollision(bodyA, bodyB);
                }

                // Check if the contact is between a bird and a pig
//                if ((bodyA.getUserData() instanceof Blocks && bodyB.getUserData() instanceof Pigs) ||
//                    (bodyA.getUserData() instanceof Pigs && bodyB.getUserData() instanceof Blocks)) {
//                    System.out.println("Bird hit a pig!");
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
                it++;
            }
            int iter=0;
            for(GameState.BlockState blck:b){
                if(iter==0){
                    if (blck.x == -1 && blck.y == -1) {
                           squareGlasses1=new SquareGlasses();
                           squareGlasses1.sprite=null;
                           bodiesToDestroy.add(squareGlasses1.body);
                           glasses.add(squareGlasses1);
                    }
                    else{
                        squareGlasses1 = new SquareGlasses();
                        squareGlasses1.setDurability(blck.durability);
                        squareGlasses1.sprite.setPosition(blck.x, blck.y);
                        squareGlasses1.sprite.setSize(50, 50);
                        squareGlasses1.body = createBody3(squareGlasses1, BodyDef.BodyType.DynamicBody);

                       glasses.add(squareGlasses1);
                    }

                }
                if(iter==1){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses2=new SquareGlasses();
                        squareGlasses2.sprite=null;
                        bodiesToDestroy.add(squareGlasses2.body);
                        glasses.add(squareGlasses2);
                    }
                    else{
                        squareGlasses2 = new SquareGlasses();
                        squareGlasses2.setDurability(blck.durability);
                        squareGlasses2.sprite.setPosition(blck.x, blck.y);
                        squareGlasses2.sprite.setSize(50, 50);
                        squareGlasses2.body = createBody3(squareGlasses2, BodyDef.BodyType.DynamicBody);

                        glasses.add(squareGlasses2);
                    }

                }
                if(iter==2){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses3=new SquareGlasses();
                        squareGlasses3.sprite=null;
                        bodiesToDestroy.add(squareGlasses3.body);
                        glasses.add(squareGlasses3);
                    }
                    else{
                        squareGlasses3 = new SquareGlasses();
                        squareGlasses3.setDurability(blck.durability);
                        squareGlasses3.sprite.setPosition(blck.x, blck.y);
                        squareGlasses3.sprite.setSize(50, 50);
                        squareGlasses3.body = createBody3(squareGlasses3, BodyDef.BodyType.DynamicBody);

                        glasses.add(squareGlasses3);
                    }

                }
                if(iter==3){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses4=new SquareGlasses();
                        squareGlasses4.sprite=null;
                        bodiesToDestroy.add(squareGlasses4.body);
                        glasses.add(squareGlasses4);
                    }
                    else{
                        squareGlasses4 = new SquareGlasses();
                        squareGlasses4.setDurability(blck.durability);
                        squareGlasses4.sprite.setPosition(blck.x, blck.y);
                        squareGlasses4.sprite.setSize(50, 50);
                        squareGlasses4.body = createBody3(squareGlasses4, BodyDef.BodyType.DynamicBody);

                        glasses.add(squareGlasses4);
                    }

                }
                if(iter==4){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses5=new SquareGlasses();
                        squareGlasses5.sprite=null;
                        bodiesToDestroy.add(squareGlasses5.body);
                        glasses.add(squareGlasses5);
                    }
                    else{
                        squareGlasses5 = new SquareGlasses();
                        squareGlasses5.setDurability(blck.durability);
                        squareGlasses5.sprite.setPosition(blck.x, blck.y);
                        squareGlasses5.sprite.setSize(50, 50);
                        squareGlasses5.body = createBody3(squareGlasses5, BodyDef.BodyType.DynamicBody);

                        glasses.add(squareGlasses5);
                    }

                }
                if(iter==5){
                    if (blck.x == -1 && blck.y == -1) {
                        squareGlasses6=new SquareGlasses();
                        squareGlasses6.sprite=null;
                        bodiesToDestroy.add(squareGlasses6.body);
                        glasses.add(squareGlasses6);
                    }
                    else{
                        squareGlasses6 = new SquareGlasses();
                        squareGlasses6.setDurability(blck.durability);
                        squareGlasses6.sprite.setPosition(blck.x, blck.y);
                        squareGlasses6.sprite.setSize(50, 50);
                        squareGlasses6.body = createBody3(squareGlasses6, BodyDef.BodyType.DynamicBody);

                        glasses.add(squareGlasses6);
                    }

                }
                iter++;
            }
        }
        else {
        pig_smallpig = new smallpig();
        pig_smallpig.sprite.setPosition(850, 100); // Adjusted position
        pig_smallpig.sprite.setSize(45, 50);
        pig_smallpig.body = createBody2(pig_smallpig, BodyDef.BodyType.DynamicBody);
        pig_smallpig.body.setAwake(false);
        pigs.add(pig_smallpig);

        pig_fatty = new fattyPig();
        pig_fatty.sprite.setPosition(900, 200); // Above small pig
            pig_fatty.sprite.setSize(45, 50);
            pig_fatty.body = createBody2(pig_fatty, BodyDef.BodyType.DynamicBody);
            pig_fatty.body.setAwake(false);
        pigs.add(pig_fatty);


        squareGlasses1=new SquareGlasses();
        squareGlasses2=new SquareGlasses();
        squareGlasses3=new SquareGlasses();
        squareGlasses4=new SquareGlasses();
        squareGlasses5=new SquareGlasses();
        squareGlasses6=new SquareGlasses();
        glasses.add(squareGlasses1);
        glasses.add(squareGlasses2);
        glasses.add(squareGlasses3);
        glasses.add(squareGlasses4);
        glasses.add(squareGlasses5);
        glasses.add(squareGlasses6);

        for(int i=0;i<3;i++){
            SquareGlasses x =glasses.get(i);

            x.sprite.setPosition(850-50,100+50*i);
            x.sprite.setSize(50,50);
            x.body=createBody3(x, BodyDef.BodyType.DynamicBody);
            x.body.setAwake(false);

        }

        for(int i=3;i<6;i++){
            SquareGlasses x=glasses.get(i);
            x.sprite.setPosition(950,100+50*(i-3));
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

    private void updateBirdPosition() {
        if (bird_black != null) {
            Vector2 bodyPosition = bird_black.body.getPosition();
            // Set the sprite's position based on the body's position
            bird_black.sprite.setPosition(
                (bodyPosition.x * PPM - bird_black.sprite.getWidth() / 2),
                (bodyPosition.y * PPM - bird_black.sprite.getHeight() / 2)
            );

            // Check the horizontal velocity to determine when to switch birds
            float horizontalVelocity = bird_black.body.getLinearVelocity().x;
            if ((horizontalVelocity == 0 || bird_black.sprite.getX()<=0 || bird_black.body.getPosition().x>12) && black_bird_flight == 1) {
                black_bird_flight = 0; // Reset flight status
                swamp(); // Switch to the next bird
            }
        }
    }

    private void drawTrajectory(float birdX, float birdY, float endX, float endY) {
        Vector2 launchVelocity = calculateLaunchVelocity(endX, endY);

        // Physics parameters
        float g = world.getGravity().y; // Gravity in world units (negative)
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
        //System.out.println("Launch Velocity Calculation: " + launchVelocity.x + ", " + launchVelocity.y);
        return new Vector2((deltaX - 0.5f) * power, (deltaY + 0.5f) * power);
    }

    @Override
    public void show() {
        // Optional: Implement any initialization logic for when the screen is shown
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 6, 2); // Step the physics
        //System.out.println("pig_cntr"+pig_cntr+"bird_cnr"+bird_cnt);
        int pig_check=0;
        for(int i=0;i<pigs.size();i++){
            if(pigs.get(i).sprite==null){
                pig_check++;
            }
        }
        if(pig_check==2){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    FileOutputStream fileOut = null;
                    ObjectOutputStream out = null;
                    try {
                        fileOut = new FileOutputStream("gameSave2.dat");
                        System.out.println("Game state has been serialized and saved.");
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions appropriately
                    }
                    game.setScreen(new VictoryScreen(game)); // Change to your desired screen
                }
            },0.5f);  // 2-second delay

        }
        if(bird_cnt==0){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    FileOutputStream fileOut = null;
                    ObjectOutputStream out = null;
                    try {
                        fileOut = new FileOutputStream("gameSave2.dat");
                        System.out.println("Game state has been serialized and saved.");
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions appropriately
                    }
                    game.setScreen(new LoseScreen(game,2)); // Change to your desired screen
                }
            }, 0.8f);  // 2-second delay
        }

        processPendingDestructions();
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
        update_pos(pig_fatty);
        for(int i=0;i<6;i++){
            SquareGlasses x=glasses.get(i);
            update_block(x);
        }
        for (Pigs pig : pigs) {
            if (pig != null && pig.body != null && pig.sprite != null) { // Check for null
                pig.sprite.draw(batch);
            }
        }
        for(int i=0;i<6;i++) {
            SquareGlasses y = glasses.get(i);
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

    @Override
    public void hide() {
//        gameHandler.saveGameState(pigs,woodenBlocks,glassBlocks,glasses,steelBlocks, bird_cnt);
        // Optional: Implement any cleanup logic for when the screen is hidden
    }
    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        stage.dispose();
    }

    // Method to switch to the next bird after one is launched
    private void swamp() {
        if (bird_cnt > 0) {
            bodiesToDestroy.add(bird_black.body);
            bird_cnt--; // Decrement counter for the next bird
            bird_black = BirdFactory.createBird(bird_cnt); // Create the next bird using factory method
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
