import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Button {
	
	private String text;
	private int x;
	private int y;
	private int mouse_x;
	private int mouse_y;
	private int width;
	private int height;
	private boolean mouse_left_down;
	private boolean is_hovered;
	private float r;
	private float b;
	private float g;
	private MouseHandler gameMouse;

	private Texture texture;

	private TrueTypeFont font;
	
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
	
	public Button(MouseHandler newMouseHandler,int newX, int newY, int newWidth, int newHeight, float newR, float newB, float newG, String newText){
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
		r = newR;
		b = newB;
		g = newG;
		
		text = newText;
		
		gameMouse = newMouseHandler;
		
		texture = loadTexture("button.png");
		loadFont("font.ttf",16);
	}
	
	
	public void update(){
		
		//Mouse.setGrabbed(true);
		mouse_x = gameMouse.getX();
		mouse_y = gameMouse.getY();
		mouse_left_down = Mouse.isButtonDown(0);
		
		if(location_clicked(x,x+width,y,y+height)){
			System.out.println("EXIT");
		}
		
		is_hovered = location_hovered(x,x+width,y,y+height);
	}
	
	// Load font from file
	public void loadFont(String newPath, int newSize){
		
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(newPath);
             
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont((float)newSize); // set font size
            font = new TrueTypeFont(awtFont, false);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
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
		font.drawString(x+(width/2)-(font.getWidth(newText)/2), y+(height/2)-(font.getHeight(newText)/2), newText, Color.white);
       
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
	    
	    if(!is_hovered)
	    		GL11.glColor3f(r, g, b);
	    
	    else GL11.glColor3f(r+0.4f, g+0.4f, b+0.4f);
	    
		GL11.glBegin(GL11.GL_QUADS);
	
	    	GL11.glVertex2f(x+1,y+1);
	
	    	GL11.glVertex2f(x-1+width,y+1);
	    	GL11.glVertex2f(x+width-1,y+height-1);
	    	GL11.glVertex2f(x+1,y+height-1);
    	
	    GL11.glEnd();
	    
		GL11.glColor3f(1, 1, 1);

		}

}
