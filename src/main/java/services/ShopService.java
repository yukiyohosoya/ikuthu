package services;

import java.time.LocalDateTime;
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

        List<Shop> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE,Shop.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ShopConverter.toViewList(reports);
    }

    /**
     * 指定したユーザーが作成したショップデータの件数を取得し、返却する
     * @param user
     * @return ショップデータの件数
     */

    public long countAllMine(UserView user) {

        long count =(long)em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, UserConverter.toModel(user))
                .getSingleResult();
        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示するショップデータを取得し、ShopViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ShopView> getAllPerPage(int page){
        List<Shop> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL,Shop.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE*(page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ShopConverter.toViewList(reports);
    }

    /**
     * ショップテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count=(long)em.createNamedQuery(JpaConst.Q_REP_COUNT,Long.class)
                .getSingleResult();
        return reports_count;
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
     * @param rv ショップの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ShopView rv){
        List<String>errors=ShopValidator.Validate(rv);
        if(errors.size()==0) {
            LocalDateTime ldt =LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたショップの登録内容を元に、ショップデータを更新する
     * @param rv ショップの更新内容
     * @return バリデーションで発生したエラーのリスト
     */

    public List<String> update(ShopView rv){

        //バリデーションを行う
        List<String>errors=ShopValidator.Validate(rv);

        if(errors.size()==0) {
            //更新日時を現在時刻に設定
            LocalDateTime ldt =LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);
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
     * @param rv ショップデータ
     */
    private void createInternal(ShopView  rv) {
        em.getTransaction().begin();
        em.persist(ShopConverter.toModel(rv));
        em.getTransaction().commit();
    }

    /**
     * ショップデータを更新する
     * @param rv ショップデータ
     */
    private void updateInternal(ShopView  rv) {

        em.getTransaction().begin();
        Shop r = findPneInternal(rv.getId());
        ShopConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();
    }

}
