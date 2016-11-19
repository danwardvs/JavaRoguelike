import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Particle {
	
	private int x;
	private int y;
	private int lifetime;
	
	Texture texture;
	
	public Particle(int newX, int newY, String newType, int newLifetime){
		
		x = newX;
		y = newY;
		lifetime = newLifetime; 
		texture = loadTexture(newType+".png");
		
	}
	
	public int getLifetime(){
		return lifetime;
	}
	
	public void update(int delta){
		
		lifetime-=delta;
		
	}
	public void draw(){
		
		drawTexture(x,y,texture, 1,1);
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
