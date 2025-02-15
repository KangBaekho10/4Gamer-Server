package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Tag

interface TagRepository : JpaRepository<Tag, String>