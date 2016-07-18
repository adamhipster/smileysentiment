package twitter;

public class emoticonTuple {
	private int positiveSmilies;
	private int negativeSmilies;
	
	emoticonTuple(){
		positiveSmilies = 0;
		negativeSmilies = 0;
	}
	
	public void incrementPositiveSmileyCount(){
		positiveSmilies++;
	}
	
	public void incrementNegativeSmileyCount(){
		negativeSmilies++;
	}
	
	public boolean isNonZero(){
		if(positiveSmilies != 0 || negativeSmilies != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getPositiveCount(){
		return positiveSmilies;
	}
	
	public int getNegativeCount(){
		return negativeSmilies;
	}
}
