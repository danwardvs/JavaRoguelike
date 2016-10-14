import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

@SuppressWarnings("deprecation")
public class main {
	
	World gameWorld;
	WorldLoader gameLoader;
	
	boolean exit;

	
	// Window size
	public static final int SCREEN_W = 320;
	public static final int SCREEN_H = 240;
	boolean fullscreen=true;
	
	 // Variables for FPS system
	long lastFrame;
    
	int fps;
	
	long lastFPS;
	
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
	@SuppressWarnings("deprecation")
	// Runs once when program starts up
	public void start() {
		
		
		
		// Trys to setup displays
	    try {
	    	if(fullscreen){
		        Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());

	    	}
	    	if(!fullscreen)
	    		Display.setDisplayMode(new DisplayMode(SCREEN_W,SCREEN_H));
	        //
	    	//
	        //Display.setFullscreen(true);
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
		GL11.glOrtho(0, SCREEN_W, SCREEN_H, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
	    //Setup game data
	    //gameCharacter = new Character(0,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_A,Keyboard.KEY_D,Keyboard.KEY_W,Keyboard.KEY_S);
		gameWorld = new World(1,1);
		gameWorld.setup();
		
		
		//Item newItem =  new Item("Sword",100,1255,50);
		//newItem.setTexture("Sword",1);
		//newItem.setDamageOffset(19, 33, 20, 22, 19, 4);
		//newItem.setItemOffset(10,10,12, 2,12,12,12,18);
		//gameWorld.create_item(newItem);
		Item newItem2 =  new Item("Fist",100,125,5,10);
		newItem2.setDamageOffset(7, 25, 7, 20, 8, 13);
		gameWorld.getCharacter(0).setItem(newItem2);
		

		
	    
	    
	    // Runs update when the program has not been exited
	    while (!Display.isCloseRequested() && !exit) {
	    	
	    	updateFPS(); 
	    	
	    	Keyboard.poll();
	    	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
	    		exit=true;
	    		gameWorld.save_level();
	    	}
	        int delta = getDelta();
	        	
	        	
	        // Clear the screen and depth buffer
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
	        
	        // Sets screen color to white AKA no color
	        Color.white.bind();
	        
	    	

	    	gameWorld.update(delta);
	    		
	    	gameWorld.draw();
	    	
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
