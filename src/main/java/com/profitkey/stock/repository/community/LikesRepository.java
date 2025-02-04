package com.profitkey.stock.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	@Transactional
	void deleteByCommentIdAndWriterId(String commentId, String writerId);

	//사용자가 좋아요 누른 댓글 id 목록 조회
	@Query("SELECT l.commentId FROM Likes l WHERE l.writerId = :userId")
	List<Long> findLikedCommentIdsByUserId(@Param("userId") Integer userId);
}
