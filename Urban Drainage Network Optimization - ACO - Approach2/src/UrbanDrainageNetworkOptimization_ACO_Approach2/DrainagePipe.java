package UrbanDrainageNetworkOptimization_ACO_Approach2;
public class DrainagePipe {
    private DrainageNode startNode;
    private DrainageNode endNode;
    private double diameter; 
    private double length; 
    private double slope; 
    private double flowCapacity; 
    private double costPerMeter; 
    private double pheromoneLevel; 
    public DrainagePipe(DrainageNode startNode, DrainageNode endNode, double diameter, double costPerMeter) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.diameter = diameter;
        this.length = startNode.calculateDistance(endNode);
        this.slope = (startNode.getElevation() - endNode.getElevation()) / length;
        this.flowCapacity = calculateFlowCapacity();
        this.costPerMeter = costPerMeter;
        this.pheromoneLevel = 1.0;
    }
    private double calculateFlowCapacity() {
        // Manning's equation: Q = (1/n)*A*[R^(2/3)]*[S^(1/2)]
        double n = 0.013; 
        double area = Math.PI * Math.pow(diameter / 2, 2);
        double hydraulicRadius = diameter / 4; 
        double slopeAbs = Math.abs(slope); 
        return (1 / n) * area * Math.pow(hydraulicRadius, 2.0 / 3.0) * Math.pow(slopeAbs, 0.5);
    }
    public double getDiameter() { 
        return diameter; 
    }
    public double getTotalCost() { 
        return length * costPerMeter; 
    }
    public double getFlowCapacity() { 
        return flowCapacity; 
    }
    public double getPheromoneLevel() { 
        return pheromoneLevel; 
    }
    public void updatePheromone(double delta) { 
        pheromoneLevel += delta; 
    }
    public DrainageNode getStartNode() { 
        return startNode; 
    }
    public DrainageNode getEndNode() { 
        return endNode; 
    }
    public double getLength() { 
        return length; 
    }
    public double getSlope() { 
        return slope; 
    }
}