package PesquisaOperacional;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;

import java.util.Arrays;
import java.util.Random;

public class ProblemaDeTransporte {
    private static int numFornecedores = 50;
    private static int numConsumidores = 50;

    public static void main(String[] args) {
        double[][] costMatrix = new double[numFornecedores][numConsumidores];
        double[] fornecer = new double[numFornecedores];
        double[] demanda = new double[numConsumidores];

        preencherValoresAleatorios(costMatrix, fornecer, demanda);

        System.out.println("Matriz de custos:");
        for (int i = 0; i < numFornecedores; i++) {
            System.out.println(Arrays.toString(costMatrix[i]));
        }

        System.out.println("\nOferta dos fornecedores:");

        System.out.println(Arrays.toString(fornecer));

        System.out.println("\nDemanda dos consumidores:");

        System.out.println(Arrays.toString(demanda));

        solveTransportProblem(costMatrix, fornecer, demanda);
    }

    private static void preencherValoresAleatorios(double[][] costMatrix, double[] fornecer, double[] demanda) {
        Random random = new Random();

        // Preenchendo a matriz de custos com valores aleatórios de 1 a 40
        for (int i = 0; i < numFornecedores; i++) {
            for (int j = 0; j < numConsumidores; j++) {
                costMatrix[i][j] = random.nextInt(40) + 1;
            }
        }

        // Preenchendo os arrays de fornecedores e demanda com valores aleatórios de 1 a 40
        for (int i = 0; i < numFornecedores; i++) {
            fornecer[i] = random.nextInt(40) + 1;
        }

        for (int i = 0; i < numConsumidores; i++) {
            demanda[i] = random.nextInt(40) + 1;
        }
    }

    public static void solveTransportProblem(double[][] costMatrix, double[] fornecer, double[] demanda) {
        // Definição da função objetivo
        LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(flattenMatrix(costMatrix), 0);

        // Criação das restrições lineares
        LinearConstraintSet constraintSet = createConstraints(flattenMatrix(costMatrix), fornecer, demanda);

        // Resolução do problema usando o SimplexSolver
        SimplexSolver solver = new SimplexSolver();
        try {
            // Encontrar a solução ótima
            PointValuePair solution = solver.optimize(objectiveFunction, constraintSet);

            // Obter os valores de transporte da solução
            double[] values = solution.getPoint();

            // Imprimir a solução do problema de transporte
            System.out.println("Solução do problema do transporte:");
            for (int i = 0; i < numFornecedores; i++) {
                for (int j = 0; j < numConsumidores; j++) {
                    int index = i * numConsumidores + j;

                    // Imprimir a quantidade a ser transportada de fornecedor para consumidor
                    System.out.println("Transportar " + values[index] + " unidades de Fornecedor " + (i + 1) + " para Consumidor " + (j + 1));
                }
            }
        } catch (NoFeasibleSolutionException e) {
            // Lidar com o caso em que não há solução viável
            System.out.println("Não há solução viável para o problema do transporte.");
        }
    }

    // Métodos auxiliares
    private static double[] flattenMatrix(double[][] matrix) {
        return Arrays.stream(matrix)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }

    private static LinearConstraintSet createConstraints(double[] costArray, double[] supply, double[] demand) {
        LinearConstraint[] constraints = new LinearConstraint[numFornecedores + numConsumidores];

        for (int i = 0; i < numFornecedores; i++) {
            constraints[i] = new LinearConstraint(
                    createConstraintRow(costArray.length, i, 1),
                    Relationship.EQ, supply[i]);
        }

        for (int i = 0; i < numConsumidores; i++) {
            constraints[numFornecedores + i] = new LinearConstraint(
                    createLimitaçãoColumn(costArray.length, i, numConsumidores),
                    Relationship.EQ, demand[i]);
        }

        return new LinearConstraintSet(constraints);
    }

    private static double[] createConstraintRow(int tamanho, int supplierIndex, double valor) {
        double[] constraintRow = new double[tamanho];
        Arrays.fill(constraintRow, supplierIndex * numConsumidores, Math.min((supplierIndex + 1) * numConsumidores, constraintRow.length), valor);
        return constraintRow;
    }

    private static double[] createLimitaçãoColumn(int tamanho, int consumerIndex, int numConsumidores) {
        double[] constraintColumn = new double[tamanho];
        for (int i = consumerIndex; i < tamanho; i += numConsumidores) {
            constraintColumn[i] = 1;
        }
        return constraintColumn;
    }

}
