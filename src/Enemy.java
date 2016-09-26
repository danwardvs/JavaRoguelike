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
	Texture texture;
	
	public Enemy(int newX, int newY, int newWorldX, int newWorldY, int newType){
	// Number 1
		x = newX;
		y = newY;
		type = newType;
		world_x = newWorldX;
		world_y = newWorldY;
		load_images();
		
	}
	
	public void draw(int newWorldX, int newWorldY){
		if(newWorldX==world_x && newWorldY==world_y)
			draw_texture(texture,1);
	}
	public void update(){
		
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
	
	public int getWorldY(){
		return world_y;
	}
	
	public void update(int delta){
		
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
	void draw_texture(Texture newTexture, int newScale){
			
			newTexture.bind();
			
			
			//GL11.glScalef(1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			
		   	
		   		
		   			
		   				GL11.glTexCoord2f(0,0);
		   		   		GL11.glVertex2f(x,y);
		   				
		   				
				    	GL11.glTexCoord2f(newScale*1,0);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
				    	GL11.glTexCoord2f(newScale*1,1);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
				    	GL11.glTexCoord2f(0,1);
				    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
		   			
		   		
			    	
			    GL11.glEnd();
			 
			
			    
			}
			
		

}