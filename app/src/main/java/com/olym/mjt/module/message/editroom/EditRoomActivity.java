package com.olym.mjt.module.message.editroom;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.utils.StatusBarUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427370)
public class EditRoomActivity
        extends BasePresenterActivity<EditRoomPresenter>
        implements IEditRoomView
{
    public static final String EDIT_MINE_NAME = "mine_name";
    public static final String EDIT_ROOMDESC = "room_notice";
    public static final String EDIT_ROOMNAME = "room_name";
    public static final String MAX_LENGTH = "max_length";
    public static final String ROOM_TITLE = "room_title";
    @ViewInject(2131230914)
    private LinearLayout editBack;
    @ViewInject(2131231310)
    private TextView editSave;
    @ViewInject(2131230915)
    private TextView edit_room_title;
    private int maxLength = -1;
    private String mineNickName;
    private String roomDesc;
    @ViewInject(2131231287)
    private EditText roomEdit;
    private String roomName;
    private String room_title;
    private int type = -1;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle)
    {
        this.room_title = paramBundle.getString("room_title");
        this.roomName = ((String)paramBundle.getSerializable("room_name"));
        this.roomDesc = ((String)paramBundle.getSerializable("room_notice"));
        this.mineNickName = ((String)paramBundle.getSerializable("mine_name"));
        this.maxLength = ((Integer)paramBundle.getSerializable("max_length")).intValue();
    }

    public void init()
    {
        StatusBarUtil.setStatusBar(this);
        if (this.maxLength != -1)
        {
            InputFilter.LengthFilter localLengthFilter = new InputFilter.LengthFilter(this.maxLength);
            this.roomEdit.setFilters(new InputFilter[] { localLengthFilter });
        }
        if (!TextUtils.isEmpty(this.room_title)) {
            this.edit_room_title.setText(this.room_title);
        }
        if (this.roomName != null)
        {
            this.roomEdit.setText(this.roomName);
            this.type = 0;
        }
        for (;;)
        {
            this.editBack.setOnClickListener(new EditRoomActivity.1(this));
            this.editSave.setOnClickListener(new EditRoomActivity.2(this));
            return;
            if (this.roomDesc != null)
            {
                this.roomEdit.setText(this.roomDesc);
                this.type = 1;
            }
            else if (this.mineNickName != null)
            {
                this.roomEdit.setText(this.mineNickName);
                this.type = 2;
            }
        }
    }

    protected void setPresenter()
    {
        this.presenter = new EditRoomPresenter(this, this);
    }
}
