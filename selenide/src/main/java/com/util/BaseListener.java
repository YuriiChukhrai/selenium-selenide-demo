package com.util;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
public class BaseListener implements ITestListener, IInvokedMethodListener {

	/* Methods of Interface 'ITestListener' */
	@Override
	public void onTestStart(ITestResult result) {
		// NOP
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// NOP
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if(!BaseUtils.isWebDriverDead(ThreadStoreLocal.getWebDriver())) {
			BaseUtils.makeScreenAsShot("Test_FAIL_", false, ThreadStoreLocal.getWebDriver());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// NOP
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// NOP
	}

	@Override
	public void onStart(ITestContext context) {
		// NOP
	}

	@Override
	public void onFinish(ITestContext context) {
		// NOP
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// NOP
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// NOP
	}
}
