package UrbanDrainageNetworkOptimization_ACO_Approach2;
public class Main {
    public static void main(String[] args) {
        Rainfall rainfall = new Rainfall(50, 2, 10);
        DrainageNetwork network = new DrainageNetwork(1000000, rainfall, 5.0); 
        DrainageNode node1 = new DrainageNode(1, 0, 0, 10, 5, 0.9);  
        DrainageNode node2 = new DrainageNode(2, 100, 100, 8, 5, 0.9);
        DrainageNode node3 = new DrainageNode(3, 200, 200, 6, 5, 0.9);
        network.addNode(node1);
        network.addNode(node2);
        network.addNode(node3);
        DrainagePipe pipe1 = new DrainagePipe(node1, node2, 0.5, 100);
        DrainagePipe pipe2 = new DrainagePipe(node2, node3, 0.6, 120); 
        network.addPipe(pipe1);
        network.addPipe(pipe2);
        AntColonyOptimization aco = new AntColonyOptimization(network, 20, 1.0, 2.0, 0.1, 10.0);
        aco.optimize(100); 
        System.out.println("********** Urban Drainage Network Design Parameters *********");
        System.out.println("Design Rainfall:");
        System.out.printf("  Intensity: %.2f mm/hr%n", rainfall.getIntensity());
        System.out.printf("  Duration: %.2f hr%n", rainfall.getDuration());
        System.out.printf("  Return Period: %.0f years%n", rainfall.getReturnPeriod());
        System.out.printf("  Total Volume (for catchment): %.2f m³%n", rainfall.getTotalVolume(network.getCatchmentArea()));
        System.out.println("\nCatchment Area Characteristics:");
        System.out.printf("  Catchment Area: %.2f m²%n", network.getCatchmentArea());
        for (DrainageNode node : network.getNodes()) {
            System.out.printf("  Node %d: Elevation=%.2f m, Infiltration Rate=%.2f mm/hr, Runoff Coefficient=%.2f%n",
                    node.getId(), node.getElevation(), node.getInfiltrationRate(), node.getRunoffCoefficient());
        }
        System.out.println("\nOptimized Flow (Hydraulic Parameters) and System Capacity:");
        for (DrainagePipe pipe : network.getPipes()) {
            System.out.printf("  Pipe (N%d to N%d): Diameter=%.2f m, Length=%.2f m, Slope=%.4f, Flow Capacity=%.2f m³/s%n",
                    pipe.getStartNode().getId(), pipe.getEndNode().getId(), pipe.getDiameter(), pipe.getLength(),
                    pipe.getSlope(), pipe.getFlowCapacity());
        }
        System.out.println("\nStormwater Detention/Retention:");
        System.out.printf("  Detention Volume Required: %.2f m³%n", network.getDetentionVolume());
        System.out.println("\nFlooding Risks and Regulatory Standards:");
        System.out.printf("  Max Allowable Flow: %.2f m³/s%n", network.getMaxAllowableFlow());
        System.out.printf("  Max Flow Capacity of Network: %.2f m³/s%n", network.getMaxFlow());
        System.out.println("\nCost and Economic Feasibility:");
        System.out.printf("  Total Optimized Cost: $%.2f%n", aco.getBestCost());
        System.out.println("\nMaintenance and Sustainability:");
        System.out.println("  (Assumed accessible for maintenance; material longevity not modeled)");
        NetworkVisualization.display(network);
    }
}