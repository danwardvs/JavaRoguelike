import java.util.ArrayList;
import java.util.List;

public class NPC extends Item {
	
	private boolean alert;
	private List<String> dialog = new ArrayList<String>();
	private String dialog_base_reply = "I have nothing for you.";
	
	
	
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
	public String getBaseReply(){
		return dialog_base_reply;
	}
	
	public String loadDialog(){
		System.out.println("KYS");
		
		if(dialog.size()>0){
			
			String newString = dialog.get(0);
			dialog.remove(0);
			return newString;
		}
		return dialog_base_reply;
	}

}
