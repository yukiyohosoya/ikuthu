package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import actions.views.ShopView;
import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * イベントデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_EVE)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_EVENT_SHOP_COUNT_ALL_MINE,
            query = JpaConst.Q_EVENT_SHOP_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_EVENT_GET_ALL_MINE,
            query = JpaConst.Q_EVENT_GET_ALL_MINE_DEF)
})

@Getter // すべてのクラスフィールドに好いてgetterを自動生成する（Lombok）
@Setter // すべてのクラスフィールドに好いてsetterを自動生成する（Lombok）
@NoArgsConstructor //引数なしコンストラクタを自動作成する
@AllArgsConstructor //すべてのクラスフィールドを引数の持つ引数ありコンストラクタを自動作成する（Lombok）
@Entity

public class Event {

    /**
     * id
     */

    @Id
    @Column( name = JpaConst.EV_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * イベントを作成したショップid
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.EV_COL_SHOP,nullable=false)
    private Shop shop;

    /**
     *イベントの名前
     */
    @Column( name = JpaConst.EV_COL_NAME, length=64 ,nullable=false)
    private String name;

    /**
     * イベント日付
     */
    @Column( name = JpaConst.EV_COL_EVENT_DAY ,nullable=false)
    private String eventday;

    /**
     *登録日時
     */
    @Column( name = JpaConst.EV_COL_CREATED_AT ,nullable=false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column( name = JpaConst.EV_COL_UPDATED_AT ,nullable=false)
    private LocalDateTime updatedAt;

}
