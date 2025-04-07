package io.github.Noah_SIO;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    //batch image et sprite, shape pour les formes carré,triangle...
    Player player;
    Shoot shoot;
    Shoot shootMonster;
    Monster monster;
    List<Shoot> listeShoot = new ArrayList<>();
    List<Monster> listeMonster = new ArrayList<>();
    List<Shoot> listeShootMonster = new ArrayList<>();
    float randomValue;
    int fly = 5;

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
    Rectangle boxShootMonster;


    ///////Test//////
    private BitmapFont font;
    int typemonster = 1;
    int xmonster = 40;

    ////PlayerVar////
    int health = 3;
    int score = 0;
    int kill = 0;
    int shootSpeed = 13;
    int shootSpeedMonster = 8;
    Texture healthplayer;




    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        player = new Player(width/2, 60, 30);
        font = new BitmapFont();
        font.getData().setScale(2.0f);


        imageFond = new Texture("background.png"); //DragonGaiden\Projet\lwjgl3\build\resources\main
        imageFond.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //repetition du fond
        scrollY = 0; // Position initiale du défilement

        healthplayer = new Texture(Gdx.files.internal("health.png"));


        
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // modifier probleme type monster
                randomValue = MathUtils.random(60, 400);;
                if(typemonster == 1){
                    typemonster=2;
                    xmonster=150;
                }else{
                    typemonster=1;
                    xmonster=40;
                }
                newMonster(typemonster,xmonster);
                System.out.println("New Monster !");
            }
        }, 3, 5);


        Timer.schedule(new Timer.Task() {
            @Override
                public void run() {
                if(listeMonster.size()>0){
                    for(int j =0; j<listeMonster.size(); j++){
                        shootMonster = new Shoot(listeMonster.get(j).getX()+20, listeMonster.get(j).getY(), 30, 40, 2);
                        if(listeShootMonster.size()==0){
                        listeShootMonster.add(shootMonster);  
                        System.out.println("add");
                        }
                        }
                    }
                }
            }, 2, 1);


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


    
        //action
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) { //Shift
            speed=speed/2; // ralentir vitesse
        }else{
            speed=10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ //ESPACE
            shoot = new Shoot(player.getX()-32, player.getY()+20, 30, 40,1);
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
        


        ////affichage shoot player
        if(listeShoot.size() >0){
        listeShoot.get(0).draw(batch);
        listeShoot.get(0).move(0, shootSpeed);
        if(listeShoot.get(0).getY() > height){
        listeShoot.remove(0);  
        }
        //System.err.println(listeShoot.size());
        }

        ////affichage shoot monster
        if(listeShootMonster.size() > 0){
            listeShootMonster.get(0).draw(batch);
            listeShootMonster.get(0).move(0, -shootSpeedMonster);
            if(listeShootMonster.get(0).getY()< -50){
            listeShootMonster.remove(0);  
            }
            //System.err.println(listeShoot.size());
            }
        // ///////////////


        // //affichage monstre
        if(listeMonster.size() >0){
            for(int i=0;i<listeMonster.size();i++){
            listeMonster.get(i).draw(batch);
            //System.out.println(listeMonster.get(i).getY());

            /////Mouvement/////
                if (listeMonster.get(i).getType() == 1) {
                    switch (listeMonster.get(i).getStep()) {
                        case 0:
                            listeMonster.get(i).nextStep();
                            break;
                        case 1:
                            listeMonster.get(i).move(0, -fly); // Déplace vers le bas
                            if(listeMonster.get(i).getY()==400 || listeMonster.get(i).getY()==200){
                            listeMonster.get(i).nextStep();
                            }
                            break;
                        case 2:
                            listeMonster.get(i).move(fly, 0); // Déplace sur le côté
                            if(listeMonster.get(i).getY()>=400 && listeMonster.get(i).getY()<=120){
                                listeMonster.get(i).move(fly, 0); 
                            }
                            if(listeMonster.get(i).getX()==250){
                            listeMonster.get(i).resetStep();
                            }
                            break;
                    }
                } 

                if(listeMonster.get(i).getType() == 2){
                    switch (listeMonster.get(i).getStep()) {
                    case 0:
                        listeMonster.get(i).nextStep();
                        break;
                    case 1:
                        listeMonster.get(i).move(0, -fly); // Déplace vers le bas
                        ///System.out.println('1');
                        if(listeMonster.get(i).getY()==400){
                        listeMonster.get(i).nextStep();
                        }
                        break;
                    case 2:
                        listeMonster.get(i).move(fly, 0); //gauche
                        //System.out.println('2');
                        if(listeMonster.get(i).getX()==400){
                            listeMonster.get(i).nextStep();
                        }
                        break;
                    case 3:
                        listeMonster.get(i).move(0, -fly); //descend
                        //System.out.println('3');
                        if(listeMonster.get(i).getY()==200){
                            listeMonster.get(i).nextStep();
                        }
                        break;
                    case 4:
                        listeMonster.get(i).move(-fly, 0); //droite
                        //System.out.println('4');
                        //System.out.println(listeMonster.get(i).getX());
                        if(listeMonster.get(i).getX()==150){
                            listeMonster.get(i).nextStep();
                        }
                        break;
                    case 5:
                        listeMonster.get(i).move(0, -fly);
                        break;
                    }
                }
        
                //listeMonster.get(i).move(0,-fly); 
                //listeMonster.get(i).move(fly,0); //fly = 5 ou -5

            ////////////////////////////////////////////////////////////


            boxPlayer = player.returnSprite().getBoundingRectangle();    
            boxMonster = listeMonster.get(i).returnSprite().getBoundingRectangle();
            
            
            
            //collisions bas monster
            if(listeMonster.get(i).getY() < -50 || listeMonster.get(i).getX() > 900){
                listeMonster.remove(i);
                System.out.println("monster remove");
            }

            //collisions joueur monster
            if(boxPlayer.overlaps(boxMonster)){
                System.out.println("touch monster");
                player.setPosition(width/2,60);
                player.setX(width/2);
                player.setY(60);
                listeMonster.remove(i);
                health = health - 1;
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
            //collision shoot player
            if(listeShootMonster.size()>0){
                boxShootMonster = listeShootMonster.get(0).returnSprite().getBoundingRectangle();
                if(boxShootMonster.overlaps(boxPlayer)){
                    listeShootMonster.remove(0);
                    player.setPosition(width/2,60);
                    player.setX(width/2);
                    player.setY(60);
                    health = health -1;
                }
            }

            }
        }
        

        


        
        ///Affichage var joueur/////
        var scoreString = String.valueOf(score); //score
        font.setColor(Color.WHITE);
        font.draw(batch, scoreString, width/10, height-50);
        
        var x = width-300; ////affichage vie
        var y=height-100;
        if(health > 0){
        for(int i=0; i<health; i++){
            batch.draw(healthplayer, x, y, 100, 75);
            x+=75;
        }
        }

        /////////
        
        batch.end();
        shape.end();
    }

    //function monster
    public void newMonster(int type, int x){ 
        monster = new Monster(x,height-100,40,type);
        listeMonster.add(monster);
    }


}
