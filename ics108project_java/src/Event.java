import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Event {
    private final String name;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Venue venue;
    private final AcademicDepartment department;

    protected Event(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
                    Venue venue, AcademicDepartment department) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.venue = venue;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public AcademicDepartment getDepartment() {
        return department;
    }

    public boolean overlapsWith(Event other) {
        return startDateTime.isBefore(other.endDateTime) && endDateTime.isAfter(other.startDateTime);
    }

    public String fullDisplay(DateTimeFormatter formatter) {
        return "Name: " + name + "\n"
                + "Category: " + getCategory() + "\n"
                + "Start: " + startDateTime.format(formatter) + "\n"
                + "End: " + endDateTime.format(formatter) + "\n"
                + "Venue: " + venue.shortDisplay() + "\n"
                + "Department Sponsor: " + department + "\n"
                + specificDetails();
    }

    public abstract String getCategory();

    protected abstract String specificDetails();
}
