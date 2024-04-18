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

    private static final String BASE_PATH = "/api";
	private static final int PORT = 8880;
	private static final String HOSTNAME = "http://0.0.0.0";

	private static final Logger log = LoggerFactory.getLogger(WebApp.class);
	
    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri(HOSTNAME).port(PORT).build();
		log.info("Server addr: {}", baseUri + BASE_PATH);
		ResourceConfig config = new ResourceConfig(TableResource.class);
		Server server = JettyHttpContainerFactory.createServer(baseUri, config,false);

		ContextHandler contextHandler = new ContextHandler(BASE_PATH);
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
