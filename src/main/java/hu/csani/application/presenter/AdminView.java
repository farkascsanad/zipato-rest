package hu.csani.application.presenter;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import hu.csani.application.service.ZipatoService;

@Route(value = "admin", layout = MainView.class)
public class AdminView extends VerticalLayout {

	@Autowired
	ZipatoService zipatoService;

	private EmailField emailField;
	private PasswordField passwordField;

	public AdminView() {

		add(new Span("Admin view content"));

		emailField = new EmailField("Email");
		emailField.setValue("csanad.farkas90@gmail.com");
		passwordField = new PasswordField();
		passwordField.setValue("");

		emailField.setClearButtonVisible(true);
		emailField.setErrorMessage("Please enter a valid email address");

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout horizontalLayout = new HorizontalLayout();

		passwordField.setLabel("Password");

		horizontalLayout.add(emailField, passwordField);

		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);

		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		verticalLayout.add(horizontalLayout);

		verticalLayout.add(new Button("Authenticate", e -> setCredentialsAndAuth()));

		add(verticalLayout);

//		return "Done";
	}

	private Object setCredentialsAndAuth() {

		zipatoService.setUSERNAME(emailField.getValue());
		zipatoService.setPASSWORD(passwordField.getValue());

		try {
			String generateAuthHeader = zipatoService.generateAuthHeader();
			Notification notification = new Notification("Sikeres belépés" + generateAuthHeader, 30000);
			notification.setPosition(Position.MIDDLE);
			notification.open();
		} catch (IOException | LoginException e) {
			new Notification("Sikertelen belépés" +e.getMessage(), 5000).open();
			e.printStackTrace();
		}
		return null;
	}
}