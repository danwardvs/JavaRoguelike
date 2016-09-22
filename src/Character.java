import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character {
	int x=0;
	int y=0;
	int world_x;
	int world_y;
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
	
	int attack_speed=100;
	float speed = 0.0625f;
	int walk_step=0;
	int walk_step_speed=0;
	int walk_frame=0;
	int move_x=0;
	int move_y=0;
	float tick_speed;
	boolean direction = false;
	boolean key_pressed=false;
	int state=1;
	
	int item_offset_x=8;
	int item_offset_y=8;
	
	Item current_item;
	
	Texture texture_attack_up;
	Texture texture_attack_forward;
	Texture texture_attack_down;
	Texture texture_idle;
	Texture texture_idle_step;
	
	World gameWorld;
	
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
	boolean collision(int x_min_1, int x_max_1, int x_min_2, int x_max_2, int y_min_1, int y_max_1, int y_min_2, int y_max_2){
		if(x_min_1 < x_max_2 && y_min_1 < y_max_2 && x_min_2 < x_max_1 && y_min_2 < y_max_1)
			return true;
		else
			return false;
		
	}
	public void setWorld(World newGameWorld){
		gameWorld = newGameWorld;
	}
	public void setWorldX(int newWorldX){
		world_x = newWorldX;
	}
	
	public void setWorldY(int newWorldY){
		world_y = newWorldY;
	}
	
	
	void load_images(){
		try{
			
			texture_attack_up = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackUpwards.png"),GL11.GL_NEAREST);

			texture_attack_down = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackDownwards.png"),GL11.GL_NEAREST);

			texture_attack_forward = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackForward.png"),GL11.GL_NEAREST);
						
			texture_idle = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdle.png"),GL11.GL_NEAREST);
			
			texture_idle_step = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdleStep.png"),GL11.GL_NEAREST);

			

		
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
		
		for(int j = 0; j < gameWorld.gameItems.size(); j++)
		{
			if(collision(x,x+16,gameWorld.gameItems.get(j).getX(),gameWorld.gameItems.get(j).getX()+16,y,y+32,gameWorld.gameItems.get(j).getY(),gameWorld.gameItems.get(j).getY()+16)){
        		if(world_x==gameWorld.gameItems.get(j).getWorldX() && world_y==gameWorld.gameItems.get(j).getWorldY()){
        			current_item = gameWorld.gameItems.get(j);
        			gameWorld.gameItems.remove(j);
        			System.out.println("True");
        		}
        	}
		
		
		}
		
		
		walk_step++;
		if(delta==17)
			delta=16;
		tick_speed=delta*speed;
		
		move_x=0;
		move_y=0;
		if(Keyboard.isKeyDown(key_left)){
			direction=true;
			if(attack_timer==0){
				item_offset_x=-10;
				item_offset_y=10;
			}
			
			move_x=(int)-tick_speed;
		}
		if(Keyboard.isKeyDown(key_right)){
			move_x=(int)tick_speed;
			if(attack_timer==0){
				item_offset_x=10;
				item_offset_y=10;
			}
			direction=false;
		}
		
		if(Keyboard.isKeyDown(key_up)){
			move_y=(int)-tick_speed;
		
		}
		
		if(Keyboard.isKeyDown(key_down)){
			move_y=(int)tick_speed;
		
		}
		if(walk_step>=walk_step_speed){
			x+=move_x;
			y+=move_y;
			walk_step=0;
			
			
		}
		
		
		if(Keyboard.isKeyDown(key_attack_left) && attack_timer==0 && !key_pressed){
			direction=true;
			state=4;
			item_offset_x=-12;
			item_offset_y=12;
			attack_timer=attack_speed;
			key_pressed=true;
		}
		
		if(Keyboard.isKeyDown(key_attack_right) && attack_timer==0 && !key_pressed){
			direction=false;
			state=4;
			item_offset_x=12;
			item_offset_y=12;
			attack_timer=attack_speed;
			key_pressed=true;
		}
		
		if(Keyboard.isKeyDown(key_attack_up) && attack_timer==0 && !key_pressed){
			
			if(direction){
				item_offset_x=-12;
				item_offset_y=2;
			}
			else{
				item_offset_x=12;
				item_offset_y=2;
			}
			state=2;
			attack_timer=attack_speed;
			key_pressed=true;
		}
		
		if(Keyboard.isKeyDown(key_attack_down) && attack_timer==0 && !key_pressed){
			
			if(direction){
				item_offset_x=-12;
				item_offset_y=18;
			}
			else{
				item_offset_x=12;
				item_offset_y=18;
			}
			state=3;
			attack_timer=attack_speed;
			key_pressed=true;
		}
		
		if(!Keyboard.isKeyDown(key_attack_down) && !Keyboard.isKeyDown(key_attack_up)  && !Keyboard.isKeyDown(key_attack_left)  && !Keyboard.isKeyDown(key_attack_right) ){
			key_pressed=false;
		}
		
		
		if(move_x!=0 || move_y!=0){
			walk_frame++;
			if(walk_frame==11)
				walk_frame=0;
		}else
			walk_frame=0;	
			
		if(state!=1){
			if(attack_timer>0)
				attack_timer-=delta;
			if(attack_timer<=0){
				attack_timer=0;
				state=1;
				if(direction){
					item_offset_x=-8;
					item_offset_y=10;
				}
				
				else{
					item_offset_x=8;
					item_offset_y=10;
				}
			}
		}
		
	}
	
	public void draw(){
		
		switch(state){
			case 1:
				if(walk_frame<5)
					draw_texture(texture_idle,direction);
				else
					draw_texture(texture_idle_step,direction);
				break;
				
			case 2:draw_texture(texture_attack_up,direction);
				break;
			case 3:draw_texture(texture_attack_down,direction);
				break;
			case 4:draw_texture(texture_attack_forward,direction);
				break;
		}
		if(current_item!=null){
			if(state==4)
				current_item.draw(x+item_offset_x,y+item_offset_y,true,direction,true);
			else
				current_item.draw(x+item_offset_x,y+item_offset_y,true,direction,false);

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
