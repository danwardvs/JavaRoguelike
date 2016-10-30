import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Item {
	
	private int x;
	private int y;
	private String name;
	private int texture_number;
	private Texture texture;
	private Texture texture_2;
	private int[] damage_offset_x = new int[3];
	private int[] damage_offset_y = new int[3];
	private int[] item_offset_x = new int[4];
	private int[] item_offset_y = new int[4];
	private int damage_radius;
	private int damage;
	
	public Item(String newName, int newX, int newY, int newDamageRadius, int newDamage, int newTextureAmount){
		name = newName;
		x = newX;
		y = newY;
		damage_radius=newDamageRadius;
		damage=newDamage;
		texture_number = newTextureAmount;
		setTexture(name,texture_number);
	}
	
	public void setDamageOffset(int newLowerX, int newLowerY,int newMidX, int newMidY, int newHighX, int newHighY){
		
		damage_offset_x[0] = newHighX;
		damage_offset_y[0] = newHighY;
		
		damage_offset_x[2] = newMidX;
		damage_offset_y[2] = newMidY;
		
		damage_offset_x[1] = newLowerX;
		damage_offset_y[1] = newLowerY;
	}
	
	
	public void setItemOffset(int newRestX, int newRestY, int newLowerX, int newLowerY,int newMidX, int newMidY, int newHighX, int newHighY){
		
		item_offset_x[0] = newRestX;
		item_offset_y[0] = newRestY;
		
		item_offset_x[2] = newHighX;
		item_offset_y[2] = newHighY;
		
		item_offset_x[3] = newMidX;
		item_offset_y[3] = newMidY;
		
		item_offset_x[1] = newLowerX;
		item_offset_y[1] = newLowerY;
	}

	public int getDamageOffsetX(int newIndex){
		return damage_offset_x[newIndex];
	}
	
	public int getDamageOffsetY(int newIndex){
		return damage_offset_y[newIndex];
	}
	
	public int getItemOffsetX(int newIndex){
		return item_offset_x[newIndex];
	}
	
	public int getItemOffsetY(int newIndex){
		return item_offset_y[newIndex];
	}
	
	public String getName(){
		return name;
	}
	public void setTexture(String newName, int newAmount){
	
		
		if(newAmount>0){
			texture = loadTexture(newName + ".png");
			
		}
		
		if(newAmount>1){
			texture_2 = loadTexture(newName + "_2.png");
		}
			
		
	}
	public void draw(){
		drawTexture(x,y,texture,1,1);

	}
	
	public void draw(int newX, int newY, boolean newLocal, boolean newSecondary, int newScaleX, int newScaleY){
				
		if(texture != null){
			
			Texture newTexture;
			
			if(newSecondary)
				newTexture=texture_2;
			else
				newTexture=texture;
		
			drawTexture(newX,newY,newTexture,newScaleX,newScaleY);
			
		}
		
	}
	
	
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public int getDamageRadius(){
		return damage_radius;
	}
	
	public int getDamage(){
		return damage;
	}
	public int getTextureNumber(){
		return texture_number;
	}
	
	Texture loadTexture(String newPath){
		Texture newTexture = null;
		try{
			System.out.println(newPath);
			newTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newPath),GL11.GL_NEAREST);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return newTexture;
	}
	
	 //Draws the texture to the screen
	void drawTexture(int newX, int newY, Texture newTexture, int newScaleX, int newScaleY){
			
			newTexture.bind();
			
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
