public class AcademicDepartment {
    private final String name;
    private final String responsiblePerson;

    public AcademicDepartment(String name, String responsiblePerson) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    public String toString() {
        return name + " - Responsible: " + responsiblePerson;
    }
}
