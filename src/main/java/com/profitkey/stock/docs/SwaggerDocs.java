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
	 * ****************************************
	 * FaqCategory Swagger Docs 모음
	 * ****************************************
	 */

	/** FAQ카테고리 생성 docs */
	public static final String SUMMARY_FAQ_CATEGORY_CREATE = "FAQ 카테고리 생성";
	public static final String DESCRIPTION_FAQ_CATEGORY_CREATE =
		"FAQ 카테고리를 생성합니다.<br><br>" + "displayOrder는 0부터 순차적으로 카테고리 순서를 의미합니다.<br>" + "published는 카테고리 사용 여부를 의미합니다.<br>"
			+ "categoryName은 필수값이며 published는 기본값이 true, displayOrder는 기본값이 후순위입니다.";
	/** FAQ카테고리 리스트 조회 docs */
	public static final String SUMMARY_FAQ_CATEGORY_LIST = "FAQ 카테고리 리스트 조회";
	public static final String DESCRIPTION_FAQ_CATEGORY_LIST = "FAQ 카테고리 항목들을 모두 조회합니다.";
	/** FAQ카테고리 수정 docs */
	public static final String SUMMARY_FAQ_CATEGORY_UPDATE = "FAQ 카테고리 수정";
	public static final String DESCRIPTION_FAQ_CATEGORY_UPDATE =
		"FAQ 카테고리를 수정합니다.<br><br>" + "displayOrder는 0부터 순차적으로 카테고리 순서를 의미합니다.<br>" + "published는 카테고리 사용 여부를 의미합니다.";

	/**
	 * ****************************************
	 * Community Swagger Docs 모음
	 * ****************************************
	 */
	public static final String SUMMARY_COMMUNITY_LIST = "커뮤니티 댓글 목록 조회";
	public static final String DESCRIPTION_COMMUNITY_LIST = "종목에 해당하는 댓글 목록을 가져옵니다.";
	public static final String SUMMARY_COMMUNITY_CREATE = "커뮤니티 댓글 생성";
	public static final String DESCRIPTION_COMMUNITY_CREATE = "종목 커뮤니티에 작성자가 입력한 댓글을 등록합니다.";
	public static final String SUMMARY_COMMUNITY_UPDATE = "커뮤니티 댓글 수정";
	public static final String DESCRIPTION_COMMUNITY_UPDATE = "종목 커뮤니티에 작성자가 입력한 댓글을 수정합니다.";
	public static final String SUMMARY_COMMUNITY_DELETE = "커뮤니티 댓글 삭제";
	public static final String DESCRIPTION_COMMUNITY_DELETE = "식별자와 일치하는 댓글을 삭제합니다.";

	/**
	 * ****************************************
	 * Stock Swagger Docs 모음
	 * ****************************************
	 */
	public static final String SUMMARY_STOCK_TOKEN = "한국투자증권 OAUTH2 토큰 GET";
	public static final String DESCRIPTION_STOCK_TOKEN = """
		한국투자증권 OEPN API에서 사용할 토큰을 가져옵니다.<br>
		모의서버에서는 1분에 한번으로 제한됩니다.""";
	public static final String SUMMARY_STOCK_INQUIRE_PRICE = "주식 현재 시세 GET";
	public static final String DESCRIPTION_STOCK_INQUIRE_PRICE = """
		종목 코드에 따라 국내 주식 시세를 불러옵니다.
		""";
	public static final String SUMMARY_STOCK_VOLUME_RANK = "국내주식 거래량순위 GET";
	public static final String DESCRIPTION_STOCK_VOLUME_RANK = """
		조건별 주식 거래량 순위를 불러옵니다.
		""";
	public static final String SUMMARY_STOCK_FLUCTUATION = "국내주식 등락률 순위 GET";
	public static final String DESCRIPTION_STOCK_FLUCTUATION = """
		조건별 주식 등락률 순위를 불러옵니다.
		""";
	public static final String SUMMARY_STOCK_MARKET_CAP = "국내주식 시가총액 상위 GET";
	public static final String DESCRIPTION_STOCK_MARKET_CAP = """
		조건별 시가총액이 높은 종목을 불러옵니다.
		""";
}
