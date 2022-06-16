package services;

import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;
import actions.views.EventConverter;
import actions.views.EventView;
import actions.views.LimitedgoodsConverter;
import actions.views.LimitedgoodsView;
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

//    /**
//     * 画面から入力されたイベントの登録内容を元にデータを1件作成し、イベントテーブルに登録する
//     * @param sv イベントの登録内容
//     * @return バリデーションで発生したエラーのリスト
//     */
//    public List<String> create(LimitedgoodsView sv){
//        List<String>errors=LimitedgoodsValidator.Validate(sv,this);
//        if(errors.size()==0) {
//            createInternal(sv);
//        }
//        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
//        return errors;
//    }
//
//    /**
//     * 画面から入力されたイベントの登録内容を元に、イベントデータを更新する
//     * @param sv イベントの更新内容
//     * @return バリデーションで発生したエラーのリスト
//     */
//
//    public List<String> update(LimitedgoodsView sv){
//
//        //バリデーションを行う
//        List<String>errors=LimitedgoodsValidator.Validate(sv,this);
//
//        if(errors.size()==0) {
//            updateInternal(sv);
//        }
//        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
//        return errors;
//    }

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
