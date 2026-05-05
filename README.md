# SwagLabs Test Automation Framework using GenAI

## 📌 Overview

This is a UI test automation framework for the **SwagLabs application** built using Selenium, TestNG, and Maven.

The framework demonstrates:
- Page Object Model (POM) design
- Data-driven testing
- CI/CD integration using GitHub Actions
- AI-assisted test data generation using Google Gemini API
- CI-safe fallback mechanism for deterministic execution

---

## ⚙️ Tech Stack
- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Google Gemini API (AI test data generation)
- GitHub Actions (CI/CD)
- Extent Reports

---

## ✨ Key Features

### 🧱 Framework Design
- Page Object Model (POM) architecture
- Clean separation of test logic and page actions
- Reusable utilities and base test structure

### 📊 Data-Driven Testing
- Test data provided via TestNG DataProvider
- Dynamic login test cases generated using AI (Gemini API)
- JSON-based structured test input format

---

### 🤖 AI Integration (GenAI in Testing)
- Uses Google Gemini API to generate login test scenarios dynamically
- AI generates structured JSON test data:
  - username
  - password
  - expected result (SUCCESS / FAIL)

Example AI output:

```json
[
  {
    "username": "admin",
    "password": "password123",
    "expected": "SUCCESS"
  },
  {
    "username": "locked_user",
    "password": "secret",
    "expected": "FAIL"
  }
]
```

---

## 🔁 CI/CD Safe Design
- GitHub Actions pipeline runs on every push to `main`
- CI uses fallback static test data instead of AI API
- Ensures stable and repeatable builds
- Avoids API rate limits and external dependency failures
- Local runs use live Gemini API for dynamic test generation

---

## 🚀 How It Works

### Local Execution Flow
1. TestNG triggers DataProvider
2. `AIDataGenerator` calls Gemini API
3. AI returns structured JSON test cases
4. Tests execute dynamically generated scenarios

---

### CI Execution Flow (GitHub Actions)
1. TestNG triggers DataProvider
2. AI API call is skipped or fails safely
3. Framework switches to fallback dataset
4. Tests execute deterministically (stable build)

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
- Screenshots for failed test cases (if enabled)
- Maven Surefire execution summary

---

## 🧪 Test Coverage

This framework covers **SwagLabs login functionality testing** using multiple scenarios:

- Valid user login → expected SUCCESS
- Invalid user credentials → expected FAIL
- Locked-out user validation
- Problem user behavior validation
- Negative testing for incorrect credentials

Coverage is driven by **data-driven test execution**, where inputs are dynamically generated (locally via AI or fallback dataset in CI).

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

## ⚠️ Design Notes

- AI is used only for **test data generation (not validation logic)**
- Test assertions remain fully deterministic and application-driven
- CI pipeline uses fallback data to ensure stable and repeatable execution
- Local environment supports dynamic AI-generated test cases
- Framework is designed to balance **experimentation (AI)** and **stability (CI)**

---

## 📌 Summary

This framework demonstrates:

- Standard Selenium automation using Page Object Model (POM)
- Data-driven testing with TestNG DataProvider
- Integration of Generative AI (Gemini API) for dynamic test data generation
- CI/CD-safe architecture using fallback mechanisms for stable execution
- Practical implementation of modern QA engineering practices combining automation and AI