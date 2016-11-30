import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class EditorMenu extends Menu {
	
	MouseHandler gameMouse;
	Texture texture;
	
	boolean click_timer;
	int point_1_x;
	int point_1_y;
	
	int point_2_x;
	int point_2_y;

	public EditorMenu(MouseHandler newMouseHandler, World newWorld) {
		super(newMouseHandler, newWorld);
		// TODO Auto-generated constructor stub
		gameMouse = newMouseHandler;
		
		texture = loadTexture("button.png");
		
		

	}
	
	
	public void update(){
		
		if(gameMouse.getLeftMouseDown() && !click_timer){
			point_1_x=gameMouse.getX();
			point_1_y=gameMouse.getY();
			click_timer=true;
		}
		
		if(!gameMouse.getLeftMouseDown() && click_timer){
			point_2_x=gameMouse.getX();
			point_2_y=gameMouse.getY();
			click_timer=false;
			Item newItem = new Item("BlockingVolume",3,point_1_x,point_1_y,0);

			newItem.setWidth(Math.abs(point_2_x-point_1_x));
			newItem.setHeight(Math.abs(point_2_y-point_1_y));
			gameWorld.createItem(newItem);
			System.out.println("MADEITEM");
		}
		
		if(gameMouse.getLeftMouseDown() && click_timer){
			point_2_x=gameMouse.getX();
			point_2_y=gameMouse.getY();
		}
	
		if(pressed_delay>0)
			pressed_delay--;		
		
		for(UIElement newButton: menuUIElements){
	          newButton.update();

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
	
	
	public void draw(){
		
		//TODO use this as standard
		  for(UIElement newButton: menuUIElements){
	          newButton.draw();

		  }
				
		if(texture != null)
		
			drawTexture(texture);
			
	}
		
	

	// Draws the texture to the screen
		void drawTexture(Texture newTexture){
						
				
			int x = point_1_x;
			int y = point_1_y;
			
			int width = -1*(point_1_x-point_2_x);
			int height = -1*(point_1_y-point_2_y);
			
				newTexture.bind();
				
				GL11.glColor3f(0, 0, 0);
				
				GL11.glBegin(GL11.GL_QUADS);

			    	GL11.glVertex2f(x,y);

			    	GL11.glVertex2f(x+width,y);
			    	GL11.glVertex2f(x+width,y+height);
			    	GL11.glVertex2f(x,y+height);
			    	
			    GL11.glEnd();
   
			   
			    
				GL11.glColor3f(1, 1, 1);

				}

}
