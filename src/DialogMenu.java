import java.util.ArrayList;
import java.util.List;

public class DialogMenu extends Menu {

	private List<String> dialog = new ArrayList<String>();
	
	public DialogMenu(MouseHandler newMouseHandler, World newWorld) {
		super(newMouseHandler, newWorld);
		// TODO Auto-generated constructor stub
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
