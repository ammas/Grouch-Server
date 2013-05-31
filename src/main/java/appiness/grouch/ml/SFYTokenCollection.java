package appiness.grouch.ml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class SFYTokenCollection {

	private HashMap<String, Integer> tokens;

	public SFYTokenCollection() {
		this(new HashMap<String, Integer>());
	}

	public SFYTokenCollection(HashMap<String, Integer> tokenMap) {
		setTokens(tokenMap);
	}

	public Integer getTokenCount(String tokenName) {
		Integer tokenCount = getTokens().get(tokenName);

		if (tokenCount == null)
			tokenCount = 0;

		return tokenCount;
	}

	public void addToken(String token) {
		Integer tokenCount = getTokens().get(token);

		if (tokenCount == null)
			tokenCount = new Integer(0);

		tokenCount++;

		getTokens().put(token, tokenCount);

	}

	public HashMap<String, Integer> getTokens() {
		return tokens;
	}

	public void setTokens(HashMap<String, Integer> tokens) {
		this.tokens = tokens;
	}

	public Integer getTotalNumberOfWords() {

		Integer numberOfWords = 0;

		for (String key : getTokens().keySet())
			numberOfWords += getTokens().get(key);
		
		return numberOfWords;
	}

	public void merge(SFYTokenCollection toMerge) {

		Iterator<Entry<String, Integer>> it = toMerge.getTokens().entrySet()
				.iterator();

		while (it.hasNext()) {
			Entry<String, Integer> pair = it.next();

			if (getTokens().containsKey(pair.getKey()))
				getTokens().put(pair.getKey(),
						pair.getValue() + getTokens().get(pair.getKey()));
			else
				getTokens().put(pair.getKey(), pair.getValue());
		}

	}
}
