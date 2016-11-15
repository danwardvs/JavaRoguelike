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
	private int key_left;
	private int key_right;
	private int key_up;
	private int key_down;
	
	private int key_attack_left;
	private int key_attack_right;
	private int key_attack_up;
	private int key_attack_down;
	
	private int attack_timer=0;
	
	private int hit_mark_time=100;
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
	private int health=100;
	private int hurt_timer;
	private boolean is_hurt;
	
	private int width=16;
	private int height=32;
	
	//private int item_offset_x=8;
	//private int item_offset_y=8;
	
	private Item current_item;
	
	private Texture texture_attack_up;
	private Texture texture_attack_forward;
	private Texture texture_attack_down;
	private Texture texture_idle;
	private Texture texture_idle_step;
	
	private Texture hit;
	//private Texture debug;
	
	World gameWorld;
	
	public Character(int newCharacterIndex,int newX, int newY, int newLeft,int newRight, int newUp, int newDown, int newAttackLeft, int newAttackRight, int newAttackUp, int newAttackDown){
		
		x = newX;
		y = newY;
		key_left =  newLeft;
		key_right = newRight;
		key_up = newUp;
		key_down = newDown;
		
		key_attack_down = newAttackDown;
		key_attack_left = newAttackLeft;
		key_attack_right = newAttackRight;
		key_attack_up = newAttackUp;
		
		loadData();
	}
	public void setHealth(int newHealth){
		health = newHealth;
	}
	public int getHealth(){
		return health;
	}
	
	
	
	
	
	
	public void recieveDamage(int newDamage){
		health -= newDamage;
		hurt_timer=50;
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
	public int getWorldX(){
		return world_x;
	}
	public Item getItem(){
		return current_item;
	}
	
	public int getWorldY(){
		return world_y;
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
	public void setItem(Item newItem){
		current_item = newItem;
	}
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	void loadData(){
		try{
			
			texture_attack_up = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackUpwards.png"),GL11.GL_NEAREST);

			texture_attack_down = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackDownwards.png"),GL11.GL_NEAREST);

			texture_attack_forward = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterAttackForward.png"),GL11.GL_NEAREST);
						
			texture_idle = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdle.png"),GL11.GL_NEAREST);
			
			texture_idle_step = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("CharacterIdleStep.png"),GL11.GL_NEAREST);

			hit = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Hit.png"),GL11.GL_NEAREST);
			
			//debug = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("debug.png"),GL11.GL_NEAREST);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	boolean collision(int x_min_1, int x_max_1, int x_min_2, int x_max_2, int y_min_1, int y_max_1, int y_min_2, int y_max_2){
		if(x_min_1 < x_max_2 && y_min_1 < y_max_2 && x_min_2 < x_max_1 && y_min_2 < y_max_1)
			return true;
		else
			return false;
		
	}
	
	void attack(int newState){
		
		state = newState;
		attack_timer=attack_speed;
		key_pressed=true;
		int damage_x=x+8+(scaleFromDirection()*current_item.getDamageOffsetX(state-2));
		int damage_y=y+current_item.getDamageOffsetY(state-2);

		hit_mark_time=0;
		
		hit_mark_x=((x+8-4)+current_item.getDamageOffsetX(state-2)*scaleFromDirection());
		hit_mark_y = (y-4)+current_item.getDamageOffsetY(state-2);
		
		gameWorld.applyDamage(damage_x, damage_y, 1, current_item.getDamage(),this);

	}
	
	public void update(int delta){
		
		if(health<=0){
			gameWorld.destroyCharacter(0);
			gameWorld.getMenu(1).getButtonById("healthbar").setVisibility(false);

		}
				
		for(int i = 0; i < gameWorld.getItems().size(); i++)
		{
			if(collision(x,x+16,gameWorld.getItems().get(i).getX(),gameWorld.getItems().get(i).getX()+16,y,y+32,gameWorld.getItems().get(i).getY(),gameWorld.getItems().get(i).getY()+16)){
				current_item = gameWorld.getItems().get(i);
        		gameWorld.getItems().remove(i);
        		
        	}
		
		}
		
		walk_step++;
		hit_mark_time++;
	
		
		if(hurt_timer>0){
			hurt_timer-=delta;
			is_hurt=true;
		
		}
		else
			is_hurt=false;
		
		
		
		
		// Correcting the 60fps rounding stutter error
		if(delta==17)
			delta=16;
		
		tick_speed=delta*speed;
		
		move_x=0;
		move_y=0;
		if(Keyboard.isKeyDown(key_left)){
			direction=true;

			
			move_x=(int)-tick_speed;
		}
		if(Keyboard.isKeyDown(key_right)){
			move_x=(int)tick_speed;

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
			
			}
		}
		
		gameWorld.getMenu(1).getButtonById("healthbar").setText(String.valueOf(health));
		gameWorld.getMenu(1).getButtonById("healthbar").setWidth(health);
		gameWorld.getMenu(1).getButtonById("healthbar").setColour((1-((float)health/100)),((float)health/100),0);

		
	}
	
	public void draw(){
		
		if(is_hurt)
			GL11.glColor3f(1, 0, 0);
		
		switch(state){
			case 1:
				if(walk_frame<5)
					drawTexture(texture_idle,scaleFromDirection());
				else
					drawTexture(texture_idle_step,scaleFromDirection());
				break;
				
			case 2:drawTexture(texture_attack_up,scaleFromDirection());
				break;
			case 3:drawTexture(texture_attack_down,scaleFromDirection());
				break;
			case 4:drawTexture(texture_attack_forward,scaleFromDirection());
				break;
		}
		
		
		if(current_item!=null){
			
			if(current_item.getTextureNumber()>0){
				int newState=state-1;

				current_item.draw(x+current_item.getItemOffsetX(newState)*scaleFromDirection(),y+current_item.getItemOffsetY(newState),true,imageFromState(),scaleFromDirection(),scaleFromState());
			}
		}
		
		if(hit_mark_time<10){
			drawTexture(hit,1,hit_mark_x,hit_mark_y);

			
		}
		
		GL11.glColor3f(1, 1, 1);

		//drawTexture(debug);
		
	}
	
	int scaleFromDirection(){
		if(direction)
			return -1;
		return 1;
	}
	int scaleFromState(){
		if(state==3)
			return -1;
		return 1;
	}
	boolean imageFromState(){
		if(state==4)
			return true;
		return false;
	}

	void drawTexture(Texture newTexture,int newScale, int newX, int newY){
		
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
	void drawTexture(Texture newTexture,int newScale){
		
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
	void drawTexture(Texture newTexture){
		
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
