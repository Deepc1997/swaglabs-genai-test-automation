package com.deep.qa.swaglabs_testing.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.deep.qa.swaglabs_testing.utils.AIDataGenerator;

public class TestResultListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        sendToAI(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        sendToAI(result, "FAIL");
    }

    private void sendToAI(ITestResult result, String status) {

    	try {

            String prompt =
                    "Analyze this QA automation result. " +
                    "Test Name: " + result.getName() +
                    ". Status: " + status;

            String response = AIDataGenerator.callGeminiAPI(prompt);

            System.out.println("\n🤖 AI REPORT ANALYSIS:");
            System.out.println(response);

        } catch (Exception e) {

            System.out.println("\n🤖 AI REPORT ANALYSIS SKIPPED");
            System.out.println("Reason: " + e.getMessage());
        }
    	
    
    	
//        String testName = result.getMethod().getMethodName();
//
//        String prompt =
//                "You are a QA automation expert.\n" +
//                "Analyze this test execution:\n" +
//                "Test: " + testName + "\n" +
//                "Status: " + status + "\n" +
//                "Give root cause + improvement suggestion in 3 lines max.";
//
//        String analysis = AIDataGenerator.callGeminiAPI(prompt);
//
//        System.out.println("\n🤖 AI TEST ANALYSIS:");
//        System.out.println(analysis);
    }

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {}
}

// test line - updating a file for push