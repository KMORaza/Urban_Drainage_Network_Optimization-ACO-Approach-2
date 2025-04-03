package UrbanDrainageNetworkOptimization_ACO_Approach2;
public class DrainageNode {
    private int id;
    private double x, y; 
    private double elevation; 
    private double infiltrationRate;
    private double runoffCoefficient; 
    public DrainageNode(int id, double x, double y, double elevation, double infiltrationRate, double runoffCoefficient) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.elevation = elevation;
        this.infiltrationRate = infiltrationRate;
        this.runoffCoefficient = runoffCoefficient;
    }
    public int getId() { 
        return id; 
    }
    public double getX() { 
        return x; 
    }
    public double getY() { 
        return y; 
    }
    public double getElevation() { 
        return elevation; 
    }
    public double getInfiltrationRate() { 
        return infiltrationRate; 
    }
    public double getRunoffCoefficient() { 
        return runoffCoefficient; 
    }
    public double calculateDistance(DrainageNode other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}