package appiness.grouch.ml;

import java.util.ArrayList;
import java.util.HashMap;

public class SFYCategorySet {

	private HashMap<String, SFYCategory> categories;

	public SFYCategorySet() {
		setCategories(new HashMap<String, SFYCategory>());
	}

	public SFYCategory getCategory(String categoryName) {
		return getCategories().get(categoryName.toLowerCase());
	}

	public SFYCategory createNewCategory(String categoryName) {
		SFYCategory category = new SFYCategory(categoryName);
		getCategories().put(categoryName.toLowerCase(), category);
		return category;
	}

	public SFYCategory getHierarchyCategories(ArrayList<String> hierarchy) {

		String categoryName;
		SFYCategory category = null;
		SFYCategory retCat = null;
		for (int i = 0; i < hierarchy.size(); i++) {

			SFYCategory currentCat = getCategory(categoryName = hierarchy
					.get(i));

			if (currentCat == null) {
				currentCat = createNewCategory(categoryName);

				if (i == 0)
					retCat = category = currentCat;
				else {
					category.setParent(currentCat);
					category = currentCat;
				}

			} else {
				if (i == 0) {
					return currentCat;
				} else {
					category.setParent(currentCat);
					break;
				}
			}

		}

		return retCat;

	}

	public HashMap<String, SFYCategory> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<String, SFYCategory> categories) {
		this.categories = categories;
	}

}
