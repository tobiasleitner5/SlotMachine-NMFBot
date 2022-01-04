package testdatagenerator;

public class TestDataConfigElement {
    private int numberOfFlights;
    private String airline;

    @Override
    public String toString() {
        return "TestDataConfigElement{" +
                "numberOfFlights=" + numberOfFlights +
                ", airline='" + airline + '\'' +
                '}';
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getNumberOfFlights() {
        return numberOfFlights;
    }

    public void setNumberOfFlights(int numberOfFlights) {
        this.numberOfFlights = numberOfFlights;
    }
}
