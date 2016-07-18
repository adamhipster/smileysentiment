package twitter;

import java.util.Scanner;


public class smileyParser {
	private final static String[] POSITIVE = 
		{
		":)", ":d", ";)",
		":-)", "=)", ":p",
		"(:", "xd", "=d",
		"=]", ":]", ":o",
		"d:", ";d", "=p",
		":-d", "^_^", ":o)",
		":-p",
		//o: is omitted
		";]", ";p", "(;",
		"=o", ";o)", "[:",
		//"(=" is omitted
		":}", ":-o",
		";o", ";-d",
		"d;", 
		//d= is omitted 
		"[=", "(^_^)", "(-:",
		"=}", "[;", 
		//p: is omitted
		"o;",
		//:od is omitted
		":-]",
		//:op is omitted
		";}", ":-}",
		//do: is omitted
		":=)", "{:", ":*)",
		";-]", "(-;", "^_^.",
		"o(^_^)o", "*^_^*", ";=)", 
		":P"
		
		
		};
	private final static String[] NEGATIVE = 
		{
		":(", ":-(", ":/",
		"=(", "=/",
		"):", ":|", ":-/",
		":[", ":@",
		";(", ";/", ":o(",
		"/:", "=|", "[:",
		");", ";-(", "|:",
		":-|", "@:", ";@",
		":{", ")=", "(^_^;)",
		":*(", ";[", "(-_-)",
		":o/", "(t_t)", ":-[",
		":,(", "/=", "]:", 
		"}:", ";-/", "=@", 
		";|", "={", ":-{",
		"/;"
		};

	private emoticonTuple smileyCounter;
	
	smileyParser(){
		smileyCounter = new emoticonTuple();
	}

	public void scanSentence(Scanner scan) { //it scans a string
		while(scan.hasNext()){
			String string = scan.next();
			scanPositive(string);
			scanNegative(string);
		}
	}
	
	private void scanNegative(String s) {
		for(int i = 0;i < NEGATIVE.length; i++){
			if(s.equals(NEGATIVE[i])){
				smileyCounter.incrementNegativeSmileyCount();
			}
		}
	}

	private void scanPositive(String s) {
		for(int i = 0;i < POSITIVE.length; i++){
			if(s.equals(POSITIVE[i])){
				smileyCounter.incrementPositiveSmileyCount();
			}
		}
	}

	public emoticonTuple getSmileyCounter(){
		return smileyCounter;
	}
	
	private void checkDuplicates(String[] array) { //DEBUG METHOD
		//This method can be used to check on duplicate smilies
		//if new smileys are added
		for (int j =0;j<array.length;j++)
		  for (int k=j+1;k<array.length;k++)
		    if (array[j].endsWith(array[k])){
		    	System.out.println(array[j] + array[k]);
		    }
	}
	
}
