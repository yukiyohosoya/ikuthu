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

    //ログイン中のユーザー
    LOGIN_US("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

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

    //ショップ管理・優先フラグ
    SH_PRI_TRUE(1),
    SH_PRI_FALSE(0);

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
