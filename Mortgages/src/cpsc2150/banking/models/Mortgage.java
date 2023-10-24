package cpsc2150.banking.models;
// we have to make a contract with invarriants and correspondants
public class Mortgage extends AbsMortgage implements IMortgage{
    private 

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
