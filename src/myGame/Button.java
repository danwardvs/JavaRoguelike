package myGame;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


public class Button extends UIElement {
	
	private int mouse_x;
	private int mouse_y;

	private boolean mouse_left_down;
	private boolean is_hovered;
	private boolean is_pressed;
	private boolean is_active;

	private int local_pressed_delay;
	
	private MouseHandler gameMouse;
	private Menu parentMenu;
	
	
	
	public Button(MouseHandler newMouseHandler, Menu newParentMenu,  int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,
			float newFontSize, String newText,boolean newActive, boolean newVisible) {
		super(newX, newY, newWidth, newHeight, newR, newG, newB, newFontSize, newText, newVisible);
		
		gameMouse = newMouseHandler;
		parentMenu = newParentMenu;
		is_active = newActive;
		
	}
	public void setActive(boolean newActive){
		is_active = newActive;
	}
	
	private boolean location_clicked(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y && mouse_left_down)
	        return true;
	    else return false;
	}
	
	private boolean location_hovered(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y)
	        return true;
	    else return false;
	}
	
	public void update(){
		
		if(is_active && is_visible){
			
			//Mouse.setGrabbed(true);
			mouse_x = gameMouse.getX();
			mouse_y = gameMouse.getY();
			mouse_left_down = gameMouse.getLeftMouseDown();
			
			is_hovered = location_hovered(x,x+width,y,y+height);
		
			if(local_pressed_delay>0 && is_pressed){
				
				local_pressed_delay--;
			
			}
			if(local_pressed_delay==0 && is_pressed && !location_clicked(x,x+width,y,y+height)){
				
				if(is_hovered){
					parentMenu.setPressedDelay(5);
					local_pressed_delay=0;
					is_pressed=false;
					parentMenu.recieveButtonPress(text.toLowerCase());
				}else
					is_pressed=false;

			}
			
			
			
			

			
			if(location_clicked(x,x+width,y,y+height) && parentMenu.getPressedDelay()==0 && !is_pressed){
				
				is_pressed = true;
				local_pressed_delay=5;
			}	
			
			
			
			
			
		}
	}

	// Draws the texture to the screen
	void drawTexture(Texture newTexture, float newR, float newB, float newG){
					
			newTexture.bind();
			
			GL11.glColor3f(0, 0, 0);
			
			GL11.glBegin(GL11.GL_QUADS);

		    	GL11.glVertex2f(x,y);

		    	GL11.glVertex2f(x+width,y);
		    	GL11.glVertex2f(x+width,y+height);
		    	GL11.glVertex2f(x,y+height);
		    	
		    GL11.glEnd();
		    	   
		   
		    
		    if(is_pressed)
		    	GL11.glColor3f(r-0.4f, g-0.4f, b-0.4f);

		    else if(is_hovered)
		   		GL11.glColor3f(r+0.4f, g+0.4f, b+0.4f);

		    else
		   		GL11.glColor3f(r, g, b);
		   
		    
			GL11.glBegin(GL11.GL_QUADS);
		
		    	GL11.glVertex2f(x+1,y+1);
		
		    	GL11.glVertex2f(x-1+width,y+1);
		    	GL11.glVertex2f(x+width-1,y+height-1);
		    	GL11.glVertex2f(x+1,y+height-1);
	    	
		    GL11.glEnd();
		    
			GL11.glColor3f(1, 1, 1);

			}

	

}
