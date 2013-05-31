package appiness.grouch.query;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import appiness.grouch.model.Council;
import appiness.grouch.model.CouncilRepository;
import appiness.grouch.model.Material;
import appiness.grouch.model.MaterialRepository;
import appiness.grouch.model.Product;

public class CouncilMaterialQuery {

	public final class CouncilQueryResult {
		@SuppressWarnings("unused")
		private CouncilQueryResult() {
			super();
		}

		public CouncilQueryResult(String message, String materialName,
				String productName, String productTip, String productComment,
				Boolean canRecycle) {
			super();
			this.message = message;
			this.materialName = materialName;
			this.productName = productName;
			this.productTip = productTip;
			this.productComment = productComment;
			this.canRecycle = canRecycle;
		}

		public String message;
		public String materialName;
		public String productName;
		public String productTip;
		public String productComment;

		public Boolean canRecycle;
	}

	private final ProductQuery productQuery;
	private final EntityManagerFactory emf;

	public CouncilMaterialQuery(EntityManagerFactory emf,
			ProductQuery productQuery) {
		this.emf = emf;
		this.productQuery = productQuery;
	}

	public CouncilQueryResult query(String councilId, String prQuery) {

		QueryResult searchResult = productQuery.query(prQuery);

		if (searchResult == null) {
			return new CouncilQueryResult("Product not found !", "", "", "",
					"", false);
		}

		Council c = new CouncilRepository(emf).loadById(UUID
				.fromString(councilId));
		Boolean canRecycle = c.recyclesMaterial(searchResult.getMaterialId());
		Material material = new MaterialRepository(emf).loadById(UUID
				.fromString(searchResult.getMaterialId()));

		Product product = findClosestProduct(prQuery, material.getId());

		return new CouncilQueryResult("", material.getName(),
				product.getName(), product.getTips(), product.getComment(),
				canRecycle);
	}

	private Product findClosestProduct(String productName, String materialId) {

		EntityManager em = emf.createEntityManager();
		javax.persistence.Query query = em.createQuery(
				"Select p from Product p where p.materialId = :materialId",
				Product.class);

		query.setParameter("materialId", materialId);

		@SuppressWarnings("unchecked")
		List<Product> matchingProducts = query.getResultList();
		em.close();

		int minDistance = 100000;
		Product bestMatch = null;

		for (Product match : matchingProducts) {
			int distance = LevenshteinDistance.computeDistance(productName,
					match.getName());
			if (distance < minDistance) {
				minDistance = distance;
				bestMatch = match;
			}
		}

		return bestMatch;
	}
}
