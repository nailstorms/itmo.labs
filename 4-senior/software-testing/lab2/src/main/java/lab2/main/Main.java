package lab2.main;

import lab2.EquationSystemSolver;
import lab2.impl.BaseLogarithm;
import lab2.impl.BaseTrig;
import lab2.impl.Logarithm;
import lab2.impl.Trig;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Trig trig = new Trig(new BaseTrig());
        Logarithm log = new Logarithm(new BaseLogarithm());
        EquationSystemSolver solver = new EquationSystemSolver(log, trig);

        Scanner reader = new Scanner(System.in);

        System.out.println("----------------------------- EQUATION SYSTEM -----------------------------");

        System.out.println("Enter csv filepath for equation system results:");
        String eqSysFilepath = reader.nextLine();
        System.out.println("Enter left bound: ");
        Double eqSysLeftBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter right bound: ");
        Double eqSysRightBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter step: ");
        Double eqSysStep = Double.parseDouble(reader.nextLine());

        System.out.println("----------------------------- LOGARITHM -----------------------------");

        System.out.println("Enter csv filepath for logarithm computation results:");
        String logFilepath = reader.nextLine();
        System.out.println("Enter left bound: ");
        Double logLeftBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter right bound: ");
        Double logRightBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter step: ");
        Double logStep = Double.parseDouble(reader.nextLine());

        System.out.println("----------------------------- TRIGONOMETRY -----------------------------");

        System.out.println("Enter csv filepath for trigonometric functions computation results:");
        String trigFilepath = reader.nextLine();
        System.out.println("Enter left bound: ");
        Double trigLeftBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter right bound: ");
        Double trigRightBound = Double.parseDouble(reader.nextLine());
        System.out.println("Enter step: ");
        Double trigStep = Double.parseDouble(reader.nextLine());


        solver.writeToCSV(eqSysFilepath, eqSysLeftBound, eqSysRightBound, eqSysStep);
        System.out.println(String.format("Results successfully written to %s", eqSysFilepath));

        log.writeToCSV(logFilepath, logLeftBound, logRightBound, logStep);
        System.out.println(String.format("Results successfully written to %s", logFilepath));

        trig.writeToCSV(trigFilepath, trigLeftBound, trigRightBound, trigStep);
        System.out.println(String.format("Results successfully written to %s", trigFilepath));
    }
}
