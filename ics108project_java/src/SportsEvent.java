import java.time.LocalDateTime;

public class SportsEvent extends Event {
    private final String sportType;
    private final boolean requiresOfficials;

    public SportsEvent(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       Venue venue, AcademicDepartment department, String sportType, boolean requiresOfficials) {
        super(name, startDateTime, endDateTime, venue, department);
        this.sportType = sportType;
        this.requiresOfficials = requiresOfficials;
    }

    @Override
    public String getCategory() {
        return "Sports";
    }

    @Override
    protected String specificDetails() {
        return "Sport Type: " + sportType + "\n"
                + "Officials Required: " + (requiresOfficials ? "Yes" : "No");
    }
}
