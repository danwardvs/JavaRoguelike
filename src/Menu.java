import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Menu {
	
	List<UIElement> menuUIElements = new ArrayList<UIElement>();
	
	private World gameWorld;
	
	private int pressed_delay;
	
	public Menu(MouseHandler newMouseHandler, World newWorld){
		
		gameWorld = newWorld;
		Mouse.setCursorPosition(180, 120);

	}
	
	public void createButton(UIElement newButton){
		menuUIElements.add(newButton);
	}
	
	public UIElement getButton(int newIndex){
		return menuUIElements.get(newIndex);
	}
	
	
	public void setPressedDelay(int newPressedDelay){
		pressed_delay = newPressedDelay;
	}
	
	public int getPressedDelay(){
		return pressed_delay;
	}
	
	public void update(){
		
	
		if(pressed_delay>0)
			pressed_delay--;		
		
		for(UIElement newButton: menuUIElements){
	          newButton.update();

		  }
		 
	}
	
	public UIElement getButtonByText(String newId){
		 for(UIElement newButton: menuUIElements){
	          if(newButton.getText().equals(newId))
	        	  return newButton;
		 }
		 return null;
		  
		
	}
	
	public UIElement getButtonById(String newId){
		 for(UIElement newUIElement: menuUIElements){
	          if(newUIElement.getId().equals(newId))
	        	  return newUIElement;
		 }
		 return null;
		  
		
	}
	
	public void draw(){
			//TODO use this as standard
		  for(UIElement newButton: menuUIElements){
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
				getButtonByText("new game").setVisibility(false);
				getButtonByText("load game").setVisibility(false);
				getButtonByText("shogun jedsun").setVisibility(false);
				getButtonByText("create game").setVisibility(true);
				getButtonByText("cancel").setVisibility(true);
				getButtonByText("are you sure? this will clear the existing save.").setVisibility(true);
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
			getButtonByText("new game").setVisibility(true);
			getButtonByText("load game").setVisibility(true);
			getButtonByText("shogun jedsun").setVisibility(true);
			getButtonByText("create game").setVisibility(false);
			getButtonByText("cancel").setVisibility(false);
			getButtonByText("are you sure? this will clear the existing save.").setVisibility(false);
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
