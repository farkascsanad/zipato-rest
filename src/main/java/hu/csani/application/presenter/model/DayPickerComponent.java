package hu.csani.application.presenter.model;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import lombok.Data;

@Data
public class DayPickerComponent extends HorizontalLayout {

	private Set<DayOfWeek> pickedDays;

	public DayPickerComponent() {

		pickedDays = new HashSet<>();

		for (DayOfWeek d : DayOfWeek.values()) {
			Checkbox checkbox = new Checkbox();
			checkbox.setLabel(d.name());
			checkbox.setValue(true);
			checkbox.addValueChangeListener(event -> updatePickedDays(checkbox));
			add(checkbox);
		}

	}

	private void updatePickedDays(Checkbox checkbox) {

		if (checkbox.getValue()) {
			pickedDays.add(DayOfWeek.valueOf(checkbox.getLabel()));
		} else {
			pickedDays.remove(DayOfWeek.valueOf(checkbox.getLabel()));
		}
	}

}
