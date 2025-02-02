package com.profitkey.stock.repository.community;

import com.profitkey.stock.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	@Transactional
	void deleteByCommentIdAndWriterId(String commentId, String writerId);
}
