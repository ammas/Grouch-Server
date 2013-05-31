package appiness.grouch.ml;

public class SFYVocabularyElement {

	// Fields
	private String word;
	private int count;

	// Getters and Setters
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	// Ctors
	public SFYVocabularyElement(String word) {
		setWord(word);
		setCount(1);
	}

	// Member functions
	public void incrementCount() {
		count++;
	}

	public void incrementCountBy(int value) {
		count += value;
	}

}
