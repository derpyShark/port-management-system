package web.db.kpi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.models.CraneOrder;

import java.util.List;

@Repository
public interface CraneOrderRepository extends JpaRepository<CraneOrder, Long> {
    @Query("select c from CraneOrder c where c.orderStatus = 0")
    List<CraneOrder> findPending();
}

