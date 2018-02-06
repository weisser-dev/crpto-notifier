package cryptodealer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import cryptodealer.exchanges.Exchange;

@Path("/")
public class CurrenciesServlet {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String currencies(@QueryParam("exchange") String exchangeName) {

		Map<String, List<String>> currencies = new HashMap<>();

		for (Exchange exchange : ExchangeFactory.ensureExchanges(exchangeName)) {
			currencies.put(exchange.name(), exchange.currencies());
		}

		return new Gson().toJson(currencies);
	}
}
