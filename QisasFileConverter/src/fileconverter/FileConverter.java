package fileconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileConverter {

	/**
	 * converts a formatted text file for story into xml story
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//read formatted text file
		File file = new File("convert.txt");
		Scanner input = new Scanner(file);
		//this one  prints out the file into nice xml 
		/*System.out.println("<scene>");
		while(input.hasNext()){
			String line = input.nextLine();
			if(line.startsWith("question:")){//check if question
				System.out.println("	<touch>");
				System.out.println("		<question>");
				System.out.println("		"+line.substring(9, line.length()));
				for(int i=0;i<3;i++){
					line = input.nextLine();
					if(line.startsWith("correct:")){
						System.out.println("		<correct>"+line.substring(8, line.length())+"</correct>");
					}else if(line.startsWith("wrong:")){
						System.out.println("		<wrong>"+line.substring(6, line.length())+"</wrong>");
					}
				}
				System.out.println("		</question>");
				System.out.println("	</touch>");
			}else if(line.startsWith("-")){
				System.out.println("</scene>");
				System.out.println("<scene>");
			}else{		//narrate	
				System.out.println("	<touch>");
				System.out.println("		<narrate>"+line+"</narrate>");
				System.out.println("	</touch>");
			}
			
		}
		System.out.println("</scene>");
		input.close();
		//then output to xml story
		*/
		
		
	}
	
	public void readSoundfile(Scanner input){
		//This one prints out all sound files
				while(input.hasNext()){
					String line = input.nextLine();
					if(line.contains("mpthree")){
						String filename="";
						boolean read = false;
						for(int i=0; i<line.length(); i++){
							if(read && line.charAt(i)=='<'){
								break;
							}
							if(read){
								filename = filename + line.charAt(i);
							}
							if(line.charAt(i)=='>'){
								read=true;
							}
							
						}
						System.out.println("\""+filename+"\",");
					}
				}
	}

}
