package appiness.grouch.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	private final String id;
	private final String name;
	private final String comment;
	private final String materialId;

	public Product(UUID id, String name, String comment, UUID materialId) {
		super();
		this.id = id.toString();
		this.name = name;
		this.comment = comment;
		if (materialId != null) {
			this.materialId = materialId.toString();
		} else {
			this.materialId = null;
		}
	}

	private Product() {
		this(UUID.randomUUID(), "", "", null);
	}

}
