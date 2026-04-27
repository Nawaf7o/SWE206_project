public class ConferenceHall extends Venue {
    private final int tableCount;
    private final boolean soundSystemAvailable;

    public ConferenceHall(String name, int maximumCapacity, int tableCount, boolean soundSystemAvailable) {
        super(name, maximumCapacity);
        this.tableCount = tableCount;
        this.soundSystemAvailable = soundSystemAvailable;
    }

    @Override
    public String getVenueType() {
        return "Conference Hall";
    }

    @Override
    protected String venueDetails() {
        return "Table Count: " + tableCount + "\n"
                + "Sound System Available: " + (soundSystemAvailable ? "Yes" : "No");
    }
}
