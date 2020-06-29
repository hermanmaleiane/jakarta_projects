package com.challenge.herman.rest;



import com.challenge.herman.model.Country;
import com.challenge.herman.service.CountryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/countries")
public class CountryController {
	
	CountryService countryService=new CountryService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountries()
	{
		
		List<Country> listOfCountries=countryService.getAllCountries();
		return listOfCountries;
	}

    @GET
    @Path("/random/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getCountryById(@PathParam("id") int id)
	{
		 Country country= countryService.getCountry(id);

		return Response.ok(country).build();

	}
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Country addCountry(Country country)
	{
		return countryService.addCountry(country);
	}

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	public Country updateCountry(Country country)
	{
		return countryService.updateCountry(country);
		
	}
	
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public void deleteCountry(@PathParam("id") int id)
	{
		 countryService.deleteCountry(id);
		
	}
	
}
