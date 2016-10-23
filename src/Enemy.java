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
	private int hurt;
	private int direction;
	private int speed=1;
	int wait_direction;
	private Texture texture;
	//private World gameWorld;
	
	public Enemy(World newWorld, int newX, int newY, int newType, int newHealth){
	// Number 1
		x = newX;
		y = newY;
		type = newType;
		//gameWorld = newWorld;
		health = newHealth;
		loadData();
		
	}
	
	public void draw(int newWorldX, int newWorldY){
		boolean newHurt=false;
		if(hurt>0)
			newHurt=true;

		draw_texture(texture,1,newHurt);
	}
	public void update(){
		if(wait_direction<=0){
			if(Math.ceil(Math.random()*20)==2){
				direction=(int)Math.floor(Math.random()*5);
				wait_direction=10;
					
			}
			if(direction==1)
				x+=speed;
			if(direction==2)
				x-=speed;
			if(direction==3)
				y+=speed;
			if(direction==4)
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
		hurt--;
		//Preventing any overflowing on an idle enemy
		if(hurt<0)
			hurt=0;
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
	public void setHurt(int newHurt){
		hurt = newHurt;
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
	
	public void recieveDamage(int newDamage){
		health-=newDamage;
		hurt=5;
		if(health<0)
			health=0;
	}
	
	public boolean update(int delta){
		if(health>=0)
			return true;
		return false;
	}
	
	
	
	void loadData(){
		try{
			if(type==0)
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Enemy.png"),GL11.GL_NEAREST);

			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Draws the texture to the screen
	void draw_texture(Texture newTexture, int newScale, boolean newHurt){
			
			newTexture.bind();
			

			
			//GL11.glScalef(1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			
				if(newHurt)
					GL11.glColor3f(1, 0, 0);
				
		   		GL11.glTexCoord2f(0,0);
		   		GL11.glVertex2f(x,y);
				GL11.glTexCoord2f(newScale*1,0);
				GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
				GL11.glTexCoord2f(newScale*1,1);
				GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(x,y+newTexture.getTextureHeight());
				GL11.glColor3f(1, 1, 1);

			    GL11.glEnd();
			 
			
			    
			}
			
		

}
