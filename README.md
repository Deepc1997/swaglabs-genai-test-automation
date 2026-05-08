# SwagLabs Test Automation Framework using GenAI

## 📌 Overview

This project is a Selenium + TestNG-based test automation framework for the SwagLabs application, enhanced with Generative AI (Gemini API) capabilities. It demonstrates how AI can be integrated into QA workflows for test data generation, test case suggestions, and basic test result analysis.

The framework is built as a personal QA engineering project to simulate real-world automation scenarios using modern AI-assisted testing approaches.

---

## Key Features

- Selenium WebDriver-based UI automation
- TestNG test execution framework
- Page Object Model (POM) design pattern
- AI-generated test data using Gemini API
- AI-generated test suggestions after successful flows
- AI-based basic test result analysis
- Fallback mechanism when AI API fails or quota is exceeded
- Data-driven testing using TestNG DataProvider

---

## ⚙️ Tech Stack
- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Google Gemini API (Generative AI)
- GitHub Actions (CI - Continuous Integration)
- Extent Reports
- JSON (org.json)

---

## AI Integration Design

### 1. AI Test Data Generation
- Gemini API generates 3 login credential sets per execution
- JSON response is parsed into TestNG DataProvider
- If API fails, fallback static users are used

### 2. AI Test Suggestions
- After successful login execution, AI generates post-login test scenarios
- Examples: cart validation, sorting, product navigation

### 3. AI Test Analysis
- Each test compares actual vs expected result
- AI provides root-cause style feedback
- Suggests improvements like missing negative or edge test coverage

---

## 🔁 CI Safe Design
- GitHub Actions pipeline runs on every push to `main`
- CI uses fallback static test data instead of AI API
- Ensures stable and repeatable builds
- Avoids API rate limits and external dependency failures
- Local runs use live Gemini API for dynamic test data generation

---

## ▶️ How to Run Locally

```bash
mvn clean test
```

---

## 📁 Reports

After execution, test reports are generated at:

- `test-output/`
- `target/surefire-reports/`
- `test-output/extent-report.html`

These reports include:
- Test execution status (PASS/FAIL)
- Step-level logs from Extent Reports
- Screenshots for failed test cases
- Maven Surefire execution summary

---

## 📦 Project Structure

```
src/main/java
├── pages
│   ├── LoginPage.java
│   ├── HomePage.java
│   ├── CartPage.java
│
├── config
│   ├── ConfigReader.java
│
├── reports
│   ├── ExtentManager.java
│
├── listeners
│   ├── TestResultListener.java
│
├── utils
│   ├── ScreenshotUtil.java
│   ├── AIDataGenerator.java
```

```
src/test/java
├── tests
│   ├── SwagLabsLoginTest.java
│   ├── SwagLabsCartTest.java
│
├── BaseTest.java
```

```
testng.xml
pom.xml
.github/workflows
```

---

## AI Failure Handling

If Gemini API fails due to:
- Rate limit (429)
- Quota exhaustion
- Network issues

Then:
- Framework switches to fallback dataset
- Execution continues without stopping suite


---

## Known Limitations

- Free-tier Gemini API has strict request limits
- AI responses may vary under load
- Chrome DevTools warnings may appear due to version mismatch
- AI is assistive, not authoritative for test assertions

---

## Future Enhancements

- Retry mechanism for failed AI API calls
- Integration with Extent / Allure reporting
- AI-based failure classification (UI / data / backend)
- Smarter test prioritization using AI signals
- Rule-based + AI hybrid assertion engine

---