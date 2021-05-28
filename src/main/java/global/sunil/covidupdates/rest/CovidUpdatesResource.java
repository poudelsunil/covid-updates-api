package global.sunil.covidupdates.rest;

import global.sunil.covidupdates.lib.utils.RestResponse;
import global.sunil.covidupdates.repositories.CovidUpdatesRepository;
import global.sunil.covidupdates.repositories.dtos.GetCountryWiseCovidInfoRequest;
import global.sunil.covidupdates.rest.adaptors.GetCountryWiseCovidInfoRequestAdaptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sunil on 2021-05-25 - १४:०६
 */
@RequestScoped
@Path("/covidupdates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class CovidUpdatesResource {

    CovidUpdatesRepository covidUpdatesRepository;
    GetCountryWiseCovidInfoRequestAdaptor getCountryWiseCovidInfoRequestAdaptor;

    @Inject
    CovidUpdatesResource(CovidUpdatesRepository covidUpdatesRepository,
                         GetCountryWiseCovidInfoRequestAdaptor getCountryWiseCovidInfoRequestAdaptor){
        this.covidUpdatesRepository = covidUpdatesRepository;
        this.getCountryWiseCovidInfoRequestAdaptor = getCountryWiseCovidInfoRequestAdaptor;
    }

    @GET
    @Path("countries")
    public Response getCountries(){

        return RestResponse.ok(covidUpdatesRepository.getCountries());
    }

    @GET
    @Path("country/{countryIso2}")
    public Response getCountries(@PathParam("countryIso2") String iso2){

        GetCountryWiseCovidInfoRequest request = this.getCountryWiseCovidInfoRequestAdaptor.toServiceObject(null);
        request.setIso2(iso2);
        return RestResponse.ok(covidUpdatesRepository.getCountryWiseCovidInfo(request));
    }
}
