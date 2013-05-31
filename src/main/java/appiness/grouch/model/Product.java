package appiness.grouch.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Product ")
public class Product {

	@Id
	private final String id;

	private final String name;
	private final String comment;
	private final String tips;

	@SuppressWarnings("unused")
	private final String materialId;

	public Product(UUID id, String name, String comment, String tips,
			UUID materialId) {
		super();
		this.id = id.toString();
		this.name = name;
		this.comment = comment;
		this.tips = tips;
		if (materialId != null) {
			this.materialId = materialId.toString();
		} else {
			this.materialId = null;
		}
	}

	@SuppressWarnings("unused")
	private Product() {
		this(UUID.randomUUID(), "", "", "", null);
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public String getTips() {
		return tips;
	}

}
