
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Font {
	
	private Texture[] character = new Texture[38];
	
	public Font(){
		
	}
	
	public void loadFont(){
		for(int i=0; i<38; i++){
			character[i] = loadTexture("font/font_"+i+".png");			
		}
		
	}
	public int getLength(String newString, float newScaleX, float newScaleY){
		return (int)(newString.length()*21*newScaleX); 
	}
	
	public int getHeight(String newString, float newScaleX, float newScaleY){
		return(int)(31*newScaleY);
	}
	
	public void drawString(String newString, int newX, int newY, float newScaleX, float newScaleY){
		
		newString = newString.toLowerCase();


		for(int i = 0; i < newString.length(); i++){
			int offset = (int)(i * 21*newScaleX) + newX;
			char c = newString.charAt(i);
			
			if(c=='!')
				drawTexture(offset,newY,character[0],newScaleX,newScaleY);
			
			else if(c=='.')
				drawTexture(offset,newY,character[1],newScaleX,newScaleY);
			
			else if(c=='0')
				drawTexture(offset,newY,character[2],newScaleX,newScaleY);
			
			else if(c=='1')
				drawTexture(offset,newY,character[3],newScaleX,newScaleY);
			
			else if(c=='2')
				drawTexture(offset,newY,character[4],newScaleX,newScaleY);
	
			else if(c=='3')
				drawTexture(offset,newY,character[5],newScaleX,newScaleY);
	
			else if(c=='4')
				drawTexture(offset,newY,character[6],newScaleX,newScaleY);

			else if(c=='5')
				drawTexture(offset,newY,character[7],newScaleX,newScaleY);
			
			else if(c=='6')
				drawTexture(offset,newY,character[8],newScaleX,newScaleY);
			
			else if(c=='7')
				drawTexture(offset,newY,character[9],newScaleX,newScaleY);
			
			else if(c=='8')
				drawTexture(offset,newY,character[10],newScaleX,newScaleY);
			
			else if(c=='9')
				drawTexture(offset,newY,character[11],newScaleX,newScaleY);
			
			else if(c=='?')
				drawTexture(offset,newY,character[12],newScaleX,newScaleY);
			
			else if(c=='a')
				drawTexture(offset,newY,character[13],newScaleX,newScaleY);
			
			else if(c=='b')
				drawTexture(offset,newY,character[14],newScaleX,newScaleY);
			
			else if(c=='c')
				drawTexture(offset,newY,character[15],newScaleX,newScaleY);
			
			else if(c=='d')
				drawTexture(offset,newY,character[16],newScaleX,newScaleY);
			
			else if(c=='e')
				drawTexture(offset,newY,character[17],newScaleX,newScaleY);
			
			else if(c=='f')
				drawTexture(offset,newY,character[18],newScaleX,newScaleY);
			
			else if(c=='g')
				drawTexture(offset,newY,character[19],newScaleX,newScaleY);
			
			else if(c=='h')
				drawTexture(offset,newY,character[20],newScaleX,newScaleY);
			
			else if(c=='i')
				drawTexture(offset,newY,character[21],newScaleX,newScaleY);
			
			else if(c=='j')
				drawTexture(offset,newY,character[22],newScaleX,newScaleY);
			
			else if(c=='k')
				drawTexture(offset,newY,character[23],newScaleX,newScaleY);
			
			else if(c=='l')
				drawTexture(offset,newY,character[24],newScaleX,newScaleY);
			
			else if(c=='m')
				drawTexture(offset,newY,character[25],newScaleX,newScaleY);
			
			else if(c=='n')
				drawTexture(offset,newY,character[26],newScaleX,newScaleY);
			
			else if(c=='o')
				drawTexture(offset,newY,character[27],newScaleX,newScaleY);
			
			else if(c=='p')
				drawTexture(offset,newY,character[28],newScaleX,newScaleY);
			
			else if(c=='q')
				drawTexture(offset,newY,character[29],newScaleX,newScaleY);
			
			else if(c=='r')
				drawTexture(offset,newY,character[30],newScaleX,newScaleY);
			
			else if(c=='s')
				drawTexture(offset,newY,character[31],newScaleX,newScaleY);
			
			else if(c=='t')
				drawTexture(offset,newY,character[32],newScaleX,newScaleY);
			
			else if(c=='u')
				drawTexture(offset,newY,character[33],newScaleX,newScaleY);
			
			else if(c=='v')
				drawTexture(offset,newY,character[34],newScaleX,newScaleY);
			
			else if(c=='w')
				drawTexture(offset,newY,character[35],newScaleX,newScaleY);
			
			else if(c=='x')
				drawTexture(offset,newY,character[36],newScaleX,newScaleY);
			
			else if(c=='y')
				drawTexture(offset,newY,character[37],newScaleX,newScaleY);
			
			else if(c=='z')
				drawTexture(offset,newY,character[38],newScaleX,newScaleY);
		
			else if(c!=' ')
				drawTexture(offset,newY,character[12],newScaleX,newScaleY);
			
		}
	}
	
	 //Draws the texture to the screen
	void drawTexture(int newX, int newY, Texture newTexture, float newScaleX, float newScaleY){
				
		newTexture.bind();
				
		GL11.glBegin(GL11.GL_QUADS);
			   			
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(newX,newY);
		    GL11.glTexCoord2f(1,0);
				    GL11.glVertex2f(newX+(newTexture.getTextureWidth()*newScaleX),newY);
				    GL11.glTexCoord2f(1,1);
				    GL11.glVertex2f(newX+(newTexture.getTextureWidth()*newScaleX),newY+(newTexture.getTextureHeight()*newScaleY));
					GL11.glTexCoord2f(0,1);
					GL11.glVertex2f(newX,newY+(newTexture.getTextureHeight()*newScaleY));
			   		
				GL11.glEnd();
				 
		}
	
	
	private Texture loadTexture(String newPath){
		
		Texture newTexture = null;
		
		try{
			
			newTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newPath),GL11.GL_NEAREST);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newTexture;
		
	}
	
}
