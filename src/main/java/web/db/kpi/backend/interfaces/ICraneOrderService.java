package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.models.CraneOrder;

import java.util.List;

public interface ICraneOrderService {
    List<CraneOrder> getAll();
    CraneOrder getById(Long craneOrderId) throws NotFoundException;
    CraneOrder create(CraneOrder craneOrderObject);
    CraneOrder update(Long craneOrderId, CraneOrder craneOrderDetails) throws NotFoundException;
    void delete(Long craneOrderId) throws NotFoundException;
    List<CraneOrder> getPending();
}
