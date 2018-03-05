# NinoValidator
[![Build Status](https://travis-ci.org/dwp/nino-format-validation.svg?branch=master)](https://travis-ci.org/dwp/nino-format-validation) [![Known Vulnerabilities](https://snyk.io/test/github/dwp/nino-format-validation/badge.svg)](https://snyk.io/test/github/dwp/nino-format-validation)

This class performs validation of a National Insurance Number (Nino) format. It uses regex to do this. There is no validation that the nino itself is valid. There is no data lookup functionality.

The class includes the ability to discover the day of week on which various social security benefits are payable.

The class has a static method to validate user input, or it can be used dynamically to query the nino itself.

For example the following will return true because AA370773A is in a valid format.

`NinoValidator.validateNINO("AA370773A");`

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

`ninoValidator.validateNINO("AA370773A")`

`NinoValidator.returnDayOfWeek("AA370773A");`

or

`NinoValidator ninoValidator = new NinoValidator("AA370773A");`

`ninoValidator.returnThisDayOfWeek();`

An InvalidNinoException will be thrown if an error occurs.
