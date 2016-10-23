//import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;


import org.newdawn.slick.SlickException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Button {
	

	private Font buttonFont;
	
	private float font_size;
	private String text;
	private int x;
	private int y;
	private int mouse_x;
	private int mouse_y;
	private int width;
	private int height;
	private boolean mouse_left_down;
	private boolean is_hovered;
	private boolean is_pressed;
	private boolean is_active;
	private float r;
	private float b;
	private float g;
	private int pressed_timer;
	
	private MouseHandler gameMouse;
	private Menu parentMenu;

	private Texture texture;

	
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
	
	public Button(MouseHandler newMouseHandler, Menu newParentMenu, int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,float newFontSize, String newText, boolean newActive){
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
		r = newR;
		b = newB;
		g = newG;
		font_size = newFontSize;
		is_active = newActive;
		
		text = newText;
		
		gameMouse = newMouseHandler;
		parentMenu = newParentMenu;
		
		texture = loadTexture("button.png");
		//loadFont("font.ttf",16);
		buttonFont = new Font();
		buttonFont.loadFont();
	}
	
	public void setText(String newText){
		text = newText;
	}
	
	public void update(){
		
		if(is_active){
		
			if(pressed_timer>0){
				pressed_timer--;
				is_pressed = true;
			
			}else{
				is_pressed=false;
			}
			
			//Mouse.setGrabbed(true);
			mouse_x = gameMouse.getX();
			mouse_y = gameMouse.getY();
			mouse_left_down = Mouse.isButtonDown(0);
			
			if(location_clicked(x,x+width,y,y+height)){
				pressed_timer=5;
				parentMenu.recieveButtonPress(text);
			}
			is_hovered = location_hovered(x,x+width,y,y+height);
		}
	}
	
	// Load font from file
	public void loadFont(String newPath, int newSize){
		
	}
	public void setActive(boolean newActive){
		is_active = newActive;
	}
	
	 
	
	private Texture loadTexture(String newPath){
		
		Texture newTexture = null;
		
		try{
			
			newTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newPath),GL11.GL_NEAREST);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newTexture;
		
	}
	
	public void draw(){
		
		
		drawTexture(texture,r,g,b);
		drawFont(text);
		

        

	}
	private void drawFont(String newText){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		if(!is_pressed)
			buttonFont.drawString( text,x+(width/2)-(buttonFont.getWidth(text,font_size)/2), y+(height/2)-(buttonFont.getHeight(text,font_size)/2),font_size);
		else
			buttonFont.drawString( text,x-1+(width/2)-(buttonFont.getWidth(text,font_size)/2), y-1+(height/2)-(buttonFont.getHeight(text,font_size)/2),font_size);

		
		GL11.glColor3f(1,1,1);
       
	}

	
	// Draws the texture to the screen
	private void drawTexture(Texture newTexture, float newR, float newB, float newG){
				
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
