package com.github.GandhiTC.java.MarkSkippedAnnotation.listeners;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.github.GandhiTC.java.MarkSkippedAnnotation.annotations.MarkSkipped;



public class OmitMarkSkipped extends TestListenerAdapter implements IMethodInterceptor
{
	private static List<String>	disabledMethods;
	
	
	@Override
	public void onStart(ITestContext testContext)
	{
		disabledMethods	= new ArrayList<String>();
		
		System.out.println("\r\n");
	}
	
	
	@Override
	public void onFinish(ITestContext testContext)
	{
		System.out.println("\r\nThe following test methods were omitted during this test:");
		
		for(String methodName : disabledMethods)
		{
			System.out.println("    - " + methodName + "()");
		}
		
		System.out.println(" ");
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
		List<IMethodInstance> 	methodsToTest 	= new ArrayList<IMethodInstance>();

		for(IMethodInstance m : methods)
		{
			Method				actualMethod	= m.getMethod().getConstructorOrMethod().getMethod();
			MarkSkipped			msAnnotation	= actualMethod.getAnnotation(MarkSkipped.class);
			
			methodsToTest.add(m);
			
			if(msAnnotation != null)
			{
				disabledMethods.add(actualMethod.getName());
				
				for(String dependent : msAnnotation.dependentMethods())
				{
					disabledMethods.add(dependent);
				}
			}
		}
		
		for(int x = 0; x < methodsToTest.size(); x++)
		{
			IMethodInstance	imi		= methodsToTest.get(x);
			String			imiName	= imi.getMethod().getConstructorOrMethod().getMethod().getName();
			
			if(disabledMethods.contains(imiName))
			{
				methodsToTest.remove(imi);
			}
		}

		return methodsToTest;
	}
}