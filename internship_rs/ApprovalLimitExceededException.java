package internship_rs;

/**
 * A custom checked exception thrown when an attempt is made to approve more applicants
 * for an internship than the allowed limit.
 *
 * Using a custom exception makes the code self-documenting and allows for specific
 * handling of this business rule violation.
 */
public class ApprovalLimitExceededException extends Exception {
    public ApprovalLimitExceededException(String message) {
        super(message);
    }
}
