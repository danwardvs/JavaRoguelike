import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class UIElement {
	

	 Font buttonFont;
	
	 float font_size;
	 String text;
	 int x;
	 int y;
	 int width;
	 int height;
	 boolean is_visible;
	 float r;
	 float b;
	 float g;

	 Texture texture;


	
	public UIElement(int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,float newFontSize, String newText, boolean newVisible){
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
		r = newR;
		b = newB;
		g = newG;
		font_size = newFontSize;
		is_visible = newVisible;
		
		text = newText;
		
		texture = loadTexture("button.png");
		//loadFont("font.ttf",16);
		buttonFont = new Font();
		buttonFont.loadFont();
	}
	
	public void setText(String newText){
		text = newText;
	}
	
	public String getText(){
		return text.toLowerCase();
	}
	
	public void setVisibility(boolean newVisible){
		is_visible = newVisible;
	}
	public void setActive(boolean newActive){}
	
	public void setColour(float newR, float newG, float newB){
		r = newR;
		g = newG;
		b = newB;
		
	}


	public void setTexture(String newPath){
		texture = loadTexture(newPath + ".png");
		
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
		
		if(is_visible){
		
			drawTexture(texture,r,g,b);
			drawFont(text);
		
		}
		

        

	}
	
	public void update(){
		
	}
	
	void drawFont(String newText){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
	
		buttonFont.drawString( text,x+(width/2)-(buttonFont.getWidth(text,font_size)/2), y+(height/2)-(buttonFont.getHeight(text,font_size)/2),font_size);
	

		
		GL11.glColor3f(1,1,1);
       
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
