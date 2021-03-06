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
    I_DELETEDSHOP("ショップの削除が完了しました。"),

    //ユーザーバリデーション
    U_NONAME("登録名を入力してください。"),
    U_NOPASSWORD("パスワードを入力してください。"),
    U_NOMATCHPASSWORD("確認入力とパスワードが一致しません。"),
    U_NOEMP_MAIL("メールアドレスを入力してください。"),
    U_US_MAIL_EXIST("入力されたメールアドレスの情報は既に存在しています。"),
    U_US_DELETEMAIL_EXIST("入力されたメールアドレスの情報は既に削除済みです。"),
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
    U_EVEDAY_NO("開催日は日付で記入してください。"),

    //グッズバリデーション
    U_GSNAME("グッズ名を入れてください。"),
    U_GSCRDAY("作成日を入力してください。"),
    U_GSCRDAY_NO("作成日は日付で記入してください。"),
    U_GS_PRNO("デフォルト販売価格、仕入価格を入力してください。"),
    U_GS_PRNOIN("デフォルト販売価格、仕入価格は数字で入力してください。"),
    U_GS_STOCKNO("在庫数を入力してください。"),
    U_GS_STOCKNOIN("在庫数は数字で入力してください。"),
    U_GS_PICTURESIZE("画像の容量が大きすぎます。1MB以下にしてください"),
    U_GS_NOPICTURE("jpgファイルかpngファイルを選択してください。"),

    //イベントごとグッズバリデーション
    U_LEGSNULL("チェックが入っていないか、ページを更新してチェックを入れなおしてください。"),
    U_LEGSNOPRICE("販売価格または販売数が空欄です。"),
    U_LEGS_PRNOIN("販売価格または販売数は数字で入力してください。");

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
