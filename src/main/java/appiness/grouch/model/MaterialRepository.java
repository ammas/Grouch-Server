package appiness.grouch.model;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MaterialRepository {

	private final EntityManager em;

	public MaterialRepository(EntityManagerFactory emf) {
		this.em = emf.createEntityManager();
	}

	public Material loadById(UUID id) {
		Material material = em.find(Material.class, id.toString());
		return material;
	}

}
