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
        //以下追記

        //セッションからログイン中の従業員情報を取得
        UserView loginUser = (UserView)getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得
        int page = getPage();
        List<ShopView> reports = service.getMinePerPage(loginUser, page);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myShopsCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.REPORTS,reports);//取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT,myShopsCount);//ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE,page);//追え―時数
        putRequestScope(AttributeConst.MAX_ROW,JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

        //↑追記ここまで


        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
