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

	@Query("SELECT c.id AS commentId, COUNT(l.id) AS likeCount " +
		"FROM Likes l JOIN Community c ON l.commentId = c.id " +
		"WHERE SUBSTRING(c.id, 9, 6) = :stockCode " + // 종목코드 필터
		"  AND c.parentId = '0' " +                   // 최상위 댓글만
		"GROUP BY c.id " +
		"ORDER BY COUNT(l.id) DESC")
	List<Object[]> findMostLikedCommentsByStockCode(@Param("stockCode") String stockCode);

}
