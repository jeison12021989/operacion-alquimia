package com.alquimia.infrastructure.rest;
import com.alquimia.application.usecase.AlquimiaService;
import com.alquimia.domain.model.DeviceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/alquimia")
public class AlquimiaController {
    private final AlquimiaService service;
    public AlquimiaController(AlquimiaService service) { this.service = service; }
    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody List<DeviceInfo> devices) {
        try { return ResponseEntity.ok(service.processComplete(devices)); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
    @PostMapping("/devices")
    public ResponseEntity<?> register(@RequestBody DeviceInfo d) {
        try { service.registerDevice(d); return ResponseEntity.ok(Map.of("status", "Registrado")); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
    @GetMapping("/calculate")
    public ResponseEntity<?> calculate() {
        try { return ResponseEntity.ok(service.processAccumulated()); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
    @PutMapping("/devices/{id}")
    public ResponseEntity<?> replace(@PathVariable String id, @RequestBody DeviceInfo d) {
        try { service.replaceDevice(id, d.getDistance(), d.getMessageFragment()); return ResponseEntity.ok(Map.of("status", "Reemplazado")); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
    @PatchMapping("/devices/{id}")
    public ResponseEntity<?> patch(@PathVariable String id, @RequestBody DeviceInfo d) {
        try { service.patchDevice(id, d.getDistance(), d.getMessageFragment()); return ResponseEntity.ok(Map.of("status", "Actualizado")); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try { service.deleteDevice(id); return ResponseEntity.ok(Map.of("status", "Eliminado")); } catch (Exception e) { return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    }
}