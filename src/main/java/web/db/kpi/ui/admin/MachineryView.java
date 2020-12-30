package web.db.kpi.ui.admin;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.codec.digest.DigestUtils;
import web.db.kpi.backend.enums.MachineryType;
import web.db.kpi.backend.models.Machinery;
import web.db.kpi.backend.services.MachineryService;

import java.text.SimpleDateFormat;

@Route(value = "machinery", layout = AdminLayout.class)
public class MachineryView extends VerticalLayout {

    private MachineryService machineryService;

    public MachineryView(MachineryService machineryService){
        this.machineryService = machineryService;
        add(new H1("MachineryMenu"));
        addMachineryLayout();
    }

    private void addMachineryLayout(){
        HorizontalLayout horDataEntry = new HorizontalLayout();
        TextField machineryLoginField = new TextField();
        machineryLoginField.setLabel("Machinery login");
        TextField machineryPasswordField = new TextField();
        machineryPasswordField.setLabel("Machinery password");
        Select<String> typeSelect = new Select<>();
        Button addMachineryButton = new Button("Add");
        typeSelect.setItems("Truck", "Ship", "Crane");
        typeSelect.setValue("Truck");
        typeSelect.setLabel("Type");

        Grid<Machinery> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(machinery->machinery.getMachineryId()).setHeader("Id");
        grid.addColumn(machinery->machinery.getMachineryLogin()).setHeader("Login");
        grid.addColumn(machinery->machinery.getMachineryType()).setHeader("Type");
        grid.addColumn(machinery->simpleDateFormat.format(machinery.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Action");
        grid.setItems(machineryService.getAll());

        addMachineryButton.addClickListener(click -> {
                    boolean loginExist = true;
                    try{
                        machineryService.getByLogin(machineryLoginField.getValue());
                    }
                    catch(Exception e){
                        loginExist = false;
                    }
                    if(!loginExist){
                        if(machineryPasswordField.getValue() != null || !machineryPasswordField.getValue().isEmpty()){
                            machineryService.create(
                                    Machinery.builder()
                                            .machineryLogin(machineryLoginField.getValue())
                                            .machineryPasswordHash(DigestUtils.sha1Hex(machineryPasswordField.getValue()))
                                            .machineryType(stringTypeToEnum(typeSelect.getValue()))
                                            .build()
                            );
                            grid.setItems(machineryService.getAll());
                        }
                    }
        }
        );
        horDataEntry.add(machineryLoginField,machineryPasswordField,typeSelect,addMachineryButton);
        horDataEntry.setDefaultVerticalComponentAlignment(Alignment.END);
        add(horDataEntry,grid);
    }

    private Button createRemoveButton(Grid<Machinery> grid, Machinery item) {
        return new Button("Remove", clickEvent -> {
            try{
                machineryService.delete(item.getMachineryId());
            }
            catch (Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(machineryService.getAll());
        });
    }

    private MachineryType stringTypeToEnum(String str){
        MachineryType value = null;
        switch (str){
            case "Truck":
                value = MachineryType.TRUCK;
                break;
            case "Ship":
                value = MachineryType.SHIP;
                break;
            case "Crane":
                value = MachineryType.CRANE;
            break;
        }
        return value;
    }


}
