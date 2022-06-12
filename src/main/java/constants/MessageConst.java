package constants;

//各出力メッセージを定義するEnumクラス

public enum MessageConst {

    //承認
    I_LOGINED("ログインしました"),
    U_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),
    NO_SHOP_US("ショップを作成したユーザーではありません"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //ユーザーバリデーション
    U_NONAME("登録名を入力してください。"),
    U_NOPASSWORD("パスワードを入力してください。"),
    U_NOMATCHPASSWORD("確認入力とパスワードが一致しません。"),
    U_NOEMP_MAIL("メールアドレスを入力してください。"),
    U_US_MAIL_EXIST("入力されたメールアドレスの情報は既に存在しています。"),
    U_NOADDRESS("メールアドレスを入力してください。"),
    U_NOSTRINGPASS("パスワードに使用できる文字はアルファベットの大文字と小文字、数字です。"),
    U_NOLONGPASS("パスワードは8文字以上24文字以内にしてください。"),

    //ショップバリデーション
    U_SHOPNAME("ショップ名を入れてください。"),
    U_SHOPNAME_L("ショップ名は60文字以内にしてください。"),
    U_SHOPNAME_TRUE("同じショップ名は登録できません。"),

    //イベントバリデーション
    U_EVENAME("イベント名を入れてください。"),
    U_EVEDAY("開催日を入力してください。"),
    U_EVEDAY_NO("開催日は日付で記入してください。");

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
