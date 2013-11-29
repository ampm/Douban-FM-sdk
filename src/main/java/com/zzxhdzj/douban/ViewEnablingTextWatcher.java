package com.zzxhdzj.douban;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class ViewEnablingTextWatcher {
    private View toEnable;
    private EditText[] toWatch;

    public ViewEnablingTextWatcher(View toEnable, EditText... toWatch) {
        super();
        this.toEnable = toEnable;
        this.toWatch = toWatch;
        ChangeListener changeListener = new ChangeListener();
        for (EditText editText : toWatch) {
            editText.addTextChangedListener(changeListener);
        }
    }

    private void setViewEnabledState() {
        boolean enabledState = true;
        for (EditText editText : toWatch) {
            CharSequence text = editText.getText();
            if (text == null || text.toString().trim().length() == 0) {
                enabledState = false;
                break;
            }
        }
        toEnable.setEnabled(enabledState);
    }

    private class ChangeListener extends StubTextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            setViewEnabledState();
        }
    }
}
