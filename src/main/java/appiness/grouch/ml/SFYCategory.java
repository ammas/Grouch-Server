package appiness.grouch.ml;

import java.util.ArrayList;
import java.util.UUID;

public class SFYCategory {

	private SFYCategory parent;
	private ArrayList<SFYCategory> subCategories;

	private String id;
	private String name;

	// Ctors
	public SFYCategory() {
		this(UUID.randomUUID().toString(), null);
	}

	public SFYCategory(String Name) {
		this(UUID.randomUUID().toString(), Name);
	}

	public SFYCategory(String ID, String Name) {
		setId(ID);
		setName(Name);
	}

	// Setters and Getters
	public SFYCategory getParent() {
		return parent;
	}

	public void setParent(SFYCategory parent) {
		this.parent = parent;

		if (parent == null || parent.getSubCategories().contains(this))
			return;

		parent.getSubCategories().add(this);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SFYCategory> getSubCategories() {
		if (subCategories == null)
			subCategories = new ArrayList<SFYCategory>();
		return subCategories;
	}

	public void setSubCategories(ArrayList<SFYCategory> subCategories) {
		this.subCategories = subCategories;
	}

}
