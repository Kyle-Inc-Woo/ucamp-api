package com.ucamp.api.board

import com.ucamp.api.board.domain.Board
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class BoardInitializer(
    private val boardRepository: BoardRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (boardRepository.count() > 0) return

        val defaultBoards = listOf(
            Board(
                name = "Free",
                description = "자유롭게 이야기하는 게시판"
            ),
            Board(
                name = "QnA",
                description = "질문과 답변을 나누는 게시판"
            ),
            Board(
                name = "Market",
                description = "중고거래와 나눔을 위한 게시판"
            ),
            Board(
                name = "Notice",
                description = "공지사항 게시판"
            )
        )

        boardRepository.saveAll(defaultBoards)
    }
}