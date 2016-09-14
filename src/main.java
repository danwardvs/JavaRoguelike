import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.Font;
import java.io.InputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

@SuppressWarnings("deprecation")
public class main {
	
	Character MainCharacter;
	// Store variables
	boolean exit;
	
	// Window size
	int width = 320;
	int height = 240;
	
	// Declaring the background image
	private Texture background;
	
	// Declaring the font 
    private TrueTypeFont font2;
     
    // Should the text be aliased?
    private boolean antiAlias = true;
	
    // Mouse position variables
	int mouse_x;
	int mouse_y;
	boolean leftButtonDown;
	boolean rightButtonDown;

	 // Variables for FPS system
	long lastFrame;
    
	int fps;
	
	long lastFPS;
	
	// Function for checking if a location is clicked
	public boolean location_clicked(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y && leftButtonDown)
	        return true;
	    else return false;
	}
	
	//Loads images from file and declares the items with their properties
	public void loadImage(){
		try{
		
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("background.png"),GL11.GL_NEAREST);
			

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Load font from file
	public void loadFont(){
		
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("OpenSans-Regular.ttf");
             
            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(24f); // set font size
            font2 = new TrueTypeFont(awtFont2, antiAlias);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// Calculate and display FPS
	public void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	    	Display.setTitle("FPS: " + fps);
	        fps = 0;
	        lastFPS += 1000;
	    }
	    fps++;
	}
	
	// Calculate delta time
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	      
	    return delta;
	}
	     
	// Gets the current time to the millisecond
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	// Store update loop
	public void update(int delta) {
	       
		// Get mouse location
		mouse_x = Mouse.getX(); 
	    mouse_y = Mouse.getY(); 
	    
	    // Convert mouse to 0,0 at top left of the window
	    mouse_y=240-mouse_y;
	    
	    // Get mouse buttons
	    leftButtonDown = Mouse.isButtonDown(0);
	    rightButtonDown = Mouse.isButtonDown(1);
	    	
	    // Handles exit button
	    if(location_clicked(160,300,380,600))
	    	exit=true;
	    
	    
	    // update FPS Counter
	    updateFPS(); 
	}
	
	// Draw a texture object to the screen
	public void draw_texture(Texture newTexture,int x, int y){
		
		newTexture.bind();
	    
		// Draw the texture using 4 verts
	    GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0,0);
	    	GL11.glVertex2f(x,y);
	    	GL11.glTexCoord2f(1,0);
	    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y);
	    	GL11.glTexCoord2f(1,1);
	    	GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
	    	GL11.glTexCoord2f(0,1);
	    	GL11.glVertex2f(x,y+newTexture.getTextureHeight());
	    GL11.glEnd();
	}
	
	@SuppressWarnings("deprecation")
	// Runs once when program starts up
	public void start() {
	   
		// Trys to setup displays
	    try {
	        //Display.setDisplayMode(new DisplayMode(width,height));
	        //Display.setFullscreen(true);
	        Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
	        Display.create();
	        Keyboard.create();
	    } catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	    
	    // Sets the FPS system up initially
	    lastFPS = getTime();

	    // Setup OpenGL shaders up
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                    
	 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
	    GL11.glClearDepth(1);                                       
	 
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
		// Load our data
	    loadFont();
	    loadImage();
	    
	    //Setup game data
	    MainCharacter = new Character(0,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_A,Keyboard.KEY_D,Keyboard.KEY_W,Keyboard.KEY_S);
			
	    // Runs update when the program has not been exited
	    while (!Display.isCloseRequested() && !exit) {
	    	
	    	Keyboard.poll();
	    	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
	    		exit=true;
	    	
	        int delta = getDelta();
	        	
	        	
	        // Clear the screen and depth buffer
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
	        
	        // Sets screen color to white AKA no color
	        Color.white.bind();
	        
	        // Draw everything to the screen
	        draw_texture(background,0,0);
	 
	  
	    		
	    	// Run the update loop to get mouse information and exit status
	    	update(delta);
	    	MainCharacter.update(delta);
	    	MainCharacter.draw();
	    	
	    	
	 
	    	
	    	Display.update();
	        
	    	// Keeps the display from going above 60 FPS
	        Display.sync(60);
	            
	    }
	    	
	    // Destroy the display when the program ends
	    Display.destroy();
	        
	    }
	
	// Create a new main and run it
	public static void main(String[] args) {
		main MyMain = new main();
		MyMain.start();
		
	}

}
