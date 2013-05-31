package appiness.grouch.web;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import appiness.grouch.query.CouncilMaterialQuery;
import appiness.grouch.query.CouncilMaterialQuery.CouncilQueryResult;
import appiness.grouch.query.ProductQuery;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
public class QueryApiServlet {

	private final CouncilMaterialQuery councilQuery;

	@Inject
	QueryApiServlet(EntityManagerFactory emf, ProductQuery productQuery)
			throws Exception {
		councilQuery = new CouncilMaterialQuery(emf, productQuery);

	}

	@Path("/askcharlie")
	@GET
	public CouncilQueryResult smartSearch(@QueryParam("query") String query) {
		return councilQuery
				.query("e6b5c35e-c789-4718-9e58-09806db7e0a1", query);
	}

	@Path("/ping")
	@GET
	public String ping() {
		return "pong";
	}

}
