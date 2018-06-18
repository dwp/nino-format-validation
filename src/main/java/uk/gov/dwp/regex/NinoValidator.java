package uk.gov.dwp.regex;

import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class NinoValidator {
    private static final String NINO_REGEX = "(^(?!BG)(?!GB)(?!NK)(?!KN)(?!TN)(?!NT)(?!ZZ)[A-Z&&[^DFIQUV]][A-Z&&[^DFIOQUV]][0-9]{6}[ABCD ]?$)";
    private static final String NINO_ERROR_MESSAGE = "Nino Validation Failed";
    private String ninoSuffix;
    private String ninoBody;

    public NinoValidator(String ninoBody, String ninoSuffix) {
        this.ninoBody = ninoBody;
        this.ninoSuffix = ninoSuffix;
    }

    /**
     * Calls setNino in order to maintain validation. If the validation fails throws an InvalidNinoException.
     *
     * @param inputNino data number input to use
     * @throws InvalidNinoException if the inputNino is not of a valid format
     */
    public NinoValidator(String inputNino) throws InvalidNinoException {
        if (!setNino(inputNino)) {
            throw new InvalidNinoException(NINO_ERROR_MESSAGE);
        }
    }

    /**
     * Validates the input against Nino formatting. Checks carried out include:
     * input is not null
     * input is not less than 7 characters
     * input matches REGEX nino format
     * <p>
     * Called by static functions and constructor(String)
     *
     * @param ninoInput data number input to use
     * @return boolean <p>True: Nino is valid
     * <p>False: Nino is not valid
     */
    public static boolean validateNINO(String ninoInput) {
        return (null != ninoInput) && (ninoInput.length() >= 8) && reformatInput(ninoInput).matches(NINO_REGEX);
    }

    /**
     * Validates the input against stricter nino formatting rules (see CV0013).
     * Checks carried out include:
     * <p>
     * input is not null
     * input is 9 characters exactly
     * input matches REGEX nino format
     * <p>
     * The rules as written from the corporate data standards document (reference CV0013) are:
     * + The NI Number must be 9 characters long with no spaces
     * + The first two characters must be alphabetical
     * + Characters three to eight must be numeric
     * + Character nine must be A, B, C, D, or a space
     * + The first character must not be D, F, I, Q, U, or V
     * + The second character must not be D, F, I, O, Q, U, or V
     * + The first two characters must not be combinations of GB, NK, TN, or ZZ (this includes
     *   the reverse of each combination, so BG, KN, and NT are also not valid)
     *
     * @param ninoInput data number input to use that must adhere to CV0013
     * @return boolean <p>True: Nino is valid
     * <p>False: Nino is not valid
     */
    public static boolean validateStrictNINO(String ninoInput) {
        return (null != ninoInput) && (ninoInput.length() == 9) && reformatInput(ninoInput).matches(NINO_REGEX);
    }

    /**
     * Validates Nino input and returns in uppercase format for user display
     *
     * @param inputNino data number input to use
     * @return String - validate and formatted
     * @throws InvalidNinoException if Nino is not a valid format
     */
    public static String getFormNino(String inputNino) throws InvalidNinoException {
        if ((null != inputNino) && (!inputNino.isEmpty()) && (!validateNINO(inputNino))) {
            throw new InvalidNinoException(NINO_ERROR_MESSAGE);
        }

        return reformatInput(inputNino);
    }

    /**
     * Validates Nino input loosely, but returns a strict nino back which
     * follows the corporate data standards for a Nino
     *
     * @param inputNino data number input to use
     * @return String - validated loosely and reformatted strictly
     * @throws InvalidNinoException if Nino is not a valid format
     */
    public static String getStrictFormNino(String inputNino) throws InvalidNinoException {
        if ((null != inputNino) && (!inputNino.isEmpty()) && (!validateNINO(inputNino))) {
            throw new InvalidNinoException(NINO_ERROR_MESSAGE);
        }

        return strictReformatInput(inputNino);
    }

    /**
     * A private function to standardise the input before validation
     * <p>
     * Called by:
     * getDayOfWeek
     * getFormNino
     * validateNINO
     * setNino
     * strictReformatInput
     *
     * @param inputNino data number input to use
     * @return String reformatted input (stripped spaces and changed to uppercase)
     */
    private static String reformatInput(String inputNino) {
        return null != inputNino ? inputNino.replaceAll("[ ]", "").toUpperCase(Locale.ROOT) : null;
    }

    /**
     * A private function to standardise the input to a stricter level before validation
     * <p>
     * getStrictFormNino
     *
     * @param inputNino data number input to use
     * @return String reformatted input (stripped spaces, changed to uppercase, 9 characters long)
     */
    private static String strictReformatInput(String inputNino) {
        return Optional.ofNullable(reformatInput(inputNino))
                .map(input ->
                        (input.length() < 9) ? input.concat(" ") : input)
                .orElse(null);
    }

    /**
     * Returns the day of week that relates to benefits for the given Nino
     *
     * @param inputNino data number input to use
     * @return DayOfWeek relating to benefits for the given input
     * @throws InvalidNinoException if inputNino is invalid
     */
    public static DayOfWeek returnDayOfWeek(String inputNino) throws InvalidNinoException {
        String reformattedInput = reformatInput(inputNino);
        DayOfWeek thisDay; // never returned
        if ((null != reformattedInput) && (validateNINO(reformattedInput))) {
            //0-19 = Monday, 20-39 = Tuesday etc.
            thisDay = DayOfWeek.of((parseInt(reformattedInput.substring(6, 8)) / 20) + 1);
        } else {
            throw new InvalidNinoException(NINO_ERROR_MESSAGE);
        }
        return thisDay;
    }

    /**
     * Validates the Nino for the correct format. Returns true if the format is correct and data is set, false otherwise.
     *
     * @param input - nino to store
     * @return boolean <p>True = valid format and data stored <p>False otherwise.
     */
    public boolean setNino(String input) {
        boolean returnValue = false;
        String reformattedInput = reformatInput(input);

        if ((null != reformattedInput) && (validateNINO(reformattedInput))) {
            if (reformattedInput.length() > 8) {
                ninoSuffix = reformattedInput.substring(8, 9);
            } else {
                ninoSuffix = "";
            }
            ninoBody = reformattedInput.substring(0, 8);
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * Returns just the main body of the Nino, not the suffix
     *
     * @return String The main body of the Nino without the suffix. Nino should be unique without the suffix
     */
    public String getNinoBody() {
        return ninoBody;
    }

    /**
     * The returns the suffix of the Nino A-D
     *
     * @return String Suffix
     */
    public String getNinoSuffix() {
        return ninoSuffix;
    }

    /**
     * Called to validate the existing contents of a Nino
     *
     * @return boolean -    <p>True: Nino is valid
     * <p>False: Nino is not valid
     */
    public boolean validateThis() {
        return validateNINO(ninoBody + ninoSuffix);
    }

    /**
     * Called to validate the existing contents of a Nino according to strict rules
     *
     * @return boolean - <p>True: Nino is valid against strict rules
     * <p>False: Nino is not valid
     */
    public boolean validateThisStrict() {
        return validateStrictNINO(ninoBody + ninoSuffix);
    }

    /**
     * Returns the day of week that relates to benefits for the object
     *
     * @return DayOfWeek relating to benefits for the given input
     * @throws InvalidNinoException if existing nino is invalid or doesn't exist.
     */
    public DayOfWeek returnThisDayOfWeek() throws InvalidNinoException {
        return NinoValidator.returnDayOfWeek(ninoBody);
    }
}
