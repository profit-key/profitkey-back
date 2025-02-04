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

	@Query(value = "WITH RECURSIVE community_tree AS ( "
		+ "    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, "
		+ "           c.id AS root_id, 1 AS depth " + "    FROM community c "
		+ "    WHERE (:stockCode IS NOT NULL AND SUBSTRING(c.id, 9, 6) = :stockCode) "
		+ "       OR (:id IS NOT NULL AND c.id = :id) " + "    UNION ALL "
		+ "    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, "
		+ "           ct.root_id, ct.depth + 1 " + "    FROM community c "
		+ "    JOIN community_tree ct ON c.parent_id = ct.id " + ") "
		+ "SELECT t.*, COALESCE(l.like_count, 0) AS likeCount " + "FROM community_tree t " + "LEFT JOIN ( "
		+ "    SELECT comment_id, COUNT(*) AS like_count " + "    FROM likes " + "    GROUP BY comment_id "
		+ ") l ON t.id = l.comment_id " + "ORDER BY t.root_id ASC, t.depth ASC, t.created_at ASC", countQuery =
		"SELECT COUNT(*) FROM community c " + "WHERE (:stockCode IS NOT NULL AND SUBSTRING(c.id, 9, 6) = :stockCode) "
			+ "   OR (:id IS NOT NULL AND c.id = :id)", nativeQuery = true)
	Page<Community> findByStockCode(@Param("stockCode") String stockCode, @Param("id") String id, Pageable pageable);

	@Query(value = """
		    SELECT COUNT(*) + 1
		      FROM community
		     WHERE SUBSTRING(ID, 1, 8) = :today
		       AND SUBSTRING(ID, 9, 6) = :stockCode
		""", nativeQuery = true)
	int getNextSequence(@Param("today") String today, @Param("stockCode") String stockCode);

	@Modifying
	@Transactional
	@Query("DELETE FROM Community c WHERE c.parentId = :id")
	void deleteByParentId(@Param("id") Long id);

	//내가 쓴 글 조회
	List<Community> findByWriterId(Long writerId);

}
