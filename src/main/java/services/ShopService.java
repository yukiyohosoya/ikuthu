package services;

import java.util.List;

import actions.views.UserConverter;
import actions.views.UserView;
import actions.views.ShopConverter;
import actions.views.ShopView;
import constants.JpaConst;
import models.Shop;
import models.validators.ShopValidator;

/**
 * ショップテーブルの操作に関わる処理を行うクラス
 */
public class ShopService extends ServiceBase {
    /**
     * 指定したユーザーが作成したショップデータを、指定されたページ数の一覧画面に表示する分取得しShopViewのリストで返却する
     * @param user ユーザー
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ShopView> getMinePerPage(UserView user,int page){

        List<Shop> shops = em.createNamedQuery(JpaConst.Q_SHOP_GET_ALL_MINE,Shop.class)
                .setParameter(JpaConst.JPQL_PARM_US, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ShopConverter.toViewList(shops);
    }

    /**
     * 指定したユーザーが作成したショップデータの件数を取得し、返却する
     * @param user
     * @return ショップデータの件数
     */

    public long countAllMine(UserView user) {

        long count =(long)em.createNamedQuery(JpaConst.Q_US_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_US, UserConverter.toModel(user))
                .getSingleResult();
        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示するショップデータを取得し、ShopViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ShopView> getAllPerPage(int page){
        List<Shop> shops = em.createNamedQuery(JpaConst.Q_SHOP_GET_ALL_MINE,Shop.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE*(page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ShopConverter.toViewList(shops);
    }

    /**
     * idを条件に取得したデータをShopViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */

    public ShopView findOne(int id) {
        return ShopConverter.toView(findPneInternal(id));
    }

    /**
     * 画面から入力されたショップの登録内容を元にデータを1件作成し、ショップテーブルに登録する
     * @param sv ショップの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ShopView sv){
        List<String>errors=ShopValidator.Validate(sv);
        if(errors.size()==0) {
            createInternal(sv);
        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたショップの登録内容を元に、ショップデータを更新する
     * @param sv ショップの更新内容
     * @return バリデーションで発生したエラーのリスト
     */

    public List<String> update(ShopView sv){

        //バリデーションを行う
        List<String>errors=ShopValidator.Validate(sv);

        if(errors.size()==0) {
            updateInternal(sv);
        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Shop findPneInternal(int id) {
        return em.find(Shop.class, id);
    }
    /**
     * ショップデータを1件登録する
     * @param sv ショップデータ
     */
    private void createInternal(ShopView  sv) {
        em.getTransaction().begin();
        em.persist(ShopConverter.toModel(sv));
        em.getTransaction().commit();
    }

    /**
     * ショップデータを更新する
     * @param sv ショップデータ
     */
    private void updateInternal(ShopView  sv) {

        em.getTransaction().begin();
        Shop s = findPneInternal(sv.getId());
        ShopConverter.copyViewToModel(s, sv);
        em.getTransaction().commit();
    }

}
