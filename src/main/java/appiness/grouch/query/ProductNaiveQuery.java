package appiness.grouch.query;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProductNaiveQuery implements ProductQuery {

	private final EntityManagerFactory emf;

	@Inject
	public ProductNaiveQuery(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	public QueryResult query(String queryString) {

		EntityManager em = emf.createEntityManager();
		javax.persistence.Query query = em
				.createNativeQuery("select p.id productId, p.name productName, m.id materialId, m.name materialName from Product p"
						+ " inner join `Material` m on m.`id` = p.`materialId`"
						+ " where  p.name like :productName");

		query.setParameter("productName", "%" + queryString + "%");

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		em.close();

		for (Object[] result : results) {
			return (new QueryResult((String) result[0], (String) result[2]));
		}

		return null;

	}

}
