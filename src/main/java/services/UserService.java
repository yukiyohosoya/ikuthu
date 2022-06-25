package services;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.LimitedgoodsView;
import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

/**
 * ユーザーテーブルの操作に関わる処理を行うクラス
 */
public class UserService extends ServiceBase {
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、UserViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<UserView> getPerPage(int page) {
        List<User> users = em.createNamedQuery(JpaConst.Q_US_GET_ALL, User.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return UserConverter.toViewList(users);
    }

    /**
     * ユーザーテーブルのデータの件数を取得し、返却する
     * @return ユーザーテーブルのデータの件数
     */
    public long countAll() {
        long usCount = (long) em.createNamedQuery(JpaConst.Q_US_COUNT, Long.class)
                .getSingleResult();

        return usCount;
    }

    /**
     * メールアドレス、パスワードを条件に取得したデータをUserViewのインスタンスで返却する
     * @param mailaddress メールアドレス
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public UserView findOne(String mailaddress, String plainPass, String pepper) {
        User u = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //メールアドレスとハッシュ化済パスワードを条件に未削除のユーザーを1件取得する
            u = em.createNamedQuery(JpaConst.Q_US_GET_BY_MAIL_AND_PASS, User.class)
                    .setParameter(JpaConst.US_COL_MAIL, mailaddress)
                    .setParameter(JpaConst.US_COL_PASS, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return UserConverter.toView(u);

    }

    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u = findOneInternal(id);
        return UserConverter.toView(u);
    }

    /**
     * メールアドレスを条件に該当するデータの件数を取得し、返却する
     * @param mailaddress メールアドレス
     * @param 削除してないアカウント
     * @return 該当するデータの件数
     */
    public long countByMail(String mailaddress) {

        //指定したメールアドレスを保持するユーザーの件数を取得する
        long users_count = (long) em.createNamedQuery(JpaConst.Q_US_COUNT_RESISTERED_BY_MAIL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_MAIL, mailaddress)
                .setParameter(JpaConst.JPQL_PARM_FLAG, 0)
                .getSingleResult();
        return users_count;
    }

    /**
     * メールアドレスを条件に該当するデータの件数を取得し、返却する
     * @param mailaddress メールアドレス
     * @param 削除フラグtrue
     * @return 該当するデータの件数
     */
    public long countBydeleteMail(String mailaddress) {

        //指定したメールアドレスを保持するユーザーの件数を取得する
        long users_count = (long) em.createNamedQuery(JpaConst.Q_US_COUNT_RESISTERED_BY_MAIL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_MAIL, mailaddress)
                .setParameter(JpaConst.JPQL_PARM_FLAG, 1)
                .getSingleResult();
        return users_count;
    }

    /**
     * 画面から入力されたユーザーの登録内容を元にデータを1件作成し、ユーザーテーブルに登録する
     * @param uv 画面から入力されたユーザーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(UserView uv, String pepper,String pass_k) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper);
        String pass_kh = EncryptUtil.getPasswordEncrypt(pass_k, pepper);
        uv.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        uv.setCreatedAt(now);
        uv.setUpdatedAt(now);

      //登録内容のバリデーションを行う
        List<String> errors = UserValidator.validate(this, uv, true, true,pass_kh);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(uv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたユーザーの更新内容を元にデータを1件作成し、ユーザーテーブルを更新する
     * @param uv 画面から入力されたユーザーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(UserView uv, String pepper,String pass_k) {

        //idを条件に登録済みのユーザー情報を取得する
        UserView savedUser = findOne(uv.getId());

        String pass_kh = EncryptUtil.getPasswordEncrypt(pass_k, pepper);

        boolean validateMail = false;
        if (!savedUser.getMailaddress().equals(uv.getMailaddress())) {
            //メールアドレスを更新する場合

            //メールアドレスについてのバリデーションを行う
            validateMail = true;
            //変更後のメールアドレスを設定する
            savedUser.setMailaddress(uv.getMailaddress());
        }

        boolean validatePass = false;
        if (uv.getPassword() != null && !uv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedUser.setPassword(
                    EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper));
        }

        savedUser.setName(uv.getName()); //変更後の氏名を設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedUser.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = UserValidator.validate(this, savedUser, validateMail, validatePass,pass_kh);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedUser);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得
        UserView saved = findOne(id);

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        saved.setUpdatedAt(today);

        //論理削除フラグを立てる
        saved.setDeleteFlag(JpaConst.ALL_DEL_TRUE);

        //更新処理
        update(saved);

    }

    /**
     * メールアドレスとパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param mailaddress メールアドレス
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String mailaddress, String plainPass, String pepper) {

        boolean isValidUserShop = false;
        if (mailaddress != null && !mailaddress.equals("") && plainPass != null && !plainPass.equals("")) {
            UserView uv = findOne(mailaddress, plainPass, pepper);

            if (uv != null && uv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidUserShop = true;
            }
        }

        //認証結果を返却する
        return isValidUserShop;
    }

    /**
     * idを条件にデータを1件取得し、Userのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private User findOneInternal(int id) {
        User e = em.find(User.class, id);

        return e;
    }

    /**
     * ユーザーデータを1件登録する
     * @param ev ユーザーデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();

    }

    /**
     * ユーザーデータを更新する
     * @param ev 画面から入力されたユーザーの登録内容
     */
    private void update(UserView uv) {

        em.getTransaction().begin();
        User u = findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();

    }

}