import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Menu {
	
	List<Button> menuButtons = new ArrayList<Button>();
	
	private int mouse_x;
	private int mouse_y;
	
	private MouseHandler gameMouse;
	private World gameWorld;
	
	private Texture cursor;
	private int pressed_delay;
	
	public Menu(MouseHandler newMouseHandler, World newWorld){
		cursor = loadTexture("Hit.png");
		gameMouse = newMouseHandler;
		gameWorld = newWorld;
		Mouse.setCursorPosition(180, 120);

	}
	
	public void createButton(Button newButton){
		menuButtons.add(newButton);
	}
	
	public Button getButton(int newIndex){
		return menuButtons.get(newIndex);
	}
	
	
	public void setPressedDelay(int newPressedDelay){
		pressed_delay = newPressedDelay;
	}
	
	public int getPressedDelay(){
		return pressed_delay;
	}
	
	public void update(){
		
		
		mouse_x = gameMouse.getX();
		mouse_y = gameMouse.getY();
		
		if(pressed_delay>0)
			pressed_delay--;
		
		
		for(Button newButton: menuButtons){
	          newButton.update();

		  }
		 
	}
	
	public Button getButtonById(String newId){
		 for(Button newButton: menuButtons){
	          if(newButton.getText().equals(newId))
	        	  return newButton;
		 }
		 return null;
		  
		
	}
	
	public void draw(){
			//TODO use this as standard
		  for(Button newButton: menuButtons){
	          newButton.draw();

		  }
		  
		  
		
	}
	public void recieveButtonPress(String newId){
		
		/*if(newId=="A" || newId=="B" || newId=="C" || newId=="1" || newId=="2" || newId=="3"){
			char newChar = newId.charAt(0);
			gameWorld.pressVendingButton(newChar);
		}
		if(newId=="Order"){
			char newChar = '0';
			gameWorld.pressVendingButton(newChar);
		}*/
		
		if(newId.equals("new game")){
			if(gameWorld.isSaveGame()){
				getButtonById("new game").setVisibility(false);
				getButtonById("load game").setVisibility(false);
				getButtonById("shogun jedsun").setVisibility(false);
				getButtonById("create game").setVisibility(true);
				getButtonById("cancel").setVisibility(true);
				getButtonById("are you sure? this will clear the existing save.").setVisibility(true);
			}else{
				gameWorld.startGame(true);
			}


		}
		
		if(newId.equals("create game")){
			gameWorld.startGame(true);
		}
		if(newId.equals("load game")){
			gameWorld.startGame(false);
		}
		if(newId.equals("cancel")){
			getButtonById("new game").setVisibility(true);
			getButtonById("load game").setVisibility(true);
			getButtonById("shogun jedsun").setVisibility(true);
			getButtonById("create game").setVisibility(false);
			getButtonById("cancel").setVisibility(false);
			getButtonById("are you sure? this will clear the existing save.").setVisibility(false);
		}
	}
	
	Texture loadTexture(String newPath){
		Texture newTexture = null;
		try{
			
			newTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newPath),GL11.GL_NEAREST);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return newTexture;
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

}
