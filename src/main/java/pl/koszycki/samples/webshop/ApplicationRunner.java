package pl.koszycki.samples.webshop;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * Main application class responsible for starting jetty server.
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
public class ApplicationRunner {

  private static final String webappPath = ApplicationRunner.class
      .getClassLoader()
      .getResource("webapp")
      .toExternalForm();
  private static final int PORT = 8080;
  private static final String CONTEXT_PATH = "/webshop";
  private static final String[] jettyConfigurationClasses
      = {
      "org.eclipse.jetty.webapp.WebInfConfiguration",
      "org.eclipse.jetty.webapp.WebXmlConfiguration",
      "org.eclipse.jetty.webapp.MetaInfConfiguration",
      "org.eclipse.jetty.webapp.FragmentConfiguration",
      "org.eclipse.jetty.plus.webapp.EnvConfiguration",
      "org.eclipse.jetty.webapp.JettyWebXmlConfiguration"
  };
  private static HandlerList handlerList;
  private static WebAppContext webapp;
  private static Server server;

  public static void main(String[] args) throws Exception {
    Logger logger = configureLogger();
    initializeServer(PORT, CONTEXT_PATH);
    server.start();
    logger.info("Application avialable at: http://localhost:8080/webshop/");
  }

  private static Logger configureLogger() throws Exception {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    logger.setLevel(Level.INFO);
    return logger;
  }

  private static void initializeServer(int port, String contextPath) throws Exception {
    server = new Server(port);
    ServletContextHandler servletContextHandler = createServletContextHandler();
    servletContextHandler.setInitParameter("cacheControl", "max-age=0,public");
    ResourceHandler resourceHandler = createResourceHandler();
    setUpWebApplicationContext(contextPath);
    prepareHandlers(resourceHandler, servletContextHandler, webapp);
    server.setHandler(handlerList);
  }

  private static ServletContextHandler createServletContextHandler() {
    return new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
  }

  private static void prepareHandlers(Handler... handlers) {
    handlerList = new HandlerList();
    for (Handler handler : handlers) {
      handlerList.addHandler(handler);
    }
  }

  private static ResourceHandler createResourceHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setResourceBase(webappPath);
    resourceHandler.setCacheControl("no-store,no-cache,must-revalidate");
    return resourceHandler;
  }

  private static void setUpWebApplicationContext(String contextPath) {
    webapp = new WebAppContext();
    webapp.setConfigurationClasses(jettyConfigurationClasses);
    webapp.setDescriptor(webappPath + "/WEB-INF/web.xml");
    webapp.setContextPath(contextPath);
    webapp.setResourceBase(webappPath);
    webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
  }

}
