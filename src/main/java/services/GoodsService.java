package services;

import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;

import actions.views.GoodsConverter;
import actions.views.GoodsView;
import constants.JpaConst;
import models.Goods;

/**
 * イベントテーブルの操作に関わる処理を行うクラス
 */
public class GoodsService extends ServiceBase {
    /**
     * 指定したショップが作成したイベントデータを、指定されたページ数の一覧画面に表示する分取得しGoodsViewのリストで返却する
     * @param shop ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodsView> getMinePerPage(ShopView shop,int page){

        List<Goods> shops = em.createNamedQuery(JpaConst.Q_GOODS_US_COUNT_ALL_MINE,Goods.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return GoodsConverter.toViewList(shops);
    }

    /**
     * 指定したショップが作成したイベントデータの件数を取得し、返却する
     * @param shop
     * @return イベントデータの件数
     */

    public long countAllMine(ShopView shop) {

        long count =(long)em.createNamedQuery(JpaConst.Q_GOODS_GET_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .getSingleResult();
        return count;
    }

    /**
     * idを条件に取得したデータをGoodsViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */

    public GoodsView findOne(int id) {
        return GoodsConverter.toView(findPneInternal(id));
    }

    /**
     * shop名をを条件に取得したデータをGoodsViewのインスタンスで返却する
     * @param name
     * @return 取得データのインスタンス
     */

    public GoodsView findOneName(String name) {
        return GoodsConverter.toView(findPneInternalName(name));
    }

//    /**
//     * 画面から入力されたイベントの登録内容を元にデータを1件作成し、イベントテーブルに登録する
//     * @param sv イベントの登録内容
//     * @return バリデーションで発生したエラーのリスト
//     */
//    public List<String> create(GoodsView sv){
//        List<String>errors=GoodsValidator.Validate(sv,this);
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
//    public List<String> update(GoodsView sv){
//
//        //バリデーションを行う
//        List<String>errors=GoodsValidator.Validate(sv,this);
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
    private Goods findPneInternal(int id) {
        return em.find(Goods.class, id);
    }

    /**
     * イベント名を条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Goods findPneInternalName(String name) {
        return em.find(Goods.class, name);
    }


    /**
     * ショップIDと入力された名前をを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param mailaddress メールアドレス
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateGoods(ShopView uv, GoodsView sv) {
        boolean validateGoods = false;
        if (uv.getId().equals(sv.getShop().getId())) {
                //データが取得できた場合、認証成功
                validateGoods = true;
            }
        return validateGoods;
        }
    /**
     * イベント名とShopを条件に該当するデータの件数を取得し、返却する
     * @param shopname イベント名
     * @return 該当するデータの件数
     */
    public long countByGoods(ShopView shop,String shopname) {

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
    private void createInternal(GoodsView  sv) {
        em.getTransaction().begin();
        em.persist(GoodsConverter.toModel(sv));
        em.getTransaction().commit();
    }

    /**
     * イベントデータを更新する
     * @param sv イベントデータ
     */
    private void updateInternal(GoodsView  sv) {

        em.getTransaction().begin();
        Goods s = findPneInternal(sv.getId());
        GoodsConverter.copyViewToModel(s, sv);
        em.getTransaction().commit();
    }

}
