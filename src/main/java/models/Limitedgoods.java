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
    @JoinColumn(name=JpaConst.LMEVGOODS_COL_EV_ID,nullable=false)
    private Event eventid;

    /**
     *商品id
     */
    @Column( name = JpaConst.LMEVGOODS_COL_GO_ID, length=64 ,nullable=false)
    private Goods goodsid;

    /**
     * イベントごと並び順
     */
    @Column( name = JpaConst.LMEVGOODS_COL_EV_ORDER ,nullable=false)
    private Integer evorder;
    /**
     * イベントごと価格
     */
    @Column( name = JpaConst.LMEVGOODS_COL_LM_SELLING_PRICE ,nullable=false)
    private Integer lm_sellingprice;
    /**
     * イベントごと販売数
     */
    @Column( name = JpaConst.LMEVGOODS_COL_SOLD_GOODS ,nullable=false)
    private Integer soldgoods;

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

}
