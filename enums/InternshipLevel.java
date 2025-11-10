package enums;

public enum InternshipLevel {
    BASIC("Basic"), // ? Year 1/2 Students can only apply for this
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    private final String displayName;

    InternshipLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static InternshipLevel fromString(String input) {
        for (InternshipLevel level : values()) {
            if (level.displayName.equalsIgnoreCase(input)
                    || level.name().equalsIgnoreCase(input)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid Internship Level");
    }
}