package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import actions.views.UserView;
import constants.AttributeConst;
import constants.MessageConst;
import services.UserService;
import javax.servlet.http.HttpServletRequest;

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
        UserService service, UserView uv,Boolean mailDuplicateCheckFlag, Boolean passwordCheckFlag,String pass_k){
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
        String passError = validatePassword(uv.getPassword(),passwordCheckFlag,pass_k);
        if(!passError.equals("")) {
            errors.add(passError);
        }


        return errors;

    }

    /**
     * メールアドレス関係入力チェックを行い、エラーメッセージを返却
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
        //そもメールアドレスがちゃんとアドレスなのか
        Pattern p = Pattern.compile("^[a-zA-Z0-9-_\\.]+@[a-zA-Z0-9-_\\.]+$");
        Matcher m = p.matcher(mail);
        if(!m.find()) {
            return MessageConst.U_NOADDRESS.getMessage();
        }
        if(mailDuplicateCheckFlag) {
            //メールアドレスの重複チェックを実施
            Long usersCount = isDuplicateUser(service,mail);
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
    private static Long isDuplicateUser(UserService service, String mail) {

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

    private static String validatePassword(String password, Boolean passwordCheckFlag,String pass_k) {
        //入力チェックを実施かつ入力がなければエラーメッセージ返却
        if(passwordCheckFlag && (password==null||password.equals(""))) {
            return MessageConst.U_NOPASSWORD.getMessage();
        }
        //パスが指定の文字列か
        Pattern ps = Pattern.compile("[A-Za-z0-9]{8,24}");
        Matcher m = ps.matcher(password);
        if(!m.find()) {
            return MessageConst.U_NOSTRINGPASS.getMessage();
        }

        if(!pass_k.equals(password)) {
            return MessageConst.U_NOMATCHPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返す
        return "";
    }



}
