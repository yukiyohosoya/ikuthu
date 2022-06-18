package constants;

//画面項目の値等を定義するEnumクラス

public enum AttributeConst {
    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザーと店舗
    LOGIN_US("login_user"),
    SELECT_SH("select_shop"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //どこのコマンドから来たのかの記録
    COMAND("comand"),

    //ユーザー管理
    USER("user"),
    US_ID("id"),
    USERS("users"),
    US_COUNT("users_count"),
    US_MAIL("mailaddress"),
    US_PASS("password"),
    US_PASS_K("password_k"),//入力確認用パスワード
    US_NAME("name"),

    //ショップ管理
    SHOP("shop"),
    SHOPS("shops"),
    SH_COUNT("shops_count"),
    SH_ID("sh_id"),
    SH_US_ID("shops_us_id"),
    SH_NAME("shops_name"),

    //イベント管理
    EVENT("event"),
    EVENTS("events"),
    EV_COUNT("events_count"),
    EV_ID("ev_id"),
    EV_SHOP("ev_shop"),
    EV_INDEX_SHOP("ev_index_shop"),
    EV_NAME("ev_name"),
    EV_DAY("ev_day"),

    //グッズ管理
    GOODS("goods"),
    GOODSS("goodss"),
    GS_COUNT("goodss_count"),
    GS_ID("gd_id"),
    GS_SHOP("gd_shop"),
    GS_INDEX_SHOP("gd_index_shop"),
    GS_NAME("gd_name"),
    GS_STOCK("gd_stock"),
    GS_CREATEDAY("gd_createday"),
    GS_SELLINGPRICE("gd_sellingprice"),
    GS_PURCHASEPRICE("gd_purchaseprice"),
    GS_PICTURE("gd_picture"),

    //イベント限定グッズ管理
    LMEVGOODS("lmevgoods"),
    LMEVGOODSS("lmevgoodss"),
    LMEVGS_COUNT("lmevgoodss_count"),
    LMEVGS_SELLINGPRICE("lmevgoods_sellingprice"),
    LMEVGS_SOLDGOODS("lmevgoods_soldgoods"),

    //ショップ管理・優先フラグ
    SH_PRI_TRUE(1),
    SH_PRI_FALSE(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0);

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text=text;
        this.i=null;
    }

    private AttributeConst(final Integer i) {
        this.text=null;
        this.i=i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
