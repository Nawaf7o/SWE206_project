public abstract class Venue {
    private final String name;
    private final int maximumCapacity;

    protected Venue(String name, int maximumCapacity) {
        this.name = name;
        this.maximumCapacity = maximumCapacity;
    }

    public String getName() {
        return name;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public String shortDisplay() {
        return name + " (" + getVenueType() + ", capacity " + maximumCapacity + ")";
    }

    public String fullDisplay() {
        return "Venue Name: " + name + "\n"
                + "Type: " + getVenueType() + "\n"
                + "Maximum Capacity: " + maximumCapacity + "\n"
                + venueDetails();
    }

    public abstract String getVenueType();

    protected abstract String venueDetails();
}
