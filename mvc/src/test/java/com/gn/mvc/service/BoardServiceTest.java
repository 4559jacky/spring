package com.gn.mvc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gn.mvc.entity.Board;

@SpringBootTest
class BoardServiceTest {
	
	@Autowired
	private BoardService service;

	@Test
	void testSelectBoardOne_success() {
		// 1. 예상 데이터
		Long id = 62L;
		Board expected = Board.builder().boardTitle("이거 6개인데").build();
		// 2. 실제 데이터
		Board real = service.selectBoardOne(id);
		// 3. 비교 및 검증
		assertEquals(expected.getBoardTitle(), real.getBoardTitle());
		
	}
	
	@Test
	void testSelectBoardOne_fail() {
		// 1. 예상 데이터
		Long id = 192L;
		Board expected = null;
		// 2. 실제 데이터
		Board real = service.selectBoardOne(id);
		// 3. 비교 및 검증
		assertEquals(expected, real);
	}
	
	@Test
	void testCreateBoardOne_success() {
		
	}
	
	

}
