import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Item {
	
	int x;
	int y;
	String name;
	Texture texture;
	int world_x;
	int world_y;
	
	public Item(String newName, int newX, int newY, int newWorldX, int newWorldY){
		name = newName;
		x = newX;
		y = newY;
		world_x = newWorldX;
		world_y = newWorldY;
		load_images(name);
	}
	
	
	public void draw(int newWorldX, int newWorldY){
		if(newWorldX==world_x && newWorldY==world_y)
			draw_texture(texture,false);
	}
	
	void load_images(String newName){
		try{
			
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newName + ".png"),GL11.GL_NEAREST);

			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 //Draws the texture to the screen
	void draw_texture(Texture newTexture, boolean isFlipped){
			
			newTexture.bind();
			
			
			//GL11.glScalef(1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			
		   	
		   		
		   			if(!isFlipped){
		   				GL11.glTexCoord2f(0,0);
		   		   		GL11.glVertex2f(x,y);
		   				
		   				
				    	GL11.glTexCoord2f(1,0);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
				    	GL11.glTexCoord2f(1,1);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
				    	GL11.glTexCoord2f(0,1);
				    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
		   			}
		   			if(isFlipped){
		   				GL11.glTexCoord2f(0,0);
		   		   		GL11.glVertex2f(x,y);
		   				
		   				
		   				GL11.glTexCoord2f(-1,0);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
				    	GL11.glTexCoord2f(-1,1);
				    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
				    	GL11.glTexCoord2f(0f,1);
				    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
		   				
		   			}
			    	
			    GL11.glEnd();
			 
			
			    
			}
	
}
