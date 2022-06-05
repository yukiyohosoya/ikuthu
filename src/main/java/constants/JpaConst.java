package constants;

//DB関連の項目を定義するインターフェース
//※インターフェースに定義した変数はpublic static final 修飾子が付いてるとみなされる

public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME="daily_report_system";

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

    //日報テーブル
    String TABLE_REP="reports";//テーブル名
    //日報テーブルカラム
    String REP_COL_ID="id";//id
    String REP_COL_US="employee_id";//日報を作成した従業員のid
    String REP_COL_REP_DATE="report_date";//いつの日報か示す日付
    String REP_COL_TITLE="title";//日報のタイトル
    String REP_COL_CONTENT="content";//日報の内容
    String REP_COL_CREATED_AT="created_at";//登録日時
    String REP_COL_UPDATED_AT="updated_at";//更新日時

    //Entity名
    String ENTITY_US="user";//ユーザー
    String ENTITY_REP="report";//日報

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

    //すべての日報をidの降順に取得する
    String Q_US_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_US_GET_ALL_MINE_DEF= "SELECT COUNT(r) FROM Report AS r WHERE e.employee = :" + JPQL_PARM_US + "ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String  Q_US_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String  Q_US_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_US;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_US + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_US;



}
