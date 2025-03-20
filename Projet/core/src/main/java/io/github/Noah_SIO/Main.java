package io.github.Noah_SIO;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shape;
    private Texture imageFond;
    private float scrollY;
    private SpriteBatch batch;

    //batch image et sprite, shape pour les formes carré,triangle...

    Ball ball;
    Shoot shoot;
    List<Shoot> listeShoot = new ArrayList<>();
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
        float speed=10;
        int test = 0;

        

        shape.begin(ShapeRenderer.ShapeType.Filled);

        //collision côté
        if(ball.getX() >= 450){ //gauche
            ball.move(-speed, 0); 
         }
         if(ball.getX() <= 0){ //droite
             ball.move(speed, 0);
         }
         if(ball.getY() >= 450){ //haut
             ball.move(0, -speed);
         }
         if(ball.getY() <= 0){
             ball.move(0,speed);
         }

        //action
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) { //Shift
            speed=speed/2; // ralentir vitesse
        }else{
            speed=10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ //ESPACE
            shoot = new Shoot(ball.getX()-10, ball.getY()+40, 20, 40);
            if(listeShoot.size() < 1){
            listeShoot.add(shoot);
            }
        }


        //mouvement
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


        
        //shape.circle(x,y,50);
        batch.begin();
        //affichage fond et scroll
        batch.draw(imageFond, 0, 0, 0, (int) scrollY, 450, 450);
        // Gdx.graphics.getWidth(), Gdx.graphics.getHeight() : taille
        ball.draw(shape);
        
        if(listeShoot.size() >0){
        listeShoot.get(0).draw(shape);
        listeShoot.get(0).move(0, 7);
        if(listeShoot.get(0).getY() > 450){
        listeShoot.remove(0);  
        }
        System.err.println(listeShoot.size());
        }
        

        batch.end();
        shape.end();
    }
}
