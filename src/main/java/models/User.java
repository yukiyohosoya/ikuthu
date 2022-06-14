package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_US)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_US_GET_ALL,
            query = JpaConst.Q_US_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_US_COUNT,
            query = JpaConst.Q_US_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_US_GET_BY_MAIL_AND_PASS,
            query = JpaConst.Q_US_GET_BY_MAIL_AND_PASS_DEF),
    @NamedQuery(
            name = JpaConst.Q_US_COUNT_RESISTERED_BY_MAIL,
            query = JpaConst.Q_US_COUNT_RESISTERED_BY_MAIL_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class User {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.US_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 氏名
     */
    @Column(name = JpaConst.US_COL_NAME, nullable = false)
    private String name;


    /**
     * メールアドレス
     */
    @Column(name = JpaConst.US_COL_MAIL, nullable = false, unique = true)
    private String mailaddress;


    /**
     * パスワード
     */
    @Column(name = JpaConst.US_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     *登録日時
     */
    @Column(name = JpaConst.US_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.US_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除されたかどうか（有効：0、削除済み：1）
     */
    @Column( name = JpaConst.ALL_PRIORITY_FLAG_COL_DELETE_FLAG, nullable= false)
    private Integer deleteFlag;


}
