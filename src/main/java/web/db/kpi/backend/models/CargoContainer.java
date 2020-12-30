package web.db.kpi.backend.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EnableAutoConfiguration
@Entity
@Table(name = "port_cargo_containers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoContainer {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name = "cargo_types")
    private String cargoType;
    @NotNull
    @Column(name = "cargo_weight")
    private float cargoWeight;
}
