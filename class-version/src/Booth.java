public class Booth extends Patient {
    private String customerName;
    private String vRequested;

    public Booth(String customerName, String vRequested) {
        this.customerName = customerName;
        this.vRequested = vRequested;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getvRequested() {
        return vRequested;
    }
}
