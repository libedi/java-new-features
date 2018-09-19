package com.libedi.new_java_feature.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class Java8FunctionalTest {
	
	/*
	 * Java 8의 가장 큰 변화는 람다식과 Stream API의 추가입니다.
	 * 여기서는 그 중, 람다식에 대해서 알아봅니다.
	 * 람다식은 여태 Java에 없었던 함수형 프로그래밍을 지원하기 위함입니다.
	 * 함수형 프로그래밍은 순수함수->입력이 같으면 출력은 항상 동일하다는 패러다임 을 갖고 있습니다.
	 * 따라서 stateless 한 특성을 갖고 있습니다. (이는 Thread-Safe / 병렬 프로그래밍에 이점을 줍니다.)
	 * 람다식은 이후 사용할 Stream API와도 밀접한 관련이 있습니다.
	 * 
	 * 람다식에 대해 더 쉽게 자세히 이해하시려면 다음 링크에 접속하세요.
	 * http://multifrontgarden.tistory.com/124
	 */

	/**
	 * 람다식
	 * - 람다식의 핵심은 컴파일러의 추론을 통한 코드의 간결화라고 할 수 있다.
	 * @throws Exception
	 */
	@Test
	public void test_lambdaExpressionTest() throws Exception {
		
		/*
		 * Java 8 이전
		 * - Comparator 인터페이스는 사실 함수처럼 사용되지만 익명 클래스로 생성해야 하는 불편함이 있었다.
		 * - 이와 유사한 인터페이스들이 객채화 또는 재사용성이 많지 않을 때에도 많은 코드를 작성해야 했다. 
		 */
		Comparator<Integer> oldExp = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};
		int oldResult = oldExp.compare(5, 10);
		System.out.println("oldResult: " + oldResult);	// 5
		
		
		// Java 8 이후 : 람다식을 이용하여 코드가 매우 간결해졌다.
		Comparator<Integer> newExp = (o1, o2) -> o2 - o1;
		int newResult = newExp.compare(5, 10);
		System.out.println("newresult: " + newResult);	// 5
		
		
		// 파라미터가 1개인 경우, 괄호 생략가능
		Predicate<String> predicate = t -> t.length() > 10;
		boolean isNewResult = predicate.test("test");
		System.out.println("isNewResult: " + isNewResult);	// false
		
		
		// 파라미터가 없는 경우
		Supplier<String> supplier = () -> "supplier";
		String getSupl = supplier.get();
		System.out.println("getSupl: " + getSupl);
		
		
		/*
		 * 위의 예제를 통해 알 수 있듯이, 람다식은 추상메서드가 1개뿐인 인터페이스를 구현한 것.
		 * 추상메서드가 2개 이상이면 람다식으로 구현할 수 없다.
		 * 이러한 인터페이스를 함수형 인터페이스(Functional Interface)라 한다.
		 * 함수형 인터페이스를 만들 때는 추상메서드 1개를 강제하기 위해
		 * 인터페이스 선언시에 @FunctionalInterface 애노테이션을 붙여준다.
		 * - 안 붙여도 되지만, 추후 메서드가 늘어날 경우 람다식으로 사용한 부분에서 컴파일 오류가 발생한다.
		 * 아래 Operator 인터페이스 참조.
		 */
		// 코드 블럭을 사용해야할 경우, return을 사용하여 결과값을 반환한다.
		Operator<Integer> plusSqureOp = (o1, o2) -> {
			int result = o1 * o2;
			return result * result;
		};
		int operatorResult = plusSqureOp.operate(2, 3);
		System.out.println("operatorResult: " + operatorResult);
		
		
		// 자유변수 : 람다식의 파라미터나 식 내부에서 선언되지 않은 변수
		// 자유변수의 값은 변경할 수 없음. (FP는 stateless 하기 때문)
		// 변경시 컴파일 오류발생. 해당 변수는 암묵적 final 변수가 됨.
		int freeVariable = 10;
//		freeVariable = 12;	// 값을 변경하려고 하면, 컴파일 오류 발생
		Operator<Integer> opr = (o1, o2) -> {
			int result = o1 * o2 + freeVariable;
			return result;
		};
		int freeVariableResult = opr.operate(4, 3);
		System.out.println("freeVariableResult: " + freeVariableResult);
	}
	
	/**
	 * 함수형 인터페이스
	 * - @FunctionalInterface 를 붙여서 추상메서드 1개를 강제.
	 * - 추상메서드가 2개 이상이면, 컴파일 오류 발생.
	 * @author Sang jun, Park
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public static interface Operator<T> {
		T operate(T op1, T op2);
	}
	
	
	/**
	 * 메서드 레퍼런스
	 * - 메서드의 레퍼런스를 이용해 람다식을 더 간결하게 만들 수 있다.
	 * @throws Exception
	 */
	@Test
	public void test_MethodReference() throws Exception {
		
		// 람다식 : 메서드 구현 부분이 Integer.parseInt() 메서드 한번 호출로 종료.
		Function<String, Integer> lambdaFunc = str -> Integer.parseInt(str);
		
		// 메서드 레퍼런스 : 위와 같은 경우, 메서드 레퍼런스 사용가능.
		// 함수인자인 str이 공통적으로 사용되므로 컴파일러가 추론가능하다.
		Function<String, Integer> methodReferenceFunc = Integer::parseInt;
		
		// 1. 정적 메서드 레퍼런스
		Function<String, Integer> staticMRFunc = Integer::parseInt;
		
		// 2. 특정 타입의 임의의 객체에 대한 인스턴스 메소드 레퍼런스
		Function<String, Boolean> particularObjectMRFunc = String::isEmpty;
		
		// 3. 특정 객체의 인스턴스 메소드 참조
		String str = "test";
		Predicate<String> particularTypeMRFunc = str::equals;
		
		// 4. 생성자 메서드 레퍼런스
		Supplier<List<String>> constructorMRFunc = ArrayList<String>::new;
	}
	
}
