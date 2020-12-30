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
@Table(name = "port_incoming_handling_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingHandlingOrder {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "incoming_transports")
    private Machinery incomingTransport;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "outgoing_transports")
    private Machinery outgoingTransport;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cargo_containers")
    private CargoContainer cargoContainer;
    @NotNull
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @NotNull
    @Column(name = "vehicle_order_time_created")
    private Date timeCreated;

    public IncomingHandlingOrder(IncomingHandlingOrder inc){
        this.id = inc.id;
        this.incomingTransport = inc.incomingTransport;
        this.outgoingTransport = inc.outgoingTransport;
        this.cargoContainer = inc.cargoContainer;
        this.timeCreated = inc.timeCreated;
    }


}
