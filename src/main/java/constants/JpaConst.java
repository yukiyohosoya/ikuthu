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
    String SHOP_COL_US="user";//ショップを作成したユーザーのid
    String SHOP_COL_NAME="name";//ショップの名前
    String SHOP_COL_PRIORITY_FLAG="priority_flag"; //優先度

    //イベントテーブル
    String TABLE_EVE="event";//テーブル名
    //イベントカラム
    String EV_COL_ID="id";//id
    String EV_COL_SHOP="shop";//イベントを作成したショップ
    String EV_COL_NAME="name";//イベントの名前
    String EV_COL_EVENT_DAY="event_day"; //イベント日付
    String EV_COL_CREATED_AT="created_at"; //登録日時
    String EV_COL_UPDATED_AT="updated_at"; //更新日時

    //商品テーブル
    String TABLE_GOODS="goods";//テーブル名
    //商品カラム
    String GOODS_COL_ID="id";//id
    String GOODS_COL_SHOP="shop";//商品を作成したショップ
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
    String LMEVGOODS_COL_EVENT="event";//イベント
    String LMEVGOODS_COL_GOODS="goods";//商品
    String LMEVGOODS_COL_EV_ORDER="ev_order";//イベントごと並び順
    String LMEVGOODS_COL_LM_SELLING_PRICE="lm_selling_price"; //イベントごと価格
    String LMEVGOODS_COL_SOLD_GOODS="sold_goods";//イベントごと販売数
    String LMEVGOODS_COL_CREATED_AT="created_at"; //登録日時
    String LMEVGOODS_COL_UPDATED_AT="updated_at"; //更新日時


    //Entity名
    String ENTITY_US="user";//ユーザー
    String ENTITY_SHOP="shop";//ショップ
    String ENTITY_EVENT="event";//イベント
    String ENTITY_GOODS="goods";//グッズ
    String ENTITY_LIMIGOODS="lomitedgoods";//イベント限定商品

    //JPQL内パラメータ
    String JPQL_PARM_MAIL="mailaddress";//メールアドレス
    String JPQL_PARM_PASS="password";//パスワード
    String JPQL_PARM_US="user";//ユーザー
    String JPQL_PARM_SHOPNAME="name";//ショップ名
    String JPQL_PARM_SHOP="shop";//ショップ名

    //NamedQueryのnameとquery
    //ユーザ
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

    //ショップ
    //指定したユーザーが作成したショップの件数を取得する
   String  Q_SHOP_US_COUNT_ALL_MINE = ENTITY_SHOP + ".countAllMine";
   String  Q_SHOP_US_COUNT_ALL_MINE_DEF = "SELECT COUNT(s) FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US;
   //指定したユーザーが作成したショップを全件idの降順で取得する
   String Q_SHOP_GET_ALL_MINE = ENTITY_SHOP + ".getAllMine";
   String Q_SHOP_GET_ALL_MINE_DEF = "SELECT s FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US + " ORDER BY s.id DESC";
   //指定したユーザーとショップ名を指定し、該当ショップの件数を取得する
   String  Q_SHOP_US_NAME_COUNT_ALL_MINE = ENTITY_SHOP + ".countAllNameMine";
   String  Q_SHOP_US_NAME_COUNT_ALL_MINE_DEF = "SELECT COUNT(s) FROM Shop AS s WHERE s.user = :" + JPQL_PARM_US + " AND s.name = :" + JPQL_PARM_SHOPNAME;

   //イベント
   //指定したショップが作成したイベントを全件idの降順で取得する
   String Q_EVENT_GET_ALL_MINE = ENTITY_EVENT + ".getAllMine";
   String Q_EVENT_GET_ALL_MINE_DEF = "SELECT e FROM Event AS e WHERE e.shop = :" + JPQL_PARM_SHOP + " ORDER BY e.id DESC";
   //指定したショップが作成したイベントの件数を降順に取得する
   String  Q_EVENT_SHOP_COUNT_ALL_MINE = ENTITY_EVENT + ".countAllMine";
   String  Q_EVENT_SHOP_COUNT_ALL_MINE_DEF = "SELECT COUNT(e) FROM Event AS e WHERE e.shop = :" + JPQL_PARM_SHOP;

   //商品情報
   //指定したショップが作成したグッズを全件idの降順で取得する
   String Q_GOODS_GET_ALL_MINE = ENTITY_GOODS + ".getAllMine";
   String Q_GOODS_GET_ALL_MINE_DEF = "SELECT g FROM Goods AS g WHERE g.shop = :" + JPQL_PARM_SHOP + " ORDER BY g.id DESC";
   //指定したショップが作成したグッズの件数を取得する
   String  Q_GOODS_US_COUNT_ALL_MINE = ENTITY_GOODS + ".countAllMine";
   String  Q_GOODS_US_COUNT_ALL_MINE_DEF = "SELECT COUNT(g) FROM Goods AS g WHERE g.shop = :" + JPQL_PARM_SHOP;

   //イベントごと商品ごと商品情報
   //指定したショップが作成したイベントごと商品を全件idの降順で取得する
   String Q_LIMIGOODS_GET_ALL_MINE = ENTITY_LIMIGOODS + ".getAllMine";
   String Q_LIMIGOODS_GET_ALL_MINE_DEF = "SELECT lg FROM Limitedgoods AS lg WHERE lg.event = :" + JPQL_PARM_SHOP + " ORDER BY lg.id DESC";
   //指定したショップが作成したイベントごと商品の件数を取得する
   String  Q_LIMIGOODS_US_COUNT_ALL_MINE = ENTITY_LIMIGOODS + ".countAllMine";
   String  Q_LIMIGOODS_US_COUNT_ALL_MINE_DEF = "SELECT COUNT(lg) FROM Limitedgoods AS lg WHERE lg.event = :" + JPQL_PARM_SHOP;


}
