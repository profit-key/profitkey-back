package com.profitkey.stock.docs;

public class SwaggerDocs {

	/**
	 * Boards
	 */
	public static final String SUMMARY_BOARD_LIST = "게시판 목록조회";
	public static final String DESCRIPTION_BOARD_LIST = "게시판 목록을 조회한다!!";
	public static final String SUMMARY_BOARD_DETAIL = "게시판 상세조회";
	public static final String DESCRIPTION_BOARD_DETAIL = "게시판 내용을 조회한다!!";
	public static final String SUMMARY_BOARD_CRAETE = "게시판 글 작성";
	public static final String DESCRIPTION_BOARD_CRAETE = "게시판 새글을 작성한다!!";
	public static final String SUMMARY_BOARD_UPDATE = "게시판 글 수정";
	public static final String DESCRIPTION_BOARD_UPDATE = "게시판 글을 수정한다!!";
	public static final String SUMMARY_BOARD_DELETE = "게시판 글 삭제";
	public static final String DESCRIPTION_BOARD_DELETE = "게시판 글을 삭제한다!!";

	/**
	 * FaqCategory
	 */
	public static final String SUMMARY_FAQ_CATEGORY_CREATE = "FAQ 카테고리 생성";
	public static final String DESCRIPTION_FAQ_CATEGORY_CREATE =
		"FAQ 카테고리를 생성합니다.<br><br>" +
        "displayOrder는 0부터 순차적으로 카테고리 순서를 의미합니다.<br>" +
        "published는 카테고리 사용 여부를 의미합니다.<br>" +
        "categoryName은 필수값이며 published는 기본값이 true, displayOrder는 기본값이 후순위입니다.";

}
