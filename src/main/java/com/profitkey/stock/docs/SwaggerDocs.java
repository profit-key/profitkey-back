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
    public static final String SUMMARY_FAQ_CREATE = "FAQ 생성";
    public static final String DESCRIPTION_FAQ_CREATE = "FAQ 항목을 생성합니다.";
    public static final String SUMMARY_FAQ_LIST = "FAQ 목록 조회";
    public static final String DESCRIPTION_FAQ_LIST = """
            FAQ 목록을 조회합니다. <br>
            공개여부가 true인 리스트만 반환합니다. <br>
            page는 1페이지부터 조회할 페이지 번호입니다. <br>
            size는 한 페이지에 보여줄 아이템 수입니다. <br>
            최신순으로 반환합니다.
            """;
    public static final String SUMMARY_FAQ_INFO = "FAQ 상세 항목 조회";
    public static final String DESCRIPTION_FAQ_INFO = """
            FAQ 상세 항목을 조회합니다. 잘못된 id 조회시 404 에러를 반환합니다.
            """;

    /**
     * ****************************************
     * AnnounceMent(공지사항) Swagger Docs 모음
     * ****************************************
     */
    public static final String SUMMARY_ANNOUNCE_CREATE = "공지사항 생성";
    public static final String DESCRIPTION_ANNOUNCE_CREATE = "공지사항을 생성합니다.";
    public static final String SUMMARY_ANNOUNCE_LIST = "공지사항 목록 조회";
    public static final String DESCRIPTION_ANNOUNCE_LIST = """
            공지사항 목록을 조회합니다. <br>
            공개여부가 true인 리스트만 반환합니다. <br>
            page는 1페이지부터 조회할 페이지 번호입니다. <br>
            size는 한 페이지에 보여줄 아이템 수입니다. <br>
            최신순으로 반환합니다.
            """;
    public static final String SUMMARY_ANNOUNCE_INFO = "공지사항 상세 항목 조회";
    public static final String DESCRIPTION_ANNOUNCE_INFO = """
            공지사항 상세 항목을 조회합니다. 잘못된 id 조회시 404 에러를 반환합니다.
            """;

    /**
     * ****************************************
     * Community Swagger Docs 모음
     * ****************************************
     */
    public static final String SUMMARY_COMMUNITY_LIST = "커뮤니티 댓글 목록 조회";
    public static final String DESCRIPTION_COMMUNITY_LIST = "종목에 해당하는 댓글 목록을 가져옵니다.";
    public static final String SUMMARY_COMMUNITY_DETAIL = "커뮤니티 댓글 상세 조회";
    public static final String DESCRIPTION_COMMUNITY_DETAIL = """
            선택한 댓글과 하위 대댓글 목록을 조회합니다.<br/>
            id 식별자 yyyyMMdd(8) + 종목코드(6) + 시퀀스(4)
            """;
    public static final String SUMMARY_COMMUNITY_CREATE = "커뮤니티 댓글 생성";
    public static final String DESCRIPTION_COMMUNITY_CREATE = "작성자가 입력한 댓글을 등록합니다.";
    public static final String SUMMARY_COMMUNITY_UPDATE = "커뮤니티 댓글 수정";
    public static final String DESCRIPTION_COMMUNITY_UPDATE = "작성자가 입력한 댓글을 수정합니다.";
    public static final String SUMMARY_COMMUNITY_DELETE = "커뮤니티 댓글 삭제";
    public static final String DESCRIPTION_COMMUNITY_DELETE = """
            선택한 댓글과 하위 대댓글을 삭제합니다.<br/>
            id 식별자 yyyyMMdd(8) + 종목코드(6) + 시퀀스(4)
            """;
    public static final String SUMMARY_COMMUNITY_LIKE = "좋아요 등록/삭제";
    public static final String DESCRIPTION_COMMUNITY_LIKE = """
            선택한 댓글의 좋아요 상태를 변경합니다.<br/>
            isLike(true) : 좋아요<br/>
            isLike(false) : 좋아요 취소
            """;
    /**
     * ****************************************
     * Stock Swagger Docs 모음
     * ****************************************
     */
    public static final String SUMMARY_STOCK_TOKEN = "한국투자증권 OAUTH2 토큰 GET";
    public static final String DESCRIPTION_STOCK_TOKEN = """
            한국투자증권 OEPN API에서 사용할 토큰을 가져옵니다.<br/>
            모의서버에서는 1분에 한번으로 제한됩니다.""";
    public static final String SUMMARY_STOCK_INQUIRE_PRICE = "주식 현재 시세 GET";
    public static final String DESCRIPTION_STOCK_INQUIRE_PRICE = """
            종목 코드에 따라 국내 주식 시세를 불러옵니다.
            """;
    public static final String SUMMARY_STOCK_INQUIRE_DAILY = "주식 현재 시세 기간별 GET";
    public static final String DESCRIPTION_STOCK_INQUIRE_DAILY = """
            종목 코드에 따라 기간별 국내 주식 시세를 불러옵니다.
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

    public static final String SUMMARY_STOCK_INVEST_OPINION = "국내주식 종목투자의견 GET";
    public static final String DESCRIPTION_STOCK_INVEST_OPINION = """
             회원사별로 종목에 대한 투자 가치를 제시합니다.<br/>
             * BUY / 매수: 해당 주식을 사는 것이 좋다는 의견<br/>
             * HOLD / 보유: 현재 보유 중이면 그대로 유지하라는 의견<br/>
             * SELL / 매도: 해당 주식을 파는 것이 좋다는 의견<br/>
             * STRONG BUY / 적극 매수: 강력하게 매수를 추천하는 의견<br/>
             * UNDERPERFORM / 비추천: 시장 평균 대비 성과가 낮을 것으로 예상됨견
            """;

    public static final String SUMMARY_STOCK_INCOME_STATMENT = "국내주식 손익계산서 GET";
    public static final String DESCRIPTION_STOCK_INCOME_STATMENT = """
             해당 종목의 년도별 손익계산서를 불러옵니다.
            """;
    public static final String SUMMARY_STOCK_FINANCIAL_RATIO = "국내주식 재무비율 GET";
    public static final String DESCRIPTION_STOCK_FINANCIAL_RATIO = """
             해당 종목의 년도별 재무비율을 불러옵니다.
            """;
    public static final String SUMMARY_STOCK_PROFIT_RATIO = "국내주식 수익성비율 GET";
    public static final String DESCRIPTION_STOCK_PROFIT_RATIO = """
             해당 종목의 년도별 수익성비율을 불러옵니다.
            """;
    public static final String SUMMARY_STOCK_STABILITY_RATIO = "국내주식 안정성비율 GET";
    public static final String DESCRIPTION_STOCK_STABILITY_RATIO = """
             해당 종목의 년도별 안정성비율을 불러옵니다.
            """;
    public static final String SUMMARY_STOCK_GROWTH_RATIO = "국내주식 성장성비율 GET";
    public static final String DESCRIPTION_STOCK_GROWTH_RATIO = """
             해당 종목의 년도별 성장성비율을 불러옵니다.
            """;

    /**
     * ****************************************
     * Login/Token Docs 모음
     * ****************************************
     */
    public static final String SUMMARY_KAKAO_LOGIN = "카카오 소셜 로그인";
    public static final String DESCRIPTION_KAKAO_LOGIN = """
            카카오 소셜 로그인 API입니다.
            인가 코드를 사용하여 카카오에서 제공하는 사용자 정보를 바탕으로 JWT 토큰을 발급합니다.
            사용자 정보가 존재하지 않으면 새로운 사용자가 생성되고, 그에 맞는 JWT 토큰이 발급됩니다.
            """;

    public static final String SUMMARY_TOKEN_ISSUANCE = "JWT 토큰 발급";
    public static final String DESCRIPTION_TOKEN_ISSUANCE = """
            이메일로 JWT 토큰을 발급하는 API입니다.
            발급된 JWT 토큰에는 아이디, 이메일, 닉네임, 공급자 정보를 포함하고 있습니다.
            """;
    public static final String SUMMARY_TOKEN_REFRESH = "JWT 토큰 갱신";
    public static final String DESCRIPTION_TOKEN_REFRESH = """
            이메일로 JWT 토큰을 갱신하는 API입니다.
            기존 토큰과 비교하여 갱신여부를 판단합니다.
            """;
    public static final String SUMMARY_TOKEN_DISPOSE = "JWT 토큰 폐기";
    public static final String DESCRIPTION_TOKEN_DISPOSE = """
            이메일로 JWT 토큰을 폐기하는 API입니다.
            토큰을 빈값으로 갱신합니다.
            """;

}
