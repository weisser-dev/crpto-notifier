package cryptodealer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import cryptodealer.exchanges.Exchange;

@Path("/")
public class SuggestionServlet {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String suggestions(@QueryParam("exchange") String exchangeName, @QueryParam("interval") String intervalTime) {

		List<Suggestion> suggestions = new ArrayList<>();
		if(isValidInterval(intervalTime)) {
			List<Thread> threads = new ArrayList<>();
			for (Exchange exchange : ExchangeFactory.ensureExchanges(exchangeName)) {
				Runnable run = new Runnable() {
					
					@Override
					public void run() {
						suggestions.addAll(Suggestor.loadSuggestions(exchange, intervalTime));
					}
				};
				Thread temp = new Thread(run);
				threads.add(temp);
			}
			ThreadHandler.startAndWaitForThreads(threads);
	
			return new Gson().toJson(suggestions); 
		} else {
			return new Gson().toJson("{Error: Wrong Interval}");
		}
	}

	private boolean isValidInterval(String intervalTime) {
		if(StringUtils.isBlank(intervalTime) || intervalTime.equals("1m") || intervalTime.equals("5m") || intervalTime.equals("15m") || intervalTime.equals("30m") || intervalTime.equals("1h") || intervalTime.equals("2h") || intervalTime.equals("4h") || intervalTime.equals("1d")) {
			return true;
		}
		return false;
	}
	
}
