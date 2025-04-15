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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.time.LocalDate;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


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
    private float scrollX;
    private SpriteBatch batch;
    private TextField playerNameField;


    ////Collisions/////
    Rectangle boxPlayer;
    Rectangle boxMonster;
    Rectangle boxShoot;
    Rectangle boxShootMonster;


    ///////Monster//////
    private BitmapFont font;
    private BitmapFont fontScore;
    int typemonster = 1;
    int xmonster = 40;
    int ymonster = height-300;
    Timer.Task monsterTask;
    Timer.Task shooTask;

    //////PowerUp/////////
    int xpowerup;
    int ypowerup;
    List<PowerUp> listePowerUp = new ArrayList<>();
    float randomSpawn;
    float randomType;
    Rectangle boxPowerUp;

    ////PlayerVar////
    int health = 3;
    int score = 0;
    int kill = 0;
    int shootSpeed = 13;
    int shootSpeedMonster = 8;
    String playerName = "playerTest";
    Texture healthplayer;
    SQLiteManager managerSQLite = new SQLiteManager();
    String pseudo = "playerTest";
    LocalDate dateAujourdhui = LocalDate.now();

    //////Music///////////
    //////Music/Sound//////////
    Music music;
    Sound shootSound;
    Sound deadSound;
    Sound bonusSound;
    Sound shootMonsterSound;
    Sound bonusSpawnSound;
    Sound gameoverSound;
    /////////////////////



    ///////test menu/////
    int gameStart = 0; // Variable pour contrôler l'état du jeu
    private Stage menuStage;
    private Skin skin;
    private TextButton startButton;
    private TextButton exitButton;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        player = new Player(60, height/2, 30);
        font = new BitmapFont();
        font.getData().setScale(2.0f);
        fontScore = new BitmapFont(); // Police par défaut de LibGDX
        fontScore.getData().setScale(1.5f);

        imageFond = new Texture("cityparis.png"); //DragonGaiden\Projet\lwjgl3\build\resources\main
        imageFond.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //repetition du fond
        scrollY = 0; // Position initiale du défilement
        scrollX = 0;

        healthplayer = new Texture(Gdx.files.internal("health.png"));

        //////Music/Sound//////////
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"));
        deadSound = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        bonusSound = Gdx.audio.newSound(Gdx.files.internal("bonus.wav"));
        shootMonsterSound = Gdx.audio.newSound(Gdx.files.internal("shootmonster.wav"));
        bonusSpawnSound = Gdx.audio.newSound(Gdx.files.internal("bonusappear.wav"));
        gameoverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        /////////////////////

        /////// Menu test ///////

        // Chargement du skin
        skin = new Skin(Gdx.files.internal("skins/arcade/arcade-ui.json"));
        // Thème bouton
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button-blue");
        textButtonStyle.down = skin.getDrawable("button-pressed-blue");
        textButtonStyle.font = skin.getFont("font");
        skin.add("blue", textButtonStyle);
        // Création du stage du menu
        menuStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(menuStage);
        

        ////////background menu//////////
        Texture bgTexture = new Texture(Gdx.files.internal("skins/arcade/background2.jpg"));
        Image background = new Image(bgTexture);
        background.setSize(width, height);
        /////////////////////////////


        ////////Button////////////
        // Création des boutons
        startButton = new TextButton("Start Game", skin, "blue");
        exitButton = new TextButton("Exit", skin, "blue");

        // Taille des boutons
        startButton.setSize(200, 60);
        exitButton.setSize(200, 60);

        // Position des boutons
        startButton.setPosition(width / 2f - 100, height / 2f - 80);
        exitButton.setPosition(width / 2f - 100, height / 2f - 150);
        ///////////////////////////////    


        ////////Champ de text//////////////
        
        BitmapFont font = new BitmapFont(); // Police par défaut de LibGDX
        font.getData().setScale(3f);
        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = font;

        // Définir des couleurs (optionnel)
        textFieldStyle.fontColor = com.badlogic.gdx.graphics.Color.BLACK; // Cyan clair



        playerNameField = new TextField("", textFieldStyle);
        playerNameField.setMessageText("Enter your name"); // Texte indicatif
        playerNameField.setPosition(width / 2 - 140, height / 2f - 200);
        playerNameField.setSize(400, 40);




        ///// Action Listener /////
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                health=3;
                gameStart = 1;
                playerName = playerNameField.getText();
                System.out.println(playerName);
                music.setLooping(true);
                music.play();
                Gdx.input.setInputProcessor(null); // désactive les inputs du menu
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        /////////////////////////
        


        // Ajout des boutons à la scène ////ordre à prendre en compte pour affichage 
        menuStage.addActor(background);
        menuStage.addActor(playerNameField);
        menuStage.addActor(startButton);
        menuStage.addActor(exitButton);
        
    }



    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //x+=5;
        float speed=10;
        int test = 0;
        

        //shape.begin(ShapeRenderer.ShapeType.Filled);
        

        ///////Menu de jeux///////
        if (gameStart == 0) {
            ScreenUtils.clear(0, 0, 0, 1);
            menuStage.act(Gdx.graphics.getDeltaTime());
            menuStage.draw();
            return;
        } else{

            //////Timer///////////////
            if(gameStart == 1){ 
                gameStart = 2;
                monsterTask = Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // modifier probleme type monster
                        randomValue = MathUtils.random(60, 400);
                        if(typemonster == 1){
                            typemonster=2;
                            xmonster=width+100;
                            ymonster = height-300;
                        }else{
                            typemonster=1;
                            xmonster=width+100;
                            ymonster = height-500;
                        }
                        newMonster(typemonster,xmonster,ymonster);
                        System.out.println("New Monster !");
                    }
                }, 3, 3);
        
        
                shooTask = Timer.schedule(new Timer.Task() {
                    @Override
                        public void run() {
                        if(listeMonster.size()>0){
                            for(int j =0; j<listeMonster.size(); j++){
                                shootMonster = new Shoot(listeMonster.get(j).getX()-30, listeMonster.get(j).getY(), 30, 40, 2);
                                //if(listeShootMonster.size()0){
                                listeShootMonster.add(shootMonster);
                                shootMonsterSound.play(3f);  
                                System.out.println("add");
                                //}
                                }
                            }
                        }
                    }, 2, 1);
                }



        ///////////////////////////////////////////

        /////Update la sprite
        if(listeMonster.size()>0){
            for(int i =0;i<listeMonster.size();i++){
                listeMonster.get(i).update();
            }
        }        


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
            shoot = new Shoot(player.getX()+32, player.getY()-30, 30, 40,1);
            if(listeShoot.size() < 1){ //tir
            listeShoot.add(shoot);
            shootSound.play(3f);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gameStart=0;
            stopGameLogic();
            managerSQLite.insererScore(playerName, score, dateAujourdhui.toString());
            score = 0;
            speed = 10;
            shootSpeed = 13;
            listeMonster.clear();
            listePowerUp.clear();
            listeShootMonster.clear();
            listeShoot.clear();
            music.stop();
            gameoverSound.play(3f);
            Gdx.input.setInputProcessor(menuStage);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
            health+=1;
            System.out.println(health);
        }


        if(health == 0){
            gameStart=0;
            stopGameLogic();
            managerSQLite.insererScore(playerName, score, dateAujourdhui.toString());
            score = 0;
            speed = 10;
            shootSpeed = 13;
            listeMonster.clear();
            listePowerUp.clear();
            listeShootMonster.clear();
            listeShoot.clear();
            music.stop();
            gameoverSound.play(3f);
            Gdx.input.setInputProcessor(menuStage);
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
        scrollX += Gdx.graphics.getDeltaTime() * 100; // Vitesse de défilement
        if (scrollX > imageFond.getWidth()) {
            scrollX = 0; // Réinitialiser pour éviter les dépassements
        }
        batch.begin();
        batch.draw(imageFond, 0, 0, (int) scrollX, 0, width, height);
        // Gdx.graphics.getWidth(), Gdx.graphics.getHeight() : taille
        ////////////////////////////////
        


        //////affichage PowerUp//////////
        if(listePowerUp.size()>0){
            for(int i=0; i<listePowerUp.size(); i++){
                listePowerUp.get(i).draw(batch);
            }
        }
        //////////////////////////////////

        //joueur draw
        player.draw(batch);
        


        ////affichage shoot player
        if(listeShoot.size() >0){
        listeShoot.get(0).draw(batch);
        listeShoot.get(0).move(shootSpeed, 0);
        //System.out.println(listeShoot.size());
        if(listeShoot.get(0).getX() > width){
        listeShoot.remove(0);  
        }
        //System.err.println(listeShoot.size());
        }

        ////affichage shoot monster
        if(listeShootMonster.size() > 0){
            for(int k=0; k<listeShootMonster.size();k++){
            listeShootMonster.get(k).draw(batch);
            listeShootMonster.get(k).move(-shootSpeedMonster, 0);
            if(listeShootMonster.get(k).getX()< 0){
            listeShootMonster.remove(k);  
            }
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
                            listeMonster.get(i).move(-fly, 0); // Déplace vers la gauche
                            break;
                    }
                } 

                if(listeMonster.get(i).getType() == 2){
                    switch (listeMonster.get(i).getStep()) {
                        case 0:
                            listeMonster.get(i).nextStep();
                            break;
                        case 1:
                            listeMonster.get(i).move(-fly, 0); // Déplace vers la gauche
                            if(listeMonster.get(i).getX()==400){
                            listeMonster.get(i).nextStep();
                            }
                            break;
                        case 2:
                            listeMonster.get(i).move(0, +fly); // Déplace ver le haut
                            if(listeMonster.get(i).getY()==500){
                            listeMonster.get(i).nextStep();
                            }
                        break;
                        case 3:
                            listeMonster.get(i).move(0, -fly); // Déplace vers la droite
                            if(listeMonster.get(i).getY()==150){
                            listeMonster.get(i).nextStep();
                            }
                        break;
                        case 4:
                        listeMonster.get(i).move(fly, 0); // Déplace vers le bas
                    }
                }
        
                //listeMonster.get(i).move(0,-fly); 
                //listeMonster.get(i).move(fly,0); //fly = 5 ou -5

            ////////////////////////////////////////////////////////////


            boxPlayer = player.returnSprite().getBoundingRectangle();    
            boxMonster = listeMonster.get(i).returnSprite().getBoundingRectangle();
            
            
            
            //collisions côté monster
            if(listeMonster.get(i).getX() < -100 || listeMonster.get(i).getX() > 1000){
                listeMonster.remove(i);
                System.out.println("monster remove");
            }

            //collisions joueur monster
            if(boxPlayer.overlaps(boxMonster)){
                System.out.println("touch monster");
                player.setPosition(60,height/2);
                player.setX(width/2);
                player.setY(60);
                listeMonster.remove(i);
                health = health - 1;
                deadSound.play(3f);
            }    

            //collisions shoot monster
            if(listeShoot.size()>0){
                boxShoot = listeShoot.get(0).returnSprite().getBoundingRectangle();
                if(boxShoot.overlaps(boxMonster)){
                    listeMonster.remove(i);
                    listeShoot.remove(0);
                    kill = kill + 1;
                    score = score + 100;


                    randomSpawn = MathUtils.random(1, 3);
                    if(randomSpawn == 3){
                        randomType = MathUtils.random(1, 3);
                        xpowerup = MathUtils.random(50, width-100);
                        ypowerup = MathUtils.random(50, height-100);
                        PowerUp powerup = new PowerUp(xpowerup, ypowerup, 20, 20, (int) randomType);
                        listePowerUp.add(powerup);
                        bonusSpawnSound.play(3f);
                    }
                }
            }
            //collision shoot player
            if(listeShootMonster.size()>0){
                boxShootMonster = listeShootMonster.get(0).returnSprite().getBoundingRectangle();
                if(boxShootMonster.overlaps(boxPlayer)){
                    listeShootMonster.remove(0);
                    player.setPosition(60,height/2);
                    player.setX(60);
                    player.setY(height/2);
                    health = health -1;
                    deadSound.play(3f);
                }
            }

            ////collision powerUp
            if(listePowerUp.size()>0){
                for(int j=0; j<listePowerUp.size(); j++){
                    boxPowerUp = listePowerUp.get(j).returnSprite().getBoundingRectangle();
                    if(boxPowerUp.overlaps(boxPlayer)){
                        int typetest = listePowerUp.get(j).getType();
                        if(typetest == 1){
                            shootSpeed += 1;
                            speed += 1;
                        }if(typetest == 2){
                            score += 50;
                        }if(typetest == 3){
                            health += 1;
                        }
                        listePowerUp.remove(j);
                        bonusSound.play(3f);
                    }
                }
            }

            }
        }
        

        if(health > 8){
            health = 8;
        }if(speed > 15){
            speed = 15;
        }if(shootSpeed > 18){
            shootSpeed =18;
        }

        ///Affichage var joueur/////
        var scoreString = String.valueOf(score); //score
        font.setColor(Color.WHITE);
        font.draw(batch, scoreString, width/10, height-50);
        
        var x = width-300; ////affichage vie
        var y=height-100;
        if(health > 0){
        for(int i=0; i<health; i++){
            if(i > 3){
                y = height-175;
            }
            batch.draw(healthplayer, x, y, 100, 75);
            if(i==3){
                x = width-300;
            }else{
            x+=75;
            }
        }
        }

        ArrayList<String[]> topScores = managerSQLite.recupererTopScores();

        // Afficher les scores à l'écran
        for (int i = 0; i < topScores.size(); i++) {
            String[] scoreEntry = topScores.get(i);
            String text = scoreEntry[0] + " - " + scoreEntry[1] + " - " + scoreEntry[2];
            fontScore.draw(batch, text, 10, 125 - 30 * (i + 1)); // Position en bas à gauche
        }
        /////////
        
        batch.end();
        //shape.end();
    }
    }


    //function monster
    public void newMonster(int type, int x, int y){ 
        monster = new Monster(x,y,40,type);
        listeMonster.add(monster);
    }

    ////arrêt timer/////////////////
    public void stopGameLogic() {
        if (monsterTask != null) {
            monsterTask.cancel();
            System.out.println("Monster task stopped.");
        }
        if (shooTask != null) {
            shooTask.cancel();
            System.out.println("Shoot task stopped.");
        }
        gameStart = 0; // Mettre à jour l'état de jeu
    }


}
