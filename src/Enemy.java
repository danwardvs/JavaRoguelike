import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {
	
	int x;
	int y;
	int type;
	int world_x;
	int world_y;
	int health;
	int hurt;
	int direction;
	int speed=1;
	int wait_direction;
	Texture texture;
	World gameWorld;
	
	public Enemy(World newWorld, int newX, int newY, int newWorldX, int newWorldY, int newType, int newHealth){
	// Number 1
		x = newX;
		y = newY;
		type = newType;
		world_x = newWorldX;
		world_y = newWorldY;
		gameWorld = newWorld;
		health = newHealth;
		load_images();
		System.out.println(x + ":" + y);
		
	}
	
	public void draw(int newWorldX, int newWorldY){
		boolean newHurt=false;
		if(hurt>0)
			newHurt=true;
		if(newWorldX==world_x && newWorldY==world_y)
			draw_texture(texture,1,newHurt);
	}
	public void update(){
		if(wait_direction<=0){
			if(Math.ceil(Math.random()*20)==2){
				direction=(int)Math.floor(Math.random()*5);
				wait_direction=200;
					
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
		hurt--;
		wait_direction--;
		if(hurt<0)
			hurt=0;
		
		
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
	public int getWorldX(){
		return world_x;
	}
	public void setHurt(int newHurt){
		hurt = newHurt;
	}
	
	public int getWorldY(){
		return world_y;
	}
	public int getHealth(){
		return health;
	}
	
	public void recieveDamage(int newDamage){
		health-=newDamage;
		hurt=10;
		if(health<0)
			health=0;
	}
	
	public boolean update(int delta){
		if(health>=0)
			return true;
		return false;
	}
	
	
	
	void load_images(){
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
