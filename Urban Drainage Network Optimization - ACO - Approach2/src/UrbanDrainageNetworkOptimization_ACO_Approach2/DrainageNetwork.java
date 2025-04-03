package UrbanDrainageNetworkOptimization_ACO_Approach2;
import java.util.ArrayList;
import java.util.List;
public class DrainageNetwork {
    private List<DrainageNode> nodes;
    private List<DrainagePipe> pipes;
    private double catchmentArea; 
    private Rainfall designRainfall;
    private double detentionVolume; 
    private double maxAllowableFlow;
    public DrainageNetwork(double catchmentArea, Rainfall designRainfall, double maxAllowableFlow) {
        this.nodes = new ArrayList<>();
        this.pipes = new ArrayList<>();
        this.catchmentArea = catchmentArea;
        this.designRainfall = designRainfall;
        this.maxAllowableFlow = maxAllowableFlow;
        this.detentionVolume = calculateRequiredDetentionVolume();
    }
    private double calculateRequiredDetentionVolume() {
        double totalRunoff = designRainfall.getTotalVolume(catchmentArea) * getAverageRunoffCoefficient();
        return totalRunoff * 0.3;
    }
    private double getAverageRunoffCoefficient() {
        double sum = 0;
        for (DrainageNode node : nodes) {
            sum += node.getRunoffCoefficient();
        }
        return nodes.isEmpty() ? 0.5 : sum / nodes.size();
    }
    public void addNode(DrainageNode node) { 
        nodes.add(node); 
    }
    public void addPipe(DrainagePipe pipe) { 
        pipes.add(pipe); 
    }
    public double getTotalCost() {
        double cost = 0;
        for (DrainagePipe pipe : pipes) {
            cost += pipe.getTotalCost();
        }
        return cost;
    }
    public List<DrainagePipe> getPipes() { 
        return pipes; 
    }
    public List<DrainageNode> getNodes() { 
        return nodes; 
    } 
    public double getMaxFlow() {
        double maxFlow = Double.MAX_VALUE;
        for (DrainagePipe pipe : pipes) {
            maxFlow = Math.min(maxFlow, pipe.getFlowCapacity());
        }
        return maxFlow;
    }
    public Rainfall getDesignRainfall() { 
        return designRainfall; 
    }
    public double getCatchmentArea() { 
        return catchmentArea; 
    }
    public double getDetentionVolume() { 
        return detentionVolume; 
    }
    public double getMaxAllowableFlow() { 
        return maxAllowableFlow; 
    }
}