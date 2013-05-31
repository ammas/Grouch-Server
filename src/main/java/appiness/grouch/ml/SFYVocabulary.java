package appiness.grouch.ml;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFYVocabulary implements java.io.Serializable {

	Logger logger = LoggerFactory.getLogger(SFYVocabulary.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	private Map<String, SFYVocabularyElement> words;
	private PriorityQueue<SFYVocabularyElement> wordsCount;

	// Setters and Getters

	public int getCarinality() {
		return words.size();
	}

	public Map<String, SFYVocabularyElement> getWords() {
		return words;
	}

	public void setWords(Map<String, SFYVocabularyElement> words) {
		this.words = words;
	}

	public PriorityQueue<SFYVocabularyElement> getWordsCount() {
		return wordsCount;
	}

	public void setWordsCount(PriorityQueue<SFYVocabularyElement> wordsCount) {
		this.wordsCount = wordsCount;
	}

	// Ctor
	public SFYVocabulary() {
		setWords(new HashMap<String, SFYVocabularyElement>());
		setWordsCount(new PriorityQueue<SFYVocabularyElement>(200,
				new SFYVocabularyElementComparator()));
	}

	// members
	public void addWord(String word, int count) {
		if (getWords().containsKey(word)) {
			// Removing the word to organize the sorting
			SFYVocabularyElement element = getWords().get(word);
			getWordsCount().remove(element);
			element.incrementCountBy(count);
			getWordsCount().add(element);
		} else {
			SFYVocabularyElement element = new SFYVocabularyElement(word);
			element.setCount(count);
			getWords().put(word, element);
			getWordsCount().add(element);
		}
	}

	public void addWord(String word) {
		addWord(word, 1);
	}

	public HashSet<String> getDictionaryWords(HashSet<String> stopwords,
			OutputStream stream) {
		HashSet<String> vocab = new HashSet<String>(getWords().keySet());

		for (String word : stopwords)
			if (vocab.contains(word))
				vocab.remove(word);

		if (stream != null) {

			try {
				ObjectOutputStream objStream = new ObjectOutputStream(stream);
				objStream.writeObject(vocab);
				objStream.close();

			} catch (Exception e) {
				logger.error("Error serializing the vocabulary", e);
			}

		}

		return vocab;
	}

	public HashSet<String> getStopWords(double percentage,
			HashSet<String> keywords, OutputStream stream) {

		int len = (int) (getWords().size() * percentage / 100);

		HashSet<String> stopwords = new HashSet<String>();

		// Adding stop words
		while (len-- > 0) {
			SFYVocabularyElement element = getWordsCount().poll();
			stopwords.add(element.getWord());
		}

		// Removing keywords
		for (String word : keywords)
			if (stopwords.contains(word))
				stopwords.remove(word);

		if (stream != null) {
			try {

				ObjectOutputStream objStream = new ObjectOutputStream(stream);
				objStream.writeObject(stopwords);
				objStream.close();

			} catch (IOException e) {
				logger.error("Error writing stopwords", e);
			}
		}

		return stopwords;
	}

	// Vocab Comparator
	public class SFYVocabularyElementComparator implements
			Comparator<SFYVocabularyElement> {

	
		public int compare(SFYVocabularyElement lhs, SFYVocabularyElement rhs) {

			if (lhs.getCount() > rhs.getCount()) {
				return -1;
			}
			if (lhs.getCount() < rhs.getCount()) {
				return 1;
			}
			return 0;

		}

	}

}
