package appiness.grouch.web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import appiness.grouch.query.ProductQuery;
import appiness.grouch.query.ProductSFYBayesSearch;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class GrouchModule extends AbstractModule {

	@Override
	protected void configure() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("defaultPersistenceUnit");

		bind(EntityManagerFactory.class).toInstance(emf);
		bind(ProductQuery.class).to(ProductSFYBayesSearch.class).in(
				Scopes.SINGLETON);

		install(new ApiServletModule());

	}

}
