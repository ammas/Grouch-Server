package appiness.grouch;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import appiness.grouch.query.CouncilMaterialQuery;
import appiness.grouch.query.ProductSFYBayesSearch;

public class BayesSearchTest {

	private EntityManagerFactory emf;

	@Before
	public void createEMF() {
		emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
	}

	@Test
	public void testSearch() throws Exception {
		ProductSFYBayesSearch search = new ProductSFYBayesSearch(emf);
	
		CouncilMaterialQuery query = new CouncilMaterialQuery(emf, search);
		System.out.println(query.query("e6b5c35e-c789-4718-9e58-09806db7e0a1", "fruit can").productName);
		
		
	}

}
