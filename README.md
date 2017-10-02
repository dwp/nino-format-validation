# NinoValidator

This class performs validation of a National Insurance Number (Nino) format. It uses regex to do this. There is no validation that the nino itself is valid. There is no data lookup functionality.

The class includes the ability to discover the day of week on which various social security benefits are payable.

The class has a static method to validate user input, or it can be used dynamically to query the postcode itself.

For example: 

`NinoValidator::validateNINO("AA370773A");`

will return true because AA370773A is in a valid format.


#### Project inclusion

properties entry in pom

    <properties>
        <dwp.formatvalidation.nino>x.x</dwp.formatvalidation.nino>
    </properties>
    
internal Artifactory repository reference is required (plugin reference required for OWASP checks)

    <repositories>
        <repository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </pluginRepository>
    </pluginRepositories>

dependency reference

    <dependency>
        <groupId>gov.dwp.software-engineering.formatvalidation</groupId>
        <artifactId>nino</artifactId>
        <version>${dwp.formatvalidation.nino}</version>
    </dependency>
    

#### Example of use

    import gov.dwp.utilities.fomatvalidation.nino;

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
