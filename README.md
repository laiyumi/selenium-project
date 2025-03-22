# Selenium Project

Automated test cases using Java, Selenium WebDriver, TestNG, and ExtentReports.

- Data-driven testing from Excel
- Smart screenshot capture
- HTML reporting
- Configurable via `config.properties`

## Getting Started
1. Clone the repo
2. Set up `config.properties` with your credentials
   - username=school_credential_email
   - password=school_credential_password
   - driverPath=/absolute/path/to/chromedriver
3. Install dependencies `mvn clean install`
4. Run `mvn test`
5After test execution, open `test-output/extent-report.html` to view the HTML report
