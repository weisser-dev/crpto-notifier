package cryptodealer;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import cryptodealer.exchanges.BinanceExchange;
import cryptodealer.exchanges.Exchange;
import cryptodealer.exchanges.PoloniexExchange;

@Path("/")
public class SuggestionServlet {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String suggestions(@QueryParam("exchange") String exchangeName) {

		Exchange exchange = new BinanceExchange();
		if ("poloniex".equalsIgnoreCase(exchangeName)) {
			exchange = new PoloniexExchange();
		} else if ("binance".equalsIgnoreCase(exchangeName)) {
			exchange = new BinanceExchange();
		}

		List<Suggestion> suggestions = Suggestor.loadSuggestions(exchange);

		return new Gson().toJson(suggestions);
	}
}
