package hu.csani.application.presenter;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "admin", layout = MainView.class)
public class AdminView extends Div {
	public AdminView() {
		add(new Span("Admin view content"));
	}
}