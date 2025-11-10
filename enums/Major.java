package enums;

public enum Major {
    COMPUTER_ENGINEERING("Computer Engineering"),
    DATA_SCIENCE("Data Science"),
    COMPUTER_SCIENCE("Computer Science"),
    INFORMATION_ENGINEERING("Information Engineering"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    CIVIL_ENGINEERING("Civil Engineering"),
    BUSINESS_ADMINISTRATION("Business Administration"),
    ECONOMICS("Economics"),
    PSYCHOLOGY("Psychology"),
    BIOLOGY("Biology"),
    CHEMISTRY("Chemistry"),
    PHYSICS("Physics"),
    MATHEMATICS("Mathematics"),
    ENGLISH_LITERATURE("English Literature"),
    HISTORY("History"),
    POLITICAL_SCIENCE("Political Science"),
    SOCIOLOGY("Sociology"),
    EDUCATION("Education"),
    FINE_ARTS("Fine Arts");

    private final String displayName;

    Major(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Major fromString(String input) {
        for (Major major : values()) {
            if (major.displayName.equalsIgnoreCase(input)
                    || major.name().replace('_', ' ').equalsIgnoreCase(input)) {
                return major;
            }
        }
        throw new IllegalArgumentException("Invalid Major");
    }
}