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
	
	private Texture cursor;
	
	public Menu(MouseHandler newMouseHandler){
		cursor = loadTexture("Hit.png");
		gameMouse = newMouseHandler;
		Mouse.setCursorPosition(180, 120);

	}
	
	public void create_button(Button newButton){
		menuButtons.add(newButton);
	}
	
	
	public void update(){
		
		//mouse_x = Mouse.getX();
		//mouse_y = -Mouse.getY()+240;
		
		mouse_x = gameMouse.getX();
		mouse_y = gameMouse.getY();
		
		
		for(Button newButton: menuButtons){
	          newButton.update();

		  }
		 
	}
	
	public void draw(){
			//TODO use this as standard
		  for(Button newButton: menuButtons){
	          newButton.draw();

		  }
		  menuButtons.get(menuButtons.size()-1).draw();
		  
		  
		
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
