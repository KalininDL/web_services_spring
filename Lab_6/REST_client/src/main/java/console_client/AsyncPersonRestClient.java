package console_client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AsyncPersonRestClient {

    private final Gson gson = new Gson();
    private final JsonParser jsonParser = new JsonParser();
    private final String URI;

    public AsyncPersonRestClient(String uri) {
        target = ClientBuilder.newClient().target(uri);
        this.URI = uri;
    }

    private final InvocationCallback<Response> callback = new InvocationCallback<Response>() {
        @Override
        public void completed(Response response) {
            response.bufferEntity();
            if (response.getStatus() == 200) {
                System.out.println("New person added successfully\n" + response.readEntity(String.class));
            } else {
                System.out.println("Server error");
                System.out.println(response.readEntity(String.class));
            }
        }

        @Override
        public void failed(Throwable throwable) {
            System.out.println("Request failed!");
        }
    };

    private WebTarget personToQueryParams(Person p) {
        WebTarget webResource = ClientBuilder.newClient().target(URI);
        if (p.getName() != null) webResource = webResource.queryParam("name", p.getName());
        if (p.getSurname() != null) webResource = webResource.queryParam("surname", p.getSurname());
        if (p.getAge() != 0) webResource = webResource.queryParam("age", String.valueOf(p.getAge()));
        else webResource = webResource.queryParam("age", "0");
        if (p.getCountry() != null) webResource = webResource.queryParam("country", p.getCountry());
        if (p.getGender() != null) webResource = webResource.queryParam("gender", p.getGender());
        if (p.getId() != 0) webResource = webResource.queryParam("id", String.valueOf(p.getId()));
        else webResource = webResource.queryParam("id", "0");
        return webResource;
    }

    private final WebTarget target;

    private void processError(Response response) {
        if (response.getStatus() == 404) {
            System.out.println("No person found by given query params");
        } else {
            response.bufferEntity();
            String x = response.readEntity(String.class);
            System.out.println(x);
            throw new IllegalStateException("Request failed");
        }
    }

    private List<Person> processJSON(Response response) throws ParseException {
        List<Person> list;
        String x = response.readEntity(String.class);
        JsonElement jsonContainer = jsonParser.parse(x);
        JsonElement jsonQuery = ((JsonObject) jsonContainer).get("person");
        if (jsonQuery.isJsonArray()) {
            list = gson.fromJson(jsonQuery, new TypeToken<List<Person>>() {
            }.getType());
        } else if (jsonQuery.isJsonObject()) {
            Person result = gson.fromJson(jsonQuery, Person.class);
            list = new ArrayList<>();
            list.add(result);
        } else throw new ParseException("Unable to parse input json", 1);
        return list;
    }

    private List<Person> processResponse(Response response) {
        List<Person> list = new ArrayList<>();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            processError(response);
            return list;
        } else {
            try {
                return processJSON(response);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }


    public void getPersonsAsync(Person query) {
        WebTarget target = personToQueryParams(query);
        target
                .path("persons")
                .queryParam("id", 1)
                .request()
                .async()
                .get(new InvocationCallback<Response>() {
                    @Override
                    public void completed(Response response) {
                        processResponse(response).forEach(System.out::println);
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        System.out.println("Request failed!");
                    }
                });
    }

    public void addPersonAsync(Person person) {
        target
                .path("persons")
                .request()
                .async()
                .post(Entity.entity(person, MediaType.APPLICATION_JSON),
                        new InvocationCallback<Response>() {
                            @Override
                            public void completed(Response response) {
                                response.bufferEntity();
                                if (response.getStatus() == 200) {
                                    System.out.println("New person added successfully\n" + response.readEntity(String.class));
                                } else {
                                    System.out.println("Server error");
                                    System.out.println(response.readEntity(String.class));
                                }
                            }

                            @Override
                            public void failed(Throwable throwable) {
                                System.out.println("Request failed!");
                            }
                        });

    }

    public void updatePersonAsync(int id, Person newPerson) {
        WebTarget target = ClientBuilder.newClient().target(URI);
        target
                .queryParam("id", String.valueOf(id))
                .path("persons")
                .request()
                .async()
                .put(Entity.entity(newPerson, MediaType.APPLICATION_JSON), callback);
    }

    public void deletePersonAsync(int id) {
        WebTarget target = ClientBuilder.newClient().target(URI);
        target
                .path("person")
                .queryParam("id", id)
                .request()
                .async()
                .delete(callback);
    }
}
