package web.db.kpi.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import web.db.kpi.backend.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table(name = "port_role_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUser {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "users")
    private User user;
    @NotNull
    @Column(name = "roles")
    private Role role;
    @NotNull
    @Column(name = "time_role_user_created")
    private Date timeCreated;
}
