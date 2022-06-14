package models;

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

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * イベントごと商品テーブルのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_LMEVGOODS)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_LIMIGOODS_US_COUNT_ALL_MINE,
            query = JpaConst.Q_LIMIGOODS_US_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_LIMIGOODS_GET_ALL_MINE,
            query = JpaConst.Q_LIMIGOODS_GET_ALL_MINE_DEF)
})

@Getter // すべてのクラスフィールドに好いてgetterを自動生成する（Lombok）
@Setter // すべてのクラスフィールドに好いてsetterを自動生成する（Lombok）
@NoArgsConstructor //引数なしコンストラクタを自動作成する
@AllArgsConstructor //すべてのクラスフィールドを引数の持つ引数ありコンストラクタを自動作成する（Lombok）
@Entity

public class Limitedgoods {

    /**
     * id
     */

    @Id
    @Column( name = JpaConst.LMEVGOODS_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * イベントid
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.LMEVGOODS_COL_EVENT,nullable=false)
    private Event event;

    /**
     *商品id
     */
    @ManyToOne
    @JoinColumn( name = JpaConst.LMEVGOODS_COL_GOODS ,nullable=false)
    private Goods goods;

    /**
     * イベントごと並び順
     */
    @Column( name = JpaConst.LMEVGOODS_COL_EV_ORDER ,nullable=false)
    private String evorder;
    /**
     * イベントごと価格
     */
    @Column( name = JpaConst.LMEVGOODS_COL_LM_SELLING_PRICE ,nullable=false)
    private String lm_sellingprice;
    /**
     * イベントごと販売数
     */
    @Column( name = JpaConst.LMEVGOODS_COL_SOLD_GOODS ,nullable=false)
    private String soldgoods;

    /**
     *登録日時
     */
    @Column( name = JpaConst.LMEVGOODS_COL_CREATED_AT ,nullable=false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column( name = JpaConst.LMEVGOODS_COL_UPDATED_AT ,nullable=false)
    private LocalDateTime updatedAt;

    /**
     * 削除されたかどうか（有効：0、削除済み：1）
     */

    @Column( name = JpaConst.ALL_PRIORITY_FLAG_COL_DELETE_FLAG, nullable= false)
    private Integer deleteFlag;

}
