package uk.gov.dwp.regex;

import org.junit.Test;

import java.time.DayOfWeek;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class NinoValidatorTest {
    private static final String TEST_BODY = "AA370773";
    private static final String TEST_SUFFIX = "A";
    private static final String TEST_INPUT = TEST_BODY + TEST_SUFFIX;
    private static final String TEST_BODY_LOWER_SPACES = "aa 37 07 73";
    private static final String TEST_INPUT_LOWER = "aa370773a";
    private static final String TEST_INPUT_SUFFIX_SPACE = "AA370773 ";
    private static final String TEST_BODY_SPACES = "AA 37 07 73";
    private static final String TEST_INPUT_SPACES = TEST_BODY_SPACES + " A";
    private static final String TEST_INVALID_NINO_BODY = "12345678";
    private static final String TEST_INVALID_NINO_SUFFIX = "E";

    public NinoValidatorTest() throws InvalidNinoException {
    }

    @Test
    public void validateConstructorStringInput() throws Exception {
        NinoValidator thisNino = new NinoValidator(TEST_INPUT);
        assertEquals(TEST_BODY, thisNino.getNinoBody());
    }

    @Test(expected = InvalidNinoException.class)
    public void validateConstructorStringInputInvalid() throws Exception {
        new NinoValidator("");
    }

    @Test
    public void validateGetNinoBody() throws Exception {
        NinoValidator nino = new NinoValidator(TEST_INPUT);
        assertEquals(TEST_BODY, nino.getNinoBody());
    }

    @Test
    public void validateGetNinoSuffix() throws Exception {
        NinoValidator nino = new NinoValidator(TEST_INPUT);
        assertEquals(TEST_SUFFIX, nino.getNinoSuffix());
    }

    @Test
    public void validateNinoGivesFalseIfTheFirstLetterIsD_F_I_Q_U_V() {
        assertFalse(NinoValidator.validateNINO("DS123456A"));
    }

    @Test
    public void validateNinoGivesFalseIfTheSecondLetterIsD_F_I_O_Q_U_V() {
        assertFalse(NinoValidator.validateNINO("AO123456A"));
    }

    @Test
    public void validateNinoGivesFalseIfTheFirstTwoLettersAreBG_GB_NK_KN_TN_NT_ZZ() {
        assertFalse(NinoValidator.validateNINO("ZZ123456A"));
    }

    @Test
    public void validateNinoGivesTrueWhenSuffixIsA_B_C_D_Space() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_SUFFIX_SPACE));
    }

    @Test
    public void validateNinoGivesTrueWhenLettersAreLowercase() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_LOWER));
    }

    @Test
    public void validateNinoGivesTrueWhenInputIncludesSpaces() {
        assertTrue(NinoValidator.validateNINO(TEST_INPUT_SPACES));
    }

    @Test
    public void validateSetNinoGivesTrueWhenNinoContainsSpaces() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_INPUT_SPACES);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateSetNinoGivesTrueWhenNinoContainsSpacesWithNoSuffix() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_BODY_LOWER_SPACES);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateValidateThisGivesTrueWhenNinoContainsValidData() {
        NinoValidator nino = new NinoValidator(TEST_BODY, TEST_SUFFIX);
        assertTrue(nino.validateThis());
    }

    @Test
    public void validateValidateNinoWhenGivenNull() {
        assertFalse(NinoValidator.validateNINO(null));
    }

    @Test
    public void validateSetNinoWhenGivenNull() throws InvalidNinoException {
        NinoValidator nino = new NinoValidator(TEST_INPUT);

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
        NinoValidator thisNino = new NinoValidator(TEST_INVALID_NINO_BODY, TEST_SUFFIX);
        assertFalse(thisNino.validateThis());
    }

    @Test
    public void validateReturnDayOfWeekGivesThursdayWhenNinoIsValidWithThursdaysLastTwoDigits_60To79() throws InvalidNinoException {
        assertEquals(DayOfWeek.THURSDAY, NinoValidator.returnDayOfWeek(TEST_BODY));
    }

    @Test(expected = InvalidNinoException.class)
    public void validateReturnDayOfWeekThrowsErrorWhenNinoIsInvalid() throws InvalidNinoException {
        NinoValidator.returnDayOfWeek(TEST_INVALID_NINO_BODY);
    }

    @Test(expected = InvalidNinoException.class)
    public void validateReturnThisDayOfWeekThrowsErrorWhenNinoIsInvalid() throws InvalidNinoException {
        NinoValidator localNino = new NinoValidator(TEST_INVALID_NINO_BODY, TEST_SUFFIX);
        localNino.returnThisDayOfWeek();
    }

    @Test
    public void validateGetFormNinoFromLowercase() throws InvalidNinoException {
        assertEquals(TEST_INPUT, NinoValidator.getFormNino(TEST_INPUT_LOWER));
    }

    @Test
    public void validateGetFormNinoFromSpaces() throws InvalidNinoException {
        assertEquals(TEST_INPUT, NinoValidator.getFormNino(TEST_INPUT_SPACES));
    }

    @Test(expected = InvalidNinoException.class)
    public void validateGetFormNinoFailsWithInvalidNino() throws InvalidNinoException {
        NinoValidator.getFormNino(TEST_BODY + TEST_INVALID_NINO_SUFFIX);
        throw new AssertionError("Should never get here as invalid nino should cause error to be thrown");
    }
}