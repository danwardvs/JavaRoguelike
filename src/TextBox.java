
public class TextBox extends UIElement {
	
	KeyboardHandler gameKeyboard;
	private int key_repeat;
	
	public TextBox(KeyboardHandler newKeyboardHandler, int newX, int newY, int newWidth, int newHeight, float newR, float newG, float newB,
			float newFontSize, String newText, boolean newVisible) {
		super(newX, newY, newWidth, newHeight, newR, newG, newB, newFontSize, newText, newVisible);
		
		gameKeyboard = newKeyboardHandler;
		
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		
		key_repeat++;
		
		if(gameKeyboard.isKeyPressed() && key_repeat>7){
			key_repeat=0;
		}
		
		String key_pressed = gameKeyboard.lastKeyPressed();
		
		if(key_repeat==0){
			if(key_pressed.equals("BACK")){
				if(!text.equals(""))
					text=text.substring(0, text.length()-1);
			}else if(key_pressed.equals("PERIOD")){
				text+=".";
			}else
				text+=key_pressed;
		}
	}

}
