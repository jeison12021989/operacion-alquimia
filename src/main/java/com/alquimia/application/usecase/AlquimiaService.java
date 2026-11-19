package com.alquimia.application.usecase;
import com.alquimia.domain.model.DeviceInfo;
import com.alquimia.domain.model.LabLocation;
import com.alquimia.domain.model.MessageReconstructor;
import com.alquimia.domain.model.TrilaterationCalculator;
import com.alquimia.domain.port.DeviceRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class AlquimiaService {
    private final DeviceRepositoryPort deviceRepository;
    public AlquimiaService(DeviceRepositoryPort deviceRepository) { this.deviceRepository = deviceRepository; }
    public record AlquimiaResponse(LabLocation location, String message) {}
    public AlquimiaResponse processComplete(List<DeviceInfo> devicesInput) {
        Map<String, DeviceInfo> map = devicesInput.stream().collect(Collectors.toMap(DeviceInfo::getDeviceId, d -> d));
        LabLocation location = TrilaterationCalculator.calculateLocation(map);
        List<String> fragments = devicesInput.stream().map(DeviceInfo::getMessageFragment).collect(Collectors.toList());
        return new AlquimiaResponse(location, MessageReconstructor.reconstruct(fragments));
    }
    public void registerDevice(DeviceInfo device) { deviceRepository.save(device); }
    public AlquimiaResponse processAccumulated() {
        Map<String, DeviceInfo> accumulated = deviceRepository.findAll();
        if (accumulated.size() < 3) { throw new IllegalArgumentException("No hay datos suficientes (se requieren 3 dispositivos)."); }
        LabLocation location = TrilaterationCalculator.calculateLocation(accumulated);
        List<String> fragments = accumulated.values().stream().map(DeviceInfo::getMessageFragment).collect(Collectors.toList());
        return new AlquimiaResponse(location, MessageReconstructor.reconstruct(fragments));
    }
    public void replaceDevice(String deviceId, double distance, String fragment) {
        if (deviceRepository.findById(deviceId).isEmpty()) { throw new IllegalArgumentException("Dispositivo no encontrado"); }
        deviceRepository.update(new DeviceInfo(deviceId, distance, fragment));
    }
    public void patchDevice(String deviceId, Double distance, String fragment) {
        DeviceInfo existing = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Dispositivo no encontrado"));
        deviceRepository.update(new DeviceInfo(deviceId, distance != null ? distance : existing.getDistance(), fragment != null ? fragment : existing.getMessageFragment()));
    }
    public void deleteDevice(String deviceId) { deviceRepository.delete(deviceId); }
}