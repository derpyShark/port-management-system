package web.db.kpi.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import web.db.kpi.backend.enums.MachineryType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table(name = "port_machinery")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Machinery {
    @Id
    @GeneratedValue
    private Long machineryId;
    @NotEmpty
    @NotNull
    @Column(name = "machinery_logins", unique = true)
    private String machineryLogin;
    @NotEmpty
    @NotNull
    @Column(name = "machinery_passwords")
    private String machineryPasswordHash;
    @NotNull
    @Column(name = "machinery_types")
    private MachineryType machineryType;
    @NotNull
    @Column(name = "machinery_time_created")
    private Date timeCreated;
}
