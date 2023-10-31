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
     * @pre homeCost > 0 AND downPayment > 0 AND downPayment <= homeCost AND numYears > 0 AND customer != null AND downPayment < homeCost
     * AND numYears >= MIN_YEARS AND numYears <= MAX_YEARS
     * @post
     */
    public Mortgage (double homeCost, double downPayment, int numYears, ICustomer customer) {
        this.Principal = homeCost - downPayment;
        this.PercentDown = downPayment / homeCost;
        this.NumberOfPayments = numYears * MONTHS_IN_YEAR;
        this.Customer = customer; 

        //calculate Rate
        this.rate = BASERATE;

        // down payment adjustment
        if(PercentDown < PREFERRED_PERCENT_DOWN){
            rate += GOODRATEADD:
        }
        
        //adjust for credit score
        if(customer.getCreditScore() < BADCREDIT){
            rate += VERYBADRATEADD; 
        }else if(customer.getCreditScore() < FAIRCREDIT){
            rate += BADRATEADD;
        }else if(customer.getCreditScore() < GOODCREDIT){
            rate += NORMALRATEADD;
        }else if(customer.getCreditScore() < GREATCREDIT){
            rate += GOODRATEADD;
        }

        // adjust for the duration of the loan
        if(numYears < MAX_YEARS){
            rate += NORMALRATEADD;
        }else{
            rate += BADRATEADD;
        }   
    }

    @Override
    public boolean loanApproved() {

        return Rate * MONTHS_IN_YEAR < RATETOOHIGH && PercentDown >= MIN_PERCENT_DOWN && DebtToIncomeRatio <= DTOITOOHIGH;

    }

    @Override
    public double getPayment() {
        return Payment;
    }

    @Override
    public double getRate() {
        return Rate * MONTHS_IN_YEAR;
    }

    @Override
    public double getPrincipal() {
        return Principal;
    }

    @Override
    public int getYears() {
        return NumberOfPayments / MONTHS_IN_YEAR;
    }
}
