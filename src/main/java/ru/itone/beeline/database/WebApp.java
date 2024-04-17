package ru.itone.beeline.database;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.resource.TableResource;

public class WebApp {

    private static Logger log = LoggerFactory.getLogger(WebApp.class);

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://0.0.0.0").port(8880).build();
		ResourceConfig config = new ResourceConfig(TableResource.class);
		Server server = JettyHttpContainerFactory.createServer(baseUri, config,false);

		ContextHandler contextHandler = new ContextHandler("/api");
		contextHandler.setHandler(server.getHandler());
				
		HandlerCollection handlerCollection = new HandlerCollection();
		handlerCollection.setHandlers(new Handler[] {
			contextHandler
		});
		
		server.setHandler(handlerCollection);
		
		server.start();
		server.join();
    }

}
