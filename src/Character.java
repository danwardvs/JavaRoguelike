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
	
	int key_attack_left;
	int key_attack_right;
	int key_attack_up;
	int key_attack_down;
	
	int attack_timer=0;
	
	int speed = 2;
	boolean direction = false;
	int state=1;
	
	Texture texture_attack_up;
	Texture texture_attack_forward;
	Texture texture_attack_down;
	Texture texture_idle;
	
	public Character(int newCharacterIndex,int newLeft,int newRight, int newUp, int newDown, int newAttackLeft, int newAttackRight, int newAttackUp, int newAttackDown){
		character_index = newCharacterIndex;
		key_left =  newLeft;
		key_right = newRight;
		key_up = newUp;
		key_down = newDown;
		
		key_attack_down = newAttackDown;
		key_attack_left = newAttackLeft;
		key_attack_right = newAttackRight;
		key_attack_up = newAttackUp;
		
		load_images();
		
	}
	void load_images(){
		try{
			
			texture_attack_up = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackUpwards.png"),GL11.GL_NEAREST);

			texture_attack_down = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackDownwards.png"),GL11.GL_NEAREST);

			texture_attack_forward = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackForward.png"),GL11.GL_NEAREST);
						
			texture_idle = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdle.png"),GL11.GL_NEAREST);

			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int newX){
		x = newX;
	}
	
	public void setY(int newY){
		y = newY;
	}
	
	public void update(int delta){
		if(Keyboard.isKeyDown(key_left)){
			x-=speed;
			direction=true;
		}
		if(Keyboard.isKeyDown(key_right)){
			x+=speed;
			direction=false;
		}
		
		if(Keyboard.isKeyDown(key_up)){
			y-=speed;
		}
		if(Keyboard.isKeyDown(key_down)){
			y+=speed;
		}
		if(Keyboard.isKeyDown(key_attack_left) && attack_timer==0){
			direction=true;
			state=4;
			attack_timer=1000;
		}
		
		if(Keyboard.isKeyDown(key_attack_right) && attack_timer==0){
			direction=false;
			state=4;
			attack_timer=1000;
		}
		
		if(Keyboard.isKeyDown(key_attack_up) && attack_timer==0){
			
			state=2;
			attack_timer=1000;
		}
		
		if(Keyboard.isKeyDown(key_attack_down) && attack_timer==0){
		
			state=3;
			attack_timer=1000;
		}
		
		if(state!=1){
			if(attack_timer>0)
				attack_timer-=delta;
			if(attack_timer<=0){
				attack_timer=0;
				state=1;
			}
		}
		
		
	}
	
	public void draw(){
		
		switch(state){
			case 1:draw_texture(texture_idle,direction);
				break;
			case 2:draw_texture(texture_attack_up,direction);
				break;
			case 3:draw_texture(texture_attack_down,direction);
				break;
			case 4:draw_texture(texture_attack_forward,direction);
				break;
		}
		
	}
	
	// Draws the texture to the screen
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
