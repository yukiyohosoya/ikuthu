package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;
import actions.views.EventConverter;
import actions.views.EventView;
import actions.views.GoodsView;
import actions.views.LimitedgoodsConverter;
import actions.views.LimitedgoodsView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Limitedgoods;

/**
 * イベントテーブルの操作に関わる処理を行うクラス
 */
public class LimitedgoodsService extends ServiceBase {

    /**
     * 指定したイベントが作成したイベント限定グッズデータを取得しLimitedgoodsViewのリストで返却する
     * @param event イベント
     * @return 一覧画面に表示するデータのリスト
     */
    public List<LimitedgoodsView> getMine(EventView event){

        List<Limitedgoods> lmevgoods = em.createNamedQuery(JpaConst.Q_LIMIGOODS_GET_ALL_MINE,Limitedgoods.class)
                .setParameter(JpaConst.JPQL_PARM_EVENT, EventConverter.toModel(event))
                .getResultList();
        return LimitedgoodsConverter.toViewList(lmevgoods);
    }

    /**
     * 指定したイベント限定価格のイベントごとの合計数
     * @param event イベント
     * @return イベントごとの合計売上
     */
    public long lmevgoodearnings(EventView event) {
        long sum=0;
        List<LimitedgoodsView>lmevgoods=getMine(event);
        for (LimitedgoodsView str : lmevgoods) {
            sum +=Long.parseLong(str.getLm_sellingprice())*Long.parseLong(str.getSoldgoods());
        }
        return sum;
    }

    /**
     * 指定したEventが作成したイベント限定グッズの件数を取得し、返却する
     * @param event
     * @return イベントデータの件数
     */
    public long countAllMine(EventView event) {

        long count =(long)em.createNamedQuery(JpaConst.Q_LIMIGOODS_US_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_EVENT, EventConverter.toModel(event))
                .getSingleResult();
        return count;
    }


    /**
     * idを条件に取得したデータをLimitedgoodsViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */

    public LimitedgoodsView findOne(int id) {
        return LimitedgoodsConverter.toView(findPneInternal(id));
    }

    /**
     * shop名をを条件に取得したデータをLimitedgoodsViewのインスタンスで返却する
     * @param name
     * @return 取得データのインスタンス
     */

    public LimitedgoodsView findOneName(String name) {
        return LimitedgoodsConverter.toView(findPneInternalName(name));
    }

      /**
       * バリデートクリアした配列を繰り返しで登録
       * @param sv イベントの登録内容

       */
      public void create(LimitedgoodsView lgv){
          LocalDateTime ldt =LocalDateTime.now();
          lgv.setCreatedAt(ldt);
          lgv.setUpdatedAt(ldt);
          createInternal(lgv);
      }


      /**
       * 画面から入力されたイベントの登録内容を元に、イベントデータを更新する
       * @param lgv 更新内容
       * @return バリデーションで発生したエラーのリスト
       */

      public void update(LimitedgoodsView lgv){
          LocalDateTime ldt =LocalDateTime.now();
          lgv.setUpdatedAt(ldt);
          updateInternal(lgv);
      }

      /**
       * idを条件にデータを論理削除する
       * @param id
       */
      public void destroy(Integer id) {

          //idを条件に登録済みの従業員情報を取得
          LimitedgoodsView saved = findOne(id);

          //更新日時に現在時刻を設定する
          LocalDateTime today = LocalDateTime.now();
          saved.setUpdatedAt(today);

          //論理削除フラグを立てる
          saved.setDeleteFlag(JpaConst.ALL_DEL_TRUE);

          //更新処理
          update(saved);

      }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Limitedgoods findPneInternal(int id) {
        return em.find(Limitedgoods.class, id);
    }

    /**
     * イベント名を条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Limitedgoods findPneInternalName(String name) {
        return em.find(Limitedgoods.class, name);
    }

//
//    /**
//     * ショップIDと入力された名前をを条件に検索し、データが取得できるかどうかで認証結果を返却する
//     * @param mailaddress メールアドレス
//     * @param plainPass パスワード
//     * @param pepper pepper文字列
//     * @return 認証結果を返却す(成功:true 失敗:false)
//     */
//    public Boolean validateLimitedgoods(ShopView uv, LimitedgoodsView sv) {
//        boolean validateLimitedgoods = false;
//        if (uv.getId().equals(sv.getShop().getId())) {
//                //データが取得できた場合、認証成功
//                validateLimitedgoods = true;
//            }
//        return validateLimitedgoods;
//        }
    /**
     * イベント名とShopを条件に該当するデータの件数を取得し、返却する
     * @param shopname イベント名
     * @return 該当するデータの件数
     */
    public long countByLimitedgoods(ShopView shop,String shopname) {

        long count =(long)em.createNamedQuery(JpaConst.Q_SHOP_US_NAME_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_US, ShopConverter.toModel(shop))
                .setParameter(JpaConst.JPQL_PARM_SHOPNAME,shopname)
                .getSingleResult();

        return count;
    }


    /**
     * イベントデータを1件登録する
     * @param sv イベントデータ
     */
    private void createInternal(LimitedgoodsView  lgv) {
        em.getTransaction().begin();
        em.persist(LimitedgoodsConverter.toModel(lgv));
        em.getTransaction().commit();
    }

    /**
     * イベントデータを更新する
     * @param lgv イベントデータ
     */
    private void updateInternal(LimitedgoodsView  lgv) {

        em.getTransaction().begin();
        Limitedgoods lg = findPneInternal(lgv.getId());
        LimitedgoodsConverter.copyViewToModel(lg, lgv);
        em.getTransaction().commit();
    }

}
