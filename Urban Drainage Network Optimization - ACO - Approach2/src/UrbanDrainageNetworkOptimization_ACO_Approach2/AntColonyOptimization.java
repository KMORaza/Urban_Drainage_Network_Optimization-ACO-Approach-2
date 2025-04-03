package UrbanDrainageNetworkOptimization_ACO_Approach2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class AntColonyOptimization {
    private DrainageNetwork network;
    private int numAnts;
    private double alpha; 
    private double beta; 
    private double evaporationRate;
    private double pheromoneDeposit;
    private Random random;
    private List<DrainagePipe> bestSolution; 
    private double bestCost = Double.MAX_VALUE;
    public AntColonyOptimization(DrainageNetwork network, int numAnts, double alpha, double beta,
                                 double evaporationRate, double pheromoneDeposit) {
        this.network = network;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.pheromoneDeposit = pheromoneDeposit;
        this.random = new Random();
        this.bestSolution = new ArrayList<>(network.getPipes()); 
    }
    public void optimize(int iterations) {
        for (int i = 0; i < iterations; i++) {
            List<List<DrainagePipe>> antSolutions = new ArrayList<>();
            List<Double> solutionCosts = new ArrayList<>();
            for (int ant = 0; ant < numAnts; ant++) {
                List<DrainagePipe> solution = constructSolution();
                antSolutions.add(solution);
                double cost = calculateSolutionCost(solution);
                solutionCosts.add(cost);
                if (cost < bestCost) {
                    bestCost = cost;
                    bestSolution = new ArrayList<>(solution);
                }
            }
            updatePheromones(antSolutions, solutionCosts);
            evaporatePheromones();
        }
        network.getPipes().clear();
        network.getPipes().addAll(bestSolution);
    }
    private List<DrainagePipe> constructSolution() {
        List<DrainagePipe> solution = new ArrayList<>();
        List<DrainagePipe> availablePipes = new ArrayList<>(network.getPipes());
        while (!availablePipes.isEmpty()) {
            DrainagePipe nextPipe = chooseNextPipe(availablePipes, solution);
            solution.add(nextPipe);
            availablePipes.remove(nextPipe);
        }
        return solution;
    }
    private DrainagePipe chooseNextPipe(List<DrainagePipe> availablePipes, List<DrainagePipe> currentSolution) {
        double totalProbability = 0;
        double[] probabilities = new double[availablePipes.size()];
        
        for (int i = 0; i < availablePipes.size(); i++) {
            DrainagePipe pipe = availablePipes.get(i);
            double heuristic = 1.0 / pipe.getTotalCost(); 
            probabilities[i] = Math.pow(pipe.getPheromoneLevel(), alpha) * Math.pow(heuristic, beta);
            totalProbability += probabilities[i];
        }
        double r = random.nextDouble() * totalProbability;
        double cumulative = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulative += probabilities[i];
            if (r <= cumulative) return availablePipes.get(i);
        }
        return availablePipes.get(availablePipes.size() - 1);
    }
    private void updatePheromones(List<List<DrainagePipe>> solutions, List<Double> costs) {
        for (int i = 0; i < solutions.size(); i++) {
            double contribution = pheromoneDeposit / costs.get(i);
            for (DrainagePipe pipe : solutions.get(i)) {
                pipe.updatePheromone(contribution);
            }
        }
    }
    private void evaporatePheromones() {
        for (DrainagePipe pipe : network.getPipes()) {
            pipe.updatePheromone(-pipe.getPheromoneLevel() * evaporationRate);
        }
    }
    private double calculateSolutionCost(List<DrainagePipe> solution) {
        double totalCost = 0;
        double totalFlow = Double.MAX_VALUE;
        for (DrainagePipe pipe : solution) {
            totalCost += pipe.getTotalCost();
            totalFlow = Math.min(totalFlow, pipe.getFlowCapacity());
        }
        if (totalFlow > network.getMaxFlow()) {
            totalCost += 1000000; 
        }
        return totalCost;
    }
    public List<DrainagePipe> getBestSolution() {
        return bestSolution;
    }
    public double getBestCost() {
        return bestCost;
    }
}