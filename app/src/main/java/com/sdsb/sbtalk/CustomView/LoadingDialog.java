package com.sdsb.sbtalk.CustomView;

import android.app.Dialog;
import android.content.Context;

import com.sdsb.sbtalk.R;

/**
 * Created by thstj on 2017-01-12.
 */

public class LoadingDialog extends Dialog{


    public LoadingDialog(Context context) {
        super(context);
        setContentView(getLayoutInflater().inflate(R.layout.dialog_loading, null));
    }
}
