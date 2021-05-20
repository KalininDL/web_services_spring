package console_client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RestClient {
    private final Client client;
    private final String URL;
    private final Gson gson = new Gson();
    private final JsonParser p = new JsonParser();

    public RestClient(String URL){
        client = Client.create();
        this.URL = URL;
    }

    private WebResource personToQueryParams(Person p){
        WebResource webResource = client.resource(URL);
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

    private void processError(ClientResponse response){
            String x = response.getEntity(String.class);
            throw new IllegalStateException(x);
    }

    private List<Person> processJSON(ClientResponse response) throws ParseException {
        List<Person> list;
        String x = response.getEntity(String.class);
        JsonElement jsonContainer = p.parse(x);
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

    private List<Person> processResponse(ClientResponse response) throws ParseException, IllegalStateException {
        List<Person> list = new ArrayList<>();
        try {
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                processError(response);
                return list;
            } else {
                return processJSON(response);
            }
        }
        catch (ParseException | IllegalStateException e) {
            throw e;
        }
    }


    public List<Person> getPersons(Person person) throws ParseException {
        WebResource webResource = personToQueryParams(person);
            ClientResponse response =
                    webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            return processResponse(response);
    }

    public boolean addPerson(Person person){
        WebResource webResource = client.resource(URL);
        ClientResponse response =
                webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, person);
        response.bufferEntity();
        System.out.println(response.getEntity(String.class));
        return response.getStatus() == 200;
    }

    public boolean updatePerson(int id, Person newPerson){
        WebResource webResource = client.resource(URL);
        webResource.queryParam("id", String.valueOf(id));
        ClientResponse response =
                webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, newPerson);
        System.out.println(response.getEntity(String.class));
        return response.getStatus() == 200;
    }

    public boolean deletePerson(int id){
        WebResource webResource = client.resource(URL);
        webResource = webResource.queryParam("id", String.valueOf(id));
        ClientResponse response =
                webResource.delete(ClientResponse.class);
        response.bufferEntity();
        System.out.println(response.getEntity(String.class));
        return response.getStatus() == 200;
    }

    private void printList(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person.toString());
        }
    }
}
