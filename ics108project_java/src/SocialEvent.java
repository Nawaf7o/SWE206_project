import java.time.LocalDateTime;

public class SocialEvent extends Event {
    private final String targetAudience;
    private final String theme;

    public SocialEvent(String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       Venue venue, AcademicDepartment department, String targetAudience, String theme) {
        super(name, startDateTime, endDateTime, venue, department);
        this.targetAudience = targetAudience;
        this.theme = theme;
    }

    @Override
    public String getCategory() {
        return "Social";
    }

    @Override
    protected String specificDetails() {
        return "Target Audience: " + targetAudience + "\n"
                + "Theme: " + theme;
    }
}
