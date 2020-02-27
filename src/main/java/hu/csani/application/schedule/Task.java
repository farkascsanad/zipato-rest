package hu.csani.application.schedule;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import hu.csani.application.service.HttpService;
import hu.csani.application.service.ZipatoService;
import lombok.Data;

@Data
public class Task implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	private ZipatoService zipatoService;

	private LocalTime time;
	private Attribute attribute;

	private Set<DayOfWeek> runingDays;

	private Object newValue;

	public Task(ZipatoService zipatoService) {
		this.zipatoService = zipatoService;
	}

	@Override
	public void run() {

		DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
		if (!runingDays.contains(dayOfWeek)) {
			return;
		}

		try {
			zipatoService.getHttpService().httpPUT(
					"https://my.zipato.com/zipato-web/v2/attributes/" + attribute.getUuid() + "/value",
					zipatoService.getAuthenticatedHeader().getCookies(), "{\"value\":" + newValue + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}