package hu.csani.application.schedule;

import java.io.IOException;
import java.time.LocalTime;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import hu.csani.application.service.HttpService;
import hu.csani.application.service.ZipatoService;
import lombok.Data;

@Data
@Component
public class Task implements Runnable {

	@Autowired
	private ZipatoService zipatoService;

	@Autowired
	private HttpService httpService;

	private LocalTime time;
	private Attribute attribute;

	private Object newValue;

	@Override
	public void run() {

		try {
			httpService.httpPUT("https://my.zipato.com/zipato-web/v2/attributes/" + attribute.getUuid() + "/value",
					zipatoService.getAuthenticatedHeader().getCookies(), "{value: " + newValue + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}