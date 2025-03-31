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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    //batch image et sprite, shape pour les formes carré,triangle...
    Player player;
    Shoot shoot;
    Monster monster;
    List<Shoot> listeShoot = new ArrayList<>();
    List<Monster> listeMonster = new ArrayList<>();
    float randomValue;
    int fly = 80;

    //taille écran
    int width = 800;
    int height = 600;


    /////Texture///////
    private ShapeRenderer shape;
    private Texture imageFond;
    private float scrollY;
    private SpriteBatch batch;



    ////Collisions/////
    Rectangle boxPlayer;
    Rectangle boxMonster;
    Rectangle boxShoot;


    ///////Test//////
    private long lastUpdateTime = System.currentTimeMillis();
    private long delay = 500; // Délai en millisecondes entre chaque étape


    ////PlayerVar////
    int healt = 3;
    int score = 0;
    int kill = 0;





    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        player = new Player(width/2, 60, 30);
        


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
        if(player.getX() >= width-(player.getSize()*2)){ //gauche
            player.move(-speed, 0); 
         }
         if(player.getX() <= 0){ //droite
             player.move(speed, 0);
         }
         if(player.getY() >= height-(player.getSize()*2)){ //haut
             player.move(0, -speed);
         }
         if(player.getY() <= 0){ //bas
             player.move(0,speed);
         }


        // //collision monstre et décor 
        // if(listeMonster.size() >0){ 
        // if(listeMonster.get(0).getX() <= 0){
        //     avance = 5;
        // }
        // if(listeMonster.get(0).getX() >= 430){
        //     avance = -5;
        // }
        // }




        //action
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) { //Shift
            speed=speed/2; // ralentir vitesse
        }else{
            speed=10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ //ESPACE
            shoot = new Shoot(player.getX()-32, player.getY()+20, 30, 40);
            if(listeShoot.size() < 1){ //tir
            listeShoot.add(shoot);
            }
        }


        //mouvement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { //Q //A
            player.move(-speed, 0); //gauche
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // D
            player.move(speed, 0);// Déplacement à droite
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // Z //W
            player.move(0, speed); // Déplacement vers le haut
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { //S
            player.move(0, -speed); // Déplacement vers le bas
        }
        
        

        //SPACE, ESCAPE,  SHIFT_LEFT

        //image de fond scroll
        scrollY += Gdx.graphics.getDeltaTime() * 100; // Vitesse de défilement
        if (scrollY > imageFond.getHeight()) {
            scrollY = 0; // Réinitialiser pour éviter les dépassements
        }
        batch.begin();
        batch.draw(imageFond, 0, 0, 0, (int) scrollY, width, height);
        // Gdx.graphics.getWidth(), Gdx.graphics.getHeight() : taille
        ////////////////////////////////
        

        //joueur draw
        player.draw(batch);
        


        // //affichage shoot
        if(listeShoot.size() >0){
        listeShoot.get(0).draw(batch);
        listeShoot.get(0).move(0, 8);
        if(listeShoot.get(0).getY() > height){
        listeShoot.remove(0);  
        }
        System.err.println(listeShoot.size());
        }
        // ///////////////


        // //affichage monstre
        if(listeMonster.size() >0){
            for(int i=0;i<listeMonster.size();i++){
            listeMonster.get(i).draw(batch);
            

            /////Mouvement/////
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdateTime >= delay) {
                // Mettre à jour le monstre en fonction de son étape
                if (listeMonster.get(i).getType() == 1) {
                    switch (listeMonster.get(i).getStep()) {
                        case 0:
                            listeMonster.get(i).nextStep();
                            break;
                        case 1:
                            listeMonster.get(i).move(0, -fly); // Déplace vers le bas
                            listeMonster.get(i).nextStep();
                            break;
                        case 2:
                            listeMonster.get(i).move(fly, 0); // Déplace sur le côté
                            listeMonster.get(i).resetStep();
                            break;
                    }
                }else{
                    listeMonster.get(i).move(fly,0);
                    }
        
                // Réinitialiser le temps pour le prochain cycle
                lastUpdateTime = currentTime;
                //listeMonster.get(i).move(0,-fly); 
                //listeMonster.get(i).move(fly,0); //fly = 5 ou -5
                }


            boxPlayer = player.returnSprite().getBoundingRectangle();    
            boxMonster = listeMonster.get(i).returnSprite().getBoundingRectangle();
            
            
            
            //collisions bas monster
            if(listeMonster.get(i).getY() < -100){
                listeMonster.remove(i);
            }

            //collisions joueur monster
            if(boxPlayer.overlaps(boxMonster)){
                System.out.println("touch monster");
                player.setPosition(width/2,60);
                player.setX(width/2);
                player.setY(60);
                listeMonster.remove(i);
                healt = healt - 1;
            }    

            //collisions shoot monster
            if(listeShoot.size()>0){
                boxShoot = listeShoot.get(0).returnSprite().getBoundingRectangle();
                if(boxShoot.overlaps(boxMonster)){
                    listeMonster.remove(i);
                    listeShoot.remove(0);
                    kill = kill + 1;
                    score = score + 100;
                }
            }

            }
        }
       


        batch.end();
        shape.end();
    }

    //function monster
    public void newMonster(){ 
        monster = new Monster(40,height-100,40,1);
        listeMonster.add(monster);
    }


}
