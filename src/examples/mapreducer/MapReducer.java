package examples.mapreducer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateful
public class MapReducer extends Agent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapReducer() {
		super();
	}
	public MapReducer(AID aid) {
		super(aid);
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		System.out.println("Agent "+ this.id.getName() + " recieve "+ message.getPerformative()+ 
				" message from "+ message.getSender().getName());
		
		String path = message.getContent();
		int slaveNumber = getSlaveNumber(this.id.getName());
		File file = getFile(slaveNumber, path);
		String text = getText(file);
		
		System.out.println("Agent "+ this.id.getName() + " count "+ text.length() + " characters.");
		
	}
	
	public int getSlaveNumber(String name){
		if(name.contains("slave")){
			String num = name.substring(5);
			return Integer.parseInt(num);
		}else
			return -1;
	}
	
	public File getFile(int num, String path){
		
		File f = new File(path);
		File[] dir = f.listFiles();  
		return dir[num];
	}
	
	public String getText(File file){
		BufferedReader in = null;
		String line = null;
		try {
			in = new BufferedReader(new FileReader(file));
		
           line = in.readLine();
           ArrayList<String> allWords = new ArrayList<String>();
           
           while(line != null)
           {
                  String [] words = line.split(" ");
                 
                  for(int i=0; i<words.length; i++)
                	  allWords.add(words[i]);
                  
                  line = in.readLine();
           }
           
           line = "";
           for(int i=0; i<allWords.size(); i++)
        	   line +=allWords.get(i);
           
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return line;
	}

}
