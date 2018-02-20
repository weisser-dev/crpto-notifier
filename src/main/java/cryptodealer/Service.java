package cryptodealer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class  Service {

	public final static Logger LOGGER = Logger.getLogger(Service.class.getName());
	public static FileHandler fileHandler;
	
	public static void main(String[] args) throws Exception {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");

		fileHandler = new FileHandler("notifier.log");
		LOGGER.addHandler(fileHandler);
		
		
		Server server = new Server(31337);
		server.setHandler(context);

		registerServletsV1(context);

		server.start();
	}

	private static void registerServletsV1(ServletContextHandler context) {

		Map<String, Class<?>> mappings = new HashMap<>();
		mappings.put("currencies", CurrenciesServlet.class);
		mappings.put("suggestions", SuggestionServlet.class);

		registerServlets(context, "v1", mappings);
	}

	private static void registerServlets(ServletContextHandler context, String version,
			Map<String, Class<?>> mappings) {

		for (String key : mappings.keySet()) {
			Class<?> value = mappings.get(key);

			ServletHolder servlet = context.addServlet(ServletContainer.class, "/api/" + version + "/" + key + "/*");
			servlet.setInitParameter("jersey.config.server.provider.classnames", value.getCanonicalName());
		}
	}
}
