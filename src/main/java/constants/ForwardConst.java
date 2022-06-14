package constants;

//リクエストパラメーターの変数名、変数値、jspファイルの名前等画面遷移に関わる値を定義するEnumクラス

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_USER("User"),
    ACT_EVENT("Event"),
    ACT_SHOP("Shop"),
    ACT_GOODS("Goods"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_SELECT("select"),
    CMD_DESELECTION("deselection"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_US_INDEX("users/index"),
    FW_US_SHOW("users/show"),
    FW_US_NEW("users/new"),
    FW_US_EDIT("users/edit"),

    FW_SH_INDEX("shop/index"),
    FW_SH_SHOW("shop/show"),
    FW_SH_NEW("shop/new"),
    FW_SH_EDIT("shop/edit"),

    FW_EV_INDEX("event/index"),
    FW_EV_SHOW("event/show"),
    FW_EV_NEW("event/new"),
    FW_EV_EDIT("event/edit"),

    FW_GS_INDEX("goods/index"),
    FW_GS_SHOW("goods/show"),
    FW_GS_NEW("goods/new"),
    FW_GS_EDIT("goods/edit");

    //文字列
    private final String text;

    //コンストラクタ
    private ForwardConst(final String text) {
        this.text=text;
    }

    //値の取得
    public String getValue() {
        return this.text;
    }

}
