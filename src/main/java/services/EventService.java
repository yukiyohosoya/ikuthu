package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.ShopConverter;
import actions.views.ShopView;
import actions.views.EventConverter;
import actions.views.EventView;
import constants.JpaConst;
import models.Event;
import models.validators.EventValidator;

/**
 * イベントテーブルの操作に関わる処理を行うクラス
 */
public class EventService extends ServiceBase {
    /**
     * 指定したショップが作成したイベントデータを5件分取得しEventViewのリストで返却する
     * @param event イベント
     * @return 一覧画面に表示するデータのリスト
     */
    public List<EventView> getMinePer_index(ShopView shop){

        List<Event> event = em.createNamedQuery(JpaConst.Q_EVENT_GET_ALL_MINE,Event.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
        return EventConverter.toViewList(event);
    }
    /**
     * 指定したショップが作成したイベントデータを、指定されたページ数の一覧画面に表示する分取得しEventViewのリストで返却する
     * @param  event イベント
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<EventView> getMinePerPage(ShopView shop,int page){

        List<Event> event = em.createNamedQuery(JpaConst.Q_EVENT_GET_ALL_MINE,Event.class)
                .setParameter(JpaConst.JPQL_PARM_SHOP, ShopConverter.toModel(shop))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return EventConverter.toViewList(event);
    }

    /**
     * 指定したショップが作成したイベントデータの件数を取得し、返却する
     * @param shop
     * @return イベントデータの件数
     */

    public long countAllMine(ShopView shop) {

        long count =(long)em.createNamedQuery(JpaConst.Q_EVENT_SHOP_COUNT_ALL_MINE,Long.class)
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
       * 画面から入力されたイベントの登録内容を元にデータを1件作成し、イベントテーブルに登録する
       * @param sv イベントの登録内容
       * @return バリデーションで発生したエラーのリスト
       */
      public List<String> create(EventView ev){
          List<String>errors=EventValidator.Validate(ev);
          if(errors.size()==0) {
              LocalDateTime ldt =LocalDateTime.now();
              ev.setCreatedAt(ldt);
              ev.setUpdatedAt(ldt);
              createInternal(ev);
          }
          //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
          return errors;
      }
      /**
       * 画面から入力されたイベントの登録内容を元に、イベントデータを更新する
       * @param sv イベントの更新内容
       * @return バリデーションで発生したエラーのリスト
       */
      public List<String> update(EventView ev){
          //バリデーションを行う
          List<String>errors=EventValidator.Validate(ev);
          if(errors.size()==0) {
              LocalDateTime ldt =LocalDateTime.now();
              ev.setUpdatedAt(ldt);
              updateInternal(ev);
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
          EventView saved = findOne(id);

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
    private Event findPneInternal(int id) {
        return em.find(Event.class, id);
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
