package com.olym.mjt.module.contacts.addlocalcontacts;

import com.olym.mjt.base.presenter.BasePresenter;

public class AddLocalContactPresenter
        extends BasePresenter
{
    private IAddLocalContact iAddLocalContact;

    public AddLocalContactPresenter(IAddLocalContact paramIAddLocalContact)
    {
        this.iAddLocalContact = paramIAddLocalContact;
    }
}
