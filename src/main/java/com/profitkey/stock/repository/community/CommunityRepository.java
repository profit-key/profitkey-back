package com.profitkey.stock.repository.community;

import com.profitkey.stock.entity.Community;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<Community, Long> {

	// 종목코드(6자리)에 해당하는 게시글 목록 조회
	@Query(value = """
		    SELECT * 
		      FROM COMMUNITY
		     WHERE SUBSTRING(ID, 9, 6) = :stockCode
		     ORDER BY CREATED_AT DESC
		""", nativeQuery = true)
	List<Community> findByStockCode(@Param("stockCode") String stockCode);

	@Query(value = """
		    SELECT COUNT(*) + 1
		      FROM COMMUNITY
		     WHERE SUBSTRING(ID, 1, 8) = :today
		       AND SUBSTRING(ID, 9, 6) = :stockCode
		""", nativeQuery = true)
	int getNextSequence(@Param("today") String today, @Param("stockCode") String stockCode);

}
