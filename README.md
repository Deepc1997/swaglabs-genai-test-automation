# SwagLabs Test Automation Framework

## Project Overview
UI test automation framework for SwagLabs using Selenium, TestNG, and Maven.  
Integrated with GitHub Actions to run tests automatically on every push.

---

## Tech Stack
- Java 17
- Selenium WebDriver
- TestNG
- Maven
- GitHub Actions
- Extent Reports

---

## Key Features
- Page Object Model (POM)
- Data-driven testing (JSON + DataProvider)
- CI pipeline with GitHub Actions
- Headless execution in CI
- Test reports generation

---

## How to Run
mvn clean test

---

## Reports
- test-output/
- target/surefire-reports/
- test-output/extent-report.html

---

## CI/CD
- Runs on push to main
- Executes Maven tests
- Uploads reports

---

## Project Structure
- src/main/java
- src/test/java
- src/test/resources
- testng.xml
- .github/workflows