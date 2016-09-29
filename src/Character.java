import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character {
	private int x=0;
	private int y=0;
	private int world_x;
	private int world_y;
	private int character_index;
	private int key_left;
	private int key_right;
	private int key_up;
	private int key_down;
	
	private int key_attack_left;
	private int key_attack_right;
	private int key_attack_up;
	private int key_attack_down;
	
	private int attack_timer=0;
	
	private int hit_mark_time=0;
	private int hit_mark_x;
	private int hit_mark_y;
	
	private int attack_speed=100;
	private float speed = 0.0625f;
	private int walk_step=0;
	private int walk_step_speed=0;
	private int walk_frame=0;
	private int move_x=0;
	private int move_y=0;
	private float tick_speed;
	private boolean direction = false;
	private boolean key_pressed=false;
	private int state=1;
	
	private int item_offset_x=8;
	private int item_offset_y=8;
	
	private Item current_item;
	
	private Texture texture_attack_up;
	private Texture texture_attack_forward;
	private Texture texture_attack_down;
	private Texture texture_idle;
	private Texture texture_idle_step;
	
	private Texture hit;
	private Texture debug;
	
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

			hit = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Hit.png"),GL11.GL_NEAREST);
			
			debug = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("debug.png"),GL11.GL_NEAREST);



		
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
	
	void attack(int newState){
		
		state = newState;
		attack_timer=attack_speed;
		key_pressed=true;
		int damage_x=x+8+(scale_from_direction()*current_item.getDamageOffsetX(state-2));
		int damage_y=y+current_item.getDamageOffsetY(state-2);
		
		gameWorld.apply_damage(damage_x, damage_y, 1, current_item.getDamage());
		
		System.out.println(damage_x);
		System.out.println(x);

		
		hit_mark_time=0;
		//hit_mark_x = x+8+scale_from_direction()*current_item.getDamageOffsetX(state-2);
		System.out.println(damage_x + ":" + damage_y);
		
		hit_mark_x=((x+8-4)+current_item.getDamageOffsetX(state-2)*scale_from_direction());
		hit_mark_y = (y-4)+current_item.getDamageOffsetY(state-2);

		
	}
	
	public void update(int delta){
				
		for(int j = 0; j < gameWorld.getItems().size(); j++)
		{
			if(collision(x,x+16,gameWorld.getItems().get(j).getX(),gameWorld.getItems().get(j).getX()+16,y,y+32,gameWorld.getItems().get(j).getY(),gameWorld.getItems().get(j).getY()+16)){
        		if(world_x==gameWorld.getItems().get(j).getWorldX() && world_y==gameWorld.getItems().get(j).getWorldY()){
				
					
					current_item = gameWorld.getItems().get(j);
        			gameWorld.getItems().remove(j);
        		}
        	}
		
		
		}
		
		
		walk_step++;
		hit_mark_time++;
		
		// Correcting the 60fps rounding stutter error
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
		
		
		if(Keyboard.isKeyDown(key_attack_left) && attack_timer==0 && !key_pressed && move_x<=0){
			direction=true;
			attack(4);
		
		}
		
		if(Keyboard.isKeyDown(key_attack_right) && attack_timer==0 && !key_pressed && move_x>=0){
			
			direction=false;
			attack(4);
	
	
		}
		
		if(Keyboard.isKeyDown(key_attack_up) && attack_timer==0 && !key_pressed){
			
			attack(2);

		}
		
		if(Keyboard.isKeyDown(key_attack_down) && attack_timer==0 && !key_pressed){
			
			attack(3);

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
				
				item_offset_x=scale_from_direction()*8;
				item_offset_y=10;
			
			}
		}
		
	}
	int scale_from_direction(){
		if(direction)
			return -1;
		return 1;
	}
	int scale_from_state(){
		if(state==3)
			return -1;
		return 1;
	}
	boolean image_from_state(){
		if(state==4)
			return true;
		return false;
	}
	
	public void draw(){
		
		switch(state){
			case 1:
				if(walk_frame<5)
					draw_texture(texture_idle,scale_from_direction());
				else
					draw_texture(texture_idle_step,scale_from_direction());
				break;
				
			case 2:draw_texture(texture_attack_up,scale_from_direction());
				break;
			case 3:draw_texture(texture_attack_down,scale_from_direction());
				break;
			case 4:draw_texture(texture_attack_forward,scale_from_direction());
				break;
		}
		
		
		if(current_item!=null){
			int newState=state-1;
		

			current_item.draw(x+current_item.getItemOffsetX(newState)*scale_from_direction(),y+current_item.getItemOffsetY(newState),true,image_from_state(),scale_from_direction(),scale_from_state());

		}
		
		if(hit_mark_time<10){
			draw_texture(hit,1,hit_mark_x,hit_mark_y);

			
		}
		//draw_texture(debug);
		
	}
	
	void draw_texture(Texture newTexture,int newScale, int newX, int newY){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);

	   				GL11.glTexCoord2f(0,0);
	   		   		GL11.glVertex2f(newX,newY);
			    	GL11.glTexCoord2f(newScale*1,0);
			    	GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY);
			    	GL11.glTexCoord2f(newScale*1,1);
			    	GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY+newTexture.getTextureHeight());
			    	GL11.glTexCoord2f(0,1);
			    	GL11.glVertex2f(newX,newY+newTexture.getTextureHeight());
		    	
		    GL11.glEnd();
		     
		}
	
	// Draws the texture to the screen
	void draw_texture(Texture newTexture,int newScale){
		
		newTexture.bind();
		
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
	
	// Draws the texture to the screen
	void draw_texture(Texture newTexture){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
	   				GL11.glTexCoord2f(0,0);
	   		   		GL11.glVertex2f(x,y);
			    	GL11.glTexCoord2f(1,0);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
			    	GL11.glTexCoord2f(1,1);
			    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
			    	GL11.glTexCoord2f(0,1);
			    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());	
		    GL11.glEnd();
	 
		}
		
	
}
