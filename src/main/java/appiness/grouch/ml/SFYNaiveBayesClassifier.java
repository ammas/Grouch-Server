package appiness.grouch.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFYNaiveBayesClassifier implements SFYIClassifier {

	Logger logger = LoggerFactory.getLogger(SFYNaiveBayesClassifier.class);

	private class MegaDocument extends SFYInstance {

		private HashMap<String, MegaDocument> subMegaDocuments;

		public MegaDocument() {
			super(null);
		}

		public int getNumberOfInstances() {
			return numberOfInstances;
		}

		public void setNumberOfInstances(int numberOfInstances) {
			this.numberOfInstances = numberOfInstances;
		}

		public void increaseNumberOfInstancesBy(int value) {
			this.numberOfInstances += value;
		}

		public int getNumberOfWords() {
			if (totalNumberOfWords > 0)
				return totalNumberOfWords;

			return totalNumberOfWords = getContentTokens()
					.getTotalNumberOfWords();
		}

		public int getTokenCount(String word) {
			return  getContentTokens().getTokenCount(word);
		}

		public SFYCategory getTargetCategory() {
			return targetCategory;
		}

		public void setTargetCategory(SFYCategory targetCategory) {
			this.targetCategory = targetCategory;
		}

		public double computePriorProbability() {
			if (priorProbability < 0) {
				// TODO
			}

			return priorProbability;
		}

		public void setPriorProbability(double value) {
			priorProbability = value;
		}

		public HashMap<String, MegaDocument> getSubMegaDocuments(
				boolean newInstanceIfDEx) {

			if (subMegaDocuments == null && newInstanceIfDEx)
				setSubMegaDocuments(new HashMap<String, SFYNaiveBayesClassifier.MegaDocument>());
			return subMegaDocuments;
		}

		public HashMap<String, MegaDocument> getSubMegaDocuments() {
			return subMegaDocuments;
		}

		public void setSubMegaDocuments(
				HashMap<String, MegaDocument> subMegaDocuments) {
			this.subMegaDocuments = subMegaDocuments;
		}

		private int numberOfInstances;
		private int totalNumberOfWords = -1;
		private SFYCategory targetCategory;
		private double priorProbability = -1;
	}

	private SFYDataSet trainingSet;
	private HashMap<String, MegaDocument> megaDocuments;

	
	public void setTrainingData(SFYDataSet data) {
		trainingSet = data;

	}

	
	public SFYDataSet getTrainingData() {
		return trainingSet;
	}

	
	public void train() {

		setMegaDocuments(new HashMap<String, MegaDocument>());

		// Starting from parent classes
		// building mega documents

		int totalNumberOfInstances = getTrainingData()
				.getTotalNumberOfInstances();

		SFYCategory category = null;
		for (String categoryName : getTrainingData().getCategories()
				.getCategories().keySet()) {

			category = getTrainingData().getCategories().getCategory(
					categoryName);

			if (category.getParent() != null)
				continue;

			MegaDocument megaDoc = new MegaDocument();
			megaDoc.setTargetCategory(category);
			createCategoryMegaDocument(category, megaDoc);
			megaDoc.setPriorProbability(megaDoc.getNumberOfInstances()
					/ totalNumberOfInstances);
			getMegaDocuments().put(category.getName(), megaDoc);

			// logger.debug("training classifier for {} with {} instances",
			// category.getName(), megaDoc.getNumberOfInstances());
			// logger.debug("with prior prob: {}",
			// megaDoc.computePriorProbability());

		}

	}

	private void createCategoryMegaDocument(SFYCategory category,
			MegaDocument megaDoc) {

		// Adding subCategory samples as well
		for (SFYCategory subCat : category.getSubCategories()) {
			createCategoryMegaDocument(subCat, megaDoc);
		}

		// Adding the instances
		ArrayList<SFYInstance> categorySamples = getTrainingData()
				.getInstances().get(category.getName());

		if (categorySamples == null || categorySamples.size() == 0)
			return;

		MegaDocument categoryMegaDoc = new MegaDocument();
		categoryMegaDoc.setTargetCategory(category);
		for (SFYInstance sample : categorySamples)
			categoryMegaDoc.mergeTokensWith(sample);

		categoryMegaDoc.setNumberOfInstances(categorySamples.size());
		megaDoc.increaseNumberOfInstancesBy(categorySamples.size());
		megaDoc.mergeTokensWith(categoryMegaDoc);

		if (megaDoc.getTargetCategory() != category)
			megaDoc.getSubMegaDocuments(true).put(category.getName(),
					categoryMegaDoc);

	}


	public SFYClassificationResultSet classify(SFYInstance instance) {
		return classify(instance, getMegaDocuments());
	}

	public SFYClassificationResultSet classify(SFYInstance instance,
			HashMap<String, MegaDocument> givenDocs) {
		SFYClassificationResultSet resultSet = new SFYClassificationResultSet();

		for (String key : givenDocs.keySet()) {

			MegaDocument doc = givenDocs.get(key);

			resultSet.add(new SFYClassificationResult(computeProbability(
					instance, doc), doc.getTargetCategory()));

		}

		// Computing the SubCategory of the first one
		if (resultSet.getSize() > 0) {
			SFYClassificationResult res = resultSet.getResult(0);
			if (givenDocs.get(res.getCategory().getName())
					.getSubMegaDocuments() != null)
				resultSet.getResult(0).setSubCatgeories(
						classify(instance,
								givenDocs.get(res.getCategory().getName())
										.getSubMegaDocuments()));
		}

		return resultSet;
	}

	private double computeProbability(SFYInstance instance,
			MegaDocument givenDoc) {

		double normalizer = givenDoc.getNumberOfWords()
				+ trainingSet.getVocabulary().getCarinality() + 1 /*
																 * For unknown
																 * words
																 */;

	
		double contentScore = computeBagOfWordsProbability(
				instance.getContentTokens(), givenDoc, normalizer);

		return (contentScore + givenDoc.computePriorProbability());
	}

	private double computeBagOfWordsProbability(SFYTokenCollection words,
			MegaDocument documentWords, double normalizer) {

		double probability = 0;

		if (words == null || words.getTokens() == null)
			return probability;

		for (Entry<String, Integer> wordPair : words.getTokens().entrySet()) {
			probability += Math.log((1 + wordPair.getValue()
					* documentWords.getTokenCount(wordPair.getKey()))
					/ (normalizer));
		}

		return probability;
	}

	private HashMap<String, MegaDocument> getMegaDocuments() {
		return megaDocuments;
	}

	private void setMegaDocuments(HashMap<String, MegaDocument> megaDocuments) {
		this.megaDocuments = megaDocuments;
	}


	public SFYClassificationResultSet classify(SFYDocument instance) {
		try {
			return classify(getTrainingData().getAnalyzer().createInstance(
					instance));
		} catch (Exception e) {
			return null;
		}
	}

}
