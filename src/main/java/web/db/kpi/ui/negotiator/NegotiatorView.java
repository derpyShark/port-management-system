package web.db.kpi.ui.negotiator;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import web.db.kpi.backend.enums.MachineryType;
import web.db.kpi.backend.enums.OrderStatus;
import web.db.kpi.backend.models.CargoContainer;
import web.db.kpi.backend.models.IncomingHandlingOrder;
import web.db.kpi.backend.models.Machinery;
import web.db.kpi.backend.services.CargoContainerService;
import web.db.kpi.backend.services.IncomingHandlingOrderService;
import web.db.kpi.backend.services.MachineryService;
import web.db.kpi.ui.admin.AdminLayout;

import java.text.SimpleDateFormat;

@Route(value = "negotiator", layout = AdminLayout.class)
public class NegotiatorView extends VerticalLayout {

    private CargoContainerService cargoContainerService;
    private IncomingHandlingOrderService incomingHandlingOrderService;
    private MachineryService machineryService;

    public NegotiatorView(IncomingHandlingOrderService incomingHandlingOrderService,
                          CargoContainerService cargoContainerService,
                          MachineryService machineryService){
        this.incomingHandlingOrderService = incomingHandlingOrderService;
        this.cargoContainerService = cargoContainerService;
        this.machineryService = machineryService;
        Grid<IncomingHandlingOrder> grid = addGrid();
        add(new H1("Negotiations"), addFields(grid), grid);
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
        grid.addColumn(user->user.getOrderStatus()).setHeader("Status");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Action");
        grid.setItems(incomingHandlingOrderService.getAll());
        return grid;
    }

    private HorizontalLayout addFields(Grid<IncomingHandlingOrder> handlingOrderGrid){ ;
        HorizontalLayout horDataEntry = new HorizontalLayout();
        TextField incomingField = new TextField();
        incomingField.setLabel("Incoming login");
        TextField outgoingField = new TextField();
        outgoingField.setLabel("Outgoing login");
        TextField typeField = new TextField();
        typeField.setLabel("Cargo type");
        TextField weightField = new TextField();
        weightField.setLabel("Cargo weight");
        Button addHandlingOrder = new Button("Add");
        addHandlingOrder.addClickListener(click->{
            boolean shouldAdd = true;
            Machinery incoming = null;
            Machinery outgoing = null;
            try{
                incoming = machineryService.getByLogin(incomingField.getValue());
                outgoing = machineryService.getByLogin(outgoingField.getValue());
                Float.parseFloat(weightField.getValue());
                if(incomingField.getValue() == outgoingField.getValue()
                ||incoming.getMachineryType() == MachineryType.CRANE
                ||outgoing.getMachineryType() == MachineryType.CRANE)
                    shouldAdd = false;
            }
            catch(Exception e){
                shouldAdd = false;
            }
            if(shouldAdd){
                if((typeField.getValue() != null || !typeField.getValue().isEmpty())&&
                        (weightField.getValue() != null || !weightField.getValue().isEmpty())){
                    CargoContainer cargoContainer = cargoContainerService.create(
                            CargoContainer.builder()
                            .cargoType(typeField.getValue())
                            .cargoWeight(Float.parseFloat(weightField.getValue()))
                            .build()
                    );
                    incomingHandlingOrderService.create(
                            IncomingHandlingOrder.builder()
                            .orderStatus(OrderStatus.PENDING)
                            .incomingTransport(incoming)
                            .outgoingTransport(outgoing)
                            .cargoContainer(cargoContainer)
                            .build()
                    );
                    handlingOrderGrid.setItems(incomingHandlingOrderService.getAll());
                }
            }

        });
        horDataEntry.add(
                incomingField,outgoingField,typeField,weightField,addHandlingOrder
        );
        horDataEntry.setDefaultVerticalComponentAlignment(Alignment.END);
        return horDataEntry;
    }

    private Button createRemoveButton(Grid<IncomingHandlingOrder> grid, IncomingHandlingOrder item) {
        return new Button("Remove", clickEvent -> {
            try{
                incomingHandlingOrderService.delete(item.getId());
            }
            catch (Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(incomingHandlingOrderService.getAll());
        });
    }



}
