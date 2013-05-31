package appiness.grouch.model;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CouncilRepository {

	private final EntityManager em;

	public CouncilRepository(EntityManagerFactory emf) {
		this.em = emf.createEntityManager();
	}

	public Council loadById(UUID id) {

		Council council = em.find(Council.class, id.toString());

		return council;
	}

}
