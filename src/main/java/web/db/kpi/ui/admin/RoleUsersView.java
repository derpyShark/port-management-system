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
import web.db.kpi.backend.enums.Role;
import web.db.kpi.backend.models.RoleUser;
import web.db.kpi.backend.models.User;
import web.db.kpi.backend.services.RoleUserService;
import web.db.kpi.backend.services.UserService;

import java.text.SimpleDateFormat;

@Route(value = "roleUsers", layout = AdminLayout.class)
public class RoleUsersView extends VerticalLayout {

    private RoleUserService roleUserService;
    private UserService userService;


    public RoleUsersView(UserService userService, RoleUserService roleUserService){
        this.roleUserService = roleUserService;
        this.userService = userService;
        Grid<RoleUser> grid = addGrid();
        add(new H1("Role users"), addFields(grid), grid);
    }


    private Grid<RoleUser> addGrid(){
        Grid<RoleUser> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(roleUser->roleUser.getId()).setHeader("Id");
        grid.addColumn(roleUser->roleUser.getUser().getLogin()).setHeader("Login");
        grid.addColumn(roleUser->roleUser.getRole()).setHeader("Role");
        grid.addColumn(roleUser->simpleDateFormat.format(roleUser.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Action");
        grid.setItems(roleUserService.getAll());
        return grid;
    }

    private HorizontalLayout addFields(Grid<RoleUser> roleUserGrid){
        HorizontalLayout horDataEntry = new HorizontalLayout();
        TextField roleUserLogin = new TextField();
        roleUserLogin.setLabel("User login");
        Select<String> typeSelect = new Select<>();
        Button addRoleUserButton = new Button("Add");
        typeSelect.setItems("Admin", "Manager", "Negotiator", "Supervisor");
        typeSelect.setValue("Admin");
        typeSelect.setLabel("Role");
        addRoleUserButton.addClickListener(click->{
            boolean found = true;
            try{
                roleUserService.getByLoginAndRole(roleUserLogin.getValue(),
                        stringRoleToEnum(typeSelect.getValue()));
            }
            catch(Exception e){
                found = false;
            }
            if(!found){
                try{
                    User user = userService.getByLogin(roleUserLogin.getValue());
                    roleUserService.create(RoleUser.builder()
                            .role(stringRoleToEnum(typeSelect.getValue()))
                            .user(user)
                            .build());
                    roleUserGrid.setItems(roleUserService.getAll());
                }
                catch (Exception e){
                    Notification notification = new Notification("There is no such user");
                }
            }
        });
        horDataEntry.add(roleUserLogin, typeSelect, addRoleUserButton);
        horDataEntry.setDefaultVerticalComponentAlignment(Alignment.END);
        return horDataEntry;
    }

    private Button createRemoveButton(Grid<RoleUser> grid, RoleUser item) {
        return new Button("Remove", clickEvent -> {
            try{
                roleUserService.delete(item.getId());
            }
            catch (Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(roleUserService.getAll());
        });
    }

    private Role stringRoleToEnum(String str){
        Role value = null;
        switch (str){
            case "Admin":
                value = Role.ADMIN;
                break;
            case "Negotiator":
                value = Role.NEGOTIATOR;
                break;
            case "Manager":
                value = Role.MANAGER;
                break;
            case "Supervisor":
                value = Role.SUPERVISOR;
                break;

        }
        return value;
    }

}
