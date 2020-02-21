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
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hu.csani.application.model.dao.DeviceEntity;
import hu.csani.application.model.http.HttpZipatoResponse;
import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import hu.csani.application.model.zipato.DeviceDetails;
import hu.csani.application.model.zipato.ZipatoResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Service
@Data
public class ZipatoService {

	public static String BASE_URL = "https://my.zipato.com/zipato-web/v2/";

//	@Setter(AccessLevel.PUBLIC)
	private String USERNAME = "csanad.farkas90@gmail.com";
	@Getter(AccessLevel.PRIVATE)
	private String PASSWORD = "1234qwer";

	private HttpZipatoResponse authenticatedHeader;
	private ZipatoResponse login;

	private Map<String, Device> devices;
	private Map<String, DeviceDetails> devicesDetails;

	private Map<String, DeviceEntity> deviceEntitys;

	private Map<String, Attribute> attributeEntitys;

	public String generateAuthHeader() throws IOException {
		HttpZipatoResponse initResponse = httpGET("https://my.zipato.com/zipato-web/v2/user/init");

		Gson gson = new Gson();
		ZipatoResponse init = gson.fromJson(initResponse.getResponse(), ZipatoResponse.class);
		String token = calculateToken(init.getNonce(), PASSWORD);

		HttpZipatoResponse loginResponse = httpGET(
				"https://my.zipato.com/zipato-web/v2/user/login" + "?username=" + USERNAME + "&token=" + token,
				initResponse.getCookies());
		login = gson.fromJson(loginResponse.getResponse(), ZipatoResponse.class);

		authenticatedHeader = loginResponse;

		return authenticatedHeader.toString();

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
		System.err.println(connection.getResponseCode());
		System.err.println(connection.getResponseMessage());
		return connection.getResponseCode() + "  " + connection.getResponseMessage();
	}

	public String calculateToken(String nonce, String password) {
//		LOGGER.log(Level.INFO, "Init nonce: " + nonce);
		String sha1Password = sha1(password);
//		LOGGER.log(Level.INFO, "sha1Password: " + sha1Password);
		System.err.println(sha1Password);
		String token = sha1(nonce + sha1Password);
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

//		System.out.println("The sha1 of \"" + value + "\" is:");
//		System.out.println(sha1);
//		System.out.println();
		return sha1;
	}

	private HttpZipatoResponse httpGET(String url) {
		return httpGET(url, null);
	}

	private HttpZipatoResponse httpGET(String url, List<Cookie> cookies) {
		/* init client */
		HttpClient http = null;
		CookieStore httpCookieStore = new BasicCookieStore();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				httpCookieStore.addCookie(cookie);
			}
		}
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		http = builder.build();
		/* do stuff */
		HttpGet httpRequest = new HttpGet(url);
		httpRequest.setHeader("Accept", "application/json");
		httpRequest.setHeader("Content-type", "application/json");
		HttpZipatoResponse httpResponse = null;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = "";
		try {
			response = http.execute(httpRequest, responseHandler);
			System.out.println(response);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

		/* check cookies */
		List<Cookie> responseCookies = httpCookieStore.getCookies();
		return new HttpZipatoResponse(response, responseCookies);
	}

	private HttpZipatoResponse httpPUT(String url, List<Cookie> cookies, String json)
			throws ClientProtocolException, IOException {

		CookieStore httpCookieStore = new BasicCookieStore();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				httpCookieStore.addCookie(cookie);
			}
		}
		CloseableHttpClient httpclient = null;
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		httpclient = builder.build();

		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		StringEntity stringEntity = new StringEntity(json);
		httpPut.setEntity(stringEntity);

		System.out.println("Executing request " + httpPut.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};
		String responseBody = httpclient.execute(httpPut, responseHandler);
		System.out.println("----------------------------------------");
		System.out.println(responseBody);

		return new HttpZipatoResponse(responseBody, null);
	}

	public String test() throws IOException {

		HttpZipatoResponse rawResponse = httpGET("https://my.zipato.com/zipato-web/v2/user/init");

		Gson gson = new Gson();
		ZipatoResponse init = gson.fromJson(rawResponse.getResponse(), ZipatoResponse.class);
		String token = calculateToken(init.getNonce(), PASSWORD);

		HttpZipatoResponse rawResponse2 = httpGET("https://my.zipato.com/zipato-web/v2/user/login"
				+ "?username=csanad.farkas90@gmail.com" + "&token=" + token, rawResponse.getCookies());
		System.out.println("FINAL" + rawResponse2.getResponse());
		System.out.println(init.getJsessionid());
		System.out.println(httpGET("https://my.zipato.com/zipato-web/v2/thermostats", rawResponse2.getCookies()));
		ZipatoResponse login = gson.fromJson(rawResponse2.getResponse(), ZipatoResponse.class);
		System.out.println(login.getJsessionid());
		test(login.getJsessionid());

		HttpZipatoResponse httpPUT = httpPUT(
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
		System.err.println(connection.getResponseCode());
		System.err.println(connection.getResponseMessage());
	}

	public String refreshAllDevice() throws LoginException {
		if (authenticatedHeader == null) {
			throw new LoginException("Please auth first");
		}

		HttpZipatoResponse rawResponse = httpGET("https://my.zipato.com/zipato-web/v2/devices",
				authenticatedHeader.getCookies());

		Gson gson = new Gson();

		Device[] mcArray = gson.fromJson(rawResponse.getResponse(), Device[].class);

		devices = new HashMap<>();
		for (Device device : mcArray) {
			devices.put(device.getUuid(), device);
		}

		deviceEntitys = new HashMap<>();
		for (Device device : mcArray) {
			HttpZipatoResponse deviceResponse = httpGET("https://my.zipato.com:443/zipato-web/v2/devices/"
					+ device.getUuid()
					+ "?network=false&endpoints=false&type=false&config=false&state=false&icons=true&info=false&descriptor=false&room=true&unsupported=false",
					authenticatedHeader.getCookies());
			DeviceDetails deviceDetails = gson.fromJson(deviceResponse.getResponse(), DeviceDetails.class);

//			devicesDetails.put(device.getUuid(), deviceDetails);

			DeviceEntity deviceEntity = new DeviceEntity();
			deviceEntity.setUuid(device.getUuid());
			deviceEntity.setName(device.getName());
			deviceEntity.setDevice(device);
			if (deviceDetails.getRoom() != null)
				deviceEntity.setRoom(deviceDetails.getRoom().getName());
			if (deviceDetails.getIcon() != null)
				deviceEntity.setType(deviceDetails.getIcon().getEndpointType());
			if (deviceEntity.getType() != null)
				deviceEntitys.put(device.getUuid() + " " + deviceDetails.getUuid(), deviceEntity);

		}

		return deviceEntitys.toString();
	}

	public List<DeviceEntity> getDevicesByType(String type) {
		List<DeviceEntity> devices = new ArrayList<>();

		for (DeviceEntity device : deviceEntitys.values()) {
			if (device.getType().equals(type))
				devices.add(device);
		}

		return devices;
	}

	public List<Attribute> refreshAllAttribute() throws LoginException {
		if (authenticatedHeader == null) {
			throw new LoginException("Please auth first  @  ... /force-auth");
		}
		if (devices == null) {
			throw new NullPointerException(
					"Devices list is NULL please refresh all devices first @ ... /devices/refresh");
		}

		HttpZipatoResponse rawResponse = httpGET("https://my.zipato.com:443/zipato-web/v2/attributes",
				authenticatedHeader.getCookies());

		Gson gson = new Gson();

		Attribute[] mcArray = gson.fromJson(rawResponse.getResponse(), Attribute[].class);

		for (Attribute attribute : mcArray) {
			HttpZipatoResponse attributeResponse = httpGET("https://my.zipato.com:443/zipato-web/v2/attributes/"
					+ attribute.getUuid()
					+ "?network=false&device=true&endpoint=false&clusterEndpoint=false&definition=false&config=false&room=false&icons=false&value=true&parent=false&children=false&full=false&type=false",
					authenticatedHeader.getCookies());
			Attribute attributeDetails = gson.fromJson(attributeResponse.getResponse(), Attribute.class);
			attribute = attributeDetails;

		}

		for (Attribute attirbute : mcArray) {
			Device device = devices.get(attirbute.getDevice());
			if (device != null)
				device.getDeviceAttributes().add(attirbute);
		}

		return Arrays.asList(mcArray);
	}

}
