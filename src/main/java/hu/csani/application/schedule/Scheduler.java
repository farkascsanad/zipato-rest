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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Scheduler {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private Map<LocalTime, List<Task>> tasks = new HashMap<>();

	private ExecutorService executor = Executors.newFixedThreadPool(5);

	@Scheduled(cron = "0 * * * * *")
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		List<Task> list = tasks.get(LocalTime.now());
		if (list != null) {
			for (Task task : list) {
				executor.execute(task);
				log.info("Task executed: " + task);
			}
		}
	}

}