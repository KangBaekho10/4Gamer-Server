package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto

data class SignupRequest(
    val email: String,
    val password: String,
    val nickname: String,
)