package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.models.IncomingHandlingOrder;

import java.util.List;

public interface IIncomingHandlingOrderService {
    List<IncomingHandlingOrder> getAll();
    IncomingHandlingOrder getById(Long handlingOrderId) throws NotFoundException;
    IncomingHandlingOrder create(IncomingHandlingOrder handlingOrderObject);
    IncomingHandlingOrder update(Long handlingOrderId, IncomingHandlingOrder handlingOrderDetails) throws NotFoundException;
    void delete(Long handlingOrderId) throws NotFoundException;
    List<IncomingHandlingOrder> getPending();
    List<IncomingHandlingOrder> getAccepted();
}
