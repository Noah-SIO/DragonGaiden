package io.github.Noah_SIO;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shape;
    private Texture imageFond;
    private float scrollY;
    private SpriteBatch batch;

    //batch image et sprite, shape pour les formes carré,triangle...

    Ball ball;

    // int x = 50; //test variable
    // int y = 50;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        ball = new Ball(30, 30, 30);


        imageFond = new Texture("background.png"); //DragonGaiden\Projet\lwjgl3\build\resources\main
        imageFond.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //repetition du fond

        scrollY = 0; // Position initiale du défilement
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //x+=5;
        float speed=5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { //Q //A
            ball.move(-speed, 0); //gauche
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // D
            ball.move(speed, 0);// Déplacement à droite
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // Z //W
            ball.move(0, speed); // Déplacement vers le haut
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { //S
            ball.move(0, -speed); // Déplacement vers le bas
        }
        

        //SPACE, ESCAPE,  SHIFT_LEFT

        scrollY += Gdx.graphics.getDeltaTime() * 100; // Vitesse de défilement
        if (scrollY > imageFond.getHeight()) {
            scrollY = 0; // Réinitialiser pour éviter les dépassements
        }


        //collision côté
        if(ball.getX() >= 414){ //gauche
           ball.move(-5, 0); 
        }
        if(ball.getX() <= 0){ //droite
            ball.move(5, 0);
        }
        if(ball.getY() >= 414){ //haut
            ball.move(0, -5);
        }
        if(ball.getY() <= 0){
            ball.move(0,5);
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        //shape.circle(x,y,50);
        batch.begin();
        //affichage fond et scroll
        batch.draw(imageFond, 0, 0, 0, (int) scrollY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Gdx.graphics.getWidth(), Gdx.graphics.getHeight() : taille
        ball.draw(shape);
        batch.end();
        shape.end();
    }
}
