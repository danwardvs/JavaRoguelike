import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	int x;
	int y;
	Character gameCharacter;
	public List<Enemy> gameEnemys = new ArrayList<Enemy>();
	public List<Item> gameItems = new ArrayList<Item>();
	
	Texture[][] background = new Texture[5][5];
	boolean location_exists[][] = new boolean[5][5];
	
	
	
	public World(Character newCharacter,int newX, int newY){
		x = newX;
		y = newY;
		gameCharacter = newCharacter;
		
		location_exists[1][1] = true;
		location_exists[2][1] = true;
		
		gameCharacter.setWorldX(x);
		gameCharacter.setWorldY(y);
		
		load_images();
		
	}
	
	boolean collision(int x_min_1, int x_max_1, int x_min_2, int x_max_2, int y_min_1, int y_max_1, int y_min_2, int y_max_2){
		if(x_min_1 < x_max_2 && y_min_1 < y_max_2 && x_min_2 < x_max_1 && y_min_2 < y_max_1)
			return true;
		else
			return false;
		
	}
	
	public void create_enemy(Enemy newEnemy){
		gameEnemys.add(newEnemy);	
		
	}
	public void create_item(Item newItem){
		gameItems.add(newItem);
		System.out.println(newItem.name);
	}
	public void destroy_item(Item newItem){
		gameItems.remove(newItem);
	}
	
	
	public int get_world_x(){
		return x;
	}
	public int get_world_y(){
		return y;
	}
	
	public void apply_damage(int newX, int newY,int radius){
		for(int j = 0; j < gameEnemys.size(); j++)
		{
        		if(collision(newX-radius,newX+radius,gameEnemys.get(j).getX(),gameEnemys.get(j).getX()+gameEnemys.get(j).getWidth(),newY-radius,newY+radius,gameEnemys.get(j).getY(),gameEnemys.get(j).getY()+gameEnemys.get(j).getHeight())){
        			gameEnemys.remove(j);
        			System.out.println("Gotcha!");
        		}
        
		
		
		}
	}
	
	void load_images(){
		try{
			
			background[1][1] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("background_0_0.png"),GL11.GL_NEAREST);
			background[2][1] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("background_1_0.png"),GL11.GL_NEAREST);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(){
		draw_texture(background[x][y],0,0);
	}
	public void update(){

		
		
		if(gameCharacter.getX()>304){
			if(location_exists[x+1][y]){
				x+=1;
				gameCharacter.setWorldX(x);
				gameCharacter.setX(0);
			}
			else{
				gameCharacter.setX(304);
			}
		}
		if(gameCharacter.getX()<0){
			if(location_exists[x-1][y]){
				x-=1;
				gameCharacter.setWorldX(x);
				gameCharacter.setX(304);
			}
			else{
				gameCharacter.setX(0);
			}
		}
	
	
	if(gameCharacter.getY()>210){
		if(location_exists[x][y+1]){
			y+=1;
			gameCharacter.setY(0);
			gameCharacter.setWorldY(y);
		}
		else{
			gameCharacter.setY(210);
		}
	}
	if(gameCharacter.getY()<-10){
		if(location_exists[x][y-1]){
			y-=1;
			gameCharacter.setY(210);
			gameCharacter.setWorldY(y);
		}
		else{
			gameCharacter.setY(-10);
		}
	}
}
	
	// Draw a texture object to the screen
	void draw_texture(Texture newTexture,int x, int y){
			
		newTexture.bind();
		    
		// Draw the texture using 4 verts
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
