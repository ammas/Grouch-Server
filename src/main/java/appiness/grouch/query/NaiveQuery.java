package appiness.grouch.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class NaiveQuery implements Query {

	private final EntityManagerFactory emf;

	public NaiveQuery(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	public List<QueryResult> query(String queryString) {

		EntityManager em = emf.createEntityManager();
		javax.persistence.Query query = em
				.createNativeQuery("select p.id productId, p.name productName, m.id materialId, m.name materialName from Product p"
						+ " inner join `Material` m on m.`id` = p.`materialId`"
						+ " where  p.name like :productName");
		
		query.setParameter("productName", "%" + queryString + "%");
		

		List results = query.getResultList();
		em.close();

		return null;
	}

}
