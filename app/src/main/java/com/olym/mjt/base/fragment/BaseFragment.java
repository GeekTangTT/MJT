package com.olym.mjt.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xutils.ViewInjector;
import org.xutils.x;

public abstract class BaseFragment
        extends Fragment
        implements IBaseView
{
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Activity activity;

    public void onAttach(Context paramContext)
    {
        super.onAttach(paramContext);
        this.activity = ((Activity)paramContext);
    }

    public void onCreate(@Nullable Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        handleArguments();
        if (paramBundle != null)
        {
            boolean bool = paramBundle.getBoolean("STATE_SAVE_IS_HIDDEN");
            paramBundle = getFragmentManager().beginTransaction();
            if (!bool) {
                break label44;
            }
            paramBundle.hide(this);
        }
        for (;;)
        {
            paramBundle.commit();
            return;
            label44:
            paramBundle.show(this);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
    {
        return x.view().inject(this, paramLayoutInflater, paramViewGroup);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        destroy();
    }

    public void onDetach()
    {
        super.onDetach();
        this.activity = null;
    }

    public void onSaveInstanceState(Bundle paramBundle)
    {
        paramBundle.putBoolean("STATE_SAVE_IS_HIDDEN", isHidden());
    }

    public void onViewCreated(View paramView, @Nullable Bundle paramBundle)
    {
        super.onViewCreated(paramView, paramBundle);
        init();
    }
}
