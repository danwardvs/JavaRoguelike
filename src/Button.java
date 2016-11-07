
public class Button extends Label {
	
	private int mouse_x;
	private int mouse_y;

	private boolean mouse_left_down;
	private boolean is_hovered;
	private boolean is_pressed;
	private boolean is_active;

	private int local_pressed_delay;
	
	private MouseHandler gameMouse;
	private Menu parentMenu;
	
	
	
	public Button(Menu newParentMenu, MouseHandler newMouseHandler, int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,
			float newFontSize, String newText,boolean newActive, boolean newVisible) {
		super(newX, newY, newWidth, newHeight, newR, newG, newB, newFontSize, newText, newVisible);
		
		gameMouse = newMouseHandler;
		parentMenu = newParentMenu;
		is_active = newActive;
		
	}
	
	
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
	
	public void update(){
		
		if(is_active && is_visible){
		
			if(local_pressed_delay>0){
				is_pressed = true;
				local_pressed_delay--;
			
			}else{
				is_pressed=false;
			}
			
			//Mouse.setGrabbed(true);
			mouse_x = gameMouse.getX();
			mouse_y = gameMouse.getY();
			mouse_left_down = gameMouse.mouse stuff
			
			if(location_clicked(x,x+width,y,y+height) && parentMenu.getPressedDelay()==0){
				parentMenu.setPressedDelay(5);
				local_pressed_delay=5;
				parentMenu.recieveButtonPress(text.toLowerCase());
			}
			is_hovered = location_hovered(x,x+width,y,y+height);
		}
	}
	

}
