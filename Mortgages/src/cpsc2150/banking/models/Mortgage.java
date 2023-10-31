package cpsc2150.banking.models;
import java.lang.Math;

/** The Mortgage class models a mortgage contract, determining its terms based on the home cost, down payment, loan duration, and a customer's financial profile.
 *
 * @invariant Payment > 0 AND 0 <= Rate <= 1 AND Customer AND DebtToIncomeRatio > 0 AND Principal > 0 AND
 *            0 <= PercentDown < 1
 *
 * @correspondence self.Payment = payment AND self.Rate = rate AND self.Customer = customer AND
 *                 self.DebtToIncomeRatio = DebtToIncomeRatio AND self.Principal = Principal AND
 *                 self.NumberOfPayments = NumberOfPayments AND self.PercentDown = PercentDown
 *
 */
public class Mortgage extends AbsMortgage implements IMortgage{
    private double Payment;
    private double Rate;
    private ICustomer Customer;
    private double DebtToIncomeRatio;
    private double Principal;
    private int NumberOfPayments;
    private double PercentDown;
    /**
     *
     * @param homeCost the cost of the home
     * @param downPayment the down payment for the home
     * @param numYears the number of years for the loan
     * @param customer the customer applying for the mortgage
     *
     * @pre homeCost > 0 AND downPayment > 0 AND downPayment <= homeCost AND numYears > 0 AND customer != null
     * @post
     */
    public Mortgage (double homeCost, double downPayment, int numYears, ICustomer customer) {
        this.Principal = homeCost - downPayment;
        this.PercentDown = downPayment / homeCost;
        this.NumberOfPayments = numYears * MONTHS_IN_YEAR;
        this.Customer = customer; 
        


    }

    @Override
    public boolean loanApproved() {

        return Rate * 12 < RATETOOHIGH && PercentDown >= MIN_PERCENT_DOWN && DebtToIncomeRatio <= DTOITOOHIGH;

    }

    @Override
    public double getPayment() {
        return Payment;
    }

    @Override
    public double getRate() {
        return Rate*12;
    }

    @Override
    public double getPrincipal() {
        return Principal;
    }

    @Override
    public int getYears() {
        return NumberOfPayments;
    }
}
