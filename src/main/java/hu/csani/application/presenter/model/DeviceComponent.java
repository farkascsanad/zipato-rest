package hu.csani.application.presenter.model;

import java.util.List;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.model.zipato.Device;
import lombok.Data;

@Data
public class DeviceComponent extends HorizontalLayout {

//	private HorizontalLayout level1;
	private Device device;

	private TextField descriptionLabel = new TextField();
	private TextField nameLabel = new TextField();

	private Attribute attribute;

	public DeviceComponent(Device device) {
		this.device = device;

		nameLabel.setLabel("Name");
		descriptionLabel.setLabel("Description");

		add(nameLabel);

		nameLabel.setReadOnly(true);
		nameLabel.setValue(device.getConfig().getName()); // TODO

		descriptionLabel.setReadOnly(true);
//		descriptionLabel.setValue(device.getDescription());

		List<Attribute> deviceAttributes = device.getDeviceAttributes();

//		VerticalLayout verticalLayout = new VerticalLayout();
//		verticalLayout.setHeight("100%");
//		verticalLayout.setWidth(null);

//		for (Attribute attribute : deviceAttributes) {

		if (deviceAttributes.size() > 0) {
			attribute = deviceAttributes.get(0); // Only 1 attribute is important.

			TextField nameAttribute = new TextField();
			TextField valueAttribute = new TextField();
			nameAttribute.setLabel("Name");
			valueAttribute.setLabel("Value");

			nameAttribute.setValue(attribute.getName());

			valueAttribute.setValue("" + attribute.getValue().getValue());

			Label uuidLabel = new Label(attribute.getUuid());
			uuidLabel.setVisible(false);

			nameAttribute.setReadOnly(true);
			valueAttribute.setReadOnly(true);

			add(nameAttribute, valueAttribute);
		}

//		horizontalLayout.setSpacing(true);
//		verticalLayout.add(horizontalLayout);

//		}

//		grid.removeColumnByKey("id");

// The Grid<>(Person.class) sorts the properties and in order to
// reorder the properties we use the 'setColumns' method.
	}

}
