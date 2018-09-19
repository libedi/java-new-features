package com.libedi.new_java_feature.java8;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import lombok.Data;

public class Java8OptionalTest {

	/*
	 * Java 개발자들을 가장 골치아프게 하는 것 중에 하나가
	 * 바로 NullPointerException(NPE)일 것입니다.
	 * null 의 개념을 창시한 Tony Hoare란 분도 이것을 100만불 짜리 실수라고 인정했습니다;;;
	 * (https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare)
	 * Java 8에서는 이 NPE를 발생시키지 않고 안전하게 접근할 수 있는 방법을 제공합니다.
	 * Optional. 알아봅시다.
	 */
	
	/**
	 * Java 8 이전의 NullPointerException 상황
	 */
	@Test(expected = NullPointerException.class)
	public void test_OldJavaVersion() {
		// NPE 발생! 메서드의 설명 참조.
		String city = getCityOfMemberFromOrder(null);
		// city가 null 인 경우, client NPE 발생 코드
		System.out.println(city.length());
	}
	
	// 테스트 모델
	@Data
	public static class Order {
		private Long id;
		private Date date;
		private Member member;
	}
	
	@Data
	public static class Member {
		private Long id;
		private String name;
		private Address address;
	}
	
	@Data
	public static class Address {
		private String street;
		private String city;
		private String zipcode;
	}
	
	// 테스트 메서드 : NPE 발생 코드
	public String getCityOfMemberFromOrder(Order order) {
		/*
		 * NPE에 노출된 코드.
		 * - NPE가 발생하는 경우.
		 * 1. order 가 null 인 경우
		 * 2. order.getMember() 가 null 인 경우
		 * 3. order.getMember().getAddress() 가 null 인 경우
		 * 4. order.getMember().getAddress().getCity() 가 null 인 경우, client 에서 NPE 발생.
		 */
		return order.getMember().getAddress().getCity();
	}
		
	
	/**
	 * Java 8 이전의 NPE 방어패턴
	 */
	@Test
	public void test_OldJavaVersionNPEProtected() {
		// 1. 중첩 null 체크
		String city1 = getCityOfMemberFromOrder_CheckNull(null);
		
		// 2. 기본값 반환
		String city2 = getCityOfMemberFromOrder_ReturnDefault(null);
		
		// NPE 방어패턴이 들어가니 코드가 엄청나게 길어졌다;;;
	}

	// 테스트 메서드 : 중첩 null 체크... null check hell...
	public String getCityOfMemberFromOrder_CheckNull(Order order) {
		if(order != null) {
			Member member = order.getMember();
			if(member != null) {
				Address address = member.getAddress();
				if(address != null) {
					String city = address.getCity();
					if(city != null) {
						return city;
					}
				}
			}
		}
		return "Test City";
	}
	
	// 테스트 메서드 : 기본값 반환
	private String getCityOfMemberFromOrder_ReturnDefault(Order order) {
		if(order == null) {
			return "Test City";
		}
		Member member = order.getMember();
		if(member == null) {
			return "Test City";
		}
		Address address = member.getAddress();
		if(address == null) {
			return "Test City";
		}
		String city = address.getCity();
		if(city == null) {
			return "Test City";
		}
		return city;
	}
	
	
	/*
	 * 이런 코드는 가독성과 유연성, 유지보수성이 떨어진다.
	 * 이전 Java 버전에서 null을 사용했다면,
	 * Java 8에서는 "있을지 없을지 모르는 값"의 개념을 도입한다.
	 * 이른바 java.util.Optional<T> 클래스!
	 * 
	 * Optional의 효과
	 * 1. null을 직접 다루지 않고, null 체크를 하지 않는다.
	 * 2. 명시적으로 null일 수 있다는 가능성을 표현할 수 있다.
	 */
	
	/**
	 * Optional 생성
	 * 1. Optional.empty() : 비어있는 Optional Object 생성
	 * 2. Optional.of(obj) : Object의 Optional Object 생성. obj가 null일 경우, NPE 반환.
	 * 3. Optional.ofNullable(obj) :  Object의 Optional Object 생성. obj가 null일 경우, 비어있는 Optional 반환.
	 */
	@Test
	public void test_CreateOptional() {
		Optional<Order> optOrder = Optional.empty();
		Optional<Member> optMember = Optional.of(new Member());
//		Optional<Member> nullMember = Optional.of(null);	// NPE 발생
		Optional<Address> optAddress = Optional.ofNullable(new Address());
		Optional<Address> emptyAddress = Optional.ofNullable(null);	// Optional.empty() 와 동일
	}
	
	
	/**
	 * Optional 안의 Object 접근
	 * 1. get() : 비어있는 경우, NoSuchElementException 을 던짐.
	 * 2. orElse(T other) : 비어있는 경우, 넘겨준 인자를 반환.
	 * 3. orElseGet(Supplier<? extends T> other) : 비어있는 경우, Supplier 함수형 인터페이스에 적용된 값을 반환. 함수형 프로그래밍이므로 지연 반환. orElse대비 성능이점.
	 * 4. orElseThrow(Supplier<? extends X> exceptionSupplier) : 비어있는 경우, 함수형 인터페이스에 적용된 exception을 던짐.
	 * 5. ifPresent(Consumer<? super T> consumer) : 존재할 경우, Consumer 함수형 인터페이스 실행. 반환이 아닌 소비 인터페이스
	 */
	@Test(expected = IllegalStateException.class)
	public void test_GetObjectByOptional() {
		// get()
		Optional<Order> optOrder = Optional.of(new Order());
		Order order = optOrder.get();
		
		Optional<Order> emptyOrder = Optional.empty();
		// get()은 NoSuchElementException을 던지므로, isPresent()로 체크가 필요하다.
		if(emptyOrder.isPresent()) {
			Order order2 = emptyOrder.get();
		}
		
		// orElse()
		Optional<String> emptyStr = Optional.empty();
		String str = emptyStr.orElse("else string");	// optStr에 값이 있으면, 그 값이 반환된다. 여기서는 없어서 else string.
		
		// orElseGet()
		Optional<Member> optMember = Optional.empty();
		Member member = optMember.orElseGet(Member::new);
		
		// orElseThrow()
		Optional<Address> optAddress = Optional.empty();
		Address address = optAddress.orElseThrow(IllegalStateException::new);	// IllegalStateException 반환
		
		// ifPresent()
		Optional<String> optStr = Optional.of("Optional is good.");
		optStr.ifPresent(System.out::println);
	}
	
	/**
	 * 이제 Optional을 이용하여 앞서 본 메서드를 개선해보자.
	 * Optional을 사용할 때 주의할 점은, null 체크를 하지 않아야 한다는 점.
	 * 함수적 사고와 Stream API 를 이용하면 깔끔하고 간결한 코드가 나온다.
	 */
	@Test
	public void test_UseOptional() {
		String city = getCityOfMemberFromOrder_Optional(null);
		System.out.println(city);
	}
	
	// Optional로 개선한 getCityOfMemberFromOrder() 메서드
	private String getCityOfMemberFromOrder_Optional(Order order) {
		return Optional.ofNullable(order)
				.map(Order::getMember)
				.map(Member::getAddress)
				.map(Address::getCity)
				.orElse("Test City");
	}
	
}
