package package1;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestCall {

	public static void main(String[] args) {

		try {
//			putCallWithJavaObjectCore();
			putCallWithJavaObjectEnhanced();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void putCallWithJavaObjectCore() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		WebResource webResource = client.resource("http://localhost:8080/student/1");

		// JSON body as java object using POJO class

		RequestPOJO requestPOJO = new RequestPOJO();
		requestPOJO.setId(1);
		requestPOJO.setFirstName("firstname");
		requestPOJO.setLastName("lname");
		requestPOJO.setEmail("1111aaaa@gmail.com");
		requestPOJO.setProgramme("prog");
		String courses[] = { "ca", "cb" };
		requestPOJO.setCourses(courses);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class, requestPOJO);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		} else {
			System.out.println("REST put request is successful");
		}

	}

	public static void putCallWithJavaObjectEnhanced() {

		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

			WebResource webResource = client.resource("http://localhost:8080/student/1");

			// JSON body as java object using POJO class

			RequestPOJO requestPOJO = new RequestPOJO();
			requestPOJO.setId(1);
			requestPOJO.setFirstName("fname");
			requestPOJO.setLastName("lname");
			requestPOJO.setEmail("1111aaaa@gmail.com");
			requestPOJO.setProgramme("prog");
			String courses[] = { "ca", "cb" };
			requestPOJO.setCourses(courses);

			System.out.println("\n###### Request Java Object toString method ####### \n");
			System.out.println(requestPOJO.toString());

			System.out.println("\n#### Covert Java object to JSON to check  whether JSON is created correctly #### \n" );
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(requestPOJO);
			System.out.println(jsonInString);
		
			System.out.println("\n###### Formatted JSON request ####### \n");
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestPOJO));
			
			

			ClientResponse response = webResource.type("application/json").put(ClientResponse.class, requestPOJO);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			System.out.println("\n####### Output from Server ....  ####### \n");
			
			System.out.println("\n####### Receive JSON Response and convert into Java Object ####### \n");
			ResponsePOJO responsePOJO = response.getEntity(ResponsePOJO.class);
			System.out.println("\n###### Response Java Object toString method ####### \n");
			System.out.println(responsePOJO.toString());
			
			
			 System.out.println("  ####### Covert Java object to JSON to check whether JSON is created correctly ####### \n");
			ObjectMapper mapper1 = new ObjectMapper();
			String jsonInString2 = mapper1.writeValueAsString(responsePOJO);
			System.out.println(jsonInString2);
			

			System.out.println("\n###### Formatted JSON response ####### \n");
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responsePOJO));

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
