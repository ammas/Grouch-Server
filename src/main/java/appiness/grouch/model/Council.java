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
import javax.persistence.Table;

@Entity
@Table(name = "Council")
public class Council {

	@Id
	private final String id;
	@SuppressWarnings("unused")
	private final String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Council_Material", joinColumns = { @JoinColumn(name = "councilId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "materialId", nullable = false, updatable = false) })
	private List<Material> recyclingMaterials;

	public Council(UUID id, String name) {
		super();
		this.id = id.toString();
		this.name = name;
		this.recyclingMaterials = new ArrayList<Material>();
	}

	@SuppressWarnings("unused")
	private Council() {
		this(UUID.randomUUID(), null);
	}

	public boolean recyclesMaterial(String materialId) {
		for (Material m : recyclingMaterials) {
			if (m.getId().equals(materialId))
				return true;
		}
		return false;
	}
	
	

}
