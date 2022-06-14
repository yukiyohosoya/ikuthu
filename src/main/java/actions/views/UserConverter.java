package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.User;

/**
 * ユーザーデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class UserConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param uv UserViewのインスタンス
     * @return Userのインスタンス
     */
    public static User toModel(UserView uv) {

        return new User(
                uv.getId(),
                uv.getName(),
                uv.getMailaddress(),
                uv.getPassword(),
                uv.getCreatedAt(),
                uv.getUpdatedAt(),
                uv.getDeleteFlag()== null
                ? null
                : uv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Userのインスタンス
     * @return UserViewのインスタンス
     */
    public static UserView toView(User u) {

        if(u == null) {
            return null;
        }

        return new UserView(
                u.getId(),
                u.getName(),
                u.getMailaddress(),
                u.getPassword(),
                u.getCreatedAt(),
                u.getUpdatedAt(),
                u.getDeleteFlag()== null
                ? null
                : u.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE);
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<UserView> toViewList(List<User> list) {
        List<UserView> uvs = new ArrayList<>();

        for (User u : list) {
            uvs.add(toView(u));
        }

        return uvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param uv Viewモデル(コピー元)
     */
    public static void copyViewToModel(User u, UserView uv) {
        u.setId(uv.getId());
        u.setName(uv.getName());
        u.setMailaddress(uv.getMailaddress());
        u.setPassword(uv.getPassword());
        u.setCreatedAt(uv.getCreatedAt());
        u.setUpdatedAt(uv.getUpdatedAt());
        u.setDeleteFlag(uv.getDeleteFlag());


    }

}