package actions;

import java.io.IOException;
import java.util.List;//追記

import javax.servlet.ServletException;

import actions.views.UserView;//追記
import actions.views.ShopView;//追記
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;//追記
import services.ShopService;//追記


/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase{

    private ShopService service;//追記

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service =new ShopService();//追記

        //メソッドを実行
        invoke();

        service.close();//追記

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        // 以下追記

        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_US);

        //ログイン中のユーザーが作成したショップデータを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ShopView> shop = service.getMinePerPage(loginUser, page);

      //ログイン中のユーザーが作成したショップデータの件数を取得
        long myShopsCount = service.countAllMine(loginUser);
        putRequestScope(AttributeConst.SHOPS,shop); //取得したショップデータ
        putRequestScope(AttributeConst.SH_COUNT, myShopsCount); //ログイン中のユーザーが作成したショップの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
        System.out.println(getRequestParam(AttributeConst.SH_ID));
        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}