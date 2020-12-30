package web.db.kpi.ui.supervisor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import web.db.kpi.backend.enums.OrderStatus;
import web.db.kpi.backend.models.CraneOrder;
import web.db.kpi.backend.services.CraneOrderService;
import web.db.kpi.ui.admin.AdminLayout;

import java.text.SimpleDateFormat;

@Route(value = "craneOrders", layout = AdminLayout.class)
public class CraneOrdersView extends VerticalLayout {
    private CraneOrderService craneOrderService;

    public CraneOrdersView(CraneOrderService craneOrderService){
        this.craneOrderService = craneOrderService;
        Grid<CraneOrder> grid = addGrid();
        add(new H1("Crane Orders"), grid);
    }

    private Grid<CraneOrder> addGrid(){
        Grid<CraneOrder> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(roleUser->roleUser.getId()).setHeader("Id");
        grid.addColumn(roleUser->roleUser.getCrane().getMachineryLogin()).setHeader("Crane login");
        grid.addColumn(roleUser->roleUser.getTransport().getMachineryLogin()).setHeader("Transport login");
        grid.addColumn(roleUser->roleUser.getCargoContainer().getCargoType()).setHeader("Cargo type");
        grid.addColumn(roleUser->roleUser.getCargoContainer().getCargoWeight()).setHeader("Cargo weight");
        grid.addColumn(roleUser->roleUser.getCraneAction()).setHeader("Action transport");
        grid.addColumn(roleUser->simpleDateFormat.format(roleUser.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createFulfillButton(grid, item)).setHeader("Action");
        grid.setItems(craneOrderService.getPending());
        return grid;
    }

    private Button createFulfillButton(Grid<CraneOrder> grid, CraneOrder item) {
        return new Button("Fulfill", clickEvent -> {
            CraneOrder fulfilledOrder = new CraneOrder(item);
            fulfilledOrder.setOrderStatus(OrderStatus.FULFILLED);
            try{
                craneOrderService.update(item.getId(), fulfilledOrder);
            }
            catch(Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(craneOrderService.getPending());
        });
    }
}
