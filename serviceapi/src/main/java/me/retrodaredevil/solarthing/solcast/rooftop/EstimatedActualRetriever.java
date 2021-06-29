package me.retrodaredevil.solarthing.solcast.rooftop;

import me.retrodaredevil.solarthing.solcast.SolcastService;
import me.retrodaredevil.solarthing.solcast.common.EstimatedActualResult;
import me.retrodaredevil.solarthing.solcast.common.ForecastResult;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * Should retrieve past estimated actuals or future forecasts seven days in the past and seven days in the future, respectively.
 */
public interface EstimatedActualRetriever {
	EstimatedActualResult retrievePast() throws IOException;
	ForecastResult retrieveForecast() throws IOException;

	static EstimatedActualRetriever createRooftop(SolcastService service, String resourceId) {
		return new EstimatedActualRetriever() {
			@Override
			public EstimatedActualResult retrievePast() throws IOException {
				Call<EstimatedActualResult> call = service.retrieveEstimatedActuals(resourceId);
				Response<EstimatedActualResult> response = call.execute();
				EstimatedActualResult result = response.body();
				if (result == null) {
					throw createException(response);
				}
				return result;
			}

			@Override
			public ForecastResult retrieveForecast() throws IOException {
				Call<ForecastResult> call = service.retrieveForecasts(resourceId);
				Response<ForecastResult> response = call.execute();
				ForecastResult result = response.body();
				if (result == null) {
					throw createException(response);
				}
				return result;
			}
			private IOException createException(Response<?> response) throws IOException {
				String error = requireNonNull(response.errorBody()).string();
				return new IOException("Error! Code: " + response.code() + " message: " + error);
			}
		};
	}
}
