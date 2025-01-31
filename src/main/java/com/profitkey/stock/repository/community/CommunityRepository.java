package com.profitkey.stock.repository.community;

import com.profitkey.stock.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

	@Query(value = "WITH RECURSIVE community_tree AS ( " +
		"    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, c.id AS root_id, 1 AS depth "
		+
		"      FROM community c " +
		"     WHERE SUBSTRING(c.ID, 9, 6) = :stockCode " +
		"       AND c.parent_id = 0 " +
		"     UNION ALL " +
		"    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, ct.root_id, ct.depth + 1 " +
		"      FROM community c " +
		"      JOIN community_tree ct ON c.parent_id = ct.id " +
		") " +
		"SELECT * FROM community_tree " +
		" ORDER BY root_id ASC, depth ASC, created_at ASC",
		countQuery = "SELECT COUNT(*) " +
			"  FROM community c " +
			" WHERE SUBSTRING(c.ID, 9, 6) = :stockCode",
		nativeQuery = true)
	Page<Community> findByStockCode(@Param("stockCode") String stockCode, Pageable pageable);

	@Query(value = "WITH RECURSIVE community_tree AS ( " +
		"    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, c.id AS root_id, 1 AS depth "
		+
		"      FROM community c " +
		"     WHERE c.id = :id " +
		"       AND c.parent_id = 0 " +
		"     UNION ALL " +
		"    SELECT c.id, c.writer_id, c.parent_id, c.content, c.created_at, c.updated_at, ct.root_id, ct.depth + 1 " +
		"      FROM community c " +
		"      JOIN community_tree ct ON c.parent_id = ct.id " +
		") " +
		"SELECT * FROM community_tree " +
		" ORDER BY root_id ASC, depth ASC, created_at ASC",
		countQuery = "SELECT COUNT(*) " +
			"  FROM community c " +
			" WHERE id = :id",
		nativeQuery = true)
	Page<Community> findByStockCode2(@Param("id") String id, Pageable pageable);

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

}
