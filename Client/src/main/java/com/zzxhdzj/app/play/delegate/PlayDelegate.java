package com.zzxhdzj.app.play.delegate;

import android.content.Intent;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.service.PlayerService;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/5/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayDelegate{

    private static PlayDelegate playDelegate;

    public void play() {
        if (DoubanFmApp.getInstance().getServicePlayerEngine() != null) {
            DoubanFmApp.getInstance().getServicePlayerEngine().play();
        } else {
            startAction(PlayerService.ACTION_PLAY);
        }
    }

    public void pause() {

    }

    public void skip() {
        if (DoubanFmApp.getInstance().getServicePlayerEngine() != null) {
            DoubanFmApp.getInstance().getServicePlayerEngine().skip();
        } else {
            startAction(PlayerService.ACTION_SKIP);
        }
    }

    public void fav() {
        if (DoubanFmApp.getInstance().getServicePlayerEngine() != null) {
            DoubanFmApp.getInstance().getServicePlayerEngine().fav();
        } else {
            startAction(PlayerService.ACTION_FAV);
        }
    }

    public void ban() {
        if (DoubanFmApp.getInstance().getServicePlayerEngine() != null) {
            DoubanFmApp.getInstance().getServicePlayerEngine().ban();
        } else {
            startAction(PlayerService.ACTION_BAN);
        }
    }

    public void stop() {

    }

    public void setPlayerEngineListener(PlayerEngineListener playerEngineListener) {
        DoubanFmApp.getInstance().addPlayerEngineListener(playerEngineListener);
        if (DoubanFmApp.getInstance().getServicePlayerEngine()!= null||playerEngineListener!=null) {
            startAction(PlayerService.ACTION_BIND_LISTENER);
        }
    }

    private void startAction(String action) {
        Intent intent = new Intent(DoubanFmApp.getInstance(),
                PlayerService.class);
        intent.setAction(action);
        DoubanFmApp.getInstance().startService(intent);
    }
    private PlayDelegate(){
    }
    public static PlayDelegate getInstance() {
        if(playDelegate==null){
            return new PlayDelegate();
        }
        return playDelegate;
    }

    public void unfav() {
        if (DoubanFmApp.getInstance().getServicePlayerEngine() != null) {
            DoubanFmApp.getInstance().getServicePlayerEngine().unfav();
        } else {
            startAction(PlayerService.ACTION_UNFAV);
        }
    }
}