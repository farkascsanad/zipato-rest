package hu.csani.application.presenter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;

import hu.csani.application.model.zipato.Device;
import hu.csani.application.presenter.model.DeviceComponent;
import hu.csani.application.schedule.Scheduler;
import hu.csani.application.schedule.Task;
import hu.csani.application.service.ZipatoService;

@Route(value = "Schedule", layout = MainView.class)
@Component
public class ScheduleView extends VerticalLayout {

	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	private ZipatoService zipatoService;

	@Autowired
	private Scheduler schedulder;

	public ScheduleView() {
		add(new Text("Cica to MainView."));

		add(new Button("Refresh", e -> refresh()));

	}

	private void refresh() {
		Collection<Device> devices = zipatoService.getDevices().values();

		VerticalLayout verticalLayout = new VerticalLayout();
		for (Device device : devices) {
			if (zipatoService.isRelevantDevice(device)) {
				DeviceComponent deviceComponent = new DeviceComponent(device);
				TimePicker timePicker = new TimePicker("Time");
				timePicker.setLocale(Locale.GERMAN);
				timePicker.setStep(Duration.ofMinutes(5));
				timePicker.setPlaceholder("eg: 10:05");
				TextField textField = new TextField("New Value");

				deviceComponent.add(textField, timePicker, new Button("Add",
						e -> addSchedule(deviceComponent, textField.getValue(), timePicker.getValue())));
				verticalLayout.add(deviceComponent);
			}
		}
		add(verticalLayout);
	}

	private void addSchedule(DeviceComponent deviceComponent, String newValue, LocalTime value) {
		Map<LocalTime, List<Task>> tasks = schedulder.getTasks();
//		List<Task> list = tasks.get(value);
		Task task = new Task();
		task.setAttribute(deviceComponent.getAttribute());
		task.setTime(value);
		task.setNewValue(newValue);
		if (!tasks.containsKey(value))
			tasks.put(value, Arrays.asList(task));
		else {
			List<Task> list = tasks.get(value);
			list.add(task);
			tasks.put(value, list);
		}
		Notification notification = new Notification("Task added" + task, 3000);
		notification.setPosition(Position.MIDDLE);
		notification.open();
		log.info("Task added: " + task);
		log.info(tasks.toString());
	}

}