package hu.csani.application.presenter.model;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import lombok.Data;

@Data
public class DeviceView extends HorizontalLayout {

//	private HorizontalLayout level1;
	private Device device;

	private TextField descriptionLabel = new TextField();
	private TextField nameLabel = new TextField();

	public DeviceView(Device device) {
		this.device = device;

		nameLabel.setLabel("Name");
		descriptionLabel.setLabel("Description");

		nameLabel.setReadOnly(true);
		nameLabel.setValue(device.getName());

		descriptionLabel.setReadOnly(true);
//		descriptionLabel.setValue(device.getDescription());

		List<Attribute> deviceAttributes = device.getDeviceAttributes();

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setHeight("100%");
		verticalLayout.setWidth(null);

		for (Attribute attribute : deviceAttributes) {
			TextField nameAttribute = new TextField();
			TextField valueAttribute = new TextField();
			nameAttribute.setLabel("Name");
			valueAttribute.setLabel("Value");

			nameAttribute.setValue(attribute.getName());

			valueAttribute.setValue("" + attribute.getValue().getValue());
			HorizontalLayout horizontalLayout = new HorizontalLayout(/*nameAttribute,*/ valueAttribute);
			
			nameAttribute.setReadOnly(true);
			valueAttribute.setReadOnly(true);
			
			horizontalLayout.setSpacing(true);
			verticalLayout.add(horizontalLayout);

		}

		add(nameLabel, verticalLayout);

//		grid.removeColumnByKey("id");

// The Grid<>(Person.class) sorts the properties and in order to
// reorder the properties we use the 'setColumns' method.
	}

}
