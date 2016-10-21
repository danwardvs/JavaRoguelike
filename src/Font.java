
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
	public int getWidth(String newString, float newSize){
		return (int)(newString.length()*21*newSize); 
	}
	
	public int getHeight(String newString, float newSize){
		return(int)(31*newSize);
	}
	
	public void drawString(String newString, int newX, int newY, float newSize){
		
		newString = newString.toLowerCase();


		for(int i = 0; i < newString.length(); i++){
			int offset = (int)(i * 21*newSize) + newX;
			char c = newString.charAt(i);
			
			if(c=='!')
				drawTexture(offset,newY,character[0],newSize);
			
			else if(c=='.')
				drawTexture(offset,newY,character[1],newSize);
			
			else if(c=='0')
				drawTexture(offset,newY,character[2],newSize);
			
			else if(c=='1')
				drawTexture(offset,newY,character[3],newSize);
			
			else if(c=='2')
				drawTexture(offset,newY,character[4],newSize);
	
			else if(c=='3')
				drawTexture(offset,newY,character[5],newSize);
	
			else if(c=='4')
				drawTexture(offset,newY,character[6],newSize);

			else if(c=='5')
				drawTexture(offset,newY,character[7],newSize);
			
			else if(c=='6')
				drawTexture(offset,newY,character[8],newSize);
			
			else if(c=='7')
				drawTexture(offset,newY,character[9],newSize);
			
			else if(c=='8')
				drawTexture(offset,newY,character[10],newSize);
			
			else if(c=='9')
				drawTexture(offset,newY,character[11],newSize);
			
			else if(c=='?')
				drawTexture(offset,newY,character[12],newSize);
			
			else if(c=='a')
				drawTexture(offset,newY,character[13],newSize);
			
			else if(c=='b')
				drawTexture(offset,newY,character[14],newSize);
			
			else if(c=='c')
				drawTexture(offset,newY,character[15],newSize);
			
			else if(c=='d')
				drawTexture(offset,newY,character[16],newSize);
			
			else if(c=='e')
				drawTexture(offset,newY,character[17],newSize);
			
			else if(c=='f')
				drawTexture(offset,newY,character[18],newSize);
			
			else if(c=='g')
				drawTexture(offset,newY,character[19],newSize);
			
			else if(c=='h')
				drawTexture(offset,newY,character[20],newSize);
			
			else if(c=='i')
				drawTexture(offset,newY,character[21],newSize);
			
			else if(c=='j')
				drawTexture(offset,newY,character[22],newSize);
			
			else if(c=='k')
				drawTexture(offset,newY,character[23],newSize);
			
			else if(c=='l')
				drawTexture(offset,newY,character[24],newSize);
			
			else if(c=='m')
				drawTexture(offset,newY,character[25],newSize);
			
			else if(c=='n')
				drawTexture(offset,newY,character[26],newSize);
			
			else if(c=='o')
				drawTexture(offset,newY,character[27],newSize);
			
			else if(c=='p')
				drawTexture(offset,newY,character[28],newSize);
			
			else if(c=='q')
				drawTexture(offset,newY,character[29],newSize);
			
			else if(c=='r')
				drawTexture(offset,newY,character[30],newSize);
			
			else if(c=='s')
				drawTexture(offset,newY,character[31],newSize);
			
			else if(c=='t')
				drawTexture(offset,newY,character[32],newSize);
			
			else if(c=='u')
				drawTexture(offset,newY,character[33],newSize);
			
			else if(c=='v')
				drawTexture(offset,newY,character[34],newSize);
			
			else if(c=='w')
				drawTexture(offset,newY,character[35],newSize);
			
			else if(c=='x')
				drawTexture(offset,newY,character[36],newSize);
			
			else if(c=='y')
				drawTexture(offset,newY,character[37],newSize);
			
			else if(c=='z')
				drawTexture(offset,newY,character[38],newSize);
		
			else if(c!=' ')
				drawTexture(offset,newY,character[12],newSize);
			
		}
	}
	
	 //Draws the texture to the screen
	void drawTexture(int newX, int newY, Texture newTexture, float newSize){
				
		newTexture.bind();
				
		GL11.glBegin(GL11.GL_QUADS);
			   			
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(newX,newY);
		    GL11.glTexCoord2f(1,0);
				    GL11.glVertex2f(newX+(newTexture.getTextureWidth()*newSize),newY);
				    GL11.glTexCoord2f(1,1);
				    GL11.glVertex2f(newX+(newTexture.getTextureWidth()*newSize),newY+(newTexture.getTextureHeight()*newSize));
					GL11.glTexCoord2f(0,1);
					GL11.glVertex2f(newX,newY+(newTexture.getTextureHeight()*newSize));
			   		
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
