
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class WorldLoader {
	
	World gameWorld;
	String object_type;
	int x;
	int y;
	int world_x;
	int world_y;
	int index;
	int type;
	int health;
	int damage;
	int damage_radius;
	int texture_number;
	String name;
	
	
	public WorldLoader(World newWorld, int newWorldX, int newWorldY){
		
		gameWorld = newWorld;
		
	}
	public void load_characters(String newCharacterPath){
		
		try {	
			File fXmlFile = new File(newCharacterPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("object");
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
	
				Node nNode = nList.item(temp);
						
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
	
					object_type = (eElement.getAttribute("type"));
					x = Integer.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
					y = Integer.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
					
					if(object_type.equals("Character")){
						
						index = Integer.valueOf(eElement.getElementsByTagName("index").item(0).getTextContent());
	
						Character newCharacter = new Character(index,x,y,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_A,Keyboard.KEY_D,Keyboard.KEY_W,Keyboard.KEY_S);
						newCharacter.setWorldX(1);
						newCharacter.setWorldY(1);
						gameWorld.setCharacter(newCharacter,index);
					}
				}
			} 
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void load_level(String newLevelPath, int newWorldX, int newWorldY){
		try {

			File fXmlFile = new File(newLevelPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("object");
					
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					object_type = (eElement.getAttribute("type"));
					x = Integer.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
					y = Integer.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
					
				
					if(object_type.equals("Enemy")){
						
						type = Integer.valueOf(eElement.getElementsByTagName("type").item(0).getTextContent());
						health = Integer.valueOf(eElement.getElementsByTagName("health").item(0).getTextContent());

						Enemy newEnemy = new Enemy(gameWorld,x,y,type,health);
						gameWorld.create_enemy(newEnemy);
					}
					
					else if(object_type.equals("Item")){
						
						name = eElement.getElementsByTagName("name").item(0).getTextContent();
						
						x = Integer.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
						y = Integer.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
						
						texture_number = Integer.valueOf(eElement.getElementsByTagName("texture_number").item(0).getTextContent());

						
						damage = Integer.valueOf(eElement.getElementsByTagName("damage").item(0).getTextContent());
						damage_radius = Integer.valueOf(eElement.getElementsByTagName("damage_radius").item(0).getTextContent());
						
						Item newItem = new Item(name,x,y,damage_radius,damage);
						newItem.setTexture(name,texture_number);
						newItem.setDamageOffset(19, 33, 20, 22, 19, 4);
						newItem.setItemOffset(10,10,12, 2,12,12,12,18);
						gameWorld.create_item(newItem);
					}
				}
			}
			
		} catch (Exception e) {
	    	e.printStackTrace();
		}
	}

}



