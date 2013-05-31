package appiness.grouch.web;

import java.util.EnumSet;

import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				binder().requireExplicitBindings();

				install(new GrouchModule());

				bind(GuiceFilter.class);

			}
		});

		Server server = new Server(Integer.valueOf(System.getenv("PORT")));

		ServletContextHandler handler = new ServletContextHandler();
		handler.setContextPath("/");

		handler.addServlet(new ServletHolder(new InvalidRequestServlet()), "/*");

		FilterHolder guiceFilter = new FilterHolder(
				injector.getInstance(GuiceFilter.class));
		handler.addFilter(guiceFilter, "/*",
				EnumSet.allOf(DispatcherType.class));

		server.setHandler(handler);
		server.start();
	}
}
