package bearmaps.proj2c;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private final double INF = Double.POSITIVE_INFINITY;
    private int numStatesExplored;
    private SolverOutcome outcome;
    private LinkedList<Vertex> solution;
    private double explorationTime;
    private double solutionWeight;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch timeStamp = new Stopwatch();

        solution = new LinkedList<>();
        explorationTime = 0.0;
        solutionWeight = 0.0;
        numStatesExplored = 0;
        outcome = SolverOutcome.UNSOLVABLE;
        HashMap<Vertex, Double> distTo = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        distTo.put(start, 0.0);
        fringe.add(start, 0.0);

        while (fringe.size() > 0) {
            if (fringe.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                Vertex iterEnd = end;
                solutionWeight += distTo.get(iterEnd);
                while (!iterEnd.equals(start)) {
                    Vertex initialEdge = edgeTo.get(iterEnd);
                    solution.add(0, iterEnd);
                    iterEnd = initialEdge;

                }
                explorationTime = timeStamp.elapsedTime();
                solution.add(0, start);
                return;
            }
            if (timeStamp.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = timeStamp.elapsedTime();
                return;
            }
            numStatesExplored++;
            List<WeightedEdge<Vertex>> neighbor = input.neighbors(fringe.removeSmallest());
            for (WeightedEdge<Vertex> each : neighbor) {
                Vertex from = each.from();
                Vertex to = each.to();
                double weight = each.weight();
                double getPrevious = distTo.get(from);

                if (getPrevious + weight < distTo.getOrDefault(to, INF)) {
                    distTo.put(to, getPrevious + weight);
                    edgeTo.put(to, from);

                    double newPrior = distTo.get(to) + input.estimatedDistanceToGoal(to, end);
                    if (!fringe.contains(to)) {
                        fringe.add(to, newPrior);
                    }
                    if (fringe.contains(to)) {
                        fringe.changePriority(to, newPrior);
                    }

                }
            }

        }
        explorationTime = timeStamp.elapsedTime();
    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return explorationTime;
    }
}
