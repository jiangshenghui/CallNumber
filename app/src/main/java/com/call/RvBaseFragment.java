package com.call;

import android.os.Bundle;
import android.view.View;
import com.bg.baseutillib.base.BaseFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RvBaseFragment extends BaseFragment {
    Unbinder bind;

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
         bind = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null)
            bind.unbind();
    }
}
