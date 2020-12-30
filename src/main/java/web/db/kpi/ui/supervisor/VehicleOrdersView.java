package web.db.kpi.ui.supervisor;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import web.db.kpi.backend.enums.OrderStatus;
import web.db.kpi.backend.models.VehicleOrder;
import web.db.kpi.backend.services.VehicleOrderService;
import web.db.kpi.ui.admin.AdminLayout;

import java.text.SimpleDateFormat;

@Route(value = "vehicleOrders", layout = AdminLayout.class)
public class VehicleOrdersView extends VerticalLayout {
    private VehicleOrderService vehicleOrderService;

    public VehicleOrdersView(VehicleOrderService vehicleOrderService){
        this.vehicleOrderService = vehicleOrderService;
        Grid<VehicleOrder> grid = addGrid();
        add(new H1("Vehicle Orders"), grid);
    }

    private Grid<VehicleOrder> addGrid(){
        Grid<VehicleOrder> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(roleUser->roleUser.getId()).setHeader("Id");
        grid.addColumn(roleUser->roleUser.getTransport().getMachineryLogin()).setHeader("Transport login");
        grid.addColumn(roleUser->roleUser.getCrane().getMachineryLogin()).setHeader("crane login");
        grid.addColumn(roleUser->simpleDateFormat.format(roleUser.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createFulfillButton(grid, item)).setHeader("Action");
        grid.setItems(vehicleOrderService.getPending());
        return grid;
    }

    private Button createFulfillButton(Grid<VehicleOrder> grid, VehicleOrder item) {
        return new Button("Fulfill", clickEvent -> {
            VehicleOrder fulfilledOrder = new VehicleOrder(item);
            fulfilledOrder.setOrderStatus(OrderStatus.FULFILLED);
            try{
                vehicleOrderService.update(item.getId(), fulfilledOrder);
            }
            catch(Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(vehicleOrderService.getPending());
        });
    }
}
