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
	int speed = 2;
	boolean direction = false;
	int state;
	
	Texture texture_attack_up;
	Texture texture_attack_forward;
	Texture texture_attack_down;
	Texture texture_idle;
	
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
			
			texture_attack_up = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackUp.png"),GL11.GL_NEAREST);

			texture_attack_down = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackDown.png"),GL11.GL_NEAREST);

			texture_attack_forward = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackForward.png"),GL11.GL_NEAREST);
			
			texture_idle = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdle.png"),GL11.GL_NEAREST);

			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public void update(){
		if(Keyboard.isKeyDown(key_left)){
			x-=speed;
			direction=false;
		}
		if(Keyboard.isKeyDown(key_right)){
			x+=speed;
			direction=true;
		}
		
		if(Keyboard.isKeyDown(key_up)){
			y-=speed;
		}
		if(Keyboard.isKeyDown(key_down)){
			y+=speed;
		}
		
		
	}
	
	public void draw(){
		draw_texture(texture_attack_up,direction);
	}
	
	// Draws the texture to the screen
	void draw_texture(Texture newTexture, boolean isFlipped){
		
		newTexture.bind();
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		
		//GL11.glScalef(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		
		
	   		GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(x,y);
	   			if(isFlipped){
			    	GL11.glTexCoord2f(1,0);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
			    	GL11.glTexCoord2f(1,1);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+texture.getTextureHeight());
			    	GL11.glTexCoord2f(0,1);
			    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
	   			}
	   			if(!isFlipped){
	   				GL11.glTexCoord2f(-1,0);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
			    	GL11.glTexCoord2f(-1,1);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+texture.getTextureHeight());
			    	GL11.glTexCoord2f(0,1);
			    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
	   				
	   			}
		    	
		    GL11.glEnd();
		 
		    GL11.glPopMatrix();
		    
		}
		
	
}
