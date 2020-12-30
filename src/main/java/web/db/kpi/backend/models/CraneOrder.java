package web.db.kpi.backend.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import web.db.kpi.backend.enums.CraneAction;
import web.db.kpi.backend.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table(name = "port_crane_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CraneOrder {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cranes")
    private Machinery crane;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "transports")
    private Machinery transport;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cargo_containers")
    private CargoContainer cargoContainer;
    @NotNull
    @Column(name = "crane_actions")
    private CraneAction craneAction;
    @NotNull
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @NotNull
    @Column(name = "vehicle_order_time_created")
    private Date timeCreated;

    public CraneOrder(CraneOrder craneOrder){
        this.cargoContainer = craneOrder.cargoContainer;
        this.id = craneOrder.id;
        this.crane = craneOrder.crane;
        this.craneAction = craneOrder.craneAction;
        this.orderStatus = craneOrder.orderStatus;
        this.timeCreated= craneOrder.timeCreated;
        this.transport = craneOrder.transport;
    }

}
