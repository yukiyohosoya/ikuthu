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

        long count =(long)em.createNamedQuery(JpaConst.Q_SHOP_US_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_US, UserConverter.toModel(user))
                .getSingleResult();
        return count;
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
     * shop名をを条件に取得したデータをShopViewのインスタンスで返却する
     * @param name
     * @return 取得データのインスタンス
     */

    public ShopView findOneName(String name) {
        return ShopConverter.toView(findPneInternalName(name));
    }

    /**
     * 画面から入力されたショップの登録内容を元にデータを1件作成し、ショップテーブルに登録する
     * @param sv ショップの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ShopView sv){
        List<String>errors=ShopValidator.Validate(sv,this);
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
        List<String>errors=ShopValidator.Validate(sv,this);

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
     * ショップ名を条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Shop findPneInternalName(String name) {
        return em.find(Shop.class, name);
    }


    /**
     * ユーザーIDと入力された名前をを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param mailaddress メールアドレス
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateShop(UserView uv, ShopView sv) {
        boolean validateShop = false;
        if (uv.getId().equals(sv.getUser().getId())) {
                //データが取得できた場合、認証成功
                validateShop = true;
            }
        return validateShop;
        }
    /**
     * ショップ名とUserを条件に該当するデータの件数を取得し、返却する
     * @param shopname ショップ名
     * @return 該当するデータの件数
     */
    public long countByShop(UserView user,String shopname) {

        long count =(long)em.createNamedQuery(JpaConst.Q_SHOP_US_NAME_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_US, UserConverter.toModel(user))
                .setParameter(JpaConst.JPQL_PARM_SHOPNAME,shopname)
                .getSingleResult();

        return count;
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
