package appiness.grouch.web;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ApiServletModule extends ServletModule{

    @Override
    protected void configureServlets() {
//        bind(SandwichStatsResource.class);
        bind(QueryApiServlet.class);
//
        // hook Jersey into Guice Servlet
        bind(GuiceContainer.class);

        // hook Jackson into Jersey as the POJO <-> JSON mapper
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        
        serve("/*").with(GuiceContainer.class);
    }
    
}
