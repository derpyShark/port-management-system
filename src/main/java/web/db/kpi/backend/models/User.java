package web.db.kpi.backend.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table(name = "port_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    @NotEmpty
    @NotNull
    @Column(name = "user_login",unique = true)
    private String login;
    @NotEmpty
    @NotNull
    @Column(name = "user_password")
    private String passwordHash;
    @Column(name = "user_names")
    private String name;
    @Column(name = "user_surnames")
    private String surname;
    @NotNull
    @Column(name = "user_time_created")
    private Date timeCreated;

}
