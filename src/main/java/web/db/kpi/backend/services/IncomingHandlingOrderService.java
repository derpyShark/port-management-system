package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.IIncomingHandlingOrderService;
import web.db.kpi.backend.models.IncomingHandlingOrder;
import web.db.kpi.backend.repositories.IncomingHandlingOrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class IncomingHandlingOrderService implements IIncomingHandlingOrderService {

    @Autowired
    private IncomingHandlingOrderRepository incomingHandlingOrderRepository;

    @Override
    public List<IncomingHandlingOrder> getAll() {
        return incomingHandlingOrderRepository.findAll();
    }

    @Override
    public IncomingHandlingOrder getById(Long machineryId) throws NotFoundException {
        Optional<IncomingHandlingOrder> machineryMaybe = incomingHandlingOrderRepository.findById(machineryId);
        if(machineryMaybe.isPresent()){
            return machineryMaybe.get();
        }
        else
            throw new NotFoundException("No incomingHandlingOrder with such an Id: " + machineryId);
    }

    @Override
    public IncomingHandlingOrder update(Long handlingOrderId, IncomingHandlingOrder handlingOrderDetails) throws NotFoundException {
        IncomingHandlingOrder oldHandlingOrder = getById(handlingOrderId);
        oldHandlingOrder.setCargoContainer(handlingOrderDetails.getCargoContainer());
        oldHandlingOrder.setIncomingTransport(handlingOrderDetails.getIncomingTransport());
        oldHandlingOrder.setOutgoingTransport(handlingOrderDetails.getOutgoingTransport());
        oldHandlingOrder.setOrderStatus(handlingOrderDetails.getOrderStatus());
        return incomingHandlingOrderRepository.save(oldHandlingOrder);
    }

    @Override
    public IncomingHandlingOrder create(IncomingHandlingOrder handlingOrderObject) {
        handlingOrderObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return incomingHandlingOrderRepository.save(handlingOrderObject);
    }

    @Override
    public void delete(Long machineryId) throws NotFoundException{
        incomingHandlingOrderRepository.delete(getById(machineryId));
    }

    @Override
    public List<IncomingHandlingOrder> getPending() {
        return incomingHandlingOrderRepository.findPending();
    }

    @Override
    public List<IncomingHandlingOrder> getAccepted() {
        return incomingHandlingOrderRepository.findAccepted();
    }
}
