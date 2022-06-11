package services;

import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;

import actions.views.EventConverter;
import actions.views.EventView;
import constants.JpaConst;
import models.Event;

/**
 * イベントテーブルの操作に関わる処理を行うクラス
 */
public class EventService extends ServiceBase {
    /**
     * 指定したショップが作成したイベントデータを、指定されたページ数の一覧画面に表示する分取得しEventViewのリストで返却する
     * @param shop ショップ
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<EventView> getMinePerPage(ShopView shop,int page){

        List<Event> shops = em.createNamedQuery(JpaConst.Q_EVENT_US_COUNT_ALL_MINE,Event.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return EventConverter.toViewList(shops);
    }

    /**
     * 指定したショップが作成したイベントデータの件数を取得し、返却する
     * @param shop
     * @return イベントデータの件数
     */

    public long countAllMine(ShopView shop) {

        long count =(long)em.createNamedQuery(JpaConst.Q_EVENT_GET_ALL_MINE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .getSingleResult();
        return count;
    }

    /**
     * idを条件に取得したデータをEventViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */

    public EventView findOne(int id) {
        return EventConverter.toView(findPneInternal(id));
    }

    /**
     * shop名をを条件に取得したデータをEventViewのインスタンスで返却する
     * @param name
     * @return 取得データのインスタンス
     */

    public EventView findOneName(String name) {
        return EventConverter.toView(findPneInternalName(name));
    }

//    /**
//     * 画面から入力されたイベントの登録内容を元にデータを1件作成し、イベントテーブルに登録する
//     * @param sv イベントの登録内容
//     * @return バリデーションで発生したエラーのリスト
//     */
//    public List<String> create(EventView sv){
//        List<String>errors=EventValidator.Validate(sv,this);
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
//    public List<String> update(EventView sv){
//
//        //バリデーションを行う
//        List<String>errors=EventValidator.Validate(sv,this);
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
    private Event findPneInternal(int id) {
        return em.find(Event.class, id);
    }

    /**
     * イベント名を条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Event findPneInternalName(String name) {
        return em.find(Event.class, name);
    }


    /**
     * ショップIDと入力された名前をを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param mailaddress メールアドレス
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateEvent(ShopView uv, EventView sv) {
        boolean validateEvent = false;
        if (uv.getId().equals(sv.getShop().getId())) {
                //データが取得できた場合、認証成功
                validateEvent = true;
            }
        return validateEvent;
        }
    /**
     * イベント名とShopを条件に該当するデータの件数を取得し、返却する
     * @param shopname イベント名
     * @return 該当するデータの件数
     */
    public long countByEvent(ShopView shop,String shopname) {

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
    private void createInternal(EventView  sv) {
        em.getTransaction().begin();
        em.persist(EventConverter.toModel(sv));
        em.getTransaction().commit();
    }

    /**
     * イベントデータを更新する
     * @param sv イベントデータ
     */
    private void updateInternal(EventView  sv) {

        em.getTransaction().begin();
        Event s = findPneInternal(sv.getId());
        EventConverter.copyViewToModel(s, sv);
        em.getTransaction().commit();
    }

}
