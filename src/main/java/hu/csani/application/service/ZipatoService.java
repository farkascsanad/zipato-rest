package hu.csani.application.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import hu.csani.application.model.dao.DeviceEntity;
import hu.csani.application.model.http.HttpZipatoResponse;
import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import hu.csani.application.model.zipato.ZipatoResponse;
import hu.csani.application.schedule.Scheduler;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Service
@Data
public class ZipatoService {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	public static String BASE_URL = "https://my.zipato.com/zipato-web/v2/";

//	@Setter(AccessLevel.PUBLIC)
	private String USERNAME = "csanad.farkas90@gmail.com";
	@Getter(AccessLevel.PRIVATE)
	private String PASSWORD = "1234qwer";

	private HttpZipatoResponse authenticatedHeader;
	private ZipatoResponse login;

	private Map<String, Device> devices;

//	private Map<String, DeviceEntity> deviceEntitys;

	private Map<String, Attribute> attributeEntitys;

	@Autowired
	HttpService httpService;

	public String generateAuthHeader() throws IOException, LoginException {
		HttpZipatoResponse initResponse = httpService.httpGET("https://my.zipato.com/zipato-web/v2/user/init");

		Gson gson = new Gson();
		ZipatoResponse init = gson.fromJson(initResponse.getResponse(), ZipatoResponse.class);
		String token = calculateToken(init.getNonce(), PASSWORD);

		HttpZipatoResponse loginResponse = httpService.httpGET(
				"https://my.zipato.com/zipato-web/v2/user/login" + "?username=" + USERNAME + "&token=" + token,
				initResponse.getCookies());
		login = gson.fromJson(loginResponse.getResponse(), ZipatoResponse.class);

		if (login.getSuccess()) {
			authenticatedHeader = loginResponse;
			return login.toString();
		} else {
			throw new LoginException(loginResponse.toString());
		}

	}

	public String setValue(String uuid, Integer value) throws LoginException, IOException {

		if (login == null)
			throw new LoginException("Please auth first");

		URL url = new URL("https://my.zipato.com/zipato-web/v2/attributes/" + uuid + "/value");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String myCookie = "JSESSIONID=" + login.getJsessionid();
//			Add the cookie to a request: Using the setRequestProperty(String name, String value); method, we will add a property named "Cookie", passing the cookie string created in the previous step as the property value.
		connection.setRequestProperty("Cookie", myCookie);

		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write("{\"value\":" + value + "}");
		osw.flush();
		osw.close();
//		System.err.println(connection.getResponseCode());
		log.info("Connection response code: " + connection.getResponseCode() + " message: "
				+ connection.getResponseMessage());
//		System.err.println(connection.getResponseMessage());
		return connection.getResponseCode() + "  " + connection.getResponseMessage();
	}

	public String calculateToken(String nonce, String password) {
//		LOGGER.log(Level.INFO, "Init nonce: " + nonce);
		String sha1Password = sha1(password);
//		LOGGER.log(Level.INFO, "sha1Password: " + sha1Password);
//		System.err.println(sha1Password);
		String token = sha1(nonce + sha1Password);
		log.info("Token: " + token);
		return token;
	}

	public String sha1(String value) {
		String sha1 = "";
		// With the java libraries
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(value.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sha1;
	}

	public String test() throws IOException {

		HttpZipatoResponse rawResponse = httpService.httpGET("https://my.zipato.com/zipato-web/v2/user/init");

		Gson gson = new Gson();
		ZipatoResponse init = gson.fromJson(rawResponse.getResponse(), ZipatoResponse.class);
		String token = calculateToken(init.getNonce(), PASSWORD);

		HttpZipatoResponse rawResponse2 = httpService.httpGET("https://my.zipato.com/zipato-web/v2/user/login"
				+ "?username=csanad.farkas90@gmail.com" + "&token=" + token, rawResponse.getCookies());
		System.out.println("FINAL" + rawResponse2.getResponse());
		System.out.println(init.getJsessionid());
		System.out.println(
				httpService.httpGET("https://my.zipato.com/zipato-web/v2/thermostats", rawResponse2.getCookies()));
		ZipatoResponse login = gson.fromJson(rawResponse2.getResponse(), ZipatoResponse.class);
		System.out.println(login.getJsessionid());
		test(login.getJsessionid());

		HttpZipatoResponse httpPUT = httpService.httpPUT(
				"https://my.zipato.com/zipato-web/v2/attributes/d0fbf5f6-b391-48b9-8d80-141182f8d83c/value",
				rawResponse2.getCookies(), "{\"value\":90}");
		return httpPUT.toString();
	}

	public void test(String jsessionID) throws IOException {
//		LOGGER.log(Level.INFO, "Start " + jsessionID);

		URL url = new URL("https://my.zipato.com/zipato-web/v2/attributes/d0fbf5f6-b391-48b9-8d80-141182f8d83c/value");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String myCookie = "JSESSIONID=" + jsessionID;
//		Add the cookie to a request: Using the setRequestProperty(String name, String value); method, we will add a property named "Cookie", passing the cookie string created in the previous step as the property value.

		connection.setRequestProperty("Cookie", myCookie);

		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write("{\"value\":90}");
//		osw.write("{\"value\":90}");
		osw.flush();
		osw.close();
		log.info("Connection response code: " + connection.getResponseCode() + " message: "
				+ connection.getResponseMessage());
	}

	public String refreshAllDevice() throws LoginException {
		if (authenticatedHeader == null) {
			throw new LoginException("Please auth first");
		}

		HttpZipatoResponse rawResponse = httpService.httpGET("https://my.zipato.com/zipato-web/v2/devices",
				authenticatedHeader.getCookies());

		devices = new HashMap<>();
		Gson gson = new Gson();

		JsonArray array = gson.fromJson(rawResponse.getResponse(), JsonArray.class);
		for (JsonElement jsonElement : array) {
			JsonObject asJsonObject = jsonElement.getAsJsonObject();
			String uuid = asJsonObject.get("uuid").getAsString();
			Device device = new Device();
			device.setUuid(uuid);
			if (uuid.equals("3031ba41-4fef-4232-9c66-28c3c4134583"))
			devices.put(uuid, device);
		}

//		Device[] mcArray = gson.fromJson(rawResponse.getResponse(), Device[].class);

//		for (Device device : mcArray) {
//			devices.put(device.getUuid(), device);
//		}

//		deviceEntitys = new HashMap<>();
		for (Device device : devices.values()) {
			HttpZipatoResponse deviceResponse = httpService
					.httpGET(
							"https://my.zipato.com:443/zipato-web/v2/devices/" + device.getUuid()
									+ "?endpoints=true&config=true&icons=true&room=true",
							authenticatedHeader.getCookies());
			Device deviceDetails = gson.fromJson(deviceResponse.getResponse(), Device.class);

			device = gson.fromJson(deviceResponse.getResponse(), Device.class);

			devices.put(device.getUuid(), deviceDetails);

			// devicesDetails.put(device.getUuid(), deviceDetails);

//			DeviceEntity deviceEntity = new DeviceEntity();
//			deviceEntity.setUuid(device.getUuid());
////			deviceEntity.setName(device.getName());//TODO!
//			deviceEntity.setDevice(device);
//			if (deviceDetails.getRoom() != null) {
////				deviceEntity.setRoom((deviceDetails.getRoom().getName());
//			}
//			if (deviceDetails.getIcon() != null)
//				deviceEntity.setType(deviceDetails.getIcon().getEndpointType());
//			if (deviceEntity.getType() != null)
//				deviceEntitys.put(device.getUuid(), deviceEntity);

		}

		return devices.toString();
	}

	public List<Device> getDevicesByType(String type) {
		List<Device> devices = new ArrayList<>();

		for (Device device : this.devices.values()) {
			if (isRelevantDevice(device)) {
				devices.add(device);
			}
		}

//		for (DeviceEntity device : deviceEntitys.values()) {
//			if (device.getType().equals(type))
//				devices.add(device);
//		}

		return devices;
	}

	/**
	 * Refresh all Attributes from zipato API
	 * 
	 * @return List of Attributes
	 * @throws LoginException If the login wasnt success the function can not run
	 */
	public List<Attribute> refreshAllAttribute() throws LoginException {
		if (authenticatedHeader == null) {
			throw new LoginException("Please auth first  @  ... /force-auth");
		}
		if (devices == null) {
			throw new NullPointerException(
					"Devices list is NULL please refresh all devices first @ ... /devices/refresh");
		}

		HttpZipatoResponse rawResponse = httpService.httpGET("https://my.zipato.com:443/zipato-web/v2/attributes",
				authenticatedHeader.getCookies());

		GsonBuilder builder = new GsonBuilder();
		builder.setExclusionStrategies(new HiddenAnnotationExclusionStrategy());
		Gson gson = builder.create();

		Attribute[] mcArray = gson.fromJson(rawResponse.getResponse(), Attribute[].class);

		List<Attribute> attributeList = Arrays.asList(mcArray);

		for (int i = 0; i < attributeList.size(); i++) {
			Attribute attribute = attributeList.get(i);
			if (!isRelevantAttribute(attribute)) {
				continue;
			}
			HttpZipatoResponse attributeResponse = httpService.httpGET(
					"https://my.zipato.com:443/zipato-web/v2/attributes/" + attribute.getUuid()
							+ "?network=false&device=true&endpoint=false&clusterEndpoint=false&definition=false&config=false&room=false&icons=false&value=true&parent=false&children=false&full=false&type=false",
					authenticatedHeader.getCookies());
			System.out.println(attributeResponse.getResponse());
			Attribute attributeDetails = gson.fromJson(attributeResponse.getResponse(), Attribute.class);
			// attribute = attributeDetails;
			attributeList.set(i, attributeDetails); // why the heck is not working the reference update????

		}

		for (Attribute attirbute : attributeList) {
			if (attirbute.getDevice() != null) {
				Device device = devices.get(attirbute.getDevice().getUuid());
				if (device != null)
					device.getDeviceAttributes().add(attirbute);
			}
		}

		return attributeList;
	}

	/*
	 * IF you want to add more relevant attribute types paste here.
	 */
	public boolean isRelevantAttribute(Attribute attribute) {

		List<String> relevantAttributes = Arrays.asList("LEVEL");

		if (relevantAttributes.contains(attribute.getName())) { // Shutters
			return true;
		}
		return false;
	}

	public boolean isRelevantDevice(Device device) {

		List<String> relevantAttributes = Arrays.asList("actuator.shutters");

		if (device.getIcon() != null && device.getIcon().getEndpointType() != null
				&& relevantAttributes.contains(device.getIcon().getEndpointType())) { // Shutters
			return true;
		}
		return false;
	}

}
