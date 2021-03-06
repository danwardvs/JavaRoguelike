package myGame;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import org.apache.commons.io.FileUtils;
import java.io.IOException;


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
	int max_health;
	int damage;
	int damage_radius;
	int width;
	int height;
	int texture_number;
	String name;
	String base_reply;
	
	
	public WorldLoader(World newWorld, int newWorldX, int newWorldY){
		
		gameWorld = newWorld;
		
	}
	public void copyDirectory(String newSource, String newDestination){
		File source = new File(newSource);
		File dest = new File(newDestination);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
	public boolean checkIfFileExists(String newPath){
		File f = new File(newPath);
		return f.exists();
	}
	
	public void loadCharacter(String newCharacterPath){
		
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
					
					if(object_type.equals("Character")){
						
						x = Integer.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
						y = Integer.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
						world_x = Integer.valueOf(eElement.getElementsByTagName("world_x").item(0).getTextContent());
						world_y = Integer.valueOf(eElement.getElementsByTagName("world_y").item(0).getTextContent());
						
						health = Integer.valueOf(eElement.getElementsByTagName("health").item(0).getTextContent());

						
						index = Integer.valueOf(eElement.getElementsByTagName("index").item(0).getTextContent());
	
						Character newCharacter = new Character(index,x,y,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_A,Keyboard.KEY_D,Keyboard.KEY_W,Keyboard.KEY_S);
						newCharacter.setWorldX(world_x);
						newCharacter.setWorldY(world_y);
						newCharacter.setHealth(health);
						gameWorld.setCharacter(newCharacter,index);
						
					}
					if(object_type.equals("Item")){
						
						name = eElement.getElementsByTagName("name").item(0).getTextContent();
						type = Integer.valueOf(eElement.getElementsByTagName("type").item(0).getTextContent());


						texture_number = Integer.valueOf(eElement.getElementsByTagName("texture_number").item(0).getTextContent());

						damage = Integer.valueOf(eElement.getElementsByTagName("damage").item(0).getTextContent());
						damage_radius = Integer.valueOf(eElement.getElementsByTagName("damage_radius").item(0).getTextContent());
						
						Item newItem = new Item(name,type,0,0,texture_number);
					
						newItem.setItemOffset(10,10,12, 2,12,12,12,18);
						
						newItem.setDamage(damage);
						newItem.setDamageRadius(damage_radius);
						
						if(type==0)
							newItem.setDamageOffset(7, 25, 7, 20, 8, 13);
							
						
						if(type==1)
							newItem.setDamageOffset(19, 33, 20, 22, 19, 4);

						gameWorld.getCharacter(index).setItem(newItem);
						
					}
				}
			} 
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void loadLevel(String newLevelPath){
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
						max_health = Integer.valueOf(eElement.getElementsByTagName("max_health").item(0).getTextContent());


						Enemy newEnemy = new Enemy(gameWorld,x,y,type,health,max_health);
						gameWorld.createEnemy(newEnemy);
					}
					
					else if(object_type.equals("Item")){
						
						name = eElement.getElementsByTagName("name").item(0).getTextContent();
						
						type = Integer.valueOf(eElement.getElementsByTagName("type").item(0).getTextContent());

						
						texture_number = Integer.valueOf(eElement.getElementsByTagName("texture_number").item(0).getTextContent());
						
						
						Item newItem;
						
						if(type!=5){
							newItem = new Item(name,type,x,y,texture_number);
							
							
						}else{
							newItem = new NPC(name,type,x,y,texture_number);
							base_reply = eElement.getElementsByTagName("base_reply").item(0).getTextContent();
							newItem.setBaseReply(base_reply);
							
							for(int i=0; i<50; i++){
								try{
									newItem.addDialog(eElement.getElementsByTagName("dialog_"+i).item(0).getTextContent());

								} catch (Exception e) {
						    	
								}
							}
						

						}
						
						if(type==0 || type==1){
							damage = Integer.valueOf(eElement.getElementsByTagName("damage").item(0).getTextContent());
							damage_radius = Integer.valueOf(eElement.getElementsByTagName("damage_radius").item(0).getTextContent());
							newItem.setDamage(damage);
							newItem.setDamageRadius(damage_radius);
						}
						
						if(type==0)
							newItem.setDamageOffset(7, 25, 7, 20, 8, 13);
						
						if(type==1)
							newItem.setDamageOffset(19, 33, 20, 22, 19, 4);
							
						if(type==3){
							width = Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent());
							height = Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent());
							newItem.setWidth(width);
							newItem.setHeight(height);
							try{
								newItem.setBoundingY(Integer.valueOf(eElement.getElementsByTagName("bounding_y").item(0).getTextContent()));


							} catch (Exception e) {
					    	
							}
						}
			
						newItem.setItemOffset(10,10,12, 2,12,12,12,18);
						gameWorld.createItem(newItem);
					}
				}
			}
			
		} catch (Exception e) {
	    	e.printStackTrace();
		}
	}

}



