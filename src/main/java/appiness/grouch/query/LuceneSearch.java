package appiness.grouch.query;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import appiness.grouch.ml.SFYClassificationResultSet;
import appiness.grouch.ml.SFYDataSet;
import appiness.grouch.ml.SFYDocument;
import appiness.grouch.ml.SFYDocumentAnalyzer;
import appiness.grouch.ml.SFYNaiveBayesClassifier;

public class LuceneSearch implements ProductQuery {

	private final EntityManagerFactory emf;
	SFYNaiveBayesClassifier classifier;

	@Inject
	public LuceneSearch(EntityManagerFactory emf) throws Exception {
		this.emf = emf;
		classifier = buildBayesClassifier();
	}

	private SFYDataSet trainingData() throws Exception {

		// loading data from the database
		EntityManager em = emf.createEntityManager();
		javax.persistence.Query query = em
				.createNativeQuery("select p.id productId, p.name productName, m.id materialId, m.name materialName from Product p"
						+ " inner join `Material` m on m.`id` = p.`materialId`");

		SFYDataSet instances = new SFYDataSet();
		SFYDocumentAnalyzer analyzer = new SFYDocumentAnalyzer();
		instances.setAnalyzer(analyzer);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {

			SFYDocument doc = new SFYDocument();
			doc.setClassification((String) result[2]);
			doc.setContent((String) result[1]);
			instances.addInstance(analyzer.createInstance(doc));

		}

		em.close();

		return instances;
	}

	private SFYNaiveBayesClassifier buildBayesClassifier() throws Exception {

		SFYNaiveBayesClassifier cl = new SFYNaiveBayesClassifier();
		cl.setTrainingData(trainingData());
		cl.train();
		return cl;

	}

	public QueryResult query(String query) {

		SFYDocument doc = new SFYDocument();
		doc.setContent(query);
		SFYClassificationResultSet result = classifier.classify(doc);

		return (new QueryResult("", result.getResult(0).getCategory().getName()));

	}
}
