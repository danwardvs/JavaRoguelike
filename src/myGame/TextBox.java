package myGame;

public class TextBox extends UIElement {
	
	private KeyboardHandler gameKeyboard;
	private MouseHandler gameMouse;
	private Menu parentMenu;
	
	private int mouse_x;
	private int mouse_y;

	private boolean mouse_left_down;
	
	private int key_repeat;
	private boolean is_focused;
	private int cursor_delay;
	
	
	
	public TextBox(KeyboardHandler newKeyboardHandler, MouseHandler newMouseHandler, Menu newParentMenu, int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,
			float newFontSize, String newText, boolean newVisible) {
		super(newX, newY, newWidth, newHeight, newR, newG, newB, newFontSize, newText, newVisible);
		
		gameKeyboard = newKeyboardHandler;
		gameMouse = newMouseHandler;
		
		// TODO Auto-generated constructor stub
	}
	
	private boolean location_clicked(int min_x,int max_x,int min_y,int max_y){
	    if(mouse_x>min_x && mouse_x<max_x && mouse_y>min_y && mouse_y<max_y && mouse_left_down)
	        return true;
	    else return false;
	}
	public void draw(){
		
		
		if(is_visible){
		
			drawTexture(texture,r,g,b);
			
			if(is_focused && cursor_delay>20){
				drawFont(text+"i",font_justification);
			
			}else if(is_focused && cursor_delay<21){
				drawFont(text+" ",font_justification);
			}
			
			else
				drawFont(text, font_justification);
			
		
		}
		

        

	}
	
	public void update(){
		
		if(cursor_delay<0)
			cursor_delay=40;
		
		cursor_delay--;
		
		//Mouse.setGrabbed(true);
		mouse_x = gameMouse.getX();
		mouse_y = gameMouse.getY();
		mouse_left_down = gameMouse.getLeftMouseDown();
		
		if(location_clicked(x,x+width,y,y+height)){
			is_focused=true;
			cursor_delay=40;
		}
		if(!location_clicked(x,x+width,y,y+height) && gameMouse.getLeftMouseDown()){
			is_focused=false;
		}
		
		key_repeat++;
		
		if(gameKeyboard.isKeyPressed() && key_repeat>7){
			key_repeat=0;
		}
		
		
		
		if(is_focused){
			
			
			
			String key_pressed = gameKeyboard.lastKeyPressed();
		
			if(key_repeat==0){
				if(key_pressed.equals("BACK")){
					if(!text.equals(""))
						text=text.substring(0, text.length()-1);
				}else if(key_pressed.equals("PERIOD")){
					text+=".";
				}else if(key_pressed.equals("SPACE")){
					text+=" ";
				}
				else if(key_pressed.length()==1)
					text+=key_pressed;
				}
			}
		}

}
