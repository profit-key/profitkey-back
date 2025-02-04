@Getter
@Builder
@Schema(description = "FAQ 간단 조회 응답")
public class FaqSimpleResponse {
    private Long id;
    private String title;
    private String categoryName;
    private LocalDateTime createdAt;

    @Builder
    public FaqSimpleResponse(Long id, String title, String categoryName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }
} 