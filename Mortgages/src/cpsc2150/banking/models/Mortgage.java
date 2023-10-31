package cpsc2150.banking.models;
import java.lang.Math;

/** The Mortgage class models a mortgage contract, determining its terms based on the home cost, down payment, loan duration, and a customer's financial profile.
 *
 * @invariant Payment > 0 AND 0 <= Rate <= 1 AND Customer AND DebtToIncomeRatio > 0 AND Principal > 0 AND
 *            0 <= PercentDown < 1
 *
 * @correspondence self.Payment = payment AND self.Rate = rate AND self.Customer = customer AND
 *                 self.DebtToIncomeRatio = debttoincomeratio AND self.Principal = principal AND
 *                 self.NumberOfPayments = numberofpayments AND self.PercentDown = percentdown
 *
 */
public class Mortgage extends AbsMortgage implements IMortgage{
    private double Payment;
    private double Rate;
    private ICustomer Customer;
    private double DebtToIncomeRatio;
    private double Principal;
    private int LoanDurationInYears;
    private double PercentDown;
    /**
     *
     * @param homeCost the cost of the home
     * @param downPayment the down payment for the home
     * @param numYears the number of years for the loan
     * @param customer the customer applying for the mortgage
     *
     * @pre
     * @post
     */
    public Mortgage (double homeCost, double downPayment, int numYears, ICustomer customer) {
        this.Customer = customer;
        this.LoanDurationInYears = numYears;
        // If the loan is for less than 30 years, add 0.5%; otherwise, add 1%
        if (numYears < MAX_YEARS) {
            Rate = BASERATE + 0.005;
        }
        else {
            Rate = BASERATE + 0.01;
        }

        // If the percent down is not at least 20%, add 0.5% to the APR
        PercentDown = downPayment/homeCost;
        if (PercentDown < PREFERRED_PERCENT_DOWN) {
            Rate += 0.005;
        }

        // Adding based on credit score
        double creditScore = customer.getCreditScore();
        if (creditScore < BADCREDIT) {
            Rate += VERYBADRATEADD;
        }
        else if (creditScore >= BADCREDIT && creditScore < FAIRCREDIT) {
            Rate += BADRATEADD;
        }
        else if (creditScore >= FAIRCREDIT && creditScore < GOODCREDIT) {
            Rate += NORMALRATEADD;
        }
        else if (creditScore >= GOODCREDIT && creditScore < GREATCREDIT) {
            Rate += GOODRATEADD;
        }
        else if (creditScore >= GREATCREDIT) {
            Rate += 0;
        }

        // Our Principal amount for the loan is the cost of the house minus the down payment
        Principal = homeCost - downPayment;

        // monthly payments for the loan
        Payment = (Rate * Principal) / Math.pow((1-(1+Rate)), -NumberOfPayments);

        // Debt to income ratio is the debt payments (over a period of time) divided by the income
        //(over the same period of time)
        DebtToIncomeRatio = (customer.getMonthlyDebtPayments() + Payment)/(customer.getIncome() / MONTHS_IN_YEAR);


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
        return LoanDurationInYears;
    }
}
