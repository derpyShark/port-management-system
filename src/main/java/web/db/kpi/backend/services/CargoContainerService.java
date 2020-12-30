package web.db.kpi.backend.services;


import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.ICargoContainerService;
import web.db.kpi.backend.models.CargoContainer;
import web.db.kpi.backend.repositories.CargoContainerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CargoContainerService implements ICargoContainerService {

    @Autowired
    private CargoContainerRepository cargoContainerRepository;

    @Override
    public List<CargoContainer> getAll() {
        return cargoContainerRepository.findAll();
    }

    @Override
    public CargoContainer getById(Long cargoContainerId) throws NotFoundException {
        Optional<CargoContainer> cargoContainerMaybe = cargoContainerRepository.findById(cargoContainerId);
        if(cargoContainerMaybe.isPresent()){
            return cargoContainerMaybe.get();
        }
        else
            throw new NotFoundException("No cargoContainer with such an Id: " + cargoContainerId);
    }

    @Override
    public CargoContainer update(Long cargoContainerId, CargoContainer cargoContainerDetails) throws NotFoundException {
        CargoContainer oldCargoContainer = getById(cargoContainerId);
        oldCargoContainer.setCargoType(cargoContainerDetails.getCargoType());
        oldCargoContainer.setCargoWeight(cargoContainerDetails.getCargoWeight());
        return cargoContainerRepository.save(oldCargoContainer);
    }

    @Override
    public CargoContainer create(CargoContainer cargoContainerObject) {
        return cargoContainerRepository.save(cargoContainerObject);
    }

    @Override
    public void delete(Long machineryId) throws NotFoundException{
        cargoContainerRepository.delete(getById(machineryId));
    }
}
