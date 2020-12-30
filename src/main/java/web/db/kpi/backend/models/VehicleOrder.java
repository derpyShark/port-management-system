package web.db.kpi.backend.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import web.db.kpi.backend.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table(name = "port_vehicle_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleOrder {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "transports")
    private Machinery transport;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cranes")
    private Machinery crane;
    @NotNull
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @NotNull
    @Column(name = "vehicle_order_time_created")
    private Date timeCreated;


    public VehicleOrder(VehicleOrder vehicleOrder){
        this.id = vehicleOrder.id;
        this.crane = vehicleOrder.crane;
        this.orderStatus = vehicleOrder.orderStatus;
        this.timeCreated = vehicleOrder.timeCreated;
        this.transport = vehicleOrder.transport;
    }
}
