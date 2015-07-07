/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optitest;

import com.joptimizer.functions.*;
import com.joptimizer.optimizers.*;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import cern.colt.matrix.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.*;

/**
 *
 * @author pkchen
 */
public class svm {

    public svm() {
        System.out.println("constructing");
    }

    public void run() {
        // Objective function
        double[][] P = new double[][]{{1., 0.4}, {0.4, 1.}};

        PDQuadraticMultivariateRealFunction objectiveFunction;
        objectiveFunction = new PDQuadraticMultivariateRealFunction(P, null, 0);

        //equalities
        double[][] A = new double[][]{{1, 1}};
        double[] b = new double[]{1};

        //inequalities
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[2];
        inequalities[0] = new LinearMultivariateRealFunction(new double[]{-1, 0}, 0);
        inequalities[1] = new LinearMultivariateRealFunction(new double[]{0, -1}, 0);

        //optimization problem
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{0.1, 0.9});
        //or.setFi(inequalities); //if you want x>0 and y>0
        or.setA(A);
        or.setB(b);
        or.setToleranceFeas(1.E-12);
        or.setTolerance(1.E-12);

        //optimization
        JOptimizer opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        // double[] sol = opt.getOptimizationResponse().getSolution();
        int returnCode;
        try {
            returnCode = opt.optimize();
            System.out.println(returnCode);
        } catch (Exception ex) {
            Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(opt.getOptimizationResponse());
        //System.out.println(returnCode);
    }
}
