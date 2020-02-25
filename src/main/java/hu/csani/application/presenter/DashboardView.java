package hu.csani.application.presenter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = MainView.class)
public class DashboardView extends Div {
	
	
	
	public DashboardView() {
		add(new Span("Dashboard view content"));
		
		add(new Button("Refresh All"));
	}
}