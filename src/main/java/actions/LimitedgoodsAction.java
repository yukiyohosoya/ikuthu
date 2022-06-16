package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import actions.views.EventView;
import actions.views.GoodsView;
import actions.views.LimitedgoodsView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EventService;
import services.GoodsService;
import services.LimitedgoodsService;
import services.ShopService;
import services.UserService;

/**
 * ショップに関する処理を行うActionクラス
 *
 */

public class LimitedgoodsAction extends ActionBase {

    private ShopService shop_service;
    private LimitedgoodsService limievgoods_service;
    private EventService event_service;
    private GoodsService goods_service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        shop_service = new ShopService();
        limievgoods_service = new LimitedgoodsService();
        event_service = new EventService();
        goods_service = new GoodsService();


        //メソッドを実行
        invoke();

        shop_service.close();
        limievgoods_service.close();
        event_service.close();
        goods_service.close();
    }
    /**
     * イベントごとグッズ情報登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからショップ情報を取得
        ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
        //クエリからidを条件イベントデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));

        List<LimitedgoodsView> select_lmevgoods = limievgoods_service.getMine(ev);

         //選択中イベントから既に作成したイベントごとgoods件数を取得
         long lmevGoodsCount = limievgoods_service.countAllMine(ev);

         putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
         putRequestScope(AttributeConst.LMEVGOODSS,select_lmevgoods); //index用に取得したイベント
         putRequestScope(AttributeConst.LMEVGS_COUNT,lmevGoodsCount); //Goods全件数

          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
         String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }
        //一覧画面を表示
        forward(ForwardConst.FW_LMGS_SELECT);
    }

    /**
     * イベントごとグッズ情報登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void select() throws ServletException, IOException {

        //セッションからショップ情報を取得
        ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
        //クエリからidを条件イベントデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));

        //作成済みのイベント限定グッズを取得
        List<LimitedgoodsView> select_lmevgoods = limievgoods_service.getMine(ev);
        //選択中イベントから既に作成したイベントごとgoods件数を取得
        long lmevGoodsCount = limievgoods_service.countAllMine(ev);

        //作成したものがあるかどうかでの分岐.
        //もしリストが空なら
        if (select_lmevgoods == null || select_lmevgoods.size() == 0) {
            System.out.println("からやで");

          }else {
              System.out.println("から以外やで");
          }
        List<GoodsView> goodslist = goods_service.getMineAll(select_shop);
        //
       // List<GoodsView> notLimev_goods =goods_service.getNotLmevgoodsMine(select_shop,select_lmevgoods);

         putRequestScope(AttributeConst.EV_ID, getRequestParam(AttributeConst.EV_ID)); //CSRF対策用トークン
         putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
         putRequestScope(AttributeConst.GOODSS,goodslist); //index用に取得したイベント
         putRequestScope(AttributeConst.LMEVGS_COUNT,lmevGoodsCount); //Goods全件数

          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
         String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }
        //一覧画面を表示
        forward(ForwardConst.FW_LMGS_SELECT);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        //セッションからショップ情報を取得
        ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
        //クエリからidを条件イベントデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_LMGS_NEW);
        }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
 //       if(checkToken()) {

            //セッションから選択中のショップ情報を取得
            ShopView sv = (ShopView)getSessionScope(AttributeConst.SELECT_SH);

            System.out.println(getRequestParam(AttributeConst.LMEVGS_CHECK) +"と"+
                    getRequestParam(AttributeConst.LMEVGS_SELLINGPRICE));

            forward(ForwardConst.FW_LMGS_SELECT);
//            if (errors.size() > 0) {
//                //登録中にエラーがあった場合
//
//                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
//                putRequestScope(AttributeConst.GOODS, gv); //入力されたイベント情報
//                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト
//
//                System.out.println(errors);
//                //新規登録画面を再表示
//                forward(ForwardConst.FW_LMGS_SELECT);
//
//            } else {
//                //登録中にエラーがなかった場合
//                //セッションに登録完了のフラッシュメッセージを設定
//                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());
//
//                //一覧画面にリダイレクト
//                redirect(ForwardConst.ACT_LMEVEGOODS, ForwardConst.CMD_SELECT);
//            }

//        }
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
            forward(ForwardConst.FW_GS_SHOW);

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
            forward(ForwardConst.FW_GS_EDIT);
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
                forward(ForwardConst.FW_GS_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
            }
        }
    }



}
