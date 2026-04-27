public class PublicSpace extends Venue {
    private final boolean shaded;
    private final boolean openAir;

    public PublicSpace(String name, int maximumCapacity, boolean shaded, boolean openAir) {
        super(name, maximumCapacity);
        this.shaded = shaded;
        this.openAir = openAir;
    }

    @Override
    public String getVenueType() {
        return "Public Space";
    }

    @Override
    protected String venueDetails() {
        return "Shaded: " + (shaded ? "Yes" : "No") + "\n"
                + "Open Air: " + (openAir ? "Yes" : "No");
    }
}
