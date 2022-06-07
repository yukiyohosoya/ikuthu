package actions;

import java.io.IOException;
import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import services.UserService;

import actions.views.UserView;
import constants.MessageConst;
import constants.PropertyConst;


/**
 * 認証に関する処理を行うActionクラス
 *
 */

public class AuthAction extends ActionBase{

    private UserService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * ログイン画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {
        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN,getTokenId());

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_LOGIN);


    }

    /**
     * ログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {

        String mailaddress = getRequestParam(AttributeConst.US_MAIL);
        String plainPass=getRequestParam(AttributeConst.US_PASS);
        String pepper=getContextScope(PropertyConst.PEPPER);

        //有効なユーザーか承認する
        boolean isValidUser = service.validateLogin(mailaddress, plainPass, pepper);

        if(isValidUser) {
            //認証成功の場合

            //CSRF対策 tokenのチェック
            if (checkToken()) {

                //ログインしたユーザーのDBデータを取得
                UserView ev = service.findOne(mailaddress, plainPass, pepper);
                //セッションにログインしたユーザーを設定
                putSessionScope(AttributeConst.LOGIN_US,ev);
                //セッションにログイン完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH,MessageConst.I_LOGINED.getMessage());
                //トップへリダイレクト
                redirect(ForwardConst.ACT_TOP,ForwardConst.CMD_INDEX);
            }
        }else{
            //認証失敗

            //CRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN,getTokenId());
            //承認失敗エラーメッセージ表示フラグを建てる
            putRequestScope(AttributeConst.LOGIN_ERR,true);
            //入力されたユーザーコードを設定
            putRequestScope(AttributeConst.US_MAIL,mailaddress);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);

        }

    }

    /**
     * ログアウト処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException, IOException {

        //セッションからログイン中ぎゅいんのパラメータを削除
        removeSessionScope(AttributeConst.LOGIN_US);

        //セッションにログアウト時のフラッシュメッセージを追加
        putSessionScope(AttributeConst.FLUSH,MessageConst.I_LOGOUT.getMessage());

        //ログイン画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH,ForwardConst.CMD_SHOW_LOGIN);

    }

}
