// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 

    // Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
    public static void main(String[] args) {
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]);
        int n = Integer.parseInt(args[2]);
        System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

        System.out.print("\nPeriodical payment, using brute force: ");
        System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
        System.out.println("number of iterations: " + iterationCounter);

        System.out.print("\nPeriodical payment, using bi-section search: ");
        System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
        System.out.println("number of iterations: " + iterationCounter);
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) {
        double endingBalance = loan;
        for (int i = 1; i <= n; i++) {
            endingBalance = (endingBalance - payment) * (rate / 100.0 + 1.0);
        }
        return endingBalance;
    }

    // Uses sequential (brute force) search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    // Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        double payment = loan / n;
        iterationCounter = 0;

        while (endBalance(loan, rate, n, payment) > 0) {
            payment += epsilon;
            iterationCounter++;
        }

        return payment;
    }

    // Uses bisection search to compute an approximation of the periodical payment 
    // that will bring the ending balance of a loan close to 0.
    // Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        double low = loan / n;     
        double high = loan;        
        iterationCounter = 0;

        double fLow = endBalance(loan, rate, n, low);
        double mid = (low + high) / 2.0;
        double fMid = endBalance(loan, rate, n, mid);

        while ((high - low) > epsilon) {

            if (fMid * fLow > 0) {
                low = mid;
                fLow = fMid;
            } else {
                high = mid;
            }

            mid = (low + high) / 2.0;
            fMid = endBalance(loan, rate, n, mid);
            iterationCounter++;
        }
        return mid;
    }
}
