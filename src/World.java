import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	//Game Data
	private int x;
	private int y;
	
	public static final int SCREEN_W = 320;
	public static final int SCREEN_H = 240;
	
	private WorldLoader gameLoader;
	
	private MouseHandler gameMouse;
	
	/*
	private char vending_digit_1 = ' ';
	private char vending_digit_2 = ' ';
	private String vending_display;
	*/
	
	private List<Enemy> gameEnemys = new ArrayList<Enemy>();
	private List<Item> gameItems = new ArrayList<Item>();
	private Menu[] gameMenus = new Menu[5];
	private Character[] gameCharacters = new Character[4];
	
	private Texture[][] background = new Texture[5][5];
	private boolean locationExists[][] = new boolean[5][5];
	
	public World(int newX, int newY){
		
		x = newX;
		y = newY;;
		
		locationExists[1][1] = true;
		locationExists[2][1] = true;
		
		loadTextures();
		
		gameMouse = new MouseHandler();
		
		gameMenus[0] = new Menu(gameMouse,this);
		
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)-125,20,250,30,0.7f,0.9f,0.7f,0.8f,"Shogun Jedsun",false,true));

		
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)-80,SCREEN_H/2,60,20,0.7f,0.4f,0.4f,0.3f,"New Game",true,true));
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)+20,SCREEN_H/2,60,20,0.4f,0.7f,0.4f,0.3f,"Load Game",true,true));
		
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)-155,50,310,20,0.8f,0.0f,0.0f,0.3f,"Are you sure? This will clear the existing save.",false,false));
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)-90,SCREEN_H/2,80,20,0.4f,0.7f,0.4f,0.3f,"Create game",true,false));
		gameMenus[0].createButton(new Button(gameMouse,gameMenus[0],(SCREEN_W/2)+30,SCREEN_H/2,60,20,0.7f,0.4f,0.4f,0.3f,"Cancel",true,false));


	}
	public void setup(){
		
		
		
		
		
		
		//Vending machine
		/*
		create_item(new Item("Potion_Health",182,42,5,10,1));
		create_item(new Item("Gold_Axe",202,42,5,10,1));
		create_item(new Item("Gold_Shovel",222,42,5,10,1));
		create_item(new Item("Diamond_Sword",182,62,5,10,1));
		create_item(new Item("Diamond_Shovel",202,62,5,10,1));
		create_item(new Item("Orb",222,62,5,10,1));
		create_item(new Item("Apple",182,82,5,10,1));
		create_item(new Item("Diamond",202,82,5,10,1));
		create_item(new Item("Bow",222,82,5,10,1));

		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],240,40,60,20,0.7f,0.7f,0.7f,0.5f,"",false));
		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],240,60,20,20,0f,0f,1f,0.5f,"A",true));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],260,60,20,20,0f,0f,1f,0.5f,"B",true));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],280,60,20,20,0f,0f,1f,0.5f,"C",true));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],240,80,20,20,0f,1f,0f,0.5f,"1",true));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],260,80,20,20,0f,1f,0f,0.5f,"2",true));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],280,80,20,20,0f,1f,0f,0.5f,"3",true));
		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],240,100,60,20,1f,0f,0f,0.5f,"Order",true));
		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],180,100,60,20,0.5f,0.5f,0.5f,0.5f,"0",false));

		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],180,60,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],200,60,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],220,60,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],180,80,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],200,80,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],220,80,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],180,40,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],200,40,20,20,1f,0f,0f,0.2f,"",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],220,40,20,20,1f,0f,0f,0.2f,"",false));
		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],180,20,20,20,0f,0.5f,1f,0.5f,"1",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],200,20,20,20,0f,0.5f,1f,0.5f,"2",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],220,20,20,20,0f,0.5f,1f,0.5f,"3",false));
		
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],160,40,20,20,0f,0.5f,1f,0.5f,"A",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],160,60,20,20,0f,0.5f,1f,0.5f,"B",false));
		gameMenus[0].create_button(new Button(gameMouse,gameMenus[0],160,80,20,20,0f,0.5f,1f,0.5f,"C",false));

		 */

		
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
	public void startGame(){

		gameLoader = new WorldLoader(this, 1,1);
		gameLoader.loadLevel("gamedata/new/Level_1_1.xml", 1,1);
		gameLoader.loadCharacters("gamedata/new/Character_0.xml");
		
		Item newItem2 =  new Item("Fist",100,125,5,10,0);
		newItem2.setDamageOffset(7, 25, 7, 20, 8, 13);
		getCharacter(0).setItem(newItem2);
		gameLoader.copyDirectory("./gamedata/new","./gamedata/save");
		gameMenus[0] = null;
		
	
		
	}
	/*
	public void pressVendingButton(char newButton){
		
		
		if(newButton == 'A' || newButton == 'B' || newButton == 'C')
			vending_digit_1=newButton;
		
		if(newButton == '1' || newButton == '2' || newButton == '3')
			vending_digit_2=newButton;
		
		vending_display = String.valueOf(vending_digit_1) + String.valueOf(vending_digit_2);
		
		
		if(newButton == '0'){
			
			if(vending_display.equals("A1"))
				create_item(new Item("Potion_Health",205,120,50,50,1));
			
			if(vending_display.equals("A2"))
				create_item(new Item("Gold_Axe",205,120,50,50,1));
			
			if(vending_display.equals("A3"))
				create_item(new Item("Gold_Shovel",205,120,50,50,1));
			
			if(vending_display.equals("B1"))
				create_item(new Item("Diamond_Sword",205,120,50,50,1));
			
			if(vending_display.equals("B2"))
				create_item(new Item("Diamond_Shovel",205,120,50,50,1));
			
			if(vending_display.equals("B3"))
				create_item(new Item("Orb",205,120,50,50,1));
			
			if(vending_display.equals("C1"))
				create_item(new Item("Apple",205,120,50,50,1));
			
			if(vending_display.equals("C2"))
				create_item(new Item("Diamond",205,120,50,50,1));
			
			if(vending_display.equals("C3"))
				create_item(new Item("Bow",205,120,50,50,1));



				

			
			vending_digit_1 = ' ';
			vending_digit_2 = ' ';
			
		}
		
		vending_display = String.valueOf(vending_digit_1) + String.valueOf(vending_digit_2);
		
		gameMenus[0].getButton(0).setText(vending_display);
		
		
		
		
		

	}
	*/
	
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
	
	
	public int getWorldX(){
		return x;
	}
	public int getWorldY(){
		return y;
	}
	
	public void saveLevel(){
		writeFile("gamedata/Level_"+x+"_"+y+".xml",parseLevel());		

	}
	
	private void writeFile(String newPath, String newString){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(newPath), "utf-8"))) {
			writer.write(newString);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String parseLevel(){
		
	
		
		String newEntry = "<?xml version=\"1.0\"?>\n\n<level>\n";
		
		for(int j = 0; j < gameEnemys.size(); j++){
			String newLevelEntry = "";
			newLevelEntry = newLevelEntry + "	<object type=\"Enemy\">\n";
			newLevelEntry += "		<type>" + gameEnemys.get(j).getType() + "</type>\n";
			newLevelEntry += "		<health>" + gameEnemys.get(j).getHealth() + "</health>\n";
			newLevelEntry += "		<x>" + gameEnemys.get(j).getX() + "</x>\n";
			newLevelEntry += "		<y>" + gameEnemys.get(j).getY() + "</y>\n";
			newLevelEntry += "	</object>\n\n";

			newEntry += newLevelEntry;

		}
		
		for(int j = 0; j < gameItems.size(); j++){
			
			String newLevelEntry = "";
			newLevelEntry = newLevelEntry + "	<object type=\"Item\">\n";
			newLevelEntry += "		<name>" + gameItems.get(j).getName() + "</name>\n";
			newLevelEntry += "		<x>" + gameItems.get(j).getX() + "</x>\n";
			newLevelEntry += "		<y>" + gameItems.get(j).getY() + "</y>\n";
			newLevelEntry += "		<damage>" + gameItems.get(j).getDamage() + "</damage>\n";
			newLevelEntry += "		<damage_radius>" + gameItems.get(j).getDamageRadius() + "</damage_radius>\n";
			newLevelEntry += "		<texture_number>" + gameItems.get(j).getTextureNumber() + "</texture_number>\n";

			newLevelEntry += "	</object>\n\n";

			
			newEntry += newLevelEntry;
			
		}
		
		newEntry += "\n\n</level>";
		
		
		
		gameEnemys.clear();
		gameItems.clear();
		
		return newEntry;
		

	}
	public void readLevel(){
		gameLoader.loadLevel("gamedata/Level_"+x+"_"+y+".xml", x, y);
	}
	
	public void applyDamage(int newX, int newY,int newRadius, int newDamage){
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
		
		drawTexture(background[x][y],0,0);
		
		for(int i=0; i<gameCharacters.length; i++){
    		if(gameCharacters[i]!=null)
    			gameCharacters[i].draw();
    	}
		for(int i=0; i<gameMenus.length; i++){
    		if(gameMenus[i]!=null)
    			gameMenus[i].draw();
    	}
    	
		
		
		for(int i=0; i<gameEnemys.size(); i++)
    		gameEnemys.get(i).draw(x,y);

		
		for(Item item: getItems())
        	item.draw(item.getX(),item.getY(),false,false,1,1);
    	
		gameMouse.draw();
	}
	
	public void update(int delta){
		
		gameMouse.update();
    	
		for(int i=0; i<gameEnemys.size(); i++){
    		
    		gameEnemys.get(i).update();
    		
    	}
    	
    	for(int i=0; i<gameEnemys.size(); i++){
    		if(gameEnemys.get(i).getHealth()<=0){
    			gameEnemys.remove(i);
    		}
    	}
    	
    	for(int i=0; i<gameCharacters.length; i++){
    		if(gameCharacters[i]!=null)
    			gameCharacters[i].update(delta);
    	}
    	
    	for(int i=0; i<gameMenus.length; i++){
    		if(gameMenus[i]!=null)
    			gameMenus[i].update();
    	}
    	
    	
		
    	if(gameCharacters[0]!=null){
			if(gameCharacters[0].getX()>304){
				if(locationExists[x+1][y]){
					saveLevel();
					x+=1;
					gameCharacters[0].setWorldX(x);
					gameCharacters[0].setX(0);
					readLevel();
	
				}
				else{
					gameCharacters[0].setX(304);
				}
			}
			if(gameCharacters[0].getX()<0){
				if(locationExists[x-1][y]){
					saveLevel();
					x-=1;
					gameCharacters[0].setWorldX(x);
					gameCharacters[0].setX(304);
					readLevel();
	
				}
				else{
					gameCharacters[0].setX(0);
				}
			}
		
		
		if(gameCharacters[0].getY()>210){
			if(locationExists[x][y+1]){
				saveLevel();
				y+=1;
				gameCharacters[0].setY(0);
				gameCharacters[0].setWorldY(y);
				readLevel();
			}
			else{
				gameCharacters[0].setY(210);
			}
		}
		if(gameCharacters[0].getY()<-10){
			if(locationExists[x][y-1]){
				saveLevel();
				y-=1;
				gameCharacters[0].setY(210);
				gameCharacters[0].setWorldY(y);
				readLevel();
	
			}
			else{
				gameCharacters[0].setY(-10);
			}
		}
    	}
    	}
	
	// Draw a texture object to the screen
	void drawTexture(Texture newTexture,int x, int y){
			
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
