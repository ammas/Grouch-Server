package appiness.grouch.ml;


public interface SFYIClassifier {

	// Setters and Getters

	// Training Data
	void setTrainingData(SFYDataSet data);

	SFYDataSet getTrainingData();

	//
	void train();

	SFYClassificationResultSet classify(SFYInstance instance);

}
