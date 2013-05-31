package appiness.grouch.ml;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.JsonObject;

public class SFYClassificationResultSet {

	// Fields
	private ArrayList<SFYClassificationResult> results;
	private boolean listModified = false;

	// ctor
	public SFYClassificationResultSet() {
		results = new ArrayList<SFYClassificationResult>();
	}

	public void add(SFYClassificationResult result) {
		results.add(result);
		listModified = true;
	}

	public SFYClassificationResult getResult(int index) {
		checkIntegrity();
		return results.get(index);
	}

	public int getSize() {
		return results.size();
	}

	private void checkIntegrity() {

		if (!listModified)
			return;

		listModified = false;
		Collections.sort(results, new SFYClassificationResultComparator());
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {

//		if (results.size() == 0)
			return null;
//
//		checkIntegrity();
//
//		JsonObject classification = new JsonObject();
//
//		int max = Math.min(results.size(), 3);
//
//		JsonObject subcategories = new JsonObject();
//
//		for (int i = 0; i < max; i++)
//			subcategories.addProperty(results.get(i).toJSON());
//
//		classification.addProperty("list", subcategories);

//		return classification.toJString();
	}

}
