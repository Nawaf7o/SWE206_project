import java.time.LocalDateTime;

public class AcademicEvent extends Event {
    private final String topic;
    private final String guestLecturer;

    public AcademicEvent(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
                         Venue venue, AcademicDepartment department, String topic, String guestLecturer) {
        super(name, startDateTime, endDateTime, venue, department);
        this.topic = topic;
        this.guestLecturer = guestLecturer;
    }

    @Override
    public String getCategory() {
        return "Academic";
    }

    @Override
    protected String specificDetails() {
        return "Topic: " + topic + "\n"
                + "Guest Lecturer: " + guestLecturer;
    }
}
