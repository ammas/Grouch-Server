package appiness.grouch.ml;

import com.google.gson.JsonObject;

public class SFYClassificationResult {

	private double score;
	private SFYCategory category;
	private SFYClassificationResultSet subCatgeories;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public SFYCategory getCategory() {
		return category;
	}

	public void setCategory(SFYCategory category) {
		this.category = category;
	}

	public SFYClassificationResult() {
		this(0, null);
	}

	public SFYClassificationResult(double Score, SFYCategory Category) {
		setScore(Score);
		setCategory(Category);
	}

	public SFYClassificationResultSet getSubCatgeories() {
		return subCatgeories;
	}

	public void setSubCatgeories(SFYClassificationResultSet subCatgeories) {
		this.subCatgeories = subCatgeories;
	}


	public String toJSON() {

		JsonObject json = new JsonObject();
		json.addProperty("name", getCategory().getName());
		json.addProperty("score", getScore());
		if (getSubCatgeories() != null)
			json.addProperty("subcategories", getSubCatgeories().toJSON());
		return json.toString();
	}

}
