package myGame;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class EditorMenu extends Menu {
	
	MouseHandler gameMouse;
	KeyboardHandler gameKeyboard;
	Texture texture;
	
	boolean click_timer;
	int button_delay;
	int point_1_x;
	int point_1_y;
	
	int point_2_x;
	int point_2_y;
	
	int tool=1;
	//^^Ryan

	public EditorMenu(MouseHandler newMouseHandler, World newWorld, KeyboardHandler newKeyboardHandler) {
		super(newMouseHandler, newWorld);
		// TODO Auto-generated constructor stub
		gameMouse = newMouseHandler;
		gameKeyboard = newKeyboardHandler;
		
		texture = loadTexture("button.png");
		
		
		

	}
	
	
	public void update(){
		
		if(gameKeyboard.lastKeyPressed().equals("F1") && button_delay>10){
			
			gameWorld.overwriteNewLevel();
			button_delay=0;
			
		}
		if(gameKeyboard.lastKeyPressed().equals("F2") && button_delay>10){
			
			gameWorld.clearLevel();
			button_delay=0;
			
		}
		if(gameKeyboard.lastKeyPressed().equals("F3") && button_delay>10){
			
			gameMouse.toggleBounds();
			button_delay=0;
			
		}
	
		
		
		if(gameKeyboard.lastKeyPressed().equals("1") && button_delay>10){
			
			tool=1;
			gameMouse.setCursor("Cursor.png");
			button_delay=0;
			
		}
			
		if(gameKeyboard.lastKeyPressed().equals("2") && button_delay>10){
			tool=2;
			gameMouse.setCursor("Tree.png");
			button_delay=0;
		}
		
		if(gameKeyboard.lastKeyPressed().equals("3") && button_delay>10){
			tool=3;
			gameMouse.setCursor("Rock.png");
			button_delay=0;
		}
		
		if(gameKeyboard.lastKeyPressed().equals("4") && button_delay>10){
			tool=4;
			gameMouse.setCursor("Flower.png");
			button_delay=0;
		}
		
		if(gameKeyboard.lastKeyPressed().equals("5") && button_delay>10){
			tool=5;
			gameMouse.setCursor("Flower_2.png");
			button_delay=0;
		}
		
		
		if(tool==1){
		
			if(gameMouse.getLeftMouseDown() && !click_timer){
				point_1_x=gameMouse.getX();
				point_1_y=gameMouse.getY();
				click_timer=true;
			}
			
			if(!gameMouse.getLeftMouseDown() && click_timer){
				point_2_x=gameMouse.getX();
				point_2_y=gameMouse.getY();
				click_timer=false;
				
				if(point_2_x<point_1_x){
					int new_point_1_x=point_1_x;
					point_1_x=point_2_x;
					point_2_x=new_point_1_x;
				}
				
			
				if(point_2_y<point_1_y){
					int new_point_1_y=point_1_y;
					point_1_y=point_2_y;
					point_2_y=new_point_1_y;
				}
					
				Item newItem = new Item("BlockingVolume",3,point_1_x,point_1_y,0);
				newItem.setWidth(point_2_x-point_1_x);
				newItem.setHeight(point_2_y-point_1_y);
				gameWorld.createItem(newItem);
			}
		
		
			if(gameMouse.getLeftMouseDown() && click_timer){
				point_2_x=gameMouse.getX();
				point_2_y=gameMouse.getY();
			}
		}
		if(tool==2){
			
			if(gameMouse.getLeftMouseDown() && !click_timer){
				point_1_x=gameMouse.getX();
				point_1_y=gameMouse.getY();
				click_timer=true;
				Item newItem = new Item("Tree",4,point_1_x,point_1_y,1);
				gameWorld.createItem(newItem);


				
			}
			
			if(!gameMouse.getLeftMouseDown() && click_timer){
				click_timer=false;
			}
			
		}
		
		if(tool==3){
			
			if(gameMouse.getLeftMouseDown() && !click_timer){
				point_1_x=gameMouse.getX();
				point_1_y=gameMouse.getY();
				click_timer=true;
				Item newItem = new Item("Rock",4,point_1_x,point_1_y,1);
				gameWorld.createItem(newItem);


				
			}
			
			if(!gameMouse.getLeftMouseDown() && click_timer){
				click_timer=false;
			}
			
		}
		
		if(tool==4){
			
			if(gameMouse.getLeftMouseDown() && !click_timer){
				point_1_x=gameMouse.getX();
				point_1_y=gameMouse.getY();
				click_timer=true;
				Item newItem = new Item("Flower",2,point_1_x,point_1_y,1);
				gameWorld.createItem(newItem);


				
			}
			
			if(!gameMouse.getLeftMouseDown() && click_timer){
				click_timer=false;
			}
			
		}
		
		if(tool==5){
			
			if(gameMouse.getLeftMouseDown() && !click_timer){
				point_1_x=gameMouse.getX();
				point_1_y=gameMouse.getY();
				click_timer=true;
				Item newItem = new Item("Flower_2",2,point_1_x,point_1_y,1);
				gameWorld.createItem(newItem);


				
			}
			
			if(!gameMouse.getLeftMouseDown() && click_timer){
				click_timer=false;
			}
			
		}
		
		if(pressed_delay>0)
			pressed_delay--;	
		
		if(button_delay<10000)
			button_delay++;
		
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
		if(tool==1 && gameMouse.getLeftMouseDown()){
			if(texture != null)
		
				drawTexture(texture);
		}
			
	}
		
	

	// Draws the texture to the screen
		void drawTexture(Texture newTexture){
						
				
			int x = point_1_x;
			int y = point_1_y;
			
			int width = -1*(point_1_x-point_2_x);
			int height = -1*(point_1_y-point_2_y);
			
				newTexture.bind();
				
				GL11.glColor4f(0, 0, 0,0.5f);
				
				GL11.glBegin(GL11.GL_QUADS);

			    	GL11.glVertex2f(x,y);

			    	GL11.glVertex2f(x+width,y);
			    	GL11.glVertex2f(x+width,y+height);
			    	GL11.glVertex2f(x,y+height);
			    	
			    GL11.glEnd();
   
			   
			    
				GL11.glColor3f(1, 1, 1);

				}

}
