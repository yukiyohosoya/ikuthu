package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import actions.views.EventView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EventService;
import services.GoodsService;
import services.ShopService;
import services.UserService;

/**
 * ショップに関する処理を行うActionクラス
 *
 */

public class ShopAction extends ActionBase {

    private ShopService shop_service;
    private UserService user_service;
    private EventService event_service;
    private GoodsService goods_service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        shop_service = new ShopService();
        user_service = new UserService();
        event_service = new EventService();
        goods_service = new GoodsService();


        //メソッドを実行
        invoke();

        shop_service.close();
        user_service.close();
        event_service.close();
        goods_service.close();
    }

    /**
     * ログインしているユーザーのショップ一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからショップ情報を取得
         ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);

         List<EventView> index_event = event_service.getMinePer_index(select_shop);

         //選択中ショップのEvent件数を取得
         long myEventCount = event_service.countAllMine(select_shop);

          putRequestScope(AttributeConst.EVENTS,index_event); //index用に取得したイベント
          putRequestScope(AttributeConst.EV_COUNT,myEventCount); //Event全件数


          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
          String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }

        //一覧画面を表示
        forward(ForwardConst.FW_SH_INDEX);
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
                    0,//※0はデフォルト値
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //ショップ情報登録
            List<String> errors = shop_service.create(sv);

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
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
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
        ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

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
        ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.US_ID)));

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
            ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

            //入力されたショップ内容を設定する
            sv.setName(getRequestParam(AttributeConst.SH_NAME));

            //ショップデータを更新
            List<String> errors = shop_service.update(sv);

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
    public void select() throws ServletException, IOException {

        //作成ユーザー以外がショップにログインしてはまずいので、承認を行う。
        //リクエストスコープに入っているショップidを条件にショップデータを取得
        //セッションからログイン中のユーザー情報を取得
        //トップへリダイレクト

        UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);
        ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

        //有効なユーザーか承認する
      //  boolean isValidShop = shop_service.validateShop(uv, sv);
     //   System.out.println(isValidShop);
     //  if(isValidShop) {
           //認証成功の場合

           //CSRF対策 tokenのチェック
       //    if (checkToken()) {
               //セッションにログインしたユーザーを設定
               putSessionScope(AttributeConst.SELECT_SH,sv);
               //トップへリダイレクト
               redirect(ForwardConst.ACT_SHOP,ForwardConst.CMD_INDEX);
    //       }
    //   }else{
           //認証失敗
           //CRF対策用トークンを設定
   //        putRequestScope(AttributeConst.TOKEN,getTokenId());
           //承認失敗エラーメッセージ表示フラグを建てる
  //         putRequestScope(AttributeConst.LOGIN_ERR,true);
           //ログイン画面を表示
  //        forward(ForwardConst.FW_TOP_INDEX);
  //     }
    }




}
