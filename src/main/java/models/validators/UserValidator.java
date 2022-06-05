package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class UserValidator {
    /**
     * ユーザーインスタンスの各項目についてバリデーションを行う
     * @param service　呼び出し元Suviceクラスのインスタンス
     * @param uv UserViewのインスタンス
     * @param mailDuplicateCheckFlag　メールアドレスの重複チェックを実施するかどうか（実施する：true 実施しない：false）
     *@param passwordCheckFlag パスワードの入力チェックを行うかどうか
     *@return　エラーのリスト
     *
     */

    public static List<String> validate(
        UserService service, UserView uv,Boolean mailDuplicateCheckFlag, Boolean passwordCheckFlag){
        List<String>errors = new ArrayList<String>();

        //メールアドレスのチェック
        String mailError  = validateMailaddress(service, uv.getMailaddress(),mailDuplicateCheckFlag);
        if(!mailError.equals("")) {
            errors.add(mailError);
        }

        //氏名
        String nameError =validateName(uv.getName());
        if(!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードチェック
        String passError = validatePassword(uv.getPassword(),passwordCheckFlag);
        if(!passError.equals("")) {
            errors.add(passError);
        }

        return errors;

    }

    /**
     * メールアドレスの入力チェックを行い、エラーメッセージを返却
     * @param service EmployeeSuviceインスタンス
     * @param mail メールアドレス
     * @param mailDuplicateCheckFlag　メールアドレスの重複チェックを実施するかどうか（実施する：true 実施しない：false）
     * @return エラーメッセージ
     */
    private static String validateMailaddress(UserService service, String mail, Boolean mailDuplicateCheckFlag) {
        //入力がなければエラーメッセージを返却
        if(mail==null||mail.equals("")) {
            return MessageConst.U_NOEMP_MAIL.getMessage();
        }

        if(mailDuplicateCheckFlag) {
            //メールアドレスの重複チェックを実施

            Long usersCount = isDuplicateEmployee(service,mail);

            //同一メールアドレスがすでに登録されている場合はエラーメッセージを返却
            if(usersCount > 0) {
                return MessageConst.U_US_MAIL_EXIST.getMessage();
            }
        }
        //エラーがない場合は空文字を返却。
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param mail　メールアドレス
     * @return ユーザーテーブルに登録されている同一メールアドレスのデータの件数
     */
    private static Long isDuplicateEmployee(UserService service, String mail) {

        long userCount=service.countByMail(mail);
        return userCount;
    }

    /**
     * 氏名に入力があるかチェックし、入力値がなければエラーメッセージを返却
     * @param name氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if(name==null || name.equals("")) {
            return MessageConst.U_NONAME.getMessage();
        }

        return "";
    }

    /**
     * パスワード入力チェック
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか（実施する：true 実施しない：false）
     * @return エラーメッセージ
     */

    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        //入力チェックを実施かつ入力がなければエラーメッセージ返却
        if(passwordCheckFlag && (password==null||password.equals(""))) {
            return MessageConst.U_NOPASSWORD.getMessage();
        }
        //エラーがない場合は空文字を返す
        return "";
    }

}
