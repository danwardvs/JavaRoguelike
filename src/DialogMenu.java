import java.util.ArrayList;
import java.util.List;

public class DialogMenu extends Menu {

	private List<String> dialog = new ArrayList<String>();
	
	private KeyboardHandler gameKeyboard;
	
	private int button_delay;
	
	
	public DialogMenu(MouseHandler newMouseHandler, World newWorld, KeyboardHandler newKeyboardHandler) {
		super(newMouseHandler, newWorld);
		
		gameKeyboard = newKeyboardHandler;
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		
		int npc_touching=gameWorld.getNPCtouching();
		
		if(gameKeyboard.lastKeyPressed().equals("Q") && button_delay>10){
			button_delay=0;
			boolean closing_menu=false;
			
			
			if(menuUIElements.size()>0){
				if(npc_touching!=-1 && menuUIElements.get(0).getText().equals(gameWorld.getItems().get(npc_touching).getBaseReply().toLowerCase())){
					closing_menu=true;
					
				}
			}
			menuUIElements.clear();
			
			
			if(npc_touching!=-1 && !closing_menu){
				
				
				String newString = gameWorld.getItems().get(npc_touching).loadDialog();
				if(newString!=null){
					createUIElement(new UIElement(10,30,300,20,0.7f,0.9f,0.7f,0.3f,newString,true));
					createUIElement(new UIElement(80,10,160,15,0.3f,0.9f,0.7f,0.3f,gameWorld.getItems().get(npc_touching).getName(),true));
				}
				
			}
		
		}
		
		if(pressed_delay>0)
			pressed_delay--;	
		
		if(button_delay<1000)
			button_delay++;
		
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
