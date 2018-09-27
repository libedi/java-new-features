package com.libedi.new_java_feature.java8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class Java8DateTimeTest {
	
	/*
	 * Java에서 Date와 Calendar의 악평은 자자했다고 합니다.
	 * 윤초, 서머타임 불일치, immutable 이 아닌 문제 등 사회/기술적으로 문제가 많았죠.
	 * 자세한 내용은 아래 링크를 참고하세요.
	 * https://d2.naver.com/helloworld/645609
	 * 
	 * 그래서 Joda-Time 등 이를 극복하려는 노력들이 많았는데,
	 * Java 8에서 이들을 수용하여 새로운 날짜/시간 클래스들을 만들었습니다.
	 * java.time.* 패키지 (JSR-310)
	 * 이를 통해 이전보다 훨씬 훌륭한 안전성과 기능을 제공합니다.
	 * 
	 * 아래 예제는 다음 링크를 참조하여 만들었습니다.
	 * http://starplatina.tistory.com/entry/Java-8-새로운-Date-Time-API
	 * http://blog.eomdev.com/java/2016/04/01/자바8의-java.time-패키지.html
	 * 
	 */
	
	/*
	 * LocalDate : 날짜 관련 클래스. 시간대를 고정해서 사용할 필요가 없을 때 사용.
	 */
	/**
	 * LocalDate 생성
	 * @throws Exception
	 */
	@Test
	public void test_CreateLocalDate() throws Exception {
		LocalDate.now();							// 현재 날짜
		LocalDateTime.now().toLocalDate();			// 현재 날짜. LocalDateTime에서 변환.
		LocalDate.of(2018, Month.SEPTEMBER, 20);	// 2018년 9월 20일
		LocalDate.ofEpochDay(150);					// 1970년 5월 31일 -> 1970년 1월 1일부터 지난 날짜
		LocalDate.parse("2018-09-20");
		LocalDate.parse("2018-09-20", DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate.parse("20180920", DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate.parse("2018/09/20", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}
	
	/**
	 * LocalDate 포맷팅
	 * @throws Exception
	 */
	@Test
	public void test_FormatLocalDate() throws Exception {
		LocalDate localDate = LocalDate.of(2018, Month.SEPTEMBER, 20);
		String format1 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);			// yyyy-MM-dd
		String format2 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);			// yyyyMMdd
		String format3 = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));	// yyyy/MM/dd
		System.out.println("ISO_LOCAL_DATE : " + format1);		// 2018-09-20
		System.out.println("BASIC_ISO_DATE : " + format2);		// 20180920
		System.out.println("yyyy/MM/dd : " + format3);			// 2018/09/20
	}
	
	/*
	 * LocalTime : 시간 관련 클래스. 시간대를 고정해서 사용할 필요가 없을 때 사용.
	 */
	/**
	 * LocalTime 생성
	 * @throws Exception
	 */
	@Test
	public void test_CreateLocalTime() throws Exception {
		LocalTime.now();							// 현재시간
		LocalDateTime.now().toLocalTime();			// 현재시간. LocalDateTime에서 변환.
		LocalTime.of(13, 59, 59);					// 13시 59분 59초 -> of()를 통해 nano second까지 표현가능.
		LocalTime.ofSecondOfDay(24 * 60 * 60 - 1);	// 23시 59분 59초 -> 0시 0분 0초에서 지난 초
		LocalTime.parse("13:25");
		LocalTime.parse("13:25", DateTimeFormatter.ISO_LOCAL_TIME);
		LocalTime.parse("13:25:15");
		LocalTime.parse("13:25:15", DateTimeFormatter.ISO_LOCAL_TIME);
		LocalTime.parse("13/25/15", DateTimeFormatter.ofPattern("HH/mm/ss"));
		LocalTime.parse("오후 01:25:15", DateTimeFormatter.ofPattern("a hh:mm:ss"));
	}
	
	/**
	 * LocalTime 포맷팅
	 * @throws Exception
	 */
	@Test
	public void test_FormatLocalTime() throws Exception {
		LocalTime localTime = LocalTime.of(13, 25, 30);
		String format1 = localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
		String format2 = localTime.format(DateTimeFormatter.ofPattern("a hh:mm:ss"));
		System.out.println("\"ISO_LOCAL_TIME\" : " + format1);
		System.out.println("\"a hh:mm:ss\" : " + format2);
	}
	
	/*
	 * LocalDateTime 클래스 : 날짜와 시간 관련 클래스. 시간대를 고정해서 사용할 필요가 없을 때 사용.
	 */
	@Test
	public void test_CreateLocalDateTime() throws Exception {
		LocalDateTime.now();
		LocalDateTime dateTime = LocalDateTime.of(2018, Month.SEPTEMBER, 21, 13, 42, 30);	// 2018년 09월 21일 13시 42분 30초
		// java.time 패키지는 immutable 클래스이므로, 날짜 변경시 새로운 Object로 생성된다.
		LocalDateTime fast = dateTime.withDayOfMonth(27).withYear(2017);	// 2017년 09월 27일 13시 42분 30초로 새로 생성.
	}
	
	/**
	 * LocalDateTime 포맷팅
	 * @throws Exception
	 */
	@Test
	public void test_FormatLocalDateTime() throws Exception {
		LocalDateTime localDateTime = LocalDateTime.of(2018, Month.SEPTEMBER, 21, 13, 42, 30);
		String format1 = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));	// 2018-09-21 13:42:30
		System.out.println("LocalDateTime : " + format1);
	}
	
	/**
	 * 날짜/시간 자르기
	 * @throws Exception
	 */
	@Test
	public void test_ParseDateTime() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		// 지정한 단위보다 작은 필드가 0으로 설정된다. 
		now.truncatedTo(ChronoUnit.MINUTES);	// 초부터 0으로 설정
	}
	
	/**
	 * 날짜/시간 계산
	 * @throws Exception
	 */
	@Test
	public void test_CalculateDateTime() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		// plus()
		now.plusDays(3);				// 현재날짜에 3일 더한 날짜
		now.plus(3, ChronoUnit.DAYS);	// 현재날짜에 3일 더한 날짜
		now.minusDays(3);				// 현재날짜에 3일 뺀 날짜
		now.minus(3, ChronoUnit.DAYS);	// 현재날짜에 3일 뺀 날짜
		
		// Period : 날짜관점
		Period period = Period.of(0, 0, 3);	// Period.ofDays(3);
		now.plus(period);				// 현재날짜에 3일 더한 날짜
		now.minus(period);				// 현재날짜에 3일 뺀 날짜
		
		// Duration : 시간관점
		LocalDateTime dateTime1 = LocalDateTime.of(2018, Month.SEPTEMBER, 21, 13, 42, 30);
		LocalDateTime dateTime2 = LocalDateTime.of(2018, Month.SEPTEMBER, 21, 15, 02, 30);
		Duration duration = Duration.between(dateTime1, dateTime2);
		System.out.println("Between absolute seconds : " + duration.abs().getSeconds());
	}
	
	/*
	 * ANSI SQL 타입 매핑
	 * --------------------------------------------
	 * | ANSI SQL                | Java 8         |
	 * |------------------------------------------|
	 * | DATE                    | LocalDate      |
	 * | TIME                    | LocalTime      |
	 * | TIMESTAMP               | LocalDateTime  |
	 * | TIME WITH TIMEZONE      | OffsetTime     |
	 * | TIMESTAMP WITH TIMEZONE | OffsetDateTime |
	 * --------------------------------------------
	 */

}
