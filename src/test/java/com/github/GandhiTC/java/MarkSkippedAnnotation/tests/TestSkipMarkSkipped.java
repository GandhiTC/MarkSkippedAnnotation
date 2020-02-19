package com.github.GandhiTC.java.MarkSkippedAnnotation.tests;



import org.testng.annotations.Test;
import com.github.GandhiTC.java.MarkSkippedAnnotation.annotations.MarkSkipped;



public class TestSkipMarkSkipped
{
	@Test(priority=1)
	@MarkSkipped(dependentMethods={"method3"})
	public void method1()
	{
		System.out.println("method1 = I will be manually skipped.  You should not be able to read this.");
	}


	@Test(priority=2)
	public void method2()
	{
		System.out.println("method2 = I will execute completely.");
	}
	
	
	@Test(priority=3)
	public void method3()
	{
		System.out.println("method3 = I will be automatically skipped.  You should not be able to read this.");
	}
}