package web.db.kpi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.models.Machinery;

import java.util.Optional;

@Repository
public interface MachineryRepository extends JpaRepository<Machinery, Long> {
    @Query("select c from Machinery c where c.machineryLogin = :term")
    Optional<Machinery> findByLogin(@Param("term") String login);
}
