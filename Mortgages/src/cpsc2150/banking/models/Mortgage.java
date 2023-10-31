package cpsc2150.banking.models;

/** Mortgage Contract
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
    private int NumberOfPayments;
    private double PercentDown;
    /**
     *
     * @param homeCost
     * @param downPayment
     * @param numYears
     * @param customer
     * @pre
     * @post
     */
    public Mortgage (double homeCost, double downPayment, int numYears, ICustomer customer) {
        Customer = customer;

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
        Payment = (Rate * Principal) / (1-(1+Rate)^ -NumberOfPayments);

        // Debt to income ratio is the debt payments (over a period of time) divided by the income
        //(over the same period of time)
        DebtToIncomeRatio = (customer.getMonthlyDebtPayments() + Payment)/(customer.getIncome() / MONTHS_IN_YEAR);


    }

    @Override
    public boolean loanApproved() {
        return false;
    }

    @Override
    public double getPayment() {
        return 0;
    }

    @Override
    public double getRate() {
        return 0;
    }

    @Override
    public double getPrincipal() {
        return 0;
    }

    @Override
    public int getYears() {
        return 0;
    }
}
