package appiness.grouch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Material {


	@Id
	private final String id;
	private final String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Council_Material", joinColumns = { @JoinColumn(name = "councilId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "materialId", nullable = false, updatable = false) })
	private List<Council> councils;

	
	private Material() {
		this(UUID.randomUUID(),"");
	}

	public Material(UUID id, String name) {
		super();
		this.id = id.toString();
		this.name = name;
		councils = new ArrayList<Council>();
	}

}
