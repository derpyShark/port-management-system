package web.db.kpi.ui.admin;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.codec.digest.DigestUtils;
import web.db.kpi.backend.models.User;
import web.db.kpi.backend.services.UserService;

import java.text.SimpleDateFormat;

@Route(value = "users", layout = AdminLayout.class)
public class UserView extends VerticalLayout {

    private UserService userService;

    public UserView(UserService userService){
        this.userService = userService;
        Grid<User> grid = addGrid();
        add(new H1("Users"), addFields(grid), grid);
    }

    private Grid<User> addGrid(){
        Grid<User> grid = new Grid<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        grid.addColumn(user->user.getUserId()).setHeader("Id");
        grid.addColumn(user->user.getLogin()).setHeader("Login");
        grid.addColumn(user->user.getName()).setHeader("Name");
        grid.addColumn(user->user.getSurname()).setHeader("Surname");
        grid.addColumn(user->simpleDateFormat.format(user.getTimeCreated())).setHeader("Time Created");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Action");
        grid.setItems(userService.getAll());
        return grid;
    }

    private HorizontalLayout addFields(Grid<User> userGrid){ ;
        HorizontalLayout horDataEntry = new HorizontalLayout();
        TextField nameField = new TextField();
        nameField.setLabel("User name");
        TextField surnameField = new TextField();
        surnameField.setLabel("User surname");
        TextField loginField = new TextField();
        loginField.setLabel("User login");
        TextField passwordField = new TextField();
        passwordField.setLabel("User password");
        Button addUserButton = new Button("Add");
        addUserButton.addClickListener(click->{
            boolean loginExist = true;
            try{
                userService.getByLogin(loginField.getValue());
            }
            catch(Exception e){
                loginExist = false;
            }
            if(!loginExist){
                if(passwordField.getValue() != null || !passwordField.getValue().isEmpty()){
                    userService.create(
                            User.builder()
                                    .login(loginField.getValue())
                                    .passwordHash(DigestUtils.sha1Hex(passwordField.getValue()))
                                    .name(nameField.getValue())
                                    .surname(surnameField.getValue())
                                    .build()
                    );
                    userGrid.setItems(userService.getAll());
                }
            }

        });
        horDataEntry.add(
                loginField,passwordField,nameField,surnameField,addUserButton
        );
        horDataEntry.setDefaultVerticalComponentAlignment(Alignment.END);
        return horDataEntry;
    }

    private Button createRemoveButton(Grid<User> grid, User item) {
        return new Button("Remove", clickEvent -> {
            try{
                userService.delete(item.getUserId());
            }
            catch (Exception e){
                Notification notification = new Notification("Something went wrong");
            }
            grid.setItems(userService.getAll());
        });
    }
}
