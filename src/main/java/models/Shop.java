package models;

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
@Table(name = JpaConst.TABLE_SHOP)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_US_COUNT_ALL_MINE,
            query = JpaConst.Q_US_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_SHOP_GET_ALL_MINE,
            query = JpaConst.Q_SHOP_GET_ALL_MINE_DEF)
})

@Getter // すべてのクラスフィールドに好いてgetterを自動生成する（Lombok）
@Setter // すべてのクラスフィールドに好いてsetterを自動生成する（Lombok）
@NoArgsConstructor //引数なしコンストラクタを自動作成する
@AllArgsConstructor //すべてのクラスフィールドを引数の持つ引数ありコンストラクタを自動作成する（Lombok）
@Entity

public class Shop {

    /**
     * id
     */

    @Id
    @Column( name = JpaConst.SHOP_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * ショップを登録したユーザー
     */
    @ManyToOne
    @JoinColumn(name=JpaConst.SHOP_COL_US,nullable=false)
    private User user;

    /**
     * ショップ名
     */
    @Column( name = JpaConst.SHOP_COL_NAME, length=64 ,nullable=false)
    private String name;

    /**
     * ショップ優先フラグ
     */
    @Column( name = JpaConst.SHOP_COL_PRIORITY_FLAG ,nullable=false)
    private Integer priorityflag;

}
