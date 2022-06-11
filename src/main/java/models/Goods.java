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

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * ショップデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_GOODS)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_GOODS_US_COUNT_ALL_MINE,
            query = JpaConst.Q_GOODS_US_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_GOODS_GET_ALL_MINE,
            query = JpaConst.Q_GOODS_GET_ALL_MINE_DEF)

})

@Getter // すべてのクラスフィールドに好いてgetterを自動生成する（Lombok）
@Setter // すべてのクラスフィールドに好いてsetterを自動生成する（Lombok）
@NoArgsConstructor //引数なしコンストラクタを自動作成する
@AllArgsConstructor //すべてのクラスフィールドを引数の持つ引数ありコンストラクタを自動作成する（Lombok）
@Entity

public class Goods {

    /**
     * id
     */

    @Id
    @Column( name = JpaConst.GOODS_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品を作成したショップid
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.GOODS_COL_SHOP,nullable=false)
    private Shop shop;

    /**
     * 商品の名前
     */
    @Column( name = JpaConst.GOODS_COL_NAME, length=64 ,nullable=false)
    private String name;

    /**
     * 販売価格
     */
    @Column( name = JpaConst.GOODS_COL_SEL_PRICE ,nullable=false)
    private Integer sellingprice;
    /**
     * 仕入れ価格
     */
    @Column( name = JpaConst.GOODS_COL_PUR_PRICE ,nullable=false)
    private Integer purchaseprice;

    /**
     * 商品作成日
     */
    @Column( name = JpaConst.GOODS_COL_CREATED_DAY ,nullable=false)
    private LocalDate create_day;

    /**
     *登録日時
     */
    @Column( name = JpaConst.GOODS_COL_CREATED_AT ,nullable=false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column( name = JpaConst.GOODS_COL_UPDATED_AT ,nullable=false)
    private LocalDateTime updatedAt;

    /**
     *商品画像ファイル名
     */
    @Column( name = JpaConst.GOODS_COL_PICTURE ,nullable=false)
    private String picture;


}
