package com.pknu.backboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.pknu.backboard.entity.Board;
import com.pknu.backboard.repository.BoardRepository;

@SpringBootTest
class BackboardApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	void contextLoads() {
	}

	@Test  // INSERT 테스트
	void testInsertJpa() {
		Board board1 = new Board();
		board1.setTitle("첫번째 게시글입니다.");  // @Setter에서 자동 생성해주는 메서드
		board1.setContent("내용은 없습니다.");
		board1.setCreateDate(LocalDateTime.now());

		Board board2 = new Board();
		board2.setTitle("두번째 게시글입니다.");
		board2.setContent("내용은 없다니까요.");
		board2.setCreateDate(LocalDateTime.now());

		this.boardRepository.save(board1); // INSERT 실행
		this.boardRepository.save(board2); // INSERT 실행
	}

	@Test  // INSERT INTO 100개
	void testInsertDummyJpa() {
		for (int i = 0; i < 100; i++) {
			Board board = new Board();
			board.setTitle(String.format("테스트 더미데이터입니다 %03d", i));
			board.setContent("특별한 내용은 없습니다.");			
			board.setCreateDate(LocalDateTime.now());

			this.boardRepository.save(board);
		}
	}

	@Test // SELECT * 테스트
	void testSelectJpa() {
		List<Board> all = this.boardRepository.findAll();
		// 테스트시 내가 기대하는 값과 현재 값이 같은지 확인메서드
		assertEquals(7, all.size());

		Board board = all.get(0);
		assertEquals("첫번째 게시글입니다.", board.getTitle());
	}

	@Test // SELECT FROM WHERE 테스트
	void testSelectOneJpa() {
		// Optional -> Null을 허용
		Optional<Board> opBoard = this.boardRepository.findById(53L);
		if (opBoard.isPresent()) {
			Board board = opBoard.get();
			assertEquals("내용은 없다니까요.", board.getContent());
		}
	}

	@Test
	void testSelectByTitle() {
		Board board = this.boardRepository.findByTitle("두번째 게시글입니다.");
		assertEquals(53L, board.getBno());
	}

	@Test // SELECT FROM LIKE
	void testSelectByTitleLike() {
		List<Board> boards = this.boardRepository.findByTitleLike("%게시글%");
		assertEquals(7, boards.size());

		Board board = boards.get(0); // 첫번째 게시글
		assertEquals("첫번째 게시글입니다.", board.getTitle());
	}

	@Test // 삭제
	void testDeleteLastOne() {
		assertEquals(7, boardRepository.count());
		Optional<Board> opBoard = this.boardRepository.findById(53L);
		assertTrue(opBoard.isPresent());

		Board board = opBoard.get();
		this.boardRepository.delete(board);
		assertEquals(3, boardRepository.count());  // 한건 지워서 3건 남음
	}

	@Test  // 수정 테스트
	void testUpdateOne() {
		Optional<Board> opBoard = this.boardRepository.findById(1L);  // 1번 보드데이터 가져오기
		assertTrue(opBoard.isPresent());  // 가져온 데이터가 있는지 여부 체크

		Board board = opBoard.get(); // Optional<Board>가 NULL이면 get() 할 수 없음
		board.setContent("내용이 테스트에서 변경되었습니다!!!");

		this.boardRepository.save(board);

		// 테스트내용 그대로 JPA 코딩시 사용가능
	}
}
