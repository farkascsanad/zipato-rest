package hu.csani.application.presenter;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class DefaultView extends Div {
	public DefaultView() {
		add(new Span("Default view content"));
	}
}