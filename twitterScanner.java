package twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;


public class twitterScanner {
	
	//This program can count @mentions and map text to a twitter username
	//this second function is needed to look for smilies afterwards
	
	final static String FILEPATH = "/home/melvin/twitterdata";
	private static final int NUMBER_OF_DATA_ELEMENTS = 4;
	private static final String EXPLANATION = 
			"Either supply zero or one argument \n " +
			"if you supply one argument it's in the format of: " +
			"<filepath>";
	private static final String ONLY_SMILIES = "mrt500";
	
	PrintStream out;
	Map<String, int[]> freqTable;
	File file;
	
	twitterScanner(){
		out = System.out;
		freqTable = new HashMap<String, int[]>();
		file = new File(FILEPATH);
	}
	
	twitterScanner(String filePath){
		out = System.out;
		freqTable = new HashMap<String, int[]>();
		file = new File(filePath);
	}
	
	void start(){
		parseFile();
		printFreqTable();
	}
	
	void printFreqTable() {
		Set<Entry<String,int[]>> mentionSet = freqTable.entrySet();
		Iterator<Entry<String, int[]>> it = mentionSet.iterator();
		out.println("KEY" + "\t" + "#TWEETS"+ "\t" + "#TWEETS & SMILIES" + "\t" + "#POS" + "\t" + "#NEG");
		int counter = 0;
		while(it.hasNext()){
			Entry<String, int[]> entry = it.next();
			String key = entry.getKey();
			int[] array = entry.getValue();
			if(array[2] != 0 || array[3] != 0){ //The @mentioned user has smilies
				out.println(key + "\t" + array[0] + "\t" + array[1] + "\t" + array[2] + "\t" + array[3]);
				counter++;
			}
		}
		out.println("size of keys: " + freqTable.size());
		out.println("size of keys that have smilies:" + counter);
	}

	void parseFile() {
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(file);
			fileScanner.useDelimiter("\n");
			while(fileScanner.hasNext()){
				parseLine(fileScanner);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	void parseLine(Scanner fileScanner) {
		String line = fileScanner.next();
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter("\t");
		while(lineScanner.hasNext()){
			String sentence = lineScanner.next();
			parseSentence(sentence);	
		}
	}

	void parseSentence(String sentence) {
		emoticonTuple smileyCounter = parseForSmilies(sentence);
		ArrayList<String> atMentions = parseForAtMentions(sentence);
		if(smileyCounter.isNonZero() && atMentions.size() != 0){
			Iterator<String> it = atMentions.iterator();
			while(it.hasNext()){
				putInFreqTable(it.next(), smileyCounter); //this is used if smilies are found
			}
		}
		else if(atMentions.size() != 0){ //this looks at @mentioned users 
			Iterator<String> it = atMentions.iterator();
			while(it.hasNext()){
				incrementAtMentionTweetCounter(it.next()); //this has to be increment whether there is a smiley or not
			}
		}
		/*
		else if(smileyCounter.isNonZero()){ //this looks at smilies being used when no one is @mentioned
			putInSpecialUserName(smileyCounter);
		}
		*/
	}

	emoticonTuple parseForSmilies(String sentence) {
		Scanner sentenceScanner = new Scanner(sentence);
		smileyParser smiley = new smileyParser();
		smiley.scanSentence(sentenceScanner);
		return smiley.getSmileyCounter();
	}

	ArrayList<String> parseForAtMentions(String sentence) {
		Scanner sentenceScanner = new Scanner(sentence);
		atMentionParser parseAtMentions = new atMentionParser();
		parseAtMentions.scanSentence(sentenceScanner);
		return parseAtMentions.getTwitterUsernames();
	}
	
	
	void incrementAtMentionTweetCounter(String word) {
		int[] array = new int[NUMBER_OF_DATA_ELEMENTS];
		if(freqTable.containsKey(word)){
			array = freqTable.get(word);
			array[0] = array[0] + 1;									//#TWEETS regardless of smilies
			array[1] = array[1];	//#TWEETS with encountered smilies
			array[2] = array[2];	//#POSITIVE smilies
			array[3] = array[3];	//#NEGATIVE smilies
		}
		else{
			array[0] = 1;
			array[1] = 0;
			array[2] = 0;
			array[3] = 0;
		}
		freqTable.put(word, array);
	}

	void putInFreqTable(String word, emoticonTuple smileyCounter){ //dit is aangepast
		int[] array = new int[NUMBER_OF_DATA_ELEMENTS];
		if(freqTable.containsKey(word)){
			array = freqTable.get(word);
			array[0] = array[0] + 1;									//#TWEETS regardless of smilies
			array[1] = array[1] + 1;									//#TWEETS with encountered smilies
			array[2] = array[2] + smileyCounter.getPositiveCount();		//#POSITIVE smilies
			array[3] = array[3] + smileyCounter.getNegativeCount();		//#NEGATIVE smilies
		}
		else{
			array[0] = 1;
			array[1] = 1;
			array[2] = smileyCounter.getPositiveCount();
			array[3] = smileyCounter.getNegativeCount();
		}
		freqTable.put(word, array);
	}
	
	void putInSpecialUserName(emoticonTuple smileyCounter) {
		int[] array = new int[NUMBER_OF_DATA_ELEMENTS];
		String word = ONLY_SMILIES;
		if(freqTable.containsKey(word)){
			array = freqTable.get(word);
			array[0] = array[0] + 1;									//#TWEETS regardless of smilies
			array[1] = array[1] + 1;									//#TWEETS with encountered smilies
			array[2] = array[2] + smileyCounter.getPositiveCount();		//#POSITIVE smilies
			array[3] = array[3] + smileyCounter.getNegativeCount();		//#NEGATIVE smilies
		}
		else{
			array[0] = 1;
			array[1] = 1;
			array[2] = smileyCounter.getPositiveCount();
			array[3] = smileyCounter.getNegativeCount();
		}
		freqTable.put(ONLY_SMILIES, array);
	}

	public static void main(String[] argv){
		if(argv.length <= 0){
			new twitterScanner().start();
		}
		else if(argv.length == 1){
			new twitterScanner(argv[0]).start();
		}
		else{
			System.out.println(EXPLANATION);
		}
	}
}
