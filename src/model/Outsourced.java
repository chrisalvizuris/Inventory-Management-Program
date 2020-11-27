package model;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /***
     * Gets the company name of the part calling this method.
     * @return Returns the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /***
     * Sets the company name of the part calling this method.
     * @param companyName Company name being set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
