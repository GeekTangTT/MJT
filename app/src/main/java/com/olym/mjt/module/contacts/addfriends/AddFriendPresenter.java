package com.olym.mjt.module.contacts.addfriends;

import com.olym.mjt.base.presenter.BasePresenter;

public class AddFriendPresenter
        extends BasePresenter
{
    private IAddFriendView iAddFriendView;

    public AddFriendPresenter(IAddFriendView paramIAddFriendView)
    {
        this.iAddFriendView = paramIAddFriendView;
    }
}
