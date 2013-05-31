package appiness.grouch.ml;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map.Entry;

public class SFYDataSet {

	private SFYVocabulary vocabulary;
	private HashMap<String, ArrayList<SFYInstance>> instances;
	private SFYCategorySet categories;
	private SFYDocumentAnalyzer analyzer;

	public SFYDataSet() {
		setInstances(new HashMap<String, ArrayList<SFYInstance>>());
		setCategories(new SFYCategorySet());
		setVocabulary(new SFYVocabulary());
	}

	public HashMap<String, ArrayList<SFYInstance>> getInstances() {
		return instances;
	}

	public void setInstances(HashMap<String, ArrayList<SFYInstance>> instances) {
		this.instances = instances;
	}

	public void addToVocabulary(String word) {
		getVocabulary().addWord(word);
	}

	public void addToVocabulary(SFYTokenCollection tokens) {
		for (Entry<String, Integer> word : tokens.getTokens().entrySet())
			getVocabulary().addWord(word.getKey(), word.getValue());

	}

	private ArrayList<SFYInstance> createNewCategoryInstanceCollection(
			String categoryName) {
		ArrayList<SFYInstance> collection = new ArrayList<SFYInstance>();
		getInstances().put(categoryName, collection);
		return collection;
	}

	public void addCategoryInstance(String categoryName, SFYInstance instance) {
		ArrayList<SFYInstance> instanceCollection = getInstances().get(
				categoryName);
		if (instanceCollection == null) {
			instanceCollection = createNewCategoryInstanceCollection(categoryName);
		}
		instanceCollection.add(instance);
	}

	public void addInstance(SFYInstance instance) {

		// Adding the token words to the vocabulary
		addToVocabulary(instance.getContentTokens());

		// Finding the Category
		ArrayList<String> cats = new ArrayList<String>();
		cats.add(instance.getDocument().getClassification());
		SFYCategory instanceCat = getCategories().getHierarchyCategories(cats);

		addCategoryInstance(instanceCat.getName(), instance);

	}

	public SFYVocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(SFYVocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	public Dictionary<String, Double> computeWordFrequency() {
		return null;
	}

	public SFYCategorySet getCategories() {
		return categories;
	}

	public void setCategories(SFYCategorySet categories) {
		this.categories = categories;
	}

	public SFYDocumentAnalyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(SFYDocumentAnalyzer analyzer) {
		this.analyzer = analyzer;
	}

	public int getTotalNumberOfInstances() {

		int nInstances = 0;

		for (Entry<String, ArrayList<SFYInstance>> catInstances : getInstances()
				.entrySet()) {
			nInstances += catInstances.getValue().size();
		}

		return nInstances;
	}

}
