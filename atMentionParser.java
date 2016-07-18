package twitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class atMentionParser {
	
	public static final String NO_RELEVANTKEY = "no@mention";
	private StringBuffer twitterUsername;
	private ArrayList<String> usernameArray;
	
	atMentionParser(){
		twitterUsername = new StringBuffer("@");
		usernameArray = new ArrayList<String>();
	}
	
	public void scanSentence(Scanner sentenceScanner){
		//TO DO: check for double entries
		while(sentenceScanner.hasNext()){
			String word = sentenceScanner.next();
			Scanner wordScanner = new Scanner(word);
			if(word.startsWith("@") && !(word.equals("@") || word.equals("@ ")) ){
				wordScanner.useDelimiter("");
				wordScanner.next(); //skip the @
				while (nextCharBelongsToTwitterUsername(wordScanner)) {
					twitterUsername.append(nextCharLowerCase(wordScanner));
				}
				usernameArray.add(twitterUsername.toString());
				twitterUsername = new StringBuffer("@"); //reset the var
				//@mention is recognized
			}
		}
		//there is no @mention
	}
	
	/*
	 * CHARACTER RELATED FUNCTIONS
	 */
	
	public ArrayList<String> getTwitterUsernames(){
		return usernameArray;
	}
	
	private boolean nextCharBelongsToTwitterUsername(Scanner input) { //still need to change this
		return nextCharIsAllowed(input);
	}
	
	private boolean nextCharIsAllowed(Scanner input) {
		return nextCharIs(input, "[a-zA-Z0-9_]"); 
	}
	
	private boolean nextCharIs(Scanner input, String pattern) {
		return input.hasNext(pattern);
	}
	
	private char nextCharLowerCase(Scanner input) {
		return input.next().toLowerCase().charAt(0);
	}
	
}
