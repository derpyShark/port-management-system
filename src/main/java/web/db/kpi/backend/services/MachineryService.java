package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.IMachineryService;
import web.db.kpi.backend.models.Machinery;
import web.db.kpi.backend.repositories.MachineryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MachineryService implements IMachineryService {

    @Autowired
    private MachineryRepository machineryRepository;

    @Override
    public List<Machinery> getAll() {
        return machineryRepository.findAll();
    }

    @Override
    public Machinery getById(Long machineryId) throws NotFoundException {
        Optional<Machinery> machineryMaybe = machineryRepository.findById(machineryId);
        if(machineryMaybe.isPresent()){
            return machineryMaybe.get();
        }
        else
            throw new NotFoundException("No machinery with such an Id: " + machineryId);
    }

    @Override
    public Machinery update(Long machineryId, Machinery machineryDetails) throws NotFoundException {
        Machinery oldMachinery = getById(machineryId);
        oldMachinery.setMachineryLogin(machineryDetails.getMachineryLogin());
        oldMachinery.setMachineryPasswordHash(machineryDetails.getMachineryPasswordHash());
        oldMachinery.setMachineryType(machineryDetails.getMachineryType());
        return machineryRepository.save(oldMachinery);
    }

    @Override
    public Machinery create(Machinery machineryObject) {
        machineryObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return machineryRepository.save(machineryObject);
    }

    @Override
    public void delete(Long machineryId) throws NotFoundException{
        machineryRepository.delete(getById(machineryId));
    }

    @Override
    public Machinery getByLogin(String login) throws NotFoundException {
        Optional<Machinery> userMaybe = machineryRepository.findByLogin(login);
        if(userMaybe.isPresent()){
            return userMaybe.get();
        }
        else
            throw new NotFoundException("No machinery with such a login: " + login);
    }
}
