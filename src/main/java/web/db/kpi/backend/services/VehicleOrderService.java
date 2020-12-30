package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.IVehicleOrderService;
import web.db.kpi.backend.models.VehicleOrder;
import web.db.kpi.backend.repositories.VehicleOrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class VehicleOrderService implements IVehicleOrderService {
    @Autowired
    private VehicleOrderRepository vehicleOrderRepository;

    @Override
    public List<VehicleOrder> getAll() {
        return vehicleOrderRepository.findAll();
    }

    @Override
    public VehicleOrder getById(Long vehicleOrderId) throws NotFoundException {
        Optional<VehicleOrder> VehicleOrderMaybe = vehicleOrderRepository.findById(vehicleOrderId);
        if(VehicleOrderMaybe.isPresent()){
            return VehicleOrderMaybe.get();
        }
        else
            throw new NotFoundException("No vehicle order with such an Id: " + vehicleOrderId);
    }

    @Override
    public VehicleOrder update(Long vehicleOrderId, VehicleOrder vehicleOrderDetails) throws NotFoundException {
        VehicleOrder oldVehicleOrder = getById(vehicleOrderId);
        oldVehicleOrder.setCrane(vehicleOrderDetails.getCrane());
        oldVehicleOrder.setOrderStatus(vehicleOrderDetails.getOrderStatus());
        oldVehicleOrder.setTransport(vehicleOrderDetails.getTransport());
        return vehicleOrderRepository.save(oldVehicleOrder);
    }

    @Override
    public VehicleOrder create(VehicleOrder vehicleOrderObject) {
        vehicleOrderObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return vehicleOrderRepository.save(vehicleOrderObject);
    }

    @Override
    public void delete(Long vehicleOrderId) throws NotFoundException{
        vehicleOrderRepository.delete(getById(vehicleOrderId));
    }

    @Override
    public List<VehicleOrder> getPending() {
        return vehicleOrderRepository.findPending();
    }
}
