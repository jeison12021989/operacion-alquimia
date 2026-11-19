package com.alquimia.infrastructure.adapter;
import com.alquimia.domain.model.DeviceInfo;
import com.alquimia.domain.port.DeviceRepositoryPort;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Repository
public class InMemoryDeviceRepositoryAdapter implements DeviceRepositoryPort {
    private final Map<String, DeviceInfo> storage = new HashMap<>();
    @Override
    public void save(DeviceInfo device) { storage.put(device.getDeviceId(), device); }
    @Override
    public Optional<DeviceInfo> findById(String deviceId) { return Optional.ofNullable(storage.get(deviceId)); }
    @Override
    public Map<String, DeviceInfo> findAll() { return new HashMap<>(storage); }
    @Override
    public void delete(String deviceId) { storage.remove(deviceId); }
    @Override
    public void update(DeviceInfo device) { storage.put(device.getDeviceId(), device); }
}