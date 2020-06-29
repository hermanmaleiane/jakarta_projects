package com.challenge.herman.rest;



import com.challenge.herman.model.GenericResponse;
import com.challenge.herman.model.HistoryResponse;
import com.challenge.herman.model.PendingResponse;
import com.challenge.herman.model.StatsResponse;
import com.challenge.herman.service.NumberGeneratorService;
import com.challenge.herman.util.DelayMe;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

;import static com.challenge.herman.util.Constants.X_MAX_WAIT;
import static com.challenge.herman.util.Constants.X_REQUEST_DURATION;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class NumberGeneratorRest {

    @Inject
    private NumberGeneratorService numberGeneratorService;

    @Context
    private HttpHeaders httpHeaders;

    @POST
    @Path("random")
    public Response generateRandomNumber() {
        long startTime = System.currentTimeMillis();
        String xMaxWait = httpHeaders.getHeaderString(X_MAX_WAIT);
        numberGeneratorService.requestRandomNumber(xMaxWait);
        GenericResponse genericResponse = new GenericResponse(200, "SUCCESS", "The random number request was submitted sucessfully");
        long elapsedTime = System.currentTimeMillis() - startTime;
        String formatedTime = numberGeneratorService.formatTime(elapsedTime);

        return Response.ok(genericResponse).header(X_REQUEST_DURATION, formatedTime).build();
    }

    @GET
    @Path("history")
    @DelayMe(time = 2000)
    public Response history() {
        long startTime = System.currentTimeMillis();
        List<HistoryResponse> randomNumberList = numberGeneratorService.getHistory();
        long elapsedTime = System.currentTimeMillis() - startTime;
        String formatedTime = numberGeneratorService.formatTime(elapsedTime);

        return Response.ok(randomNumberList).header(X_REQUEST_DURATION, formatedTime).build();
    }

    @PUT
    @Path("{requestId}/cancel")
    public Response cancel(@PathParam("requestId") String requestId) {
        long startTime = System.currentTimeMillis();
        GenericResponse genericResponse = numberGeneratorService.cancelRequest(requestId);
        long elapsedTime = System.currentTimeMillis() - startTime;

        String formatedTime = numberGeneratorService.formatTime(elapsedTime);

        return Response.ok(genericResponse).header(X_REQUEST_DURATION, formatedTime).build();

    }

    @GET
    @Path("stats")
    @DelayMe
    public Response stats() {
        long startTime = System.currentTimeMillis();
        StatsResponse statsResponse = numberGeneratorService.getStats();
        long elapsedTime = System.currentTimeMillis() - startTime;

        String formatedTime = numberGeneratorService.formatTime(elapsedTime);

        return Response.ok(statsResponse).header(X_REQUEST_DURATION, formatedTime).build();
    }

    @GET
    @Path("pending")
    public Response pending() {
        long startTime = System.currentTimeMillis();
        List<PendingResponse> pendingResponseList = numberGeneratorService.getPending();
        long elapsedTime = System.currentTimeMillis() - startTime;

        String formatedTime = numberGeneratorService.formatTime(elapsedTime);

        return Response.ok(pendingResponseList).header(X_REQUEST_DURATION, formatedTime).build();
    }

    @PUT
    @Path("threads")
    public Response threads(@QueryParam("size")
                            @Min(value = 1, message = "Minimum allowed thread pool size is 1")
                            @Max(value = 10, message = "Maximum allowed thread pool size is 10")
                            @NotNull(message = "Size cannot be null") int size) {
        long startTime = System.currentTimeMillis();
        GenericResponse response = numberGeneratorService.setThreadSize(size);
        long elapsedTime = System.currentTimeMillis() - startTime;

        String formatedTime = numberGeneratorService.formatTime(elapsedTime);
        return Response.ok(response).header(X_REQUEST_DURATION, formatedTime).build();
    }

    @PUT
    @Path("requestDuration")
    public Response requestDuration(@QueryParam("time")
                                    @Min(value = 30,
                                            message = "Minimum allowed random number generation " +
                                                    "request duration cannot be less than 30 seconds")
                                    @NotNull(message = "Time cannot be null") long time) {
        long startTime = System.currentTimeMillis();
        GenericResponse response = numberGeneratorService.setMinRequestDuration(time);
        long elapsedTime = System.currentTimeMillis() - startTime;

        String formatedTime = numberGeneratorService.formatTime(elapsedTime);
        return Response.ok(response).header(X_REQUEST_DURATION, formatedTime).build();
    }

}