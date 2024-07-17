package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun createComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: CreateCommentRequest,
        memberId: UUID
    ): CommentResponse {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return commentRepository.save(
            Comment.from(
                content = request.content,
                memberId = memberId,
                post = post,
                author = member.nickname
            )
        ).toResponse()
    }

    fun getCommentList(
        channelId: Long,
        boardId: Long,
        postId: Long,
        pageable: Pageable
    ): Page<CommentResponse> {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByPostId(postId, pageable)

        return comment.map { it.toResponse() }
    }

    @Transactional
    fun updateComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest,
        memberId: UUID
    ): CommentResponse {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        val comment =
            commentRepository.findByIdAndPostId(commentId, postId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.memberId != memberId) {
            throw CustomAccessDeniedException("해당 댓글에 대한 수정 권한이 없습니다.")
        }

        comment.update(
            content = request.content
        )

        return comment.toResponse()
    }

    @Transactional
    fun deleteComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        commentId: Long,
        memberId: UUID
    ) {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdAndPostId(commentId, postId)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.memberId != memberId) {
            throw CustomAccessDeniedException("해당 댓글에 대한 삭제 권한이 없습니다.")
        }

        commentRepository.delete(comment)
    }
}