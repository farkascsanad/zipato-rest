package hu.csani.application.presenter;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout implements BeforeEnterObserver {

	private Tabs tabs = new Tabs();
	private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

	public MainView() {

		Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
		img.setHeight("44px");
		addToNavbar(new DrawerToggle(), img);

		addMenuTab("Main", DefaultView.class);
		addMenuTab("Admin", AdminView.class);
		addMenuTab("Device", DeviceView.class);
		addMenuTab("Schedule", ScheduleView.class);
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		addToDrawer(tabs);
//		addToNavbar(new DrawerToggle());

	}

	private void addMenuTab(String label, Class<? extends Component> target) {
		Tab tab = new Tab(new RouterLink(label, target));
		navigationTargetToTab.put(target, tab);
		tabs.add(tab);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
	}
}
