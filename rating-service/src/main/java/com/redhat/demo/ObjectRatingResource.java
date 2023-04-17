package com.redhat.demo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/rating")
@Produces(MediaType.APPLICATION_JSON)
public class ObjectRatingResource {

    @Inject
    InteractiveQueries interactiveQueries;

    @GET
    @Path("/{objectId}")
    public ObjectRating fetchObjectScore(@PathParam("objectId") long objectId) {
        var result = interactiveQueries.getObjectRating(objectId);
        if (!result.getResult().isPresent()) throw new NotFoundException();
        return result.getResult().get();
    }
}