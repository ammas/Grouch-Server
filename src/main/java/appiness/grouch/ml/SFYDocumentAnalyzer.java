package appiness.grouch.ml;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class SFYDocumentAnalyzer {

	private final Analyzer dmAnalyzer = new StandardAnalyzer(
			Version.LUCENE_36);

	private static final Pattern numberPricePattern = Pattern
			.compile("\\b\\$?((-|\\+)?[0-9]+(\\.[0-9]+)?)+\\$?\\b");

	private Analyzer getDMAnalyzer() {
		return dmAnalyzer;
	}

	public String checkWord(String word) {

		// Remove if its a webURL
		if (word.matches("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU|com.au)$"))
			return null;

		// Remove if it contains an email address
		if (word.contains("@"))
			return null;

		// Remove if its a number or price
		if (numberPricePattern.matcher(word).find())
			return null;

		return word;

	}


	
	public SFYInstance createInstance(SFYDocument document)
			throws Exception {
		SFYInstance instance = new SFYInstance(document);

		// Parsing Content
		instance.setContentTokens(tokenizeString(document.getContent()));


		return instance;

	}

	public SFYTokenCollection tokenizeString(String strInput) throws IOException {

		if (strInput == null || strInput.isEmpty())
			return null;

		String word;
		TokenStream tokenStream = getDMAnalyzer().tokenStream(null,
				new StringReader(strInput));

		SFYTokenCollection tokens = new SFYTokenCollection();
		CharTermAttribute strTerm = tokenStream
				.addAttribute(CharTermAttribute.class);

		while (tokenStream.incrementToken()) {

			if ((word = checkWord(strTerm.toString())) == null)
				continue;


			tokens.addToken(word);

		}

		return tokens;
	}
}
