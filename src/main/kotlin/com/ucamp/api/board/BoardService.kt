package com.ucamp.api.board


import com.ucamp.api.board.domain.Board
import com.ucamp.api.board.dto.BoardResponse
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardService (
    private val boardRepository: BoardRepository
) {
    fun getBoards() : List<BoardResponse> {
        val boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        return boards.map { it.toResponse() }
    }

    fun getBoard(id : Long) : BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow {IllegalArgumentException("Board not found. id =$id") }

        return board.toResponse()
    }
}

private fun Board.toResponse() : BoardResponse =
    BoardResponse(
        id = this.id!!,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
    )