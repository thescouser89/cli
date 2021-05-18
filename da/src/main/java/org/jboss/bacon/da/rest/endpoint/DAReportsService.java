package org.jboss.bacon.da.rest.endpoint;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.da.reports.model.request.LookupGAVsRequest;
import org.jboss.da.reports.model.request.LookupNPMRequest;
import org.jboss.da.reports.model.response.LookupReport;
import org.jboss.da.reports.model.response.NPMLookupReport;
import org.jboss.pnc.bacon.config.Config;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

/**
 * This interface matches the reports interface of Dependency Analysis. This is ultimately used in combination with the
 * Retrofit library to provide a REST client implementation of the Dependency Analysis service
 */
public interface DAReportsService {

    /**
     * Get list of built / aligned Maven artifacts given the list of community GAV to search
     *
     * @param gavsRequest: List of Maven community GAV to search for
     * @return List of built Maven artifacts
     */
    @POST("da/rest/v-1/reports/lookup/gavs")
    Call<List<LookupReport>> lookupGavs(@Body LookupGAVsRequest gavsRequest);

    /**
     * Get list of built / aligned NPM artifacts given the list of community GAV to search
     *
     * @param npmRequest List of NPM community artifacts to search
     * @return List of built NPM artifacts
     */
    @POST("da/rest/v-1/reports/lookup/npm")
    Call<List<NPMLookupReport>> lookupNPM(@Body LookupNPMRequest npmRequest);

    /**
     * Create an instance of the client
     *
     * @return
     */
    static DAReportsService getClient() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.instance().getActiveProfile().getDa().getUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
        return retrofit.create(DAReportsService.class);
    }
}
