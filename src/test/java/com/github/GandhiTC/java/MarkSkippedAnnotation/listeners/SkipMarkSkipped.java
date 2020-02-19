package com.github.GandhiTC.java.MarkSkippedAnnotation.listeners;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import com.github.GandhiTC.java.MarkSkippedAnnotation.annotations.MarkSkipped;



public class SkipMarkSkipped extends TestListenerAdapter implements IMethodInterceptor, IInvokedMethodListener
{
	private List<String> disabledMethods;
	
	
	@Override
	public void onStart(ITestContext testContext)
	{
		disabledMethods	= new ArrayList<String>();
	}
	
	
	@Override
	public void onFinish(ITestContext testContext)
	{
		System.out.println("The following test methods were skipped during this test:");
		
		for(String methodName : disabledMethods)
		{
			System.out.println("    - " + methodName);
		}
	}
	
	
	@Override
	public void onTestSkipped(ITestResult testResult)
	{
		if(testResult.getThrowable() != null)
		{
			testResult.getThrowable().setStackTrace(new StackTraceElement[0]);
		}
	}


	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context)
	{
		List<IMethodInstance> 	result 			= new ArrayList<IMethodInstance>();

		for(IMethodInstance m : methods)
		{
			Method				actualMethod	= m.getMethod().getConstructorOrMethod().getMethod();
			MarkSkipped			msAnnotation	= actualMethod.getAnnotation(MarkSkipped.class);
			
			result.add(m);
			
			if(msAnnotation != null)
			{
				if(!disabledMethods.contains(actualMethod.getName()))
				{
					disabledMethods.add(actualMethod.getName());
				}
				
				for(String dependent : msAnnotation.dependentMethods())
				{
					if(!disabledMethods.contains(dependent))
					{
						disabledMethods.add(dependent);
					}
				}
			}
		}

		return result;
	}


	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult)
	{
		MarkSkipped markSkipped 	= testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(MarkSkipped.class);
		String		testMethod	 	= method.getTestMethod().getConstructorOrMethod().getName();
		String		methodFullName	= method.getTestMethod().getConstructorOrMethod().getMethod().getDeclaringClass().getCanonicalName() + "." + testMethod + "()";
		String 		skipMessage1 	= "\r\nTest method \"" + methodFullName + "\" was manually skipped by using the @MarkSkipped annotation.\r\n";
		String 		skipMessage2 	= "\r\nTest method \"" + methodFullName + "\" was automatically skipped because a prerequisite method did not complete successfully.\r\n";
		
		if(markSkipped != null)
		{
			throw new SkipException(skipMessage1);
		}
		else if(disabledMethods.contains(testMethod))
		{
			throw new SkipException(skipMessage2);
		}
	}


	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult)
	{
		
	}
}