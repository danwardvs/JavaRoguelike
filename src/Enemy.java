import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {
	
	private int SCREEN_W = 320;
	private int SCREEN_H = 240;
	private int x;
	private int y;
	private int type;
	private int world_x;
	private int world_y;
	private int health;
	private int max_health;
	private int hurt_timer;
	private boolean is_hurt;
	private int direction_heading;
	private boolean direction;
	private int speed=1;
	int wait_direction;
	private Texture texture;
	private Texture health_texture;
	private World gameWorld;
	private int attack_delay;
	
	public Enemy(World newWorld, int newX, int newY, int newType, int newHealth, int newMaxHealth){
	// Number 1
		x = newX;
		y = newY;
		type = newType;
		gameWorld = newWorld;
		health = newHealth;
		max_health = newMaxHealth;
		loadData();
		
	}
	
	public void draw(int newWorldX, int newWorldY){
		

		drawTexture(texture,scaleFromDirection(),1);
		drawHealth(health_texture);
	}
	
	public void update(int delta){
		
		if(gameWorld.getgameCharacters[0]!=null){
			
		}
		
		if(hurt_timer>0)
			is_hurt=true;
		else
			is_hurt=false;
		
		hurt_timer-=delta;
		//Preventing any overflowing on an idle enemy
		if(hurt_timer<0)
			hurt_timer=0;
		
		if(attack_delay>1000){
			gameWorld.applyDamage(x,y,1,10,this);
			attack_delay=0;
			
		}
		
		attack_delay+=delta;
		
		if(wait_direction<=0){
			if(Math.ceil(Math.random()*200)==1){
				direction_heading=(int)Math.floor(Math.random()*5);
				wait_direction=100;
					
			}
			if(direction_heading==1)
				x+=speed;
			if(direction_heading==2)
				x-=speed;
			if(direction_heading==3)
				y+=speed;
			if(direction_heading==4)
				y-=speed;
		}
		
		if(x<0+getWidth())
			x=0+getWidth();
		
		if(y<0+getHeight())
			y=0+getHeight();
		
		if(x>SCREEN_W-getWidth())
			x=SCREEN_W-getWidth();
		
		if(y>SCREEN_H-getHeight())
			y=SCREEN_H-getHeight();
		
		
		
		
		wait_direction--;
		
		if(wait_direction<0)
			wait_direction=0;
		
		
	}
	public int getWidth(){
		return texture.getImageWidth();
	}
	public int getHeight(){
		return texture.getImageHeight();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public void setHurtTimer(int newHurt){
		hurt_timer = newHurt;
	}
	public int getWorldX(){
		return world_x;
	}
	public int getWorldY(){
		return world_y;
	}
	public void setWorldX(int newX){
		world_x = newX;
	}
	public void setWorldY(int newY){
		world_y = newY;
	}
	public int getHealth(){
		return health;
	}
	
	public int getType(){
		return type;
	}
	public int getMaxHealth(){
		return max_health;
	}
	int scaleFromDirection(){
		if(direction)
			return -1;
		return 1;
	}
	
	public void recieveDamage(int newDamage){
		health-=newDamage;
		hurt_timer=50;
		if(health<0)
			health=0;
	}

	
	void loadData(){
		try{
			if(type==0)
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Enemy.png"),GL11.GL_NEAREST);

			health_texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("button.png"),GL11.GL_NEAREST);


		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void drawHealth(Texture newTexture){
		
		int width=16;
		int height=4;
		int offset_y=-4;
		// Draws the texture to the screen					
			newTexture.bind();
			
			GL11.glColor3f(0, 0, 0);
			
			GL11.glBegin(GL11.GL_QUADS);

		    	GL11.glVertex2f(x,y+offset_y);

		    	GL11.glVertex2f(x+width,y+offset_y);
		    	GL11.glVertex2f(x+width,y+height+offset_y);
		    	GL11.glVertex2f(x,y+height+offset_y);
		    	
		    GL11.glEnd();

		    GL11.glColor3f(1, 0, 0);

		    
			GL11.glBegin(GL11.GL_QUADS);
		
		    	GL11.glVertex2f(x+1,y+1+offset_y);
		
		    	GL11.glVertex2f(x-1+width,y+1+offset_y);
		    	GL11.glVertex2f(x+width-1,y+height-1+offset_y);
		    	GL11.glVertex2f(x+1,y+height-1+offset_y);
	    	
		    GL11.glEnd();
		    
		    GL11.glColor3f(0, 1, 0);

		    
		 			GL11.glBegin(GL11.GL_QUADS);
		 		
		 		    	GL11.glVertex2f(x+1,y+1+offset_y);
		 		
		 		    	GL11.glVertex2f((int)(x+1)+((width-2)*((float)health/max_health)),y+1+offset_y);
		 		    	GL11.glVertex2f((int)(x+1)+((width-2)*((float)health/max_health)),y+height-1+offset_y);
		 		    	GL11.glVertex2f(x+1,y+height-1+offset_y);
		 		    	
		 		    GL11.glEnd();
		    
			GL11.glColor3f(1, 1, 1);
			
			
		}
		
	
	void drawTexture( Texture newTexture, int newScaleX, int newScaleY){
		
		newTexture.bind();
		
		
		
		GL11.glBegin(GL11.GL_QUADS);
	   			
		if(is_hurt)
			GL11.glColor3f(1, 0, 0);
		
		
	   		GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(x,y);
		    GL11.glTexCoord2f(newScaleX*1,0);
		    GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
		    GL11.glTexCoord2f(newScaleX*1,newScaleY*1);
		    GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
			GL11.glTexCoord2f(0,newScaleY*1);
			GL11.glVertex2f(x,y+newTexture.getTextureHeight());
			
			GL11.glColor3f(1, 1, 1);

	   		
		GL11.glEnd();
		 
	}


		

}
