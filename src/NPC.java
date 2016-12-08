import java.util.ArrayList;
import java.util.List;

public class NPC extends Item {
	
	private boolean alert;
	private List<String> dialog = new ArrayList<String>();
	
	
	
	public NPC(String newName, int newType, int newX, int newY, int newTextureAmount) {
		super(newName, newType, newX, newY, newTextureAmount);
		// TODO Auto-generated constructor stub
		dialog.add("Hello");
		dialog.add("Eyy");
	}

	public NPC(String newName, int newType, int newX, int newY, int newTextureAmount, boolean isAProp) {
		super(newName, newType, newX, newY, newTextureAmount, isAProp);
		// TODO Auto-generated constructor stub
	}
	
	public String loadDialog(){
		if(dialog.size()>0){
			String newString = dialog.get(0);
			dialog.remove(0);
			return newString;
		}
		return null;
	}

}
