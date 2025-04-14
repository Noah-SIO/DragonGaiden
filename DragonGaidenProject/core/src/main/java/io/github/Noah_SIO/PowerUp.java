package io.github.Noah_SIO;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUp {
    private float x;
    private float y;
    private float largeur;
    private float hauteur;
    Texture powerImg;
    Sprite power;
    private int type;

    public PowerUp(float x, float y, float largeur, float hauteur, int type){
        this.x=x;
        this.y=y;
        this.hauteur=hauteur;
        this.largeur=largeur;
        if(type==1){
        powerImg = new Texture("speed.png");
        }if(type==2){
        powerImg = new Texture("morepoint.png");
        }if(type==3){
        powerImg = new Texture("morehealth.png");
        }
        this.type = type;
        power = new Sprite(powerImg);
        power.setSize(largeur * 4, hauteur * 4);
        power.setPosition(x, y);
    }



    public void move(float x, float y){
        this.x += x;
        this.y += y;
        power.setPosition(this.x, this.y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getType(){
        return type;
    }

    public void draw(SpriteBatch batch){
        power.draw(batch);
    }

    public Sprite returnSprite(){
        return power;
    }
}
