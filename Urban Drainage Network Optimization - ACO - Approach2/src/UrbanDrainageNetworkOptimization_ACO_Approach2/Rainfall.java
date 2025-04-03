package UrbanDrainageNetworkOptimization_ACO_Approach2;
public class Rainfall {
    private double intensity; 
    private double duration;
    private double returnPeriod; 
    public Rainfall(double intensity, double duration, double returnPeriod) {
        this.intensity = intensity;
        this.duration = duration;
        this.returnPeriod = returnPeriod;
    }
    public double getIntensity() { 
        return intensity; 
    }
    public double getDuration() { 
        return duration; 
    }
    public double getReturnPeriod() { 
        return returnPeriod; 
    }
    public double getTotalVolume(double area) {
        return (intensity / 1000) * area * duration; 
    }
}