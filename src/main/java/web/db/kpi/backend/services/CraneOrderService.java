package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.ICraneOrderService;
import web.db.kpi.backend.models.CraneOrder;
import web.db.kpi.backend.repositories.CraneOrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CraneOrderService implements ICraneOrderService {
    @Autowired
    private CraneOrderRepository craneOrderRepository;

    @Override
    public List<CraneOrder> getAll() {
        return craneOrderRepository.findAll();
    }

    @Override
    public CraneOrder getById(Long craneOrderId) throws NotFoundException {
        Optional<CraneOrder> craneOrderMaybe = craneOrderRepository.findById(craneOrderId);
        if(craneOrderMaybe.isPresent()){
            return craneOrderMaybe.get();
        }
        else
            throw new NotFoundException("No crane order with such an Id: " + craneOrderId);
    }

    @Override
    public CraneOrder update(Long craneOrderId, CraneOrder craneOrderDetails) throws NotFoundException {
        CraneOrder oldCraneOrder = getById(craneOrderId);
        oldCraneOrder.setCrane(craneOrderDetails.getCrane());
        oldCraneOrder.setCargoContainer(craneOrderDetails.getCargoContainer());
        oldCraneOrder.setCraneAction(craneOrderDetails.getCraneAction());
        oldCraneOrder.setTransport(craneOrderDetails.getTransport());
        oldCraneOrder.setOrderStatus(craneOrderDetails.getOrderStatus());
        return craneOrderRepository.save(oldCraneOrder);
    }

    @Override
    public CraneOrder create(CraneOrder craneOrderObject) {
        craneOrderObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return craneOrderRepository.save(craneOrderObject);
    }

    @Override
    public void delete(Long craneOrderId) throws NotFoundException{
        craneOrderRepository.delete(getById(craneOrderId));
    }

    @Override
    public List<CraneOrder> getPending() {
        return craneOrderRepository.findPending();
    }
}
