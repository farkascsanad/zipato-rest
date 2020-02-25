package hu.csani.application.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import hu.csani.application.model.zipato.Device;
import hu.csani.application.presenter.model.DeviceView;
import hu.csani.application.service.ZipatoService;

@Route(value = "dashboard", layout = MainView.class)
@Component
public class DashboardView extends Div {

	@Autowired
	ZipatoService zipatoService;

	private VerticalLayout verticalLayout;

	public DashboardView() {
		add(new Span("Dashboard view content"));

		add(new Button("Refresh All", btn -> refresh()));

	}

	private Object refresh() {

		try {
			zipatoService.refreshAllDevice();
			zipatoService.refreshAllAttribute();

			Collection<Device> devices = zipatoService.getDevices().values();

			verticalLayout = new VerticalLayout();
			for (Device device : devices) {
				if (zipatoService.isRelevantDevice(device))
					verticalLayout.add(new DeviceView(device));
			}

			verticalLayout.setHeight("100%");
			verticalLayout.setWidth(null);

			add(verticalLayout);

		} catch (LoginException e) {
			e.printStackTrace();
		}

		return null;
	}
}