package com.libedi.new_java_feature.java7;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Java 7 새로운 기능
 * @author Sangjun, Park
 *
 */
public class Java7NewFeaturesTest {
	
	/**
	 * 다이아몬드 연산자
	 * - Generic을 훨씬 간단하게 선언할 수 있다.
	 * @throws Exception
	 */
	@Test
	public void test_DiamondOperation() throws Exception {
		// java 7 이전
		Map<String, String> oldVersion = new HashMap<String, String>();
		// java 7 이후
		Map<String, String> java7Version = new HashMap<>();
	}
	
	/**
	 * String in Switch
	 * - Switch 문 안에 문자열을 사용할 수 있다.
	 * @throws Exception
	 */
	@Test
	public void test_StringInSwitch() throws Exception {
		String str = "monday";
		switch(str) {
			case "sunday":
				System.out.println("Sunday!");
				break;
			case "monday":
				System.out.println("Monday!");
				break;
			case "saturday":
				System.out.println("Saturday!");
				break;
			default:
				System.out.println("No");
				break;
		}
	}
	
	/**
	 * try-with-resources 문
	 * - 자동적으로 자원을 해제하여 코드를 간결하게 하고, 오류를 줄여준다.
	 * @throws Exception
	 */
	@Test
	public void test_TryWithResources() {
		// java 7 이전 : 자원 할당/해제에만 많은 코딩을 해야한다.
		OldResource oldResource = null;
		try {
			oldResource = OldResource.getResource(false);
			use(oldResource);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oldResource != null) {
				try {
					oldResource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// java 7 이후 : 훨씬 코드가 간결해졌고, finally 문에서 자원해제를 하지 않아 코드 안정성이 높아졌다.
		// try-with-resources를 사용하기 위해선 리소스는 반드시 java.lang.AutoCloseable 인터페이스를 구현해야 한다.
		try(NewResource newResource = NewResource.getResource(false)) {
			use(newResource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void use(Resource resource) {
		resource.use();
	}
	
	/**
	 * 숫자 리터럴에서 _(underscore) 사용
	 * - 정수와 실수에 "_" 를 사용하여 가독성을 높일 수 있다.
	 * @throws Exception
	 */
	@Test
	public void test_UnderscoreLiteral() throws Exception {
		int oldBillion = 1000000000;
		int newBillion = 1_000_000_000;
		
		long oldCreditCardNumber = 1234567890123456L;
		long newCreditCardNumber = 1234_5678_0123_4567L;
		
		double oldPi = 3.141592;
		double newPi = 3.14_1592;
	}
	
	/**
	 * Multicatch
	 * - 예외처리를 묶어서 할 수 있어, 코드 중복을 피할 수 있다.
	 */
	@Test
	public void test_Multicatch() {
		// java 7 이전
		MultiExceptionClass oldVersion = new MultiExceptionClass();
		try {
			oldVersion.throwIOException();
			oldVersion.throwTestException();
		} catch (IOException e) {
			// 코드 중복
			System.out.println("Exception!");
		} catch (TestException e) {
			// 코드 중복
			System.out.println("Exception!");
		}
		
		// java 7 이후
		MultiExceptionClass newVersion = new MultiExceptionClass();
		try {
			newVersion.throwIOException();
			newVersion.throwTestException();
		} catch (IOException | TestException e) {
			System.out.println("Exception!");
		}
	}
	
	@Test
	public void test_Nio2() throws Exception {
		
	}

}
