package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post
import java.util.UUID

interface PostRepository : JpaRepository<Post, Long> {

    fun findByBoard(board: Board, pageable: Pageable): Page<Post>

    fun findByIdAndBoard(id: Long, board: Board): Post?

    fun findAllByBoardId(boardId: Long): List<Post>

    fun findAllByBoardIdIn(boardId: Iterable<Long>): List<Post>

    fun findByMemberId(memberId: UUID): List<Post>
}