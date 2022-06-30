package actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import actions.views.UserView;
import actions.views.EventView;
import actions.views.GoodsView;
import actions.views.LimitedgoodsView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.LimitedgoodsValidator;
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
     * イベントごとグッズ情報を登録する画面を表示する
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

        //イベントごとに作成したグッズ以外のグッズリストを取得。もし0なら作成済みグッズ全件数を返す。
        List<GoodsView> goodslist = goods_service.getNotLmevgoodsMine(select_shop,select_lmevgoods);
        if(lmevGoodsCount==0) {
            goodslist = goods_service.getMineAll(select_shop);
        }

         putRequestScope(AttributeConst.EV_ID, ev.getId()); //イベントID
         putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
         putRequestScope(AttributeConst.GOODSS,goodslist); //まだこのイベントで作成していないグッズ一覧
         putRequestScope(AttributeConst.GS_COUNT,goodslist.size()); //まだこのイベントで作成していないグッズ全件数

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
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

            //CSRF対策 tokenのチェック
            if(checkToken()) {

            //セッションからショップ情報を取得
            ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
            System.out.println("shopおｋ");
            //クエリからidを条件イベントデータを取得
            EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));
            System.out.println("evおｋ"+ev.getId());
            //チェックボックスから配列を取得
            System.out.println(getRequestParam(AttributeConst.EV_ID));
            String[] gs_id = request.getParameterValues(AttributeConst.GS_ID.getValue());
            String[] price = request.getParameterValues(AttributeConst.LMEVGS_SELLINGPRICE.getValue());
            String[] soldgoods = request.getParameterValues(AttributeConst.LMEVGS_SOLDGOODS.getValue());

            //インスタンスを作ってからバリデートを行いたいが情報が配列ななため、このActionだけ例外。
            //配列を渡してエラーがないかチェック。
            List<String>errors=LimitedgoodsValidator.Validate(gs_id,price,soldgoods);
            if (errors.size() > 0) {
                  //登録中にエラーがあった場合

                //選択中イベントから既に作成したイベントごとgoods件数を取得
                long lmevGoodsCount = limievgoods_service.countAllMine(ev);

                List<GoodsView> goodslist = goods_service.getMineAll(select_shop);

                 putRequestScope(AttributeConst.EV_ID, ev.getId()); //イベントID
                 putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                 putRequestScope(AttributeConst.GOODSS,goodslist); //index用に取得したイベント
                 putRequestScope(AttributeConst.LMEVGS_COUNT,lmevGoodsCount); //Goods全件数
                 putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                  System.out.println(errors);
                  //新規登録画面を再表示
                  forward(ForwardConst.FW_LMGS_SELECT);

              } else {
                  //登録中にエラーがなかった場合
                  //空になるまで配列をまわす。
                  for (int i = 0; i < gs_id.length; i++){
                      GoodsView gv =goods_service.findOne(toNumber(gs_id[i]));

                      LimitedgoodsView lgv = new LimitedgoodsView(
                              null,
                              ev,//セッションにあるショップ
                              gv,
                              "0",
                              price[i],
                              soldgoods[i],
                              null,
                              null,
                              AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
                      limievgoods_service.create(lgv);
                  }


                  //セッションに登録完了のフラッシュメッセージを設定
                  putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                  //イベントのインデックスにリダイレクト
                  redirectSwho(ForwardConst.ACT_EVENT, ForwardConst.CMD_SHOW,"&ev_id="+ev.getId().toString());
              }

          }

    }


    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //クエリからidを条件イベントデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));

        //作成済みのイベント限定グッズを取得
        List<LimitedgoodsView> select_lmevgoods = limievgoods_service.getMine(ev);
        //選択中イベントから既に作成したイベントごとgoods件数を取得
        long lmevGoodsCount = limievgoods_service.countAllMine(ev);

         putRequestScope(AttributeConst.EV_ID, ev.getId()); //イベントID
         putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
         putRequestScope(AttributeConst.LMEVGOODSS,select_lmevgoods); //イベントごと商品リスト
         putRequestScope(AttributeConst.LMEVGS_COUNT,lmevGoodsCount); //イベントごと商品リスト数


          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
         String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }

            //編集画面を表示する
         forward(ForwardConst.FW_LMGS_EDIT);

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

        //セッションからショップ情報を取得
        ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
        //クエリからidを条件イベントデータを取得
        EventView ev = event_service.findOne(toNumber(getRequestParam(AttributeConst.EV_ID)));

        //チェックボックスから配列を取得
        System.out.println(getRequestParam(AttributeConst.EV_ID));
        String[] lmgs_id = request.getParameterValues(AttributeConst.LMGS_ID.getValue());
        String[] price = request.getParameterValues(AttributeConst.LMEVGS_SELLINGPRICE.getValue());
        String[] soldgoods = request.getParameterValues(AttributeConst.LMEVGS_SOLDGOODS.getValue());

        //インスタンスを作ってからバリデートを行いたいが情報が配列ななため、このActionだけ例外。
        //配列を渡してエラーがないかチェック。
        List<String>errors=LimitedgoodsValidator.Validate(lmgs_id,price,soldgoods);
        if (errors.size() > 0) {
            //登録中にエラーがあった場合
            //選択中イベントから既に作成したイベントごとgoods件数を取得
            long lmevGoodsCount = limievgoods_service.countAllMine(ev);
            //作成済みのイベント限定グッズを取得
            List<LimitedgoodsView> select_lmevgoods = limievgoods_service.getMine(ev);

             putRequestScope(AttributeConst.EV_ID, ev.getId()); //イベントID
             putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
             putRequestScope(AttributeConst.LMEVGOODSS,select_lmevgoods); //イベントごと商品リスト
             putRequestScope(AttributeConst.LMEVGS_COUNT,lmevGoodsCount); //イベントごと商品リスト数
             putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

              System.out.println(errors);
              //新規登録画面を再表示
              forward(ForwardConst.FW_LMGS_SELECT);

          } else {
              //登録中にエラーがなかった場合
              //空になるまで配列をまわす。
              for (int i = 0; i < lmgs_id.length; i++){
                  //IDを条件にインスタンスを作成
                  LimitedgoodsView lgv =limievgoods_service.findOne(toNumber(lmgs_id[i]));
                  lgv.setLm_sellingprice(price[i]);
                  lgv.setSoldgoods(soldgoods[i]);
                  limievgoods_service.update(lgv);
              }

              //セッションに登録完了のフラッシュメッセージを設定
              putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

              //イベントのインデックスにリダイレクト
              redirectSwho(ForwardConst.ACT_EVENT, ForwardConst.CMD_SHOW,"&ev_id="+ev.getId().toString());
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
            limievgoods_service.destroy(toNumber(getRequestParam(AttributeConst.GS_ID)));
            System.out.println(getRequestParam(AttributeConst.GS_ID));

            //セッションに削除完了のフラッシュメッセージ
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_SH_INDEX);
        }
    }

}
