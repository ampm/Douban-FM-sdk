package com.zzxhdzj.app.play.delegate;

import android.content.Intent;
import com.zzxhdzj.app.DoubanApplication;
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
        if (DoubanApplication.getInstance().getServicePlayerEngine() != null) {
            DoubanApplication.getInstance().getServicePlayerEngine().play();
        } else {
            startAction(PlayerService.ACTION_PLAY);
        }
    }

    public void pause() {

    }

    public void skip() {
        if (DoubanApplication.getInstance().getServicePlayerEngine() != null) {
            DoubanApplication.getInstance().getServicePlayerEngine().skip();
        } else {
            startAction(PlayerService.ACTION_SKIP);
        }
    }

    public void fav() {

    }

    public void ban() {

    }

    public void stop() {

    }

    public void setPlayerEngineListener(PlayerEngineListener playerEngineListener) {
        DoubanApplication.getInstance().setUiPlayerEngineListener(playerEngineListener);
        if (DoubanApplication.getInstance().getServicePlayerEngine()!= null||playerEngineListener!=null) {
            startAction(PlayerService.ACTION_BIND_LISTENER);
        }
    }

    private void startAction(String action) {
        Intent intent = new Intent(DoubanApplication.getInstance(),
                PlayerService.class);
        intent.setAction(action);
        DoubanApplication.getInstance().startService(intent);
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

    }
}