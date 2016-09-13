import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class item {
	
	// Initialize item variables
	int x;
	int y;
	int mouse_x;
	int mouse_y;
	int click_delay;
	double increment;
	boolean leftButtonDown;
	boolean rightButtonDown;
	
	private Texture texture;
	
	// Get mouse data from main
	public void setMouseValues(int newMouseX, int newMouseY, boolean newLeftButtonDown, boolean newRightButtonDown){
		
		mouse_x = newMouseX;
		mouse_y = newMouseY;
		leftButtonDown = newLeftButtonDown;
		rightButtonDown = newRightButtonDown;
		
		//MY NAME ISDANNYIM POROOFUSSINULL PROWGRAMMAR
	}
	
	// Constructor
	public item(int newX, int newY, double newIncrement, Texture newTexture){
		x = newX;
		y = newY;
		increment = newIncrement;
		texture = newTexture;
	}
	
	// Checks if area was left clicked
	public boolean location_clicked(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y && leftButtonDown && click_delay>9)
	        return true;
	    else return false;
	}
	
	// Check if area was right clicked
	public boolean location_right_clicked(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y && rightButtonDown && click_delay>9)
	        return true;
	    else return false;
	}
	
	// Check for clicks and add the increment or negative increment
	public double update(){
		
		click_delay++;
		
		if(location_clicked(x,x+texture.getImageWidth(),y,y+texture.getImageHeight())){
			click_delay=0;
			return increment;
			
			
		}
		else if(location_right_clicked(x,x+texture.getImageWidth(),y,y+texture.getImageHeight())){
			click_delay=0;
			return -increment;
			
		}
		else
			return 0.0;
		
		
		
	}
	
	// Draws the texture to the screen
	public void drawTexture(){
	
	   texture.bind();
	            
	   GL11.glBegin(GL11.GL_QUADS);
	   		GL11.glTexCoord2f(0,0);
	    	GL11.glVertex2f(x,y);
	    	GL11.glTexCoord2f(1,0);
	    	GL11.glVertex2f(x+texture.getTextureWidth(),y);
	    	GL11.glTexCoord2f(1,1);
	    	GL11.glVertex2f(x+texture.getTextureWidth(),y+texture.getTextureHeight());
	    	GL11.glTexCoord2f(0,1);
	    	GL11.glVertex2f(x,y+texture.getTextureHeight());
	    GL11.glEnd();
	 }	
		
}

