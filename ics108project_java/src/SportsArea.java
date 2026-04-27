public class SportsArea extends Venue {
    private final String surfaceType;
    private final boolean indoor;

    public SportsArea(String name, int maximumCapacity, String surfaceType, boolean indoor) {
        super(name, maximumCapacity);
        this.surfaceType = surfaceType;
        this.indoor = indoor;
    }

    @Override
    public String getVenueType() {
        return "Sports Area";
    }

    @Override
    protected String venueDetails() {
        return "Surface Type: " + surfaceType + "\n"
                + "Indoor: " + (indoor ? "Yes" : "No");
    }
}
