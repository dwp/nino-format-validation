package uk.gov.dwp.regex;

import java.time.DayOfWeek;
import java.util.Locale;

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
     * A private function to standardise the input before validation
     * <p>
     * Called by:
     * getDayOfWeek
     * getFormNino
     * validateNINO
     * setNino
     *
     * @param inputNino data number input to use
     * @return String reformatted input (stripped spaces and changed to uppercase)
     */
    private static String reformatInput(String inputNino) {
        return null != inputNino ? inputNino.replaceAll("[ ]", "").toUpperCase(Locale.ROOT) : null;
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
     * Returns the day of week that relates to benefits for the object
     *
     * @return DayOfWeek relating to benefits for the given input
     * @throws InvalidNinoException if existing nino is invalid or doesn't exist.
     */
    public DayOfWeek returnThisDayOfWeek() throws InvalidNinoException {
        return NinoValidator.returnDayOfWeek(ninoBody);
    }
}
