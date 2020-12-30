package web.db.kpi.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.models.CargoContainer;

@Repository
public interface CargoContainerRepository extends JpaRepository<CargoContainer, Long> {
}
