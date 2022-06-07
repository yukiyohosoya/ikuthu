package constants;

//DB関連の項目を定義するインターフェース
//※インターフェースに定義した変数はpublic static final 修飾子が付いてるとみなされる

public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME="ikuthu";

    //データ取得件数の最大値
    int ROW_PER_PAGE=15;//1ページに表示するレコード

    //ユーザーテーブル
    String TABLE_US="user";//テーブル名
    //ユーザーテーブルカラム
    String US_COL_ID="id"; //id
    String US_COL_NAME="name"; //ユーザー名
    String US_COL_MAIL="mailaddress"; //メールアドレス
    String US_COL_PASS="password"; //パスワード
    String US_COL_CREATED_AT="created_at"; //登録日時
    String US_COL_UPDATED_AT="updated_at"; //更新日時

    //ショップテーブル
    String TABLE_SHOP="shops";//テーブル名
    //ショップテーブルカラム
    String SHOP_COL_ID="id";//id
    String SHOP_COL_US="user_id";//ショップを作成したユーザーのid
    String SHOP_COL_NAME="name";//ショップの名前
    String SHOP_COL_PRIORITY_FLAG="priority_flag"; //管理者権限


    //Entity名
    String ENTITY_US="user";//ユーザー
    String ENTITY_SHOP="shop";//ショップ

    //JPQL内パラメータ
    String JPQL_PARM_MAIL="mailaddress";//メールアドレス
    String JPQL_PARM_PASS="password";//パスワード
    String JPQL_PARM_US="user";//ユーザー

    //NamedQueryのnameとquery
    //すべてのユーザーをidの順序に取得する
    String Q_US_GET_ALL= ENTITY_US + ".getAll";//name
    String Q_US_GET_ALL_DEF = "SELECT u FROM User AS u ORDER BY u.id DESC";//query
    //すべてのユーザーの件数を取得する
    String Q_US_COUNT=ENTITY_US + ".count";
    String Q_US_COUNT_DEF="SELECT COUNT(u) FROM User AS u";
    //メールアドレスとハッシュ化済みパスワードを条件にユーザーを取得する
    String Q_US_GET_BY_MAIL_AND_PASS=ENTITY_US + ".getByMailAndPass";
    String Q_US_GET_BY_MAIL_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.mailaddress = :" + JPQL_PARM_MAIL + " AND u.password = :" + JPQL_PARM_PASS;
    //指定したメールアドレスを保持するユーザーの件数を取得する
    String  Q_US_COUNT_RESISTERED_BY_MAIL = ENTITY_US + ".countRegisteredByMail";
    String  Q_US_COUNT_RESISTERED_BY_MAIL_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.mailaddress = :" + JPQL_PARM_MAIL;

     //指定したユーザーが作成したショップの件数を取得する
    String  Q_US_COUNT_ALL_MINE = ENTITY_SHOP + ".countAllMine";
    String  Q_US_COUNT_ALL_MINE_DEF = "SELECT COUNT(s) FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US;
    //指定したユーザーが作成したショップを全件idの降順で取得する
    String Q_SHOP_GET_ALL_MINE = ENTITY_SHOP + ".getAllMine";
    String Q_SHOP_GET_ALL_MINE_DEF = "SELECT s FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US + " ORDER BY s.id DESC";

}
