package web.db.kpi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.models.VehicleOrder;

import java.util.List;

@Repository
public interface VehicleOrderRepository extends JpaRepository<VehicleOrder, Long> {
    @Query("select c from VehicleOrder c where c.orderStatus = 0")
    List<VehicleOrder> findPending();
}
