package web.db.kpi.ui.admin;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import web.db.kpi.ui.manager.IncomingOrdersView;
import web.db.kpi.ui.negotiator.NegotiatorView;
import web.db.kpi.ui.supervisor.CraneOrdersView;
import web.db.kpi.ui.supervisor.VehicleOrdersView;

public class AdminLayout extends AppLayout {

    public AdminLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Welcome");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink machineryLink = new RouterLink("Admin machinery", MachineryView.class);
        RouterLink usersLink = new RouterLink("Admin users", UserView.class);
        RouterLink roleUsersLink = new RouterLink("Admin role users", RoleUsersView.class);
        RouterLink negotiatorView = new RouterLink("Negotiator", NegotiatorView.class);
        RouterLink incomingOrdersView = new RouterLink("Manager incoming orders", IncomingOrdersView.class);
        RouterLink craneOrdersView = new RouterLink("Crane orders", CraneOrdersView.class);
        RouterLink vehicleOrdersView = new RouterLink("Vehicle orders", VehicleOrdersView.class);
        addToDrawer(new VerticalLayout(
                machineryLink,
                usersLink,
                roleUsersLink,
                negotiatorView,
                incomingOrdersView,
                craneOrdersView,
                vehicleOrdersView
                )
        );
    }

}
