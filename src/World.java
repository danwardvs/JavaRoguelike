import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	//Game Data
	private int x;
	private int y;
	
	private List<Enemy> gameEnemys = new ArrayList<Enemy>();
	private List<Item> gameItems = new ArrayList<Item>();
	private Character[] gameCharacters = new Character[4];
	
	private Texture[][] background = new Texture[5][5];
	private boolean locationExists[][] = new boolean[5][5];
	
	
	
	public World(int newX, int newY){
		x = newX;
		y = newY;;
		
		locationExists[1][1] = true;
		locationExists[2][1] = true;
		
		loadTextures();
		
	}
	
	public void setCharacter(Character newCharacter, int newIndex){
		newCharacter.setWorld(this);
		gameCharacters[newIndex] = newCharacter;
		
	}
	
	public List<Enemy> getEnemys(){
		return gameEnemys;
	}
	
	public List<Item> getItems(){
		return gameItems;
	}
	
	public Character getCharacter(int newIndex){
		return gameCharacters[newIndex];
	}
	
	
	
	private boolean collision(int x_min_1, int x_max_1, int x_min_2, int x_max_2, int y_min_1, int y_max_1, int y_min_2, int y_max_2){
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
	}
	public void destroy_item(Item newItem){
		gameItems.remove(newItem);
	}
	
	public void destroy_enemy(Enemy newEnemy){
		gameEnemys.remove(newEnemy);	
	}	
	
	
	public int get_world_x(){
		return x;
	}
	public int get_world_y(){
		return y;
	}
	
	public void apply_damage(int newX, int newY,int newRadius, int newDamage){
		for(int j = 0; j < gameEnemys.size(); j++){
        	if(collision(newX-newRadius,newX+newRadius,gameEnemys.get(j).getX(),gameEnemys.get(j).getX()+gameEnemys.get(j).getWidth(),newY-newRadius,newY+newRadius,gameEnemys.get(j).getY(),gameEnemys.get(j).getY()+gameEnemys.get(j).getHeight())){
        		gameEnemys.get(j).recieveDamage(newDamage);
        	
       		}
		}
	}
	
	private void loadTextures(){
		
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
	
	public void update(int delta){
		
		draw();
    	
    	for(int i=0; i<gameEnemys.size(); i++){
    		gameEnemys.get(i).update();
    		gameEnemys.get(i).draw(x,y);
    		
    		
    	}
    	
    	for(int i=0; i<gameEnemys.size(); i++){
    		if(gameEnemys.get(i).getHealth()<=0){
    			gameEnemys.remove(i);
    		}
    	}
    	for(Item item: getItems()){
        	item.draw(get_world_x(),get_world_y(),false,false,1,1);
    	}
    	
    	getCharacter(0).update(delta);
    	getCharacter(0).draw();
		
	
		if(gameCharacters[0].getX()>304){
			if(locationExists[x+1][y]){
				x+=1;
				gameCharacters[0].setWorldX(x);
				gameCharacters[0].setX(0);
			}
			else{
				gameCharacters[0].setX(304);
			}
		}
		if(gameCharacters[0].getX()<0){
			if(locationExists[x-1][y]){
				x-=1;
				gameCharacters[0].setWorldX(x);
				gameCharacters[0].setX(304);
			}
			else{
				gameCharacters[0].setX(0);
			}
		}
	
	
	if(gameCharacters[0].getY()>210){
		if(locationExists[x][y+1]){
			y+=1;
			gameCharacters[0].setY(0);
			gameCharacters[0].setWorldY(y);
		}
		else{
			gameCharacters[0].setY(210);
		}
	}
	if(gameCharacters[0].getY()<-10){
		if(locationExists[x][y-1]){
			y-=1;
			gameCharacters[0].setY(210);
			gameCharacters[0].setWorldY(y);
		}
		else{
			gameCharacters[0].setY(-10);
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
