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
    String SHOP_COL_PRIORITY_FLAG="priority_flag"; //優先度

    //イベントテーブル
    String TABLE_EVE="event";//テーブル名
    //イベントカラム
    String EV_COL_ID="id";//id
    String EV_COL_US="shop_id";//イベントを作成したショップid
    String EV_COL_NAME="name";//イベントの名前
    String EV_COL_EVENT_DAY="event_day"; //イベント日付
    String EV_COL_CREATED_AT="created_at"; //登録日時
    String EV_COL_UPDATED_AT="updated_at"; //更新日時

    //商品テーブル
    String TABLE_GOODS="goods";//テーブル名
    //商品カラム
    String GOODS_COL_ID="id";//id
    String GOODS_COL_US="shop_id";//商品を作成したショップid
    String GOODS_COL_NAME="name";//商品の名前
    String GOODS_COL_SEL_PRICE="selling_price";//販売価格
    String GOODS_COL_PUR_PRICE="purchase_price";//仕入れ価格
    String GOODS_COL_CREATED_DAY="create_day"; //商品作成日
    String GOODS_COL_CREATED_AT="created_at"; //登録日時
    String GOODS_COL_UPDATED_AT="updated_at"; //更新日時
    String GOODS_COL_PICTURE="picture"; //商品画像ファイル名

    //イベントごと商品テーブル
    String TABLE_LMEVGOODS="lm_ev_goods";//テーブル名
    //イベントごと商品カラム
    String LMEVGOODS_COL_ID="id";//id
    String LMEVGOODS_COL_EV_ID="event_id";//イベントid
    String LMEVGOODS_COL_GO_ID="goods_id";//商品id
    String LMEVGOODS_COL_EV_ORDER="ev_order";//イベントごと並び順
    String LMEVGOODS_COL_LM_SELLING_PRICE="lm_selling_price"; //イベントごと価格
    String LMEVGOODS_COL_SOLD_GOODS="sold_goods";//イベントごと販売数
    String LMEVGOODS_COL_CREATED_AT="created_at"; //登録日時
    String LMEVGOODS_COL_UPDATED_AT="updated_at"; //更新日時


    //Entity名
    String ENTITY_US="user";//ユーザー
    String ENTITY_SHOP="shop";//ショップ

    //JPQL内パラメータ
    String JPQL_PARM_MAIL="mailaddress";//メールアドレス
    String JPQL_PARM_PASS="password";//パスワード
    String JPQL_PARM_US="user";//ユーザー
    String JPQL_PARM_SHOPNAME="name";//ショップ名

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
    //指定したユーザーとショップ名を指定し、該当ショップの件数を取得する
    String  Q_US_SHOPNAME_COUNT_ALL_MINE = ENTITY_SHOP + ".countAllNameMine";
    String  Q_US_SHOPNAME_COUNT_ALL_MINE_DEF = "SELECT COUNT(s) FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US + " AND s.name = :" + JPQL_PARM_SHOPNAME;

}
