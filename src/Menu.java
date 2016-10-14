import java.util.ArrayList;
import java.util.List;

public class Menu {
	
	List<Button> menuButtons = new ArrayList<Button>();
	
	public Menu(){
		
	}
	
	public void create_button(Button newButton){
		menuButtons.add(newButton);
	}
	
	public void update(){
		 for(Button newButton: menuButtons){
	          newButton.update();

		  }
	}
	
	public void draw(){
			//TODO use this as standard
		  for(Button newButton: menuButtons){
	          newButton.draw();

		  }
		
	}

}
