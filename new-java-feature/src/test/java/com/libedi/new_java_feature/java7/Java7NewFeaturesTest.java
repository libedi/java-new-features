package com.libedi.new_java_feature.java7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		Map<String, String> oldMap = new HashMap<String, String>();
		List<String> oldList = new ArrayList<String>();
		// java 7 이후
		Map<String, String> newMap = new HashMap<>();
		List<String> newList = new ArrayList<>();
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
		assertEquals(oldBillion, newBillion);
		
		long oldCreditCardNumber = 1234567890123456L;
		long newCreditCardNumber = 1234_5678_9012_3456L;
		assertEquals(oldCreditCardNumber, newCreditCardNumber);
		
		double oldPi = 3.141592;
		double newPi = 3.14_1592;
		assertEquals(oldPi, newPi, Double.POSITIVE_INFINITY);
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
	
	/**
	 * NIO 2.0 API
	 * - 기존 java NIO api를 개선하고, 비동기 채널 지원등의 기능추가
	 * - 여기서는 File 관련 내용만 확인
	 * @throws Exception
	 */
	@Test
	public void test_Nio2() throws Exception {
		// 테스트를 위한 사용자 홈 디렉토리 경로
		String userHomePath = System.getProperty("user.home");
		
		/* NIO 2.0에서는 기존의 File 클래스 외에 다양한 정보를 제공하는 api가 추가되었다. */
		// 파일 또는 디렉토리 존재여부 검사
		Path rootPath = Paths.get(userHomePath);
		assertTrue(Files.exists(rootPath));
		
		
		// 하위 디렉토리를 seperate 없이 접근이 가능하다.
		Path testPath1 = Paths.get(userHomePath, "subdirectory1", "subdirectory2", "file1");
		
		
		// 파일 또는 디렉토리 미존재 검사
		assertTrue(Files.notExists(testPath1));
		
		
		// 디렉토리 여부 검사
		assertTrue(Files.isDirectory(rootPath));
		
		
		// 파일 여부 검사
		assertFalse(Files.isRegularFile(rootPath));
		
		
		// 파일 생성
		String newFileName = "testfile.txt";
		Path newFilePath = Paths.get(userHomePath, newFileName);
		assertFalse(Files.exists(newFilePath));
		
		Files.createFile(newFilePath);
		assertTrue(Files.exists(newFilePath));
		
		
		// 디렉토리 생성
		String newDirectoryName = "testDirectory";
		Path newDirectoryPath = Paths.get(userHomePath, newDirectoryName);
		assertFalse(Files.exists(newDirectoryPath));
		
		Files.createDirectory(newDirectoryPath);
		assertTrue(Files.exists(newDirectoryPath));
		assertFalse(Files.isRegularFile(newDirectoryPath));
		assertTrue(Files.isDirectory(newDirectoryPath));
		
		
		// 파일 삭제
		Files.deleteIfExists(newFilePath);
		Files.deleteIfExists(newDirectoryPath);	// 디렉토리는 반드시 비워져있어야 한다.
		
		
		// 파일 복사
		Path copyPath1 = Paths.get(userHomePath, "copyDirectory1");
		Path copyPath2 = Paths.get(userHomePath, "copyDirectory2");
		Files.createDirectory(copyPath1);
		Files.createDirectory(copyPath2);
		
		Path copyFile1 = copyPath1.resolve("copyFile.txt");
		Path copyFile2 = copyPath2.resolve("copyFile.txt");
		
		Files.createFile(copyFile1);
		
		assertTrue(Files.exists(copyFile1));
		assertFalse(Files.exists(copyFile2));
		
		Files.copy(copyFile1, copyFile2);
		
		assertTrue(Files.exists(copyFile2));
		
		try {
			// 만약, 이미 생성되었다면,
			Files.createFile(copyFile2);
			// REPLACE_EXISTING 옵션을 지정하면, 대상파일이 이미 생성되면 실패
			Files.copy(copyFile1, copyFile2, StandardCopyOption.REPLACE_EXISTING);
		} catch(FileAlreadyExistsException e) {
			System.out.println("File copy fail! : File already exists!");
		}
		Files.deleteIfExists(copyFile1);
		Files.deleteIfExists(copyFile2);
		Files.deleteIfExists(copyPath1);
		Files.deleteIfExists(copyPath2);
		
		
		// 파일 이동
		Path movePath1 = Paths.get(userHomePath, "moveDirectory1");
		Path movePath2 = Paths.get(userHomePath, "moveDirectory2");
		Files.createDirectory(movePath1);
		Files.createDirectory(movePath2);
		
		Path moveFile1 = movePath1.resolve("moveFile.txt");
		Path moveFile2 = movePath2.resolve("moveFile.txt");
		
		Files.createFile(moveFile1);
		
		assertTrue(Files.exists(moveFile1));
		assertFalse(Files.exists(moveFile2));
		
		Files.move(moveFile1, moveFile2);
		
		assertTrue(Files.exists(moveFile2));
		
		try {
			// 만약, 이미 생성되었다면,
			Files.createFile(moveFile2);
			// REPLACE_EXISTING 옵션을 지정하면, 대상파일이 이미 생성되면 실패
			Files.move(moveFile1, moveFile2, StandardCopyOption.REPLACE_EXISTING);
		} catch(FileAlreadyExistsException e) {
			System.out.println("File move fail! : File already exists!");
		}
		
		Files.deleteIfExists(moveFile1);
		Files.deleteIfExists(moveFile2);
		Files.deleteIfExists(movePath1);
		Files.deleteIfExists(movePath2);
	}

}
