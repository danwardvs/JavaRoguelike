package myGame;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.util.List;


public class Item {
	
	int x;
	int y;
	String name;
	int type;
	int width;
	int height;
	int texture_number;
	Texture texture;
	Texture texture_2;
	int bounding_y;
	int[] damage_offset_x = new int[3];
	int[] damage_offset_y = new int[3];
	int[] item_offset_x = new int[4];
	int[] item_offset_y = new int[4];
	int damage_radius;
	int damage;

	
	
	public Item(String newName, int newType, int newX, int newY, int newTextureAmount){
		name = newName;
		type = newType;
		x = newX;
		y = newY;
		texture_number = newTextureAmount;
		setTexture(name,texture_number);
		
	}
	
	/*public Item(String newName, int newType, int newDamageRadius, int newDamage, int newTextureAmount){
		name = newName;
		type = newType;
		damage_radius=newDamageRadius;
		damage=newDamage;
		texture_number = newTextureAmount;
		setTexture(name,texture_number);
	}*/
	
	public Item(String newName, int newType, int newX, int newY, int newTextureAmount, boolean isAProp){
		type = newType;
		texture_number = newTextureAmount;
		x = newX;
		y = newY;
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
	
	public void setDamage(int newDamage){
		damage = newDamage;
	}
	
	public void setDamageRadius(int newDamageRadius){
		damage_radius = newDamageRadius;
	}
	
	public int getType(){
		return type;
	}
	
	//Haxx to use polymorphism...
	//Pls to help
	public String loadDialog(){
		return "";
	}
	
	public String getBaseReply(){
		return "";
	}
	public void setBaseReply(String how_do_i_actally_polymorph){
		
	}
	public void addDialog(String plz_to_help){
		
	}
	public List<String> getDialog(){
		return null;
	}
	
	public void setBoundingY(int newY){
		bounding_y=newY;
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
		if(texture!=null)
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
	
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	public void setHeight(int newHeight){
		height = newHeight;
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
	public int getHeight(){
		if(texture_number==0|| (texture_number != 0 && type==3))
			return height;
		return texture.getImageHeight();
	}
	
	public int getWidth(){
		if(texture_number==0 || (texture_number != 0 && type==3))
			return width;
		return texture.getImageWidth();
	}
	
	public int getBoundingX(){
		return x;
	}
	
	public int getBoundingY(){
		if(type==4)
			return y+getHeight()-8;
		if(type==3)
			return y+bounding_y;
		return y;
	}
	
	public int getLocalBoundingY(){
		return bounding_y;
	}
	
	public int getBoundingWidth(){
		return getWidth();
	}
	
	public int getBoundingHeight(){
		if(type==4)
			return 0;
		return getHeight();
	}
	
	
	public int getTextureNumber(){
		return texture_number;
	}
	
	Texture loadTexture(String newPath){
		Texture newTexture = null;
		try{
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
