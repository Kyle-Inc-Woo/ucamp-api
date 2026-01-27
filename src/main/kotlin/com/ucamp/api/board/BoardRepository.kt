package com.ucamp.api.board

import com.ucamp.api.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
    fun existsByName(name: String): Boolean
}