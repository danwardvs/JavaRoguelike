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
	
	private Texture cursor;
	
	public Menu(){
		cursor = loadTexture("Hit.png");
	}
	
	public void create_button(Button newButton){
		menuButtons.add(newButton);
	}
	
	public void setup(){
		Mouse.setCursorPosition(180, 120);
	}
	
	public void update(){
		
		mouse_x = Mouse.getX();
		mouse_y = -Mouse.getY()+240;
		
		
		if(mouse_x>319){
			Mouse.setCursorPosition(319, mouse_y);
			mouse_x=319;
		}
		if(mouse_y<0)
			Mouse.setCursorPosition(mouse_x, 240);
		
		System.out.println(mouse_x+":"+mouse_y);
		
		for(Button newButton: menuButtons){
	          newButton.update();

		  }
		 
	}
	
	public void draw(){
			//TODO use this as standard
		  for(Button newButton: menuButtons){
	          newButton.draw();

		  }
		  
		  draw_texture(cursor,1,mouse_x,mouse_y);
		  
		
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
