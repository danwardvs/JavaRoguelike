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
	
	Character gameCharacter;
	World gameWorld;
	
	// Store variables
	boolean exit;
	
	// Window size
	int width = 320;
	int height = 240;
	
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
	    
	    //Setup game data
	    gameCharacter = new Character(0,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_A,Keyboard.KEY_D,Keyboard.KEY_W,Keyboard.KEY_S);
		gameWorld = new World(gameCharacter,0,0);
	    
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
	        gameWorld.update();
	        gameWorld.draw();
	        
	        
	 
	  
	    		
	    	// Run the update loop to get mouse information and exit status
	    	update(delta);
	    	gameCharacter.update(delta);
	    	gameCharacter.draw();
	    	
	    	
	 
	    	
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
