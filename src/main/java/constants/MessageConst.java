package constants;

//各出力メッセージを定義するEnumクラス

public enum MessageConst {

    //承認
    I_LOGINED("ログインしました"),
    U_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //バリデーション
    U_NONAME("登録名を入力してください。"),
    U_NOPASSWORD("パスワードを入力してください。"),
    U_NOEMP_MAIL("メールアドレスを入力してください。"),
    U_US_MAIL_EXIST("入力されたメールアドレスの情報は既に存在しています。"),
    U_NOTITLE("タイトルを入力してください。"),
    U_NOCONTENT("内容を入力してください。"),
    U_NOATT("出勤時間を入力してください。"),
    U_NOLET("退勤時間を入力してください。"),
    U_NOTIMEMATCH("退勤時間が出勤時間より大きいです。"),
    U_NOTIME("時間で入力してください。");

    //文字列
    private final String text;

    //コンストラクタ
    private MessageConst(final String text) {
        this.text=text;
    }

    //値（文字列）取得

    public String getMessage() {
        return this.text;
    }

}
