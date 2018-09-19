package com.libedi.new_java_feature.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.junit.Test;

public class Java8StreamApiTest {
	
	/**
	 * Java 8의 가장 큰 변화는 람다식과 Stream API의 추가입니다.
	 * 여기서는 그 중, Stream API에 대해서 알아봅니다.
	 * 
	 * Stream API가 있기 전에, Java에서는 Collection API가 있습니다.
	 * Collection은 말 그대로 데이터를 담는 역할, 즉 자료구조들의 구현체가 됩니다.
	 * 그래서 Collection의 데이터를 다룰 때는 직접 개발자가 구현을 해주어야 했습니다.
	 * 이에 반해 Stream API는 데이터를 다루는 역할로 나옵니다.
	 * 그래서 Stream API에서 제공하는 API를 이용해 개발자가 연산에 대한 선언을 해주면 됩니다.
	 * 대신 Stream 은 데이터의 연산에 목적이 있기 때문에, 요소의 추가/삭제 등은 불가능합니다.
	 * 
	 * Stream API의 구조는 다음과 같습니다.
	 * 
	 * | 스트림 생성 | 중개연산 (스트림 변환) | 종단 연산(스트림 사용) |
	 * 
	 * Stream API는 종단연산이 없으면, 중간연산은 실행되지 않습니다.
	 * 이를 지연(lazy) 연산이라 하는데, 성능 최적화를 위함입니다.
	 * 
	 * 더 자세한 내용은 아래 링크 참조
	 * https://www.slideshare.net/arawnkr/api-42494051
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_StreamAPI() throws Exception {
		// Java 8 이전 : How 중심
		int sum = 0;
		int count = 0;
		List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// 개발자가 직접 구현. 외부 반복
		for(Integer num : list1) {	// 리스트에서 개별 num을 구해서
			if(num > 5) {			// num이 5보다 크면
				sum += num;			// 함계를 구하고
				count++;			// 카운트 올리고
			}
		}
		double forLoopAvg = (double) sum / count;	// 평균값
		System.out.println("forLoopAvg: " + forLoopAvg);
		
		
		// Java 8 이후 : What 중심
		List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// 반복은 Stream 안에서 수행. 내부 반복
		OptionalDouble optionalAvg = list2.stream()	// 리스트를 스트림으로 바꿔서	(스트림 생성)
								.filter(n -> n > 5)	// 5보다 큰 n을 골라내서		(중개 연산)
								.mapToInt(n -> n)	// IntStream으로 변환해서		(중개 연산)
								.average();			// 평균값						(종단 연산)
		double streamAvg = optionalAvg.getAsDouble();
		System.out.println("streamAvg: " + streamAvg);
		
		
		// 신뢰할 수 있는 반환값 : get() 종단연산.
		List<String> listString = Arrays.asList("a", "b", "c", "d", "e");
		String firstString = listString.stream()
				.findFirst()		// 첫번째 요소를 찾아서
				.get();				// 반환
		System.out.println("firstString: " + firstString);
		
		
		/*
		 * 신뢰할 수 없는 반환값 : 반환값이 있을 수도, 없을 수도 있을 때.
		 * java.util.Optional<T> 사용.
		 */
		List<String> listEmpty = Collections.emptyList();
		
		// 1. 값이 있을 때 반환
		Optional<String> optional = listEmpty.stream().findFirst();
		if(optional.isPresent()) {
			System.out.println("isPresent: " + optional.get());
		}
		
		// 2. 값이 있을 때 수행
		listEmpty.stream().findFirst().ifPresent(System.out::println);
		
		// 3. 값이 있으면 반환, 없으면 다른 값 반환
		String orElseString = listEmpty.stream().findFirst().orElse("orElse Value");
		System.out.println("orElseString: " + orElseString);
	}
	
	
	/**
	 * Java에서 가장 골치아픈 처리 중에 하나가 NullPointerException 이다.
	 * Java 8에서는 이를 안전하게 하기 위한 대안으로 나온 것이 바로 Optional.
	 * 굉장히 유용하게 사용할 수 있는 기능.
	 * 최신 라이브러리에서는 반환값으로 Optional을 넘기고 있는 추세.
	 * @throws Exception
	 */
	@Test
	public void test_OptionalTest() throws Exception {
		// 아래 링크에서 자세하게 설명하고 있습니다. 참고
		// http://multifrontgarden.tistory.com/131
	}

}
