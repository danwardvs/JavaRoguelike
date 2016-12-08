import java.util.ArrayList;
import java.util.List;

public class DialogMenu extends Menu {

	private List<String> dialog = new ArrayList<String>();
	
	private KeyboardHandler gameKeyboard;
	
	
	public DialogMenu(MouseHandler newMouseHandler, World newWorld, KeyboardHandler newKeyboardHandler) {
		super(newMouseHandler, newWorld);
		
		gameKeyboard = newKeyboardHandler;
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		
		
		if(gameKeyboard.lastKeyPressed().equals("Q")){
			System.out.println("Horatio");
			
			if(gameWorld.getNPCtouching()!=-1){
				String newString = gameWorld.getItems().get(gameWorld.getNPCtouching()).loadDialog();
				createButton(new UIElement((320/2)-125,20,250,30,0.7f,0.9f,0.7f,0.8f,newString,true));
				
				
			}
		
		}
		
		if(pressed_delay>0)
			pressed_delay--;		
		
		for(UIElement newButton: menuUIElements){
	          newButton.update();

		  }
		 
	}
	
	
	public void recieveButtonPress(String newId){
		
		
		
		if(newId.equals("continue")){
			gameWorld.startGame(true);
		}
		
	}
	
	public void setDialog(List<String> newDialog){
		dialog = newDialog;
	}

}
