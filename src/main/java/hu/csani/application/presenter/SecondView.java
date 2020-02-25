package hu.csani.application.presenter;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "asd")
public class SecondView extends VerticalLayout {

	public SecondView() {
		add(new Text("Cica to MainView."));
	}

}