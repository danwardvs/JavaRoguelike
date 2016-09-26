import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Item {
	
	private int x;
	private int y;
	private String name;
	private Texture texture;
	private Texture texture_2;
	private int world_x;
	private int world_y;
	private int[] damage_offset_x = new int[3];
	private int[] damage_offset_y = new int[3];
	private int damage_radius;
	private int damage;
	
	public Item(String newName, int newX, int newY, int newWorldX, int newWorldY, int newDamageRadius, int newDamage){
		name = newName;
		x = newX;
		y = newY;
		world_x = newWorldX;
		world_y = newWorldY;
		damage_radius=newDamageRadius;
		damage=newDamage;
		load_images(name);
	}
	
	public void setDamageOffset(int newLowerX, int newLowerY,int newMidX, int newMidY, int newHighX, int newHighY){
		
		damage_offset_x[0] = newHighX;
		damage_offset_y[0] = newHighY;
		
		damage_offset_x[2] = newMidX;
		damage_offset_y[2] = newMidY;
		
		damage_offset_x[1] = newLowerX;
		damage_offset_y[1] = newLowerY;
	}
	public int getDamageOffsetX(int newIndex){
		return damage_offset_x[newIndex];
	}
	
	public int getDamageOffsetY(int newIndex){
		return damage_offset_y[newIndex];
	}
	
	public String getName(){
		return name;
	}
	
	
	public void draw(int newX, int newY, boolean newLocal, boolean newSecondary, int newScaleX, int newScaleY){
		Texture newTexture;
		
		if(newSecondary)
			newTexture=texture_2;
		else
			newTexture=texture;
		if(newX==world_x && newY==world_y && !newLocal)
			draw_texture(x,y,newTexture,newScaleX,newScaleY);
		else if(newLocal){
			draw_texture(newX,newY,newTexture,newScaleX,newScaleY);
		}
		
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
	public int getDamageRadius(){
		return damage_radius;
	}
	
	public int getDamage(){
		return damage;
	}
	
	void load_images(String newName){
		try{
			
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newName + ".png"),GL11.GL_NEAREST);
			texture_2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newName + "_2.png"),GL11.GL_NEAREST);

			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 //Draws the texture to the screen
	void draw_texture(int newX, int newY, Texture newTexture, int newScaleX, int newScaleY){
			
			newTexture.bind();
			
			int newScale=0;
			//GL11.glScalef(1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			
		   			
		   				
		   		
		   			
		   			GL11.glTexCoord2f(0,0);
		   		   	GL11.glVertex2f(newX,newY);
		   				
		   				
				    GL11.glTexCoord2f(newScaleX*1,0);
				    GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY);
				    GL11.glTexCoord2f(newScaleX*1,newScaleY*1);
				    GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY+newTexture.getTextureHeight());
				    GL11.glTexCoord2f(0,newScaleY*1);
				    GL11.glVertex2f(newX,newY+newTexture.getTextureHeight());
		   		
			    	
			    GL11.glEnd();
			 
			
			    
			}
	
}
