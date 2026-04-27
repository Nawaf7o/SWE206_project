public class LectureHall extends Venue {
    private final int seatRows;
    private final boolean projectorAvailable;

    public LectureHall(String name, int maximumCapacity, int seatRows, boolean projectorAvailable) {
        super(name, maximumCapacity);
        this.seatRows = seatRows;
        this.projectorAvailable = projectorAvailable;
    }

    @Override
    public String getVenueType() {
        return "Lecture Hall";
    }

    @Override
    protected String venueDetails() {
        return "Seat Rows: " + seatRows + "\n"
                + "Projector Available: " + (projectorAvailable ? "Yes" : "No");
    }
}
