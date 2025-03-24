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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shape;
    private Texture imageFond;
    private float scrollY;
    private SpriteBatch batch;

    //batch image et sprite, shape pour les formes carré,triangle...

    Ball ball;
    Shoot shoot;
    Monster monster;
    List<Shoot> listeShoot = new ArrayList<>();
    List<Monster> listeMonster = new ArrayList<>();
    float randomValue;
    int avance = 5;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        ball = new Ball(450/2, 60, 30);
        


        imageFond = new Texture("background.png"); //DragonGaiden\Projet\lwjgl3\build\resources\main
        imageFond.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //repetition du fond

        scrollY = 0; // Position initiale du défilement


        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Votre action à exécuter ici
                randomValue = MathUtils.random(60, 400);;
                newMonster();
                System.out.println("New Monster !");
            }
        }, 3, 5);
    }



    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //x+=5;
        float speed=10;
        int test = 0;
        

        shape.begin(ShapeRenderer.ShapeType.Filled);

        //collision côté décor
        if(ball.getX() >= 450){ //gauche
            ball.move(-speed, 0); 
         }
         if(ball.getX() <= 0){ //droite
             ball.move(speed, 0);
         }
         if(ball.getY() >= 450){ //haut
             ball.move(0, -speed);
         }
         if(ball.getY() <= 0){ //bas
             ball.move(0,speed);
         }


        //collision monstre et décor 
        if(listeMonster.size() >0){ 
        if(listeMonster.get(0).getX() <= 0){
            avance = 5;
        }
        if(listeMonster.get(0).getX() >= 430){
            avance = -5;
        }
        }




        //action
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) { //Shift
            speed=speed/2; // ralentir vitesse
        }else{
            speed=10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ //ESPACE
            shoot = new Shoot(ball.getX()-10, ball.getY()+40, 20, 40);
            if(listeShoot.size() < 1){ //tir
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

        //image de fond scroll
        scrollY += Gdx.graphics.getDeltaTime() * 100; // Vitesse de défilement
        if (scrollY > imageFond.getHeight()) {
            scrollY = 0; // Réinitialiser pour éviter les dépassements
        }
        batch.begin();
        batch.draw(imageFond, 0, 0, 0, (int) scrollY, 450, 450);
        // Gdx.graphics.getWidth(), Gdx.graphics.getHeight() : taille
        ////////////////////////////////
        

        //joueur draw
        ball.draw(shape);
        


        //affichage shoot
        if(listeShoot.size() >0){
        listeShoot.get(0).draw(shape);
        listeShoot.get(0).move(0, 13);
        if(listeShoot.get(0).getY() > 450){
        listeShoot.remove(0);  
        }
        System.err.println(listeShoot.size());
        }
        ///////////////


        //affichage monstre
        if(listeMonster.size() >0){
            for(int i=0;i<listeMonster.size();i++){
            listeMonster.get(i).draw(shape);

            listeMonster.get(i).move(avance,0);


            //collision joueur et monstre
            if (isCollisionCircles(ball.getX(), ball.getY(), ball.getSize(), 
            listeMonster.get(i).getX(), listeMonster.get(i).getY(), listeMonster.get(i).getSize())) {
                System.out.println("ok");
                ball.setX(450/2);
                ball.setY(60);
                listeMonster.remove(i);
            }
            
            //collision monstre et tir
            if(listeShoot.size() >0){
            if(isCircleCollidingWithRectangle(listeMonster.get(i).getX(),listeMonster.get(i).getY(),
            listeMonster.get(i).getSize(),listeShoot.get(0).getX(),listeShoot.get(0).getY(),20,40)){
                listeMonster.remove(i);
            }
            }
            }
        }
        ///////////


        batch.end();
        shape.end();
    }

    //function monster
    public void newMonster(){ 
        monster = new Monster(randomValue,333,40,1);
        listeMonster.add(monster);
    }







    ///Function calcul complexe de collision

    public boolean isCollisionCircles(float x1, float y1, float radius1, float x2, float y2, float radius2) {
    // Calculez la distance entre les centres des cercles
    float distance = Vector2.dst(x1, y1, x2, y2);

    // Vérifiez si la distance est inférieure à la somme des rayons
    return distance < (radius1 + radius2);
    }


    public static boolean isCircleCollidingWithRectangle(
    float circleX, float circleY, float radius,
    float rectX, float rectY, float rectWidth, float rectHeight) {

    float closestX = Math.max(rectX, Math.min(circleX, rectX + rectWidth));
    float closestY = Math.max(rectY, Math.min(circleY, rectY + rectHeight));

    float distanceX = circleX - closestX;
    float distanceY = circleY - closestY;

    return (distanceX * distanceX + distanceY * distanceY) <= (radius * radius);
    }




}
