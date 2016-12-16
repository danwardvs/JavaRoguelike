package myGame;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import java.util.List;

public class NPC extends Item {
	
	private List<String> dialog = new ArrayList<String>();
	private String dialog_base_reply = "I have nothing for you.";
	private Texture indicator;
	
	
	public NPC(String newName, int newType, int newX, int newY, int newTextureAmount) {
		super(newName, newType, newX, newY, newTextureAmount);
		// TODO Auto-generated constructor stub
		// ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		
		indicator=loadTexture("indicator.png");
	}

	public NPC(String newName, int newType, int newX, int newY, int newTextureAmount, boolean isAProp) {
		super(newName, newType, newX, newY, newTextureAmount, isAProp);
		// TODO Auto-generated constructor stub
	}
	public String getBaseReply(){
		return dialog_base_reply;
	}
	public void setBaseReply(String newBaseReply){
		dialog_base_reply=newBaseReply;
	}
	public void addDialog(String newDialog){
		dialog.add(newDialog);
	}
	public List<String> getDialog(){
		return dialog;
	}
	public void draw(){
		if(texture!=null)
			drawTexture(x,y,texture,1,1);
		if(dialog.size()>0)
			drawTexture(x+4,y-10,indicator,1,1);

	}
	
	
	public String loadDialog(){
		
		if(dialog.size()>0){
			
			String newString = dialog.get(0);
			dialog.remove(0);
			return newString;
		}
		return dialog_base_reply;
	}

}
