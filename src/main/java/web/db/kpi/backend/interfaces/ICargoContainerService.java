package web.db.kpi.backend.interfaces;


import javassist.NotFoundException;
import web.db.kpi.backend.models.CargoContainer;

import java.util.List;

public interface ICargoContainerService {
    List<CargoContainer> getAll();
    CargoContainer getById(Long cargoContainerId) throws NotFoundException;
    CargoContainer create(CargoContainer cargoContainerObject);
    CargoContainer update(Long cargoContainerId, CargoContainer cargoContainerDetails) throws NotFoundException;
    void delete(Long cargoContainerId) throws NotFoundException;
}
