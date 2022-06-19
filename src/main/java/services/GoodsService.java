package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;
import actions.views.EventConverter;
import actions.views.EventView;
import actions.views.GoodsConverter;
import actions.views.GoodsView;
import actions.views.LimitedgoodsView;
import constants.JpaConst;
import models.Goods;
import models.validators.EventValidator;
import models.validators.GoodsValidator;

/**
 * グッズテーブルの操作に関わる処理を行うクラス
 */
public class GoodsService extends ServiceBase {
    /**
     * 指定したショップが作成したグッズデータを、指定されたページ数の一覧画面に表示する分取得しGoodsViewのリストで返却する
     * @param shop ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodsView> getMinePer_index(ShopView shop){

        List<Goods> goods = em.createNamedQuery(JpaConst.Q_GOODS_GET_ALL_MINE,Goods.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
        return GoodsConverter.toViewList(goods);
    }

    /**
     * 指定したショップが作成したグッズデータを、指定されたページ数の一覧画面に表示する分取得しGoodsViewのリストで返却する
     * @param shop ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodsView> getMinePerPage(ShopView shop,int page){

        List<Goods> goods = em.createNamedQuery(JpaConst.Q_GOODS_GET_ALL_MINE,Goods.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return GoodsConverter.toViewList(goods);
    }

    /**
     * 指定したショップが作成したグッズデータを、すべて取得しGoodsViewのリストで返却する
     * @param shop ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodsView> getMineAll(ShopView shop){

        List<Goods> goods = em.createNamedQuery(JpaConst.Q_GOODS_GET_ALL_MINE,Goods.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .getResultList();
        return GoodsConverter.toViewList(goods);
    }


    /**
     * 既に作成したイベントごと商品以外を指定し、該当したものをGoodsViewのリストで返却する
     * @param event ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodsView> getNotLmevgoodsMine(ShopView shop,List<LimitedgoodsView> select_lmevgoods){

        List<Goods> goods = em.createNamedQuery(JpaConst.Q_GOODS_NOLIMIGOODS_ALL_MINE,Goods.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setParameter(JpaConst.JPQL_PARM_LMEVGOODSLIST, GoodsConverter.toModelList(select_lmevgoods))
                .getResultList();
        return GoodsConverter.toViewList(goods);
    }

    /**
     * 指定したショップが作成したグッズデータの件数を取得し、返却する
     * @param shop
     * @return グッズデータの件数
     */

    public long countAllMine(ShopView shop) {

        long count =(long)em.createNamedQuery(JpaConst.Q_GOODS_US_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .getSingleResult();
        return count;
    }

    /**
     * 指定したEventが作成したイベント限定グッズ以外の件数を取得し、返却する
     * @param count
     * @return データの件数
     */
    public long countNotLmevgoodsMine(ShopView shop,List<LimitedgoodsView> select_lmevgoods) {

        long count =(long)em.createNamedQuery(JpaConst.Q_GOODS_NOLIMIGOODS_COUNT_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setParameter(JpaConst.JPQL_PARM_LMEVGOODSLIST, GoodsConverter.toModelList(select_lmevgoods))
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

    /**
     * 画面から入力されたイベントの登録内容を元にデータを1件作成し、イベントテーブルに登録する
     * @param gv イベントの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(GoodsView gv){
        List<String>errors=GoodsValidator.Validate(gv);
        if(errors.size()==0) {
            LocalDateTime ldt =LocalDateTime.now();
            gv.setCreatedAt(ldt);
            gv.setUpdatedAt(ldt);
            createInternal(gv);
        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }
    /**
     * 画面から入力されたイベントの登録内容を元に、イベントデータを更新する
     * @param gv イベントの更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(GoodsView gv){
        //バリデーションを行う
        List<String>errors=GoodsValidator.Validate(gv);
        if(errors.size()==0) {
            LocalDateTime ldt =LocalDateTime.now();
            gv.setUpdatedAt(ldt);
            updateInternal(gv);
        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得
        GoodsView saved = findOne(id);

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
    private Goods findPneInternal(int id) {
        return em.find(Goods.class, id);
    }

    /**
     * グッズ名を条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Goods findPneInternalName(String name) {
        return em.find(Goods.class, name);
    }

    /**
     * グッズデータを1件登録する
     * @param gv グッズデータ
     */
    private void createInternal(GoodsView  gv) {
        em.getTransaction().begin();
        em.persist(GoodsConverter.toModel(gv));
        em.getTransaction().commit();
    }

    /**
     * グッズデータを更新する
     * @param gv グッズデータ
     */
    private void updateInternal(GoodsView  gv) {

        em.getTransaction().begin();
        Goods s = findPneInternal(gv.getId());
        GoodsConverter.copyViewToModel(s, gv);
        em.getTransaction().commit();
    }

}
