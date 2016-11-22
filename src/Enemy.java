import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {
	
	private int SCREEN_W = 320;
	private int SCREEN_H = 240;
	private float x;
	private float y;
	private int type;
	private int world_x;
	private int world_y;
	private int health;
	private int max_health;
	private int hurt_timer;
	private boolean is_hurt;
	private int wander_direction_heading;
	private int wander_delay;
	private boolean direction_facing;
	private int x_direction;
	private int y_direction;
	private Texture texture;
	private Texture health_texture;
	private World gameWorld;
	private int attack_delay_timer;
	private float delta_speed;
	
	private float behavior_speed=0.010f;
	private int behavior_attack_delay=2000;
	private boolean behavior_wander=false;
	private boolean behavior_follow=true;
	private boolean behavior_face_character=true;
	private int behavior_wander_amount=200;
	private int behavior_follow_tolerance=10;
	private int behavior_wander_delay=500;

	
	public Enemy(World newWorld, int newX, int newY, int newType, int newHealth, int newMaxHealth){
	// Number 1
		x = newX;
		y = newY;
		type = newType;
		gameWorld = newWorld;
		health = newHealth;
		max_health = newMaxHealth;
		loadData();
		
	}
	

	boolean collision(int x_min_1, int x_max_1, int x_min_2, int x_max_2, int y_min_1, int y_max_1, int y_min_2, int y_max_2){
		if(x_min_1 < x_max_2 && y_min_1 < y_max_2 && x_min_2 < x_max_1 && y_min_2 < y_max_1)
			return true;
		else
			return false;
		
	}
	
	public void draw(int newWorldX, int newWorldY){

		drawTexture(texture,scaleFromDirection(),1);
		drawHealth(health_texture);
	}
	
	
	private boolean tryMoveX(float newX){
		
		x+=newX;
		
		for(int i = 0; i < gameWorld.getItems().size(); i++){
			if(collision((int)x,(int)x+getWidth(),gameWorld.getItems().get(i).getX(),gameWorld.getItems().get(i).getX()+gameWorld.getItems().get(i).getWidth(),(int)y,(int)y+getHeight(),gameWorld.getItems().get(i).getY(),gameWorld.getItems().get(i).getY()+gameWorld.getItems().get(i).getHeight())){
				if(gameWorld.getItems().get(i).getType()==3){
					x-=newX;
		
					return false;
					
				}

				
				
			}
		}
		return true;
		
	}
	
	private boolean tryMoveY(float newY){
		
		y+=newY;
		
		for(int i = 0; i < gameWorld.getItems().size(); i++){
			if(collision((int)x,(int)x+getWidth(),gameWorld.getItems().get(i).getX(),gameWorld.getItems().get(i).getX()+gameWorld.getItems().get(i).getWidth(),(int)y,(int)y+getHeight(),gameWorld.getItems().get(i).getY(),gameWorld.getItems().get(i).getY()+gameWorld.getItems().get(i).getHeight())){
				if(gameWorld.getItems().get(i).getType()==3){
					y-=newY;
					return false;
				}

				
				
			}
		}
		return true;
		
	}
	
	
	public void update(int delta){
		
		
		
		delta_speed=delta*behavior_speed;
		if(gameWorld.getCharacter(0)!=null){
		
			if(behavior_face_character){
			
				if(gameWorld.getCharacter(0).getX()>x+5)
					direction_facing=true;
				else if(gameWorld.getCharacter(0).getX()<x-5)
					direction_facing=false;
				}
			
			
		
		if(gameWorld.getCharacter(0)!=null){
			
			if(behavior_follow){
				if(gameWorld.getCharacter(0).getX()>x+behavior_follow_tolerance){
					x_direction=2;
				}
				else if(gameWorld.getCharacter(0).getX()<x-behavior_follow_tolerance){
					x_direction=1;
				}
				else 
					x_direction=0;
				
				if(gameWorld.getCharacter(0).getY()>y+behavior_follow_tolerance)
					y_direction=2;
				else if(gameWorld.getCharacter(0).getY()<y-behavior_follow_tolerance)
					y_direction=1;
				else 
					y_direction=0;
			}
		}
	}
		
		if(attack_delay_timer>behavior_attack_delay){

			gameWorld.applyDamage(((int)x+getHalfWidth()-4)-8*scaleFromDirection(),(int)y,10,10,this);
			gameWorld.createParticle(new Particle(((int)x+getHalfWidth()-4)-8*scaleFromDirection(),(int)y+10,"Hit",100));
			attack_delay_timer=0;
			
		}
		
		if(hurt_timer>0)
			is_hurt=true;
		else
			is_hurt=false;
		
		hurt_timer-=delta;
		//Preventing any overflowing on an idle enemy
		if(hurt_timer<0)
			hurt_timer=0;
		
		
		
		attack_delay_timer+=delta;
		
	
		if(behavior_wander){
			
			if(wander_delay<=0){
				if(Math.ceil(Math.random()*behavior_wander_amount)==1){
					wander_direction_heading=(int)Math.floor(Math.random()*5);
					wander_delay=behavior_wander_delay;
	
				}
				if(wander_direction_heading==1)
					tryMoveX(delta_speed);
				if(wander_direction_heading==2)
					tryMoveX(-delta_speed);

				if(wander_direction_heading==3)
					tryMoveY(delta_speed);

				if(wander_direction_heading==4)
					tryMoveY(-delta_speed);
			}else
				wander_delay-=delta;
		}
		
		else if(behavior_follow){
			if(x_direction==2)
				tryMoveX(delta_speed);		

				
			if(x_direction==1)
				tryMoveX(-delta_speed);		

				
			if(y_direction==2)
				tryMoveY(delta_speed);

				
			if(y_direction==1)
				tryMoveY(-delta_speed);

		}
			
			
		
		
		if(x<0+getWidth())
			x=0+getWidth();
		
		if(y<0+getHeight())
			y=0+getHeight();
		
		if(x>SCREEN_W-getWidth())
			x=SCREEN_W-getWidth();
		
		if(y>SCREEN_H-getHeight())
			y=SCREEN_H-getHeight();
		
	
		
		
	}
	public int getWidth(){
		return texture.getImageWidth();
	}
	public int getHeight(){
		return texture.getImageHeight();
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	public void setHurtTimer(int newHurt){
		hurt_timer = newHurt;
	}
	
	public int getWorldX(){
		return world_x;
	}
	public int getWorldY(){
		return world_y;
	}
	public void setWorldX(int newX){
		world_x = newX;
	}
	public void setWorldY(int newY){
		world_y = newY;
	}
	public int getHealth(){
		return health;
	}
	
	public int getType(){
		return type;
	}
	public int getMaxHealth(){
		return max_health;
	}
	int scaleFromDirection(){
		if(direction_facing)
			return -1;
		return 1;
	}
	
	private int getHalfWidth(){
		return texture.getImageWidth()/2;
		
	}
	
	public void recieveDamage(int newDamage){
		health-=newDamage;
		hurt_timer=50;
		if(health<0)
			health=0;
	}

	
	void loadData(){
		try{
			if(type==0)
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Enemy.png"),GL11.GL_NEAREST);

			health_texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("button.png"),GL11.GL_NEAREST);


		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void drawHealth(Texture newTexture){
		
		int width=16;
		int height=4;
		int offset_y=-4;
		// Draws the texture to the screen					
			newTexture.bind();
			
			GL11.glColor3f(0, 0, 0);
			
			GL11.glBegin(GL11.GL_QUADS);

		    	GL11.glVertex2f((int)x,(int)y+offset_y);

		    	GL11.glVertex2f((int)x+width,(int)y+offset_y);
		    	GL11.glVertex2f((int)x+width,(int)y+height+offset_y);
		    	GL11.glVertex2f((int)x,(int)y+height+offset_y);
		    	
		    GL11.glEnd();

		    GL11.glColor3f(1, 0, 0);

		    
			GL11.glBegin(GL11.GL_QUADS);
		
		    	GL11.glVertex2f((int)x+1,(int)y+1+offset_y);
		
		    	GL11.glVertex2f((int)x-1+width,(int)y+1+offset_y);
		    	GL11.glVertex2f((int)x+width-1,(int)y+height-1+offset_y);
		    	GL11.glVertex2f((int)x+1,(int)y+height-1+offset_y);
	    	
		    GL11.glEnd();
		    
		    GL11.glColor3f(0, 1, 0);

		    
		 			GL11.glBegin(GL11.GL_QUADS);
		 		
		 		    	GL11.glVertex2f((int)x+1,(int)y+1+offset_y);
		 		
		 		    	GL11.glVertex2f((int)(x+1)+((width-2)*((float)health/max_health)),(int)y+1+offset_y);
		 		    	GL11.glVertex2f((int)(x+1)+((width-2)*((float)health/max_health)),(int)y+height-1+offset_y);
		 		    	GL11.glVertex2f((int)x+1,(int)y+height-1+offset_y);
		 		    	
		 		    GL11.glEnd();
		    
			GL11.glColor3f(1, 1, 1);
			
			
		}
		
	
	void drawTexture( Texture newTexture, int newScaleX, int newScaleY){
		
		newTexture.bind();
		
		
		
		GL11.glBegin(GL11.GL_QUADS);
	   			
		if(is_hurt)
			GL11.glColor3f(1, 0, 0);
		
		
	   		GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f((int)x,(int)y);
		    GL11.glTexCoord2f(newScaleX*1,0);
		    GL11.glVertex2f((int)x+newTexture.getTextureWidth(),(int)y);
		    GL11.glTexCoord2f(newScaleX*1,newScaleY*1);
		    GL11.glVertex2f((int)x+newTexture.getTextureWidth(),(int)y+newTexture.getTextureHeight());
			GL11.glTexCoord2f(0,newScaleY*1);
			GL11.glVertex2f((int)x,(int)y+newTexture.getTextureHeight());
			
			GL11.glColor3f(1, 1, 1);

	   		
		GL11.glEnd();
		 
	}


		

}
