package hu.csani.application.service;

import java.io.IOException;
import java.util.List;

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
import org.springframework.stereotype.Service;

import hu.csani.application.model.http.HttpZipatoResponse;
import hu.csani.application.schedule.Scheduler;

@Service
public class HttpService {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	public HttpZipatoResponse httpGET(String url) {
		return httpGET(url, null);
	}

	public HttpZipatoResponse httpGET(String url, List<Cookie> cookies) {
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
//			System.out.println(response);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

		/* check cookies */
		List<Cookie> responseCookies = httpCookieStore.getCookies();
		return new HttpZipatoResponse(response, responseCookies);
	}

	public HttpZipatoResponse httpPUT(String url, List<Cookie> cookies, String json)
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

		log.info(url);
		log.info(json);
//		System.out.println("Executing request " + httpPut.getRequestLine());

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

}
