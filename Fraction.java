/**
 * Author: James Lemayian
 * Description: Class Fraction to handle the processing of two fractions of the form a/b.
 * Environment: This program runs on Java 9 upwards, built on Java JDK 11.
 */

import java.util.Scanner;

public class Fraction {
    private int numerator;
    private int denominator;

    /* First Constructor, accepts proper fractions format */
    public Fraction(int numerator,int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("The denominator is zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;

        // Good Maths practice, always have negation sign on the numerator.
        if(denominator<0){
            this.numerator = -1*this.numerator;
            this.denominator = -1*this.denominator;
        }

        // always have fractions in reduced form.
        this.reduce();
    }

    /* Second Constructor, accepts whole numbers */
    public Fraction(int num) {
        this.numerator = num;
        this.denominator = 1;
    }


    public Fraction reduce(){
        int gcd = gcd(numerator,denominator);
        numerator = numerator/gcd;
        denominator = denominator/gcd;
        return this;
    }

    // Have a string representation of a fraction for printing etc
    public String toString() { return (denominator!=1) ? numerator+"/"+denominator : numerator+""; }

    // Calculate GCD using Recursion.
    public static int gcd(int a,int b) { return b==0 ? a : gcd(b, a%b); }

    // Static methods to add, subtract, multiply and divide.
    public static Fraction add(Fraction f1,Fraction f2){
        return new Fraction(f1.numerator*f2.denominator+f2.numerator*f1.denominator,f1.denominator*f2.denominator);
    }
    public static Fraction sub(Fraction f1,Fraction f2){
        return new Fraction(f1.numerator*f2.denominator-f2.numerator*f1.denominator,f1.denominator*f2.denominator);
    }
    public static Fraction mul(Fraction f1,Fraction f2){
        return new Fraction(f1.numerator*f2.numerator,f1.denominator*f2.denominator);
    }
    public static Fraction div(Fraction f1,Fraction f2){
        return new Fraction(f1.numerator*f2.denominator,f1.denominator*f2.numerator);
    }

    // Easily create a Fraction instance, avoid logic duplication
    public static Fraction create(String fractionString) {
        if (fractionString.indexOf("/") == -1) {
            // call the constructor for whole numbers or floating point format.
            return new Fraction(Integer.parseInt(fractionString));
        } else {
            // call the constructor for true proper fractions.
            return new Fraction(
                Integer.parseInt(fractionString, 0, fractionString.indexOf("/"), 10),
                Integer.parseInt(fractionString, fractionString.indexOf("/")+1, fractionString.length(), 10));
        }
    }

    // Method to decide what process should be applied to two given fractions
    public static Fraction process(Fraction f1, Fraction f2, String operand) {
        Boolean multiply = (operand.equals("*"));
        Boolean divide = (operand.equals("/"));
        Boolean subtract = (operand.equals("-"));
        // Omit add and have it as the default process
        return (multiply ? Fraction.mul(f1, f2) :
                divide ? Fraction.div(f1, f2) :
                subtract ? Fraction.sub(f1, f2) :
                Fraction.add(f1, f2));
    }

    // Main method: console entrypoint.
    public static void main(String[] args) {
        String info = "Program to handle processing of two proper fractions.";
        String supportedOperands = "Multiply (*), Divide (/), Add (+), Subtract (-)";
        String inputFormat = "fraction1 (operand) fraction2 {eg. 1/2 * 2/3}";

        System.out.printf("%s\nSupported Operands: %s\nOperation input format: %s\nEnter an operation: ", info, supportedOperands, inputFormat);

        Scanner sc = new Scanner(System.in);
        String fraction1S = sc.next();
        String operand = sc.next();
        String fraction2S = sc.next();
        sc.close();
        
        try {
            Fraction fraction1 = Fraction.create(fraction1S);
            Fraction fraction2 = Fraction.create(fraction2S);

            Fraction result = Fraction.process(fraction1, fraction2, operand);

            System.out.printf("%s %s %s = %s\n", fraction1.toString(), operand, fraction2.toString(), result.toString());
        } catch (Exception err) {
            System.out.printf("It's not a problem with the code. You tried something shitty.\nError: %s\n", err.getMessage());
        }
    }
}
