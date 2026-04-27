import java.time.LocalDateTime;

public class ReligiousEvent extends Event {
    private final String speakerName;
    private final String purpose;

    public ReligiousEvent(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
                          Venue venue, AcademicDepartment department, String speakerName, String purpose) {
        super(name, startDateTime, endDateTime, venue, department);
        this.speakerName = speakerName;
        this.purpose = purpose;
    }

    @Override
    public String getCategory() {
        return "Religious";
    }

    @Override
    protected String specificDetails() {
        return "Speaker/Imam: " + speakerName + "\n"
                + "Purpose: " + purpose;
    }
}
