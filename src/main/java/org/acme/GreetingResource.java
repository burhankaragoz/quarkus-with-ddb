package org.acme;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("api/items")
public class GreetingResource {

    @Inject
    DynamoDBService dbService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> getItems() {
        Iterable<Pet> result;
        result = dbService.getAllItems();
        return StreamSupport.stream(result.spliterator(), false)
                            .collect(Collectors.toUnmodifiableList());
    }

    @POST
    public void addItem(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String type = payload.get("type");
        String owner = payload.get("owner");
        String birthDate = payload.get("birth_date");

        Pet pet = new Pet();
        pet.setName(name);
        pet.setType(type);
        pet.setOwner(owner);
        pet.setBirth_date(birthDate);

        dbService.setItem(pet);
    }

}
