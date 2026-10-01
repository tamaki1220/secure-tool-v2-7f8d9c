package com.example.demo.service;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository; //このリポジトリを定義

    //UserServiceを新しく作るときは、UserRepositoryをここに差し込んで(注入して)と指示だし
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //上記の形の連携が定石

    //パスワードなしのログイン処理
    //指定されたユーザーIDでDBを検索し、見つからなかったらエラーを表示させる。見つかったらユーザーの情報を返す。
    public UserEntity login(String userId) {
        //Idが存在するか検索し、なければエラーにする
        return userRepository.findById(userId) //リポジトリを使ってデータベースからuserIdが一致するユーザーを検索
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません")); //もし見つからなかったらエラー表示
    }

    /* パスワードもログイン認証に使う場合
    public UserEntity login(String userId, String password) {　ログインにユーザーIDとパスワードを使うことを定義
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません:" + userId)); //もし見つからなかったらエラー表示

        if (!user.getPassword().equals(password)) {　入力されたパスワードとDBにあるパスワードが一致しているか比較する
            throw new RuntimeException("ユーザーIDまたはパスワードが違います");
        }

        return user; //IDもパスワードも一致していたらログイン成功
        }
    }
    */

}
