package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ShopService;
import services.UserService;

/**
 * ショップに関する処理を行うActionクラス
 *
 */

public class ShopAction extends ActionBase {

    private ShopService service;
    private UserService service_u;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        service = new ShopService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * ログインしているユーザーのショップ一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_US);

        //ログイン中のユーザーが作成したショップデータを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ShopView> shop = service.getMinePerPage(loginUser, page);

        //ログイン中のユーザーが作成したショップデータの件数を取得
        long myShopsCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.SHOPS,shop); //取得したショップデータ
        putRequestScope(AttributeConst.SH_COUNT, myShopsCount); //ログイン中のユーザーが作成したショップの数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //↑ここまで追記

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_SH_NEW);
        }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);

            //パラメータの値を元にショップ情報のインスタンスを作成する
            ShopView sv = new ShopView(
                    null,
                    uv,//ログインしているユーザーを、ショップ作者として登録
                    getRequestParam(AttributeConst.SH_NAME),
                    0);//※0はデフォルト値

            //ショップ情報登録
            List<String> errors = service.create(sv);

            if (errors.size() > 0) {
                System.out.println("shopnameCount数は"+ errors);
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SHOP, sv); //入力されたショップ情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_SH_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */

    public void show() throws ServletException, IOException {

        //idを条件にショップデータを取得
        ShopView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

        if(sv==null) {
            //該当のショップデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }else{
            putRequestScope(AttributeConst.SHOP,sv);//取得したショップデータ

            //詳細画面を表示
            forward(ForwardConst.FW_SH_SHOW);

        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にショップデータを取得
        ShopView sv = service.findOne(toNumber(getRequestParam(AttributeConst.US_ID)));

        //セッションからログイン中のユーザー情報を取得
        UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);

        if (sv == null || uv.getId() != sv.getUser().getId()) {
            //該当のショップデータが存在しない、またはログインしているユーザーがショップの作者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        }else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.SHOP, sv); //取得したショップ情報

            //編集画面を表示する
            forward(ForwardConst.FW_SH_EDIT);
        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //idを条件にショップデータを取得
            ShopView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

            //入力されたショップ内容を設定する
            sv.setName(getRequestParam(AttributeConst.SH_NAME));

            //ショップデータを更新
            List<String> errors = service.update(sv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SHOP, sv); //入力されたショップ情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_SH_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * ショップのログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {

        //作成ユーザー以外がショップにログインしてはまずいので、承認を行う。
        //リクエストスコープに入っているショップidを条件にショップデータを取得
        //セッションからログイン中のユーザー情報を取得
        //トップへリダイレクト
        System.out.println(getRequestParam(AttributeConst.SH_ID));
        UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);
        ShopView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));


 //       if(isValidShop) {
           //認証成功の場合
//
//            //CSRF対策 tokenのチェック
//            if (checkToken()) {
//
//                //セッションにログインしたユーザーを設定
//                putSessionScope(AttributeConst.LOGIN_SH,sv);
//                //トップへリダイレクト
//                System.out.println("店舗ログイン成功！");
//                redirect(ForwardConst.ACT_TOP,ForwardConst.CMD_INDEX);
//            }
//        }else{
//            //認証失敗
//
//            //CRF対策用トークンを設定
//            putRequestScope(AttributeConst.TOKEN,getTokenId());
//            //承認失敗エラーメッセージ表示フラグを建てる
//            putRequestScope(AttributeConst.LOGIN_ERR,true);
//
//            //ログイン画面を表示
//            forward(ForwardConst.FW_TOP_INDEX);
//
//        }
//
    }




}
