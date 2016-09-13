import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character {
	int x=0;
	int y=0;
	int character_index;
	int key_left;
	int key_right;
	int key_up;
	int key_down;
	
	Texture texture;
	
	public Character(int newCharacterIndex,int newLeft,int newRight, int newUp, int newDown){
		character_index = newCharacterIndex;
		key_left =  newLeft;
		key_right = newRight;
		key_up = newUp;
		key_down = newDown;
		load_images();
		
	}
	void load_images(){
		try{
			
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackForward.png"),GL11.GL_NEAREST);
			//texture.setTextureFilter(arg0);
			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public void update(){
		if(Keyboard.isKeyDown(key_left)){
			x-=5;
		}
		if(Keyboard.isKeyDown(key_right)){
			x+=5;
		}
		
		if(Keyboard.isKeyDown(key_up)){
			y-=5;
		}
		if(Keyboard.isKeyDown(key_down)){
			y+=5;
		}
		
		
	}
	
	public void draw(){
		draw_texture(texture);
	}
	
	// Draws the texture to the screen
	void draw_texture(Texture newTexture){
		
		texture.bind();
		            
		GL11.glBegin(GL11.GL_QUADS);
	   		GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(x,y);
		    	GL11.glTexCoord2f(1,0);
		    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
		    	GL11.glTexCoord2f(1,1);
		    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+texture.getTextureHeight());
		    	GL11.glTexCoord2f(0,1);
		    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
		    GL11.glEnd();
		 }	
	
}
