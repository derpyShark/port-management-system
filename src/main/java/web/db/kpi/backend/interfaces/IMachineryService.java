package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.models.Machinery;

import java.util.List;

public interface IMachineryService {

    List<Machinery> getAll();
    Machinery getById(Long machineryId) throws NotFoundException;
    Machinery create(Machinery machineryObject);
    Machinery update(Long machineryId, Machinery machineryDetails) throws NotFoundException;
    void delete(Long machineryId) throws NotFoundException;
    Machinery getByLogin(String login) throws NotFoundException;

}
