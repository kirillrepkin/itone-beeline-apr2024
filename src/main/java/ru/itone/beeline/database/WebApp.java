package ru.itone.beeline.database;

import java.net.URI;
import java.net.URL;
import java.security.ProtectionDomain;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApp {

    private static Logger log = LoggerFactory.getLogger(WebApp.class);

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost").port(8880).build();
		ResourceConfig config = new ResourceConfig(TableResource.class);
		Server server = JettyHttpContainerFactory.createServer(baseUri, config,false);

		ContextHandler contextHandler = new ContextHandler("/rest");
		contextHandler.setHandler(server.getHandler());
		
		ProtectionDomain protectionDomain = WebApp.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setWelcomeFiles(new String[] {"index.html"});
		resourceHandler.setResourceBase(location.toExternalForm());
		System.out.println(location.toExternalForm());
		HandlerCollection handlerCollection = new HandlerCollection();
		handlerCollection.setHandlers(new Handler[] {
            resourceHandler, contextHandler, new DefaultHandler()});
		server.setHandler(handlerCollection);
		
		server.start();
		server.join();
    }

}
