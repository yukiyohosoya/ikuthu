package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;

import models.Event;

/**
 * イベントデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class EventConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv EventViewのインスタンス
     * @return Eventのインスタンス
     */
    public static Event toModel(EventView ev) {

        return new Event(
                ev.getId(),
                ShopConverter.toModel(ev.getShop()),
                ev.getName(),
                ev.getEventday(),
                ev.getCreatedAt(),
                ev.getUpdatedAt()
                );
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Eventのインスタンス
     * @return EventViewのインスタンス
     */
    public static EventView toView(Event e) {

        if(e == null) {
            return null;
        }

        return new EventView(
                e.getId(),
                ShopConverter.toView(e.getShop()),
                e.getName(),
                e.getEventday(),
                e.getCreatedAt(),
                e.getUpdatedAt()
                );
     }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<EventView> toViewList(List<Event> list) {
        List<EventView> evl = new ArrayList<>();

        for (Event e : list) {
            evl.add(toView(e));
        }

        return evl;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Event e, EventView ev) {
        e.setId(ev.getId());
        e.setShop(ShopConverter.toModel(ev.getShop()));
        e.setName(ev.getName());
        e.setEventday(ev.getEventday());
        e.setCreatedAt(ev.getCreatedAt());
        e.setUpdatedAt(ev.getUpdatedAt());
    }

}