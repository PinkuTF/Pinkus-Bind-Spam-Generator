import java.io.*;
import java.util.Scanner;

public class Spambuilder 
{
	
	public static String KEY_Bind = "[";
	public static String Alias_Name = "Bindspam";
	public static String BindAlias = Alias_Name + "_Bind";	
	public static File file;
	public static File outFile;
	
	public static void buildBind(SimpleList<String> list)
	{
		/* Builds a script to cycle through different text outputs into chat.
		 * 
		 * To make reading comments easier (hopefully....), any time a comment is referring to something
		 * that would happen as a result of the ouput of this program will be in single quotes (')
		 * 
		 * ex. ('binds a key to the next alias') = referring to in game
		 * while (creating a new string and adding it to list) = referring to java code
		 * 
		 * 
		 * Now, for the outline of this method...
		 * 
		 * Starts by creating a string that would 'bind a key to the base alias'.
		 * 
		 * Then, a loop will be initiated to parse through a text file, splitting it up into chunks.
		 * 
		 * These chunks will contain only complete words, meaning they won't stop in the middle of a word.
		 * 
		 * A word, in the context of this program, is deliminated by any sequence of characters surrounded by two spaces.
		 * 
		 * Each chunk will have a max. character limit of 128, so these chunks will need to be checked before adding words to them.
		 * 
		 * Once a chunk is complete, it will then have Alias_Name + loopCount + "say " added to the beginning of it, and it will be added to the list.
		 * 
		 * Additionally, in between say chunks, a string rebinding the key to the next chunk will be created, and added to the list.
		 * 
		 * Once the list is created, it will then be parsed through and each line will be added to an output text file.
		 */

		int loopCount = 0; 
		boolean fileOver = false;

		try(Scanner input = new Scanner(file))
		{			
				
			String setBindAlias = new String();
			String chunk = new String();
			String primeChunk = new String(); // Used to carry over extra word to the next string
			
			String initial = "bind " + KEY_Bind + " " + Alias_Name + "0"; // Used for the initial bind to start the loop
			//'bind [key] shrek0'
			list.insert(initial);
			System.out.println(initial);
			System.out.println("");
			
							
			while (!fileOver) // Loops until file is over
			{
			String buildChunk = primeChunk;
			primeChunk = "";
			String word = new String();
			
			boolean fullChunk = false;
			while (!fullChunk) // Builds up the buildchunk
				{
					if (!input.hasNext())
					{
						fullChunk = true;
						break;
					}
					
					word = input.next() + " "; // Spaces between words
					int overallLength = buildChunk.length() + word.length();
					
					if (overallLength <= 127)
					{
						buildChunk = buildChunk + word;
					}
					else
					{
						if (word.length() <= 127) primeChunk = word;
						fullChunk = true;
					}
					
				}
				
			//System.out.println(buildChunk);
			
			if (loopCount > 0)
				{
				setBindAlias = ("alias " + BindAlias + (loopCount + 0) + " \"bind " + KEY_Bind + " " + Alias_Name + loopCount + "\"");				
				//'alias BindAlias "bind [Key] Shrek0"'	
				list.insert(setBindAlias);			
				System.out.println(setBindAlias);
				System.out.println("");
				
				}
			
			chunk = ("alias " + Alias_Name + loopCount + " \"" + BindAlias  + (loopCount + 1) + ";say " + buildChunk + "\"");
			//'alias shrek0 "BindAlias; say [chunk]"
			list.insert(chunk);
			
			System.out.println(chunk);
			System.out.println("");

			loopCount++;
					
			
			if (!input.hasNext()) fileOver = true;
			
			}
			
			String loopmaker = "alias " + BindAlias  + loopCount + " \"bind " + KEY_Bind + " " + Alias_Name + "0\""; // Makes bind spam circular
			list.insert(loopmaker);
			System.out.println(loopmaker);
			System.out.println("");
					
		}
		catch (IOException ex)  //Should the file not exist, tell the user exit
		{
			System.out.println("Error: File not found");
		}
		
		// Now that the list has been filled with chunks + rebinds, we now will output that to a text file
		try(PrintWriter output = new PrintWriter(outFile))
		{
			boolean listOver = false;
			
			Node<String> checkNode = list.node;
			String outLine = new String();

			if (checkNode.getCargo() == null) listOver = true;

			while(!listOver)
			{			

				outLine = checkNode.getCargo();	//Gets the cargo of a node and prints it to a line	

				output.println(outLine);
				
				output.println(""); // Makes bind easier to read

				if (checkNode.hasNextNode())
				{
					checkNode = checkNode.getLink();
				}
				else
				{
					listOver = true;
				}
			}
							
		}
		catch (IOException ex)  //Should the file not exist, tell the user exit
		{
			System.out.println("Error: File not found");
		}
		
	}
	
}












