import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class MouseHandler {
	
	private int mouse_x;
	private int mouse_y;
	private float sensitvity = 0.3f;
	
	public MouseHandler(){
		
		try{
			Mouse.create();
		} catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }  
		
	    Mouse.setGrabbed(true);
	}
	public void update(){
		mouse_x += (int)Mouse.getDX()*sensitvity;
		mouse_y += (int)Mouse.getDY()*sensitvity;

		
	}
	public int getX(){
		return mouse_x;
	}
	
	public int getY(){
		return mouse_y;
	}

}
