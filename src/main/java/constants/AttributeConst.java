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



    //日報管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content"),
       //↓追記
    REP_ATTENDACE_T("att"),//追記　出勤時間
    REP_LEAVE_T("lea");//追記　退勤時間

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
