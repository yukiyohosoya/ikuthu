package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import actions.views.EventView;
import actions.views.GoodsConverter;
import actions.views.GoodsView;
import actions.views.LimitedgoodsView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Goods;
import models.Limitedgoods;
import services.EventService;
import services.GoodsService;
import services.LimitedgoodsService;
import services.ShopService;
import services.UserService;

/**
 * ショップに関する処理を行うActionクラス
 *
 */

public class EventAction extends ActionBase {

    private ShopService shop_service;
    private UserService user_service;
    private EventService event_service;
    private GoodsService goods_service;
    private LimitedgoodsService limitedgoods_service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        shop_service = new ShopService();
        user_service = new UserService();
        event_service = new EventService();
        goods_service = new GoodsService();
        limitedgoods_service = new LimitedgoodsService();

        //メソッドを実行
        invoke();

        shop_service.close();
        user_service.close();
        event_service.close();
        goods_service.close();
        limitedgoods_service.close();
    }

    /**
     * ログインしているユーザーのショップ一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {
        // ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));　リクエストからの取り出し

        //指定されたページ数の一覧画面に表示するデータを取得
         int page = getPage();
        //セッションからショップ情報を取得
         ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);

         List<EventView> index_event = event_service.getMinePerPage(select_shop,page);

         //選択中ショップのEvent件数を取得
         long myEventCount = event_service.countAllMine(select_shop);

          putRequestScope(AttributeConst.EVENTS,index_event); //index用に取得したイベント
          putRequestScope(AttributeConst.EV_COUNT,myEventCount); //Event全件数
          putRequestScope(AttributeConst.PAGE, page); //ページ数
          putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
          String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }

        //一覧画面を表示
        forward(ForwardConst.FW_EV_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_EV_NEW);
        }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //セッションから選択中のショップ情報を取得
            ShopView sv = (ShopView)getSessionScope(AttributeConst.SELECT_SH);
            String day=getRequestParam(AttributeConst.EV_DAY).replace("/", "-");

            //パラメータの値を元にショップ情報のインスタンスを作成する
            EventView ev = new EventView(
                    null,
                    sv,//セッションにあるショップ
                    getRequestParam(AttributeConst.EV_NAME),
                    day,
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //ショップ情報登録
            List<String> errors = event_service.create(ev);

            if (errors.size() > 0) {
                System.out.println(ev.getEventday());
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EVENT, ev); //入力されたイベント情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EV_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EVENT, ForwardConst.CMD_INDEX);
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
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));
        //セッションからショップ情報を取得
        ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);

        //イベント情報から論理削除してない限定ごとの商品と商品数取得
        List<LimitedgoodsView> lmevgoodss = limitedgoods_service.getMine(ev);
        long lmevgoodsscount = limitedgoods_service.countAllMine(ev);
        //イベントごとの販売価格
        long lmevgoodearnings = limitedgoods_service.lmevgoodearnings(ev);

        long myEventCount = goods_service.countNotLmevgoodsMine(select_shop,lmevgoodss);
        System.out.println(myEventCount);

        if(ev==null) {
                //該当のショップデータが存在しない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
               }else{
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.EVENT,ev);//取得したショップデータ
            putRequestScope(AttributeConst.LMEVGOODSS,lmevgoodss);//取得したイベントごと商品情報
            putRequestScope(AttributeConst.LMEVGS_COUNT,lmevgoodsscount);//取得したイベントごと商品数
            putRequestScope(AttributeConst.LMEVGS_SELLINGPRICE,lmevgoodearnings);//取得したイベントごと商品総売り上げ
            //詳細画面を表示
            forward(ForwardConst.FW_EV_SHOW);

         }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にショップデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));
        //セッションからショップ情報を取得
        System.out.println(ev.getId());
        ShopView sv = (ShopView) getSessionScope(AttributeConst.SELECT_SH);

        if (ev == null || sv.getId() != ev.getShop().getId()) {
            //該当のショップデータが存在しない、またはログインしているユーザーがショップの作者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        }else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.EVENT, ev); //取得したショップ情報

            //編集画面を表示する
            forward(ForwardConst.FW_EV_EDIT);
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
            EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));
            //セッションからショップ情報を取得
            ShopView sv = (ShopView) getSessionScope(AttributeConst.SELECT_SH);

            //入力されたショップ内容を設定する
            ev.setName(getRequestParam(AttributeConst.EV_NAME));
            ev.setEventday(getRequestParam(AttributeConst.EV_DAY).replace("/", "-"));

            //ショップデータを更新
            List<String> errors = event_service.update(ev);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EVENT, ev); //入力されたイベント情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_EV_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirectSwho(ForwardConst.ACT_EVENT, ForwardConst.CMD_SHOW,"&ev_id="+getRequestParam(AttributeConst.EV_ID));
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //idを条件にユーザーデータを論理削除する
            event_service.destroy(toNumber(getRequestParam(AttributeConst.EV_ID)));
            System.out.println(getRequestParam(AttributeConst.EV_ID));

            //セッションに削除完了のフラッシュメッセージ
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_EVENT, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * ショップの選択処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void select() throws ServletException, IOException {

        UserView uv = (UserView)getSessionScope(AttributeConst.SELECT_SH);
        ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

               //セッションに選択したショップを設定
               putSessionScope(AttributeConst.SELECT_SH,sv);
               //トップへリダイレクト
               redirect(ForwardConst.ACT_SHOP,ForwardConst.CMD_INDEX);

    }




}
