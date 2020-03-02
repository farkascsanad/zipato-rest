/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.csani.application.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hu.csani.application.service.ZipatoService;
import lombok.Data;

@Component
@Data
public class Scheduler {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private Map<DayTime, Set<Task>> tasks = new HashMap<>();

	private ExecutorService executor = Executors.newFixedThreadPool(5);

	// This lock helps to avoid reauthentication and run job at the same time
	private final Object lock = new Object();

	@Autowired
	private ZipatoService zipatoService;

	@Scheduled(cron = "0 * * * * *")
	public void reportCurrentTime() {

		synchronized (lock) {
			LocalTime truncatedTo = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
			DayTime key = new DayTime( LocalDate.now().getDayOfWeek(),truncatedTo);
			Set<Task> list = tasks.get(key);
			log.info("Task todo this minute: " + (list != null ? list.size() : 0));
			if (list != null) {
				for (Task task : list) {
					executor.execute(task);
					log.info("Task executed: " + task);
				}
			}
		}
	}

	// Every 5 min the this schedulder renew the credentials
	@Scheduled(cron = "0 */5 * * * *")
	public void updateCredentials() {

		synchronized (lock) {

			try {
				zipatoService.generateAuthHeader();
				log.info("Re-atuh success {}", dateFormat.format(new Date()));
			} catch (LoginException | IOException e) {
//				e.printStackTrace();
			}
		}

	}

}