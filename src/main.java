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
	
	// Store variables
	boolean exit_store;
	
	float final_total;
	
	float peanuts_amount;
	int book_amount;
	int movie_amount;
	
	// Store items declared
	item peanuts;
	item book;
	item movie;
		
	// Price constants
	final float PEANUTS_PRICE = 1.8f;
	final float BOOK_PRICE = 9f;
	final float MOVIE_PRICE = 14.99f;
	
	final float PEANUTS_SHIPPING_PRICE = 0.5f;
	final float MOVIE_SHIPPING_PERCENTAGE = 5;
	final float BOOK_SHIPPING_PRICE = 1.03f;
	
	// Window size
	int width = 800;
	int height = 600;
	
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
		
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("background.png"));
			
			peanuts = new item(405,377,0.1,TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("peanuts.png")));
			book = new item(510,395,1,TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("book.png")));
			movie = new item(575,387,1,TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("movie.png")));
			

		
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
	    mouse_y=600-mouse_y;
	    
	    // Get mouse buttons
	    leftButtonDown = Mouse.isButtonDown(0);
	    rightButtonDown = Mouse.isButtonDown(1);
	    	
	    // Handles exit button
	    if(location_clicked(160,300,380,600))
	    	exit_store=true;
	    
	    // Calculate the total cost for all items
	    final_total = ((PEANUTS_SHIPPING_PRICE*peanuts_amount)+(PEANUTS_PRICE*peanuts_amount))+((BOOK_SHIPPING_PRICE*book_amount)+(book_amount*BOOK_PRICE))+(((MOVIE_SHIPPING_PERCENTAGE/100)*(movie_amount*MOVIE_PRICE))+(movie_amount*MOVIE_PRICE));
	    
	    // update FPS Counter
	    updateFPS(); 
	}
	
	// Draw a texture object to the screen
	public void drawTexture(Texture newTexture,int x, int y){
		
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
	        Display.setDisplayMode(new DisplayMode(width,height));
	        Display.create();
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
			
	    // Runs update when the program has not been exited
	    while (!Display.isCloseRequested() && !exit_store) {
	        int delta = getDelta();
	        	
	        	
	        // Clear the screen and depth buffer
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
	        
	        // Sets screen color to white AKA no color
	        Color.white.bind();
	        
	        // Draw everything to the screen
	        drawTexture(background,0,0);
	        peanuts.drawTexture();
	        book.drawTexture();
	        movie.drawTexture();
	    		
	        font2.drawString(690,360, "Left click", Color.blue);
	    	font2.drawString(690,385, "to buy", Color.blue);
	    		
	   		font2.drawString(690,420, "Right click", Color.blue);
	   		font2.drawString(690,445, "to return", Color.blue);

	    	font2.drawString(210,520, "Exit", Color.black);
	   		font2.drawString(150,320, "Welcome to Danny's Assorted Goods Market!", Color.black);
	   		font2.drawString(400,470, "Peanuts    Book     Movie", Color.black);
	   		font2.drawString(400,495, "$1.80/lb   $9.00    $14.99", Color.black);
	   		font2.drawString(360,520, "Qty: " + String.format("%.1f",peanuts_amount) + " lb", Color.black);
    		font2.drawString(520,520, book_amount + "", Color.black);
	    	font2.drawString(604,520, movie_amount + "", Color.black);
	    	font2.drawString(360,545, "Total: $" + String.format("%.2f",final_total), Color.red);
	    		
	    	// Run the update loop to get mouse information and exit status
	    	update(delta);
	    	
	    	// With the new mouse information, we send it to the item object
	    	peanuts.setMouseValues(mouse_x, mouse_y, leftButtonDown, rightButtonDown);
	    	
	    	// item.update checks for mouse clicks, and returns the amount changed
	    	peanuts_amount+=peanuts.update();
	    	
	    	// No negative values for what is bought
	    	if(peanuts_amount<0.0)peanuts_amount=0.0f;
	    		
	    	book.setMouseValues(mouse_x, mouse_y, leftButtonDown, rightButtonDown);
	    	book_amount+=book.update();
	    	if(book_amount<0.0)book_amount=0;
	    		
	    	movie.setMouseValues(mouse_x, mouse_y, leftButtonDown, rightButtonDown);
	    	movie_amount+=movie.update();
	    	if(movie_amount<0.0)movie_amount=0;
	    	
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
