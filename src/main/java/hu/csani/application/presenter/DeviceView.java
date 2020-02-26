package hu.csani.application.presenter;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import hu.csani.application.model.zipato.Device;
import hu.csani.application.presenter.model.DeviceComponent;
import hu.csani.application.service.ZipatoService;

@Route(value = "device", layout = MainView.class)
@Component
public class DeviceView extends Div {

	@Autowired
	ZipatoService zipatoService;

	private VerticalLayout verticalLayout;

	public DeviceView() {
		add(new Span("Dashboard view content"));

		add(new Button("Refresh All", btn -> refresh()));

	}

	private void refresh() {

		try {
			zipatoService.refreshAllDevice();
			zipatoService.refreshAllAttribute();

			Collection<Device> devices = zipatoService.getDevices().values();

			verticalLayout = new VerticalLayout();
			for (Device device : devices) {
				if (zipatoService.isRelevantDevice(device))
					verticalLayout.add(new DeviceComponent(device));
			}

			verticalLayout.setHeight("100%");
			verticalLayout.setWidth(null);

			add(verticalLayout);

		} catch (LoginException e) {
			e.printStackTrace();
		}

	}
}