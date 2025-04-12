package io.github.Noah_SIO;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Monster {
    private float x;
    private float y;
    private int size;
    private int type; // 1,2,3
    
    private Texture monsterSheet;
    private Sprite monster;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private int step = 0;

    public Monster(float x, float y, int size, int type) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;

        // Chargement de la feuille de sprites
        monsterSheet = new Texture("dragonsheet.png");
        
        // Découpage de la feuille de sprites
        int frameWidth = monsterSheet.getWidth() / 3;
        int frameHeight = monsterSheet.getHeight();
        
        TextureRegion[] frames = new TextureRegion[3];
        for(int i = 0; i < 3; i++) {
            frames[i] = new TextureRegion(monsterSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }
        
        // Création de l'animation avec 0.2 seconde par frame
        animation = new Animation<>(0.2f, frames);
        stateTime = 0f;
        
        // Initialisation du sprite avec la première frame
        monster = new Sprite(frames[0]);
        monster.setSize(size * 4, size * 4);
        monster.setPosition(x, y);
    }

    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void draw(SpriteBatch batch) {
        // Mise à jour de la frame actuelle
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        monster.setRegion(currentFrame);
        monster.draw(batch);
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
        monster.setPosition(this.x, this.y);
    }

    public float getX() { 
        return x; }
    
    public float getY() { 
        return y; }
    
    public float getSize() { 
        return size; }
    
    public int getType() { 
        return type; }
    
    public int getStep() { 
        return step; }
    
    public void nextStep() { 
        step++; }
    
    public void resetStep() { 
        step = 1; }
    
    public Sprite returnSprite() { 
        return monster; }

    public void dispose() {
        monsterSheet.dispose();
    }
}