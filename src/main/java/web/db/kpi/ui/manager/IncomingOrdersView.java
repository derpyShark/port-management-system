package web.db.kpi.ui.manager;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import web.db.kpi.backend.enums.CraneAction;
import web.db.kpi.backend.enums.MachineryType;
import web.db.kpi.backend.enums.OrderStatus;
import web.db.kpi.backend.models.CraneOrder;
import web.db.kpi.backend.models.IncomingHandlingOrder;
import web.db.kpi.backend.models.Machinery;
import web.db.kpi.backend.models.VehicleOrder;
import web.db.kpi.backend.services.CraneOrderService;
import web.db.kpi.backend.services.IncomingHandlingOrderService;
import web.db.kpi.backend.services.MachineryService;
import web.db.kpi.backend.services.VehicleOrderService;
import web.db.kpi.ui.admin.AdminLayout;

import java.text.SimpleDateFormat;

@Route(value = "incomingOrders", layout = AdminLayout.class)
public class IncomingOrdersView extends VerticalLayout {

    private IncomingHandlingOrderService incomingHandlingOrderService;
    private CraneOrderService craneOrderService;
    private VehicleOrderService vehicleOrderService;
    private MachineryService machineryService;

    public IncomingOrdersView(IncomingHandlingOrderService incomingHandlingOrderService,
                              CraneOrderService craneOrderService,
                              VehicleOrderService vehicleOrderService,
                              MachineryService machineryService){
        this.incomingHandlingOrderService = incomingHandlingOrderService;
        this.craneOrderService = craneOrderService;
        this.vehicleOrderService = vehicleOrderService;
        this.machineryService = machineryService;
        Grid<IncomingHandlingOrder> grid = addGrid();
        add(new H1("Incoming orders management"),addFields(grid), grid);
    }

    private HorizontalLayout addFields(Grid<IncomingHandlingOrder> grid){
        HorizontalLayout horDataEntry = new HorizontalLayout();
        TextField orderField = new TextField();
        orderField.setLabel("Order id");
        TextField craneField = new TextField();
        craneField.setLabel("Crane login");
        Button sendOrders = new Button("Send");
        sendOrders.addClickListener(click->{
            boolean shouldSend = true;
            IncomingHandlingOrder handlingOrder = null;
            Machinery crane = null;
            try{
                crane = machineryService.getByLogin(craneField.getValue());
                handlingOrder =incomingHandlingOrderService.getById(
                        Long.parseLong(orderField.getValue())) ;
                if(crane.getMachineryType() != MachineryType.CRANE ||
                handlingOrder.getOrderStatus() != OrderStatus.PENDING)shouldSend = false;
            }
            catch(Exception e){
                shouldSend = false;
            }
            if(shouldSend){
                handlingOrder.setOrderStatus(OrderStatus.ACCEPTED);
                try{
                    incomingHandlingOrderService.update(handlingOrder.getId(), handlingOrder);
                }
                catch (Exception e){
                    Notification notification = new Notification("Something really bad happened");
                }
                VehicleOrder incomingVehicleOrder = VehicleOrder.builder()
                        .orderStatus(OrderStatus.PENDING)
                        .crane(crane)
                        .transport(handlingOrder.getIncomingTransport())
                        .build();
                VehicleOrder outgoingVehicleOrder = VehicleOrder.builder()
                        .orderStatus(OrderStatus.PENDING)
                        .crane(crane)
                        .transport(handlingOrder.getOutgoingTransport())
                        .build();
                CraneOrder incomingCraneOrder = CraneOrder.builder()
                        .crane(crane)
                        .craneAction(CraneAction.UNLOAD)
                        .orderStatus(OrderStatus.PENDING)
                        .cargoContainer(handlingOrder.getCargoContainer())
                        .transport(handlingOrder.getIncomingTransport())
                        .build();
                CraneOrder outgoingCraneOrder = CraneOrder.builder()
                        .crane(crane)
                        .craneAction(CraneAction.LOAD)
                        .orderStatus(OrderStatus.PENDING)
                        .cargoContainer(handlingOrder.getCargoContainer())
                        .transport(handlingOrder.getOutgoingTransport())
                        .build();
                vehicleOrderService.create(incomingVehicleOrder);
                vehicleOrderService.create(outgoingVehicleOrder);
                craneOrderService.create(incomingCraneOrder);
                craneOrderService.create(outgoingCraneOrder);
                grid.setItems(incomingHandlingOrderService.getPending());
            }
        });
        horDataEntry.add(
                orderField,craneField,sendOrders
        );
        horDataEntry.setDefaultVerticalComponentAlignment(Alignment.END);
        return horDataEntry;
    }


    private Grid<IncomingHandlingOrder> addGrid(){
        Grid<IncomingHandlingOrder> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(user->user.getId()).setHeader("Id");
        grid.addColumn(user->user.getIncomingTransport().getMachineryLogin()).setHeader("Incoming login");
        grid.addColumn(user->user.getOutgoingTransport().getMachineryLogin()).setHeader("Outgoing login");
        grid.addColumn(user->user.getCargoContainer().getCargoType()).setHeader("Cargo type");
        grid.addColumn(user->user.getCargoContainer().getCargoWeight()).setHeader("Cargo weight");
        grid.addColumn(user->simpleDateFormat.format(user.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createRejectButton(grid, item)).setHeader("Action");
        grid.setItems(incomingHandlingOrderService.getPending());
        return grid;
    }

    private Button createRejectButton(Grid<IncomingHandlingOrder> grid, IncomingHandlingOrder item) {
        return new Button("Reject", clickEvent -> {
            IncomingHandlingOrder rejectedOrder = new IncomingHandlingOrder(item);
            rejectedOrder.setOrderStatus(OrderStatus.REJECTED);
            try{
                incomingHandlingOrderService.update(item.getId(), rejectedOrder);
            }
            catch(Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(incomingHandlingOrderService.getPending());
        });
    }
}
