package appiness.grouch.ml;

import java.util.Comparator;

public class SFYClassificationResultComparator implements
		Comparator<SFYClassificationResult> {

	public int compare(SFYClassificationResult lhs, SFYClassificationResult rhs) {

		if (lhs.getScore() > rhs.getScore()) {
			return -1;
		}
		if (lhs.getScore() < rhs.getScore()) {
			return 1;
		}
		return 0;

	}
}
