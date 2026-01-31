package com.ucamp.api.board


import com.ucamp.api.board.domain.Board
import com.ucamp.api.board.dto.BoardCreateRequest
import com.ucamp.api.board.dto.BoardPatchRequest
import com.ucamp.api.board.dto.BoardResponse
import com.ucamp.api.board.dto.BoardUpdatedRequest
import com.ucamp.api.board.exception.BoardNotFoundException
import com.ucamp.api.common.exception.InvalidBoardPatchRequestException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    fun getBoards(): List<BoardResponse> {
        val boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        return boards.map { it.toResponse() }
    }

    fun getBoard(id: Long): BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Board not found. id =$id") }

        return board.toResponse()
    }

    fun createBoard(request: BoardCreateRequest): BoardResponse {
        if (boardRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Name already exists : ${request.name}")
        }

        val saved = boardRepository.save(
            Board(
                name = request.name,
                description = request.description,
                createdAt = LocalDateTime.now(),
            )
        )

        return saved.toResponse()
    }

    @Transactional
    fun updateBoard(id: Long, request: BoardUpdatedRequest): BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow { BoardNotFoundException(id) }

        board.update(request.name, request.description)

        return board.toResponse()
    }

    @Transactional
    fun deleteBoard(id: Long) {
        val board = boardRepository.findById(id)
            .orElseThrow { BoardNotFoundException(id) }

        boardRepository.delete(board)
    }

    @Transactional
    fun patchBoard(id: Long, request: BoardPatchRequest): BoardResponse {
        val board = boardRepository.findById(id)
            .orElseThrow { BoardNotFoundException(id) }

        if (request.name == null && request.description == null) {
            throw InvalidBoardPatchRequestException("At least one field (name/description) must be provided")
        }

        if (request.name != null && request.name.isBlank()) {
            throw InvalidBoardPatchRequestException("name must not be blank")
        }

        if (request.description != null && request.description.isBlank()) {
            throw InvalidBoardPatchRequestException("description cannot be blank")
        }

        board.patch(request.name, request.description)
        return board.toResponse()
    }

}


private fun Board.toResponse(): BoardResponse =
    BoardResponse(
        id = this.id!!,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
    )