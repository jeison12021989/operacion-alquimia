package com.alquimia.domain.model;
import java.util.Map;
public class TrilaterationCalculator {
    private static final Map<String, double[]> DEVICE_POSITIONS = Map.of(
        "STAR Labs", new double[]{-500.0, -200.0},
        "CCPD", new double[]{100.0, -100.0},
        "S.T.A.R. Satellite", new double[]{500.0, 100.0}
    );
    public static LabLocation calculateLocation(Map<String, DeviceInfo> devices) {
        if (devices.size() < 3) {
            throw new IllegalArgumentException("Se necesitan datos de al menos 3 dispositivos para la trilateración.");
        }
        double[] p1 = DEVICE_POSITIONS.get("STAR Labs");
        double[] p2 = DEVICE_POSITIONS.get("CCPD");
        double[] p3 = DEVICE_POSITIONS.get("S.T.A.R. Satellite");
        double r1 = devices.get("STAR Labs").getDistance();
        double r2 = devices.get("CCPD").getDistance();
        double r3 = devices.get("S.T.A.R. Satellite").getDistance();
        double A = 2 * p2[0] - 2 * p1[0];
        double B = 2 * p2[1] - 2 * p1[1];
        double C = Math.pow(r1, 2) - Math.pow(r2, 2) - Math.pow(p1[0], 2) + Math.pow(p2[0], 2) - Math.pow(p1[1], 2) + Math.pow(p2[1], 2);
        double D = 2 * p3[0] - 2 * p2[0];
        double E = 2 * p3[1] - 2 * p2[1];
        double F = Math.pow(r2, 2) - Math.pow(r3, 2) - Math.pow(p2[0], 2) + Math.pow(p3[0], 2) - Math.pow(p2[1], 2) + Math.pow(p3[1], 2);
        double x = (C * E - F * B) / (A * E - B * D);
        double y = (A * F - C * D) / (A * E - B * D);
        return new LabLocation(Math.round(x * 100.0) / 100.0, Math.round(y * 100.0) / 100.0);
    }
}