# NinoValidator
[![Build Status](https://travis-ci.org/dwp/nino-format-validation.svg?branch=master)](https://travis-ci.org/dwp/nino-format-validation) [![Known Vulnerabilities](https://snyk.io/test/github/dwp/nino-format-validation/badge.svg)](https://snyk.io/test/github/dwp/nino-format-validation)

This class performs validation of a National Insurance Number (Nino) format. It uses regex to do
this. There is no validation that the nino itself is valid. There is no data lookup functionality.

The class includes the ability to discover the day of week on which various social security benefits
are payable.

The class has two static methods to validate user input, or it can be used dynamically to query the
nino itself.

The static methods fall into 'loose' and 'strict' validation criteria, where a 'strictly valid' nino
must follow the criteria set within Corporate Data Standards reference CV0013 which are as follows:

 + The NI Number must be 9 characters long with no spaces
 + The first two characters must be alphabetical
 + Characters three to eight must be numeric
 + Character nine must be A, B, C, D, or a space **
 + The first character must not be D, F, I, Q, U, or V
 + The second character must not be D, F, I, O, Q, U, or V
 + The first two characters must not be combinations of GB, NK, TN, or ZZ (this includes
   the reverse of each combination, so BG, KN, and NT are also not valid)

** from `1.3.0` onwards the suffix **must** be supplied (ie. cannot be blank) and **must** contain a letter (ie. spaces are not allowed).
 
A 'loosely valid' nino, on the other hand, can be between 9 and 13 characters long, where the nino
can contain spaces and the last character can be optionally blank (i.e., `AA 37 07 73 A` and `AA370773A` are all loosely valid,
but only `AA370773A`is strictly valid).

For example the following will return true because `AA 37 07 73 A` is a 'loosely valid' nino.

`NinoValidator.validateNINO("AA 37 07 73 A");`

But the following will return false because `AA 37 07 73 A` is not a 'strictly valid' nino.

`NinoValidator.validateStrictNINO("AA 37 07 73 A")`

#### Project inclusion

properties entry in pom

    <properties>
        <dwp.formatvalidation.nino>x.x</dwp.formatvalidation.nino>
    </properties>

dependency reference

    <dependency>
        <groupId>uk.gov.dwp.regex</groupId>
        <artifactId>nino-validation</artifactId>
        <version>${dwp.formatvalidation.nino}</version>
    </dependency>
    

#### Example of use

    import uk.gov.dwp.regex.NinoValidator;

_declaration_

`NinoValidator ninoValidator = new NinoValidator("AA370773A");`

or

`NinoValidator ninoValidator = new NinoValidator("AA370773", "A");`

_Use_

`NinoValidator.validateNINO("AA370773A")`

`NinoValidator.validateStrictINO("AA370773A")`

`NinoValidator.returnDayOfWeek("AA370773A");`

or

`NinoValidator ninoValidator = new NinoValidator("AA370773A");`

`ninoValidator.validateThis()`

`ninoValidator.validateThisStrict()`

`ninoValidator.returnThisDayOfWeek();`

An InvalidNinoException will be thrown if an error occurs.
