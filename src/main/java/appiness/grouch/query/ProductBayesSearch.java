package appiness.grouch.query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import cc.mallet.classify.Classification;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.NaiveBayes;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class ProductBayesSearch implements ProductQuery {

	private final EntityManagerFactory emf;
	private final Classifier classifier;

	@Inject
	public ProductBayesSearch(EntityManagerFactory emf) {
		this.emf = emf;
		classifier = buildBayesClassifier();
	}

	private InstanceList trainingData() {
		ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

		Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
		pipeList.add(new Input2CharSequence("UTF-8"));
		pipeList.add(new CharSequence2TokenSequence(tokenPattern));
		pipeList.add(new TokenSequenceRemoveStopwords(true, true));
		pipeList.add(new TokenSequence2FeatureSequence());
		pipeList.add(new Target2Label());
		pipeList.add(new FeatureSequence2FeatureVector());

		InstanceList instances = new InstanceList(new SerialPipes(pipeList));

		// loading data from the database
		EntityManager em = emf.createEntityManager();
		javax.persistence.Query query = em
				.createNativeQuery("select p.id productId, p.name productName, m.id materialId, m.name materialName from Product p"
						+ " inner join `Material` m on m.`id` = p.`materialId`");

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {

			Instance instance = new Instance((String) result[1], // data
					(String) result[3],// target
					(String) result[1],// id
					null);

			instances.addThruPipe(instance);
		}

		em.close();

		return instances;
	}

	private Classifier buildBayesClassifier() {

		NaiveBayesTrainer trainer = new NaiveBayesTrainer();

		NaiveBayes bayesClassifier = trainer.train(trainingData());

		return bayesClassifier;
	}

	public QueryResult query(String query) {

		Classification result = classifier.classify(query);

		return (new QueryResult("",result.getLabeling().getBestLabel().toString()
				));

	}
}
