package web.db.kpi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.models.IncomingHandlingOrder;

import java.util.List;

@Repository
public interface IncomingHandlingOrderRepository extends JpaRepository<IncomingHandlingOrder, Long> {
    @Query("select c from IncomingHandlingOrder c where c.orderStatus = 0")
    List<IncomingHandlingOrder> findPending();

    @Query("select c from IncomingHandlingOrder c where c.orderStatus = 1")
    List<IncomingHandlingOrder> findAccepted();
}
