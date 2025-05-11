### Optimierung des städtischen Entwässerungsnetzentwurfs mittels Ameisenkolonieoptimierung (Optimizing urban drainage network design using ant colony optimization)

This codebase aims to design an urban drainage network optimization system using ant colony optimization (ACO) by optimizing pipe configurations to minimize costs while meeting hydraulic and flow constraints

* Hydraulic Modeling :—
  * `Rainfall` models rainfall with intensity (mm/hr), duration (hr), and return period (years). It calculates total runoff volume as `intensity * area * duration / 1000`.
  * `DrainageNetwork` estimates required detention volume as 30% of total runoff, using an average runoff coefficient from nodes (defaulting to 0.5 if no nodes exist).
  * `DrainagePipe` uses Manning’s equation to calculate flow capacity based on diameter, slope, and a fixed Manning’s coefficient (0.013, typical for concrete).
  * Slope is derived from the elevation difference between start and end nodes divided by pipe length (calculated as Euclidean distance).
* Cost Estimation :—
  * Pipes: `DrainagePipe.getTotalCost` calculates cost as `length * costPerMeter`, where `costPerMeter` is a constructor parameter (e.g., 100 or 120 in `Main`).
  * Network: `DrainageNetwork.getTotalCost` sums the costs of all pipes.
* Regulatory and Environmental Compliance :—
  * `DrainageNetwork` defines a `maxAllowableFlow` (e.g., 5.0 m³/s), enforced via a penalty in `AntColonyOptimization.calculateSolutionCost` (adds 1,000,000 if flow exceeds this limit).
* Optimization using ACO :—
  * Each ant constructs a solution by iteratively selecting pipes based on pheromone levels and heuristic values (inverse of pipe cost).
  * Pheromones are updated based on solution quality (`pheromoneDeposit / cost`) and evaporated to prevent premature convergence.
  * Builds a solution by selecting all available pipes in a sequence, using `chooseNextPipe` to pick pipes probabilistically.
  * The heuristic is `1 / pipe.getTotalCost`, favoring cheaper pipes.
  * Sums pipe costs and applies a penalty if the minimum pipe flow capacity exceeds `maxAllowableFlow`.
  * The best solution (lowest cost) is stored and applied to the network after optimization.
  * Evaporation and deposition are standard, with configurable parameters (`alpha`, `beta`, `evaporationRate`, `pheromoneDeposit`).
  * The best solution and cost are maintained, ensuring the final network reflects the optimal configuration.
