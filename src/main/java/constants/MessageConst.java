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
    U_NOMATCHPASSWORD("確認入力とパスワードが一致しません。"),
    U_NOEMP_MAIL("メールアドレスを入力してください。"),
    U_US_MAIL_EXIST("入力されたメールアドレスの情報は既に存在しています。"),
    U_NOADDRESS("メールアドレスを入力してください。"),
    U_NOSTRINGPASS("パスワードに使用できる文字はアルファベットの大文字と小文字、数字です"),
    U_NOLONGPASS("パスワードは8文字以上24文字以内にしてください");


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
