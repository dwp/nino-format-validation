package uk.gov.dwp.regex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import org.junit.Test;


public class NinoValidatorTest {
    private static final String TEST_BODY_UPPER = "AA370773";
    private static final String TEST_SUFFIX_UPPER = "A";
    private static final String TEST_BODY_LOWER = "aa370773";
    private static final String TEST_SUFFIX_LOWER = "a";
    private static final String TEST_INPUT_UPPER = TEST_BODY_UPPER + TEST_SUFFIX_UPPER;
    private static final String TEST_INPUT_LOWER = TEST_BODY_LOWER + TEST_SUFFIX_LOWER;
    private static final String TEST_BODY_UPPER_SPACES = "AA 37 07 73";
    private static final String TEST_INPUT_UPPER_SPACES = TEST_BODY_UPPER_SPACES + " A";
    private static final String TEST_BODY_LOWER_SPACES = "aa 37 07 73";
    private static final String TEST_INPUT_LOWER_SPACES = TEST_BODY_LOWER_SPACES + " a";
    private static final String TEST_INPUT_UPPER_SUFFIX_SPACE = TEST_BODY_UPPER + " ";
    private static final String TEST_INVALID_NINO_BODY = "12345678";
    private static final String TEST_INVALID_NINO_SUFFIX = "E";
    private static final String TEST_INPUT_ALMOST_CORRECT = "AA 370773";

    @Test
    public void validateConstructorStringInput() throws Exception {
        NinoValidator thisNino = new NinoValidator(TEST_INPUT_UPPER);
        assertEquals(TEST_BODY_UPPER, thisNino.getNinoBody());
    }

    @Test(expected = InvalidNinoException.class)
    public void validateConstructorStringInputInvalid() throws Exception {
        new NinoValidator("");
    }

    @Test
    public void validateGetNinoBody() throws Exception {
        NinoValidator nino = new NinoValidator(TEST_INPUT_UPPER);
        assertEquals(TEST_BODY_UPPER, nino.getNinoBody());
    }

    @Test
    public void validateGetNinoSuffix() throws Exception {
        NinoValidator nino = new NinoValidator(TEST_INPUT_UPPER);
        assertEquals(TEST_SUFFIX_UPPER, nino.getNinoSuffix());
    }

    @Test
    public void validateNinoGivesFalseIfTheFirstLetterIsD_F_I_Q_U_V() {
        assertFalse(NinoValidator.validateNINO("DS370773A"));
    }

    @Test
    public void validateNinoGivesFalseIfTheSecondLetterIsD_F_I_O_Q_U_V() {
        assertFalse(NinoValidator.validateNINO("AO370773A"));
    }

    @Test
    public void validateNinoGivesFalseIfTheFirstTwoLettersAreBG_GB_NK_KN_TN_NT_ZZ() {
        assertFalse(NinoValidator.validateNINO("ZZ370773A"));
    }

    @Test
    public void validateNinoGivesTrueWhenSuffixIsA_B_C_D_Space() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_UPPER_SUFFIX_SPACE));
    }

    @Test
    public void validateNinoGivesTrueWhenLettersAreLowercase() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_LOWER));
    }

    @Test
    public void validateNinoGivesTrueWhenInputIncludesSpaces() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_UPPER_SPACES));
    }

    @Test
    public void validateNinoGivesTrueWhenInputStructuredArbitrarily() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_ALMOST_CORRECT));
    }

    @Test
    public void validateSetNinoGivesTrueWhenNinoContainsSpaces() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_INPUT_UPPER_SPACES);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateSetNinoGivesTrueWhenNinoContainsSpacesWithNoSuffix() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_BODY_LOWER_SPACES);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateValidateThisGivesTrueWhenNinoContainsValidData() {
        NinoValidator nino = new NinoValidator(TEST_BODY_UPPER, TEST_SUFFIX_UPPER);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateValidateNinoWhenGivenNull() {
        assertFalse(NinoValidator.validateNINO(null));
    }

    @Test
    public void validateStrictNinoWhenNinoIsValid() {
        assertTrue(NinoValidator.validateStrictNINO(TEST_INPUT_UPPER));
    }

    @Test
    public void validateStrictNinoIsFalseWhenNinoIsLowerAndHasSpaces() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_INPUT_LOWER_SPACES));
    }

    @Test
    public void validateStrictNinoIsFalseWhenNinoHasNoSuffix() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_BODY_UPPER));
    }

    @Test
    public void validateStrictNinoIsFalseWhenNinoIsUpperAndHasSpaces() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_BODY_UPPER_SPACES));
    }

    @Test
    public void validateStrictNinoGivesFalseIfTheFirstLetterIsD_F_I_Q_U_V() {
        assertFalse(NinoValidator.validateStrictNINO("DS370773A"));
    }

    @Test
    public void validateStrictNinoGivesFalseIfTheSecondLetterIsD_F_I_O_Q_U_V() {
        assertFalse(NinoValidator.validateStrictNINO("AO370773A"));
    }

    @Test
    public void validateStrictNinoGivesFalseIfTheFirstTwoLettersAreBG_GB_NK_KN_TN_NT_ZZ() {
        assertFalse(NinoValidator.validateStrictNINO("ZZ370773A"));
    }

    @Test
    public void validateStrictNinoGivesTrueWhenSuffixIsA_B_C_D_Space() {
        assertTrue(NinoValidator.validateStrictNINO(TEST_INPUT_UPPER_SUFFIX_SPACE));
    }

    @Test
    public void validateStrictNinoGivesTrueWhenLettersAreLowercase() {
        assertTrue(NinoValidator.validateStrictNINO(TEST_INPUT_LOWER));
    }

    @Test
    public void validateStrictNinoGivesFalseWhenInputIncludesSpaces() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_INPUT_UPPER_SPACES));
    }

    @Test
    public void validateStrictNinoGivesFalseWhenInputHasMultipleSpaceSuffixes() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_INPUT_UPPER_SUFFIX_SPACE + " "));
    }

    @Test
    public void validateStrictNinoGivesFalseWhenInputIsStructuredIncorrectly() {
        assertFalse(NinoValidator.validateStrictNINO(TEST_INPUT_ALMOST_CORRECT));
    }

    @Test
    public void validateStrictValidateNinoWhenGivenNull() {
        assertFalse(NinoValidator.validateStrictNINO(null));
    }

    @Test
    public void validateValidateThisStrictGivesTrueWhenInputIsValid() throws InvalidNinoException {
        NinoValidator ninoValidator = new NinoValidator(TEST_INPUT_UPPER);
        assertTrue(ninoValidator.validateThisStrict());
    }

    @Test
    public void validateValidateThisStrictGivesFalseWhenInputContainsNoSuffix() throws InvalidNinoException {
        NinoValidator ninoValidator = new NinoValidator(TEST_BODY_UPPER);
        assertFalse(ninoValidator.validateThisStrict());
    }

    @Test
    public void validateSetNinoWhenGivenNull() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_INPUT_UPPER);

        try {
            nino = new NinoValidator(null);
            fail("should throw an error");

        } catch (InvalidNinoException e) {
            assertThat("should fail validation", e.getMessage(), is(equalTo("Nino Validation Failed")));
        }

        assertFalse(nino.setNino(null));
    }

    @Test
    public void validateValidateThisGivesFalseWhenNinoContainsInvalidData() {
        NinoValidator thisNino = new NinoValidator(TEST_INVALID_NINO_BODY, TEST_SUFFIX_UPPER);
        assertFalse(thisNino.validateThis());
    }

    @Test
    public void validateReturnDayOfWeekGivesThursdayWhenNinoIsValidWithThursdaysLastTwoDigits_60To79() throws InvalidNinoException {
        assertEquals(DayOfWeek.THURSDAY, NinoValidator.returnDayOfWeek(TEST_BODY_UPPER));
    }

    @Test(expected = InvalidNinoException.class)
    public void validateReturnDayOfWeekThrowsErrorWhenNinoIsInvalid() throws InvalidNinoException {
        NinoValidator.returnDayOfWeek(TEST_INVALID_NINO_BODY);
    }

    @Test(expected = InvalidNinoException.class)
    public void validateReturnThisDayOfWeekThrowsErrorWhenNinoIsInvalid() throws InvalidNinoException {
        NinoValidator localNino = new NinoValidator(TEST_INVALID_NINO_BODY, TEST_SUFFIX_UPPER);
        localNino.returnThisDayOfWeek();
    }

    @Test
    public void validateGetFormNinoFromLowercase() throws InvalidNinoException {
        assertEquals(TEST_INPUT_UPPER, NinoValidator.getFormNino(TEST_INPUT_LOWER));
    }

    @Test
    public void validateGetFormNinoFromSpaces() throws InvalidNinoException {
        assertEquals(TEST_INPUT_UPPER, NinoValidator.getFormNino(TEST_INPUT_UPPER_SPACES));
    }

    @Test
    public void validateGetStrictFormNinoFromLowercaseAndSpaces() throws InvalidNinoException {
        assertEquals(TEST_INPUT_UPPER, NinoValidator.getStrictFormNino(TEST_INPUT_LOWER_SPACES));
    }

    @Test
    public void validateGetStrictFormNinoFromLowercaseWithNoSuffix() throws InvalidNinoException {
        assertEquals(TEST_INPUT_UPPER_SUFFIX_SPACE, NinoValidator.getStrictFormNino(TEST_BODY_UPPER));
    }

    @Test
    public void validateGetStrictFormNinoFromUppercaseAndSpaces() throws InvalidNinoException {
        assertEquals(TEST_INPUT_UPPER, NinoValidator.getStrictFormNino(TEST_INPUT_UPPER_SPACES));
    }

    @Test(expected = InvalidNinoException.class)
    public void validateGetFormNinoFailsWithInvalidNino() throws InvalidNinoException {
        NinoValidator.getFormNino(TEST_BODY_UPPER + TEST_INVALID_NINO_SUFFIX);
        throw new AssertionError("Should never get here as invalid nino should cause error to be thrown");
    }

    @Test(expected = InvalidNinoException.class)
    public void validateGetStrictFormNinoFailsWithInvalidNino() throws InvalidNinoException {
        NinoValidator.getStrictFormNino(TEST_BODY_UPPER + TEST_INVALID_NINO_SUFFIX);
        throw new AssertionError("Should never get here as invalid nino should cause error to be thrown");
    }
}
