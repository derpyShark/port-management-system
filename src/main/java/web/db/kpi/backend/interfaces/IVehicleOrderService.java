package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.models.VehicleOrder;

import java.util.List;

public interface IVehicleOrderService {
    List<VehicleOrder> getAll();
    VehicleOrder getById(Long vehicleOrderId) throws NotFoundException;
    VehicleOrder create(VehicleOrder vehicleOrderObject);
    VehicleOrder update(Long vehicleOrderId, VehicleOrder vehicleOrderDetails) throws NotFoundException;
    void delete(Long vehicleOrderId) throws NotFoundException;
    List<VehicleOrder> getPending();
}
