package com.profitkey.stock.repository.community;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {

	@Query("SELECT c, " +
		" (SELECT COUNT(*) FROM Likes l WHERE l.commentId = c.id) AS like_count, " +
		" (SELECT COUNT(*) FROM Community cc WHERE cc.parentId = c.id) AS replie_count " +
		"  FROM Community c " +
		" WHERE SUBSTRING(c.id, 9, 6) = :stockCode " +
		"   AND c.parentId = '0'")
	Page<Object[]> findByStockCodeWithCounts(@Param("stockCode") String stockCode, Pageable pageable);

	@Query("SELECT c, " +
		" (SELECT COUNT(*) FROM Likes l WHERE l.commentId = c.id) AS like_count" +
		"  FROM Community c " +
		" WHERE c.parentId = :id " +
		" ORDER BY c.createdAt ASC")
	Page<Object[]> findByParentId(@Param("id") String id, Pageable pageable);

	@Query(value = " SELECT COUNT(*) + 1 " +
		"  FROM Community c " +
		" WHERE SUBSTRING(c.id, 1, 8) = :today " +
		"   AND SUBSTRING(c.id, 9, 6) = :stockCode ")
	int getNextSequence(@Param("today") String today, @Param("stockCode") String stockCode);

	@Modifying
	@Transactional
	@Query("DELETE FROM Community c WHERE c.parentId = :id")
	void deleteByParentId(@Param("id") Long id);

	//내가 쓴 글 조회
	List<Community> findByWriterId(Long writerId);

	//최신순 정렬
	@Query("SELECT c, " +
		" (SELECT COUNT(*) FROM Likes l WHERE l.commentId = c.id) AS like_count " +
		" FROM Community c " +
		" WHERE c.parentId = '0' " +
		" ORDER BY c.createdAt DESC, c.content ASC")
	// content 또는 다른 유효한 필드로 수정
	Page<Object[]> findLatest(Pageable pageable);

	//인기순 정렬
	@Query(value = "SELECT c.*, " +
		"(SELECT COUNT(*) FROM Likes l WHERE l.COMMENT_ID = c.id) AS like_count " +
		"FROM community c " +
		"WHERE c.PARENT_ID = '0' " +
		"ORDER BY like_count DESC, c.created_at DESC",
		nativeQuery = true)
	Page<Object[]> findCommentsByPopularity(Pageable pageable);

}
