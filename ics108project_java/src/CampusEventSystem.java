import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CampusEventSystem {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Scanner scanner = new Scanner(System.in);
    private final List<Event> events = new ArrayList<>();
    private final List<Venue> venues = new ArrayList<>();
    private final List<AcademicDepartment> departments = new ArrayList<>();

    public CampusEventSystem() {
        seedData();
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = InputHelper.readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1 -> manageEvents();
                case 2 -> manageVenues();
                case 3 -> manageDepartments();
                case 4 -> showSystemSummary();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("System closed.");
    }

    private void printMainMenu() {
        System.out.println("\n=== Campus Event Management System ===");
        System.out.println("1. Manage events");
        System.out.println("2. Manage venues");
        System.out.println("3. Manage departments");
        System.out.println("4. View system summary");
        System.out.println("0. Exit");
    }

    private void manageEvents() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Event Menu ---");
            System.out.println("1. Add event");
            System.out.println("2. List events");
            System.out.println("3. View event details");
            System.out.println("0. Back");

            int choice = InputHelper.readInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1 -> addEvent();
                case 2 -> listEvents();
                case 3 -> showEventDetails();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void manageVenues() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Venue Menu ---");
            System.out.println("1. Add venue");
            System.out.println("2. List venues");
            System.out.println("0. Back");

            int choice = InputHelper.readInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1 -> addVenue();
                case 2 -> listVenues();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void manageDepartments() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Department Menu ---");
            System.out.println("1. Add department");
            System.out.println("2. List departments");
            System.out.println("0. Back");

            int choice = InputHelper.readInt(scanner, "Choose an option: ");
            switch (choice) {
                case 1 -> addDepartment();
                case 2 -> listDepartments();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addEvent() {
        if (venues.isEmpty()) {
            System.out.println("Add a venue before creating an event.");
            return;
        }

        if (departments.isEmpty()) {
            System.out.println("Add a department before creating an event.");
            return;
        }

        System.out.println("\nSelect event classification:");
        System.out.println("1. Sports");
        System.out.println("2. Social");
        System.out.println("3. Religious");
        System.out.println("4. Academic");

        int typeChoice = InputHelper.readInt(scanner, "Choose an option: ");
        String name = InputHelper.readNonEmptyString(scanner, "Event name: ");
        LocalDateTime start = InputHelper.readDateTime(scanner, "Start date/time (yyyy-MM-dd HH:mm): ", DATE_TIME_FORMATTER);
        LocalDateTime end = InputHelper.readDateTime(scanner, "End date/time (yyyy-MM-dd HH:mm): ", DATE_TIME_FORMATTER);

        if (!start.isBefore(end)) {
            System.out.println("Event start time must be before end time.");
            return;
        }

        Venue venue = chooseVenue();
        if (venue == null) {
            return;
        }

        AcademicDepartment department = chooseDepartment();
        if (department == null) {
            return;
        }

        Event event = buildEventByType(typeChoice, name, start, end, venue, department);
        if (event == null) {
            System.out.println("Invalid event classification.");
            return;
        }

        if (hasOverlap(event)) {
            System.out.println("Cannot create event because the selected venue already has an overlapping event.");
            return;
        }

        events.add(event);
        System.out.println("Event added successfully.");
    }

    private Event buildEventByType(int typeChoice, String name, LocalDateTime start, LocalDateTime end,
                                   Venue venue, AcademicDepartment department) {
        return switch (typeChoice) {
            case 1 -> {
                String sportType = InputHelper.readNonEmptyString(scanner, "Sport type: ");
                boolean requiresOfficials = InputHelper.readYesNo(scanner, "Requires referees/officials? (y/n): ");
                yield new SportsEvent(name, start, end, venue, department, sportType, requiresOfficials);
            }
            case 2 -> {
                String audience = InputHelper.readNonEmptyString(scanner, "Target audience: ");
                String theme = InputHelper.readNonEmptyString(scanner, "Social theme: ");
                yield new SocialEvent(name, start, end, venue, department, audience, theme);
            }
            case 3 -> {
                String speaker = InputHelper.readNonEmptyString(scanner, "Speaker or imam name: ");
                String purpose = InputHelper.readNonEmptyString(scanner, "Purpose of event: ");
                yield new ReligiousEvent(name, start, end, venue, department, speaker, purpose);
            }
            case 4 -> {
                String topic = InputHelper.readNonEmptyString(scanner, "Academic topic: ");
                String guestName = InputHelper.readNonEmptyString(scanner, "Guest lecturer name: ");
                yield new AcademicEvent(name, start, end, venue, department, topic, guestName);
            }
            default -> null;
        };
    }

    private boolean hasOverlap(Event candidate) {
        for (Event existing : events) {
            if (existing.getVenue().getName().equalsIgnoreCase(candidate.getVenue().getName())
                    && existing.overlapsWith(candidate)) {
                return true;
            }
        }
        return false;
    }

    private Venue chooseVenue() {
        System.out.println("\nAvailable venues:");
        for (int i = 0; i < venues.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, venues.get(i).shortDisplay());
        }

        int choice = InputHelper.readInt(scanner, "Select venue number: ");
        if (choice < 1 || choice > venues.size()) {
            System.out.println("Invalid venue selection.");
            return null;
        }

        return venues.get(choice - 1);
    }

    private AcademicDepartment chooseDepartment() {
        System.out.println("\nAvailable departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, departments.get(i));
        }

        int choice = InputHelper.readInt(scanner, "Select department number: ");
        if (choice < 1 || choice > departments.size()) {
            System.out.println("Invalid department selection.");
            return null;
        }

        return departments.get(choice - 1);
    }

    private void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n--- Event List ---");
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            System.out.printf("%d. %s | %s | %s%n",
                    i + 1,
                    event.getName(),
                    event.getCategory(),
                    event.getVenue().getName());
        }
    }

    private void showEventDetails() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        listEvents();
        int choice = InputHelper.readInt(scanner, "Select event number: ");
        if (choice < 1 || choice > events.size()) {
            System.out.println("Invalid event selection.");
            return;
        }

        System.out.println();
        System.out.println(events.get(choice - 1).fullDisplay(DATE_TIME_FORMATTER));
    }

    private void addVenue() {
        System.out.println("\nSelect venue type:");
        System.out.println("1. Sports area");
        System.out.println("2. Lecture hall");
        System.out.println("3. Conference hall");
        System.out.println("4. Public space");

        int typeChoice = InputHelper.readInt(scanner, "Choose an option: ");
        String name = InputHelper.readNonEmptyString(scanner, "Venue name: ");
        int capacity = InputHelper.readPositiveInt(scanner, "Maximum capacity: ");

        Venue venue = switch (typeChoice) {
            case 1 -> {
                String surfaceType = InputHelper.readNonEmptyString(scanner, "Surface type: ");
                boolean indoor = InputHelper.readYesNo(scanner, "Indoor area? (y/n): ");
                yield new SportsArea(name, capacity, surfaceType, indoor);
            }
            case 2 -> {
                int seatRows = InputHelper.readPositiveInt(scanner, "Number of seat rows: ");
                boolean projectorAvailable = InputHelper.readYesNo(scanner, "Projector available? (y/n): ");
                yield new LectureHall(name, capacity, seatRows, projectorAvailable);
            }
            case 3 -> {
                int tableCount = InputHelper.readPositiveInt(scanner, "Number of tables: ");
                boolean soundSystem = InputHelper.readYesNo(scanner, "Sound system available? (y/n): ");
                yield new ConferenceHall(name, capacity, tableCount, soundSystem);
            }
            case 4 -> {
                boolean shaded = InputHelper.readYesNo(scanner, "Shaded area? (y/n): ");
                boolean openAir = InputHelper.readYesNo(scanner, "Open-air space? (y/n): ");
                yield new PublicSpace(name, capacity, shaded, openAir);
            }
            default -> null;
        };

        if (venue == null) {
            System.out.println("Invalid venue type.");
            return;
        }

        venues.add(venue);
        System.out.println("Venue added successfully.");
    }

    private void listVenues() {
        if (venues.isEmpty()) {
            System.out.println("No venues available.");
            return;
        }

        System.out.println("\n--- Venue List ---");
        for (Venue venue : venues) {
            System.out.println(venue.fullDisplay());
            System.out.println();
        }
    }

    private void addDepartment() {
        String name = InputHelper.readNonEmptyString(scanner, "Department name: ");
        String responsiblePerson = InputHelper.readNonEmptyString(scanner, "Responsible person: ");
        departments.add(new AcademicDepartment(name, responsiblePerson));
        System.out.println("Department added successfully.");
    }

    private void listDepartments() {
        if (departments.isEmpty()) {
            System.out.println("No departments available.");
            return;
        }

        System.out.println("\n--- Department List ---");
        for (AcademicDepartment department : departments) {
            System.out.println(department);
        }
    }

    private void showSystemSummary() {
        System.out.println("\n--- System Summary ---");
        System.out.println("Departments: " + departments.size());
        System.out.println("Venues: " + venues.size());
        System.out.println("Events: " + events.size());
        System.out.println();
        listDepartments();
        System.out.println();
        listVenues();
        System.out.println();
        listEvents();
    }

    private void seedData() {
        departments.add(new AcademicDepartment("Computer Science", "Dr. Sara Ahmed"));
        departments.add(new AcademicDepartment("Business Administration", "Prof. Omar Khalid"));

        venues.add(new LectureHall("Hall A1", 120, 12, true));
        venues.add(new SportsArea("Main Stadium", 500, "Grass", false));
        venues.add(new ConferenceHall("Innovation Center", 80, 10, true));
        venues.add(new PublicSpace("Central Courtyard", 300, true, true));
    }
}
