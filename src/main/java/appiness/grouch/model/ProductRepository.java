package appiness.grouch.model;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProductRepository {

	private final EntityManager em;

	public ProductRepository(EntityManagerFactory emf) {
		this.em = emf.createEntityManager();
	}

	public Product loadById(UUID id) {

		Product product = em.find(Product.class, id.toString());

		return product;
	}

}
