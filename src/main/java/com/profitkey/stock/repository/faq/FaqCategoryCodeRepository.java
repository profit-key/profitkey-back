public interface FaqCategoryCodeRepository extends JpaRepository<FaqCategoryCode, Long> {
    Optional<FaqCategoryCode> findByCategoryName(String categoryName);
} 