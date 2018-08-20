package com.olym.mjt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import java.lang.reflect.Method;

public class DigitsEditText
        extends EditText
{
    private Boolean isDigit = null;
    private Method showSoftInputOnFocus = null;

    public DigitsEditText(Context paramContext)
    {
        this(paramContext, null);
    }

    public DigitsEditText(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        setIsDigit(true, false);
    }

    public DigitsEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        this(paramContext, paramAttributeSet);
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent)
    {
        if (paramAccessibilityEvent.getEventType() == 16)
        {
            i = paramAccessibilityEvent.getAddedCount();
            j = paramAccessibilityEvent.getRemovedCount();
            k = paramAccessibilityEvent.getBeforeText().length();
            if (i > j)
            {
                paramAccessibilityEvent.setRemovedCount(0);
                paramAccessibilityEvent.setAddedCount(1);
                paramAccessibilityEvent.setFromIndex(k);
            }
        }
        while (paramAccessibilityEvent.getEventType() != 8) {
            for (;;)
            {
                int i;
                int j;
                int k;
                super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
                do
                {
                    return;
                } while (j <= i);
                paramAccessibilityEvent.setRemovedCount(1);
                paramAccessibilityEvent.setAddedCount(0);
                paramAccessibilityEvent.setFromIndex(k - 1);
            }
        }
    }

    /* Error */
    public void setIsDigit(boolean paramBoolean1, boolean paramBoolean2)
    {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: getfield 17	com/olym/mjt/widget/DigitsEditText:isDigit	Ljava/lang/Boolean;
        //   6: ifnull +14 -> 20
        //   9: aload_0
        //   10: getfield 17	com/olym/mjt/widget/DigitsEditText:isDigit	Ljava/lang/Boolean;
        //   13: invokevirtual 65	java/lang/Boolean:booleanValue	()Z
        //   16: iload_1
        //   17: if_icmpeq +38 -> 55
        //   20: aload_0
        //   21: iload_1
        //   22: invokestatic 69	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
        //   25: putfield 17	com/olym/mjt/widget/DigitsEditText:isDigit	Ljava/lang/Boolean;
        //   28: iload_1
        //   29: ifeq +29 -> 58
        //   32: aload_0
        //   33: ldc 70
        //   35: invokevirtual 73	com/olym/mjt/widget/DigitsEditText:setRawInputType	(I)V
        //   38: aload_0
        //   39: iconst_0
        //   40: aload_0
        //   41: invokevirtual 77	com/olym/mjt/widget/DigitsEditText:getContext	()Landroid/content/Context;
        //   44: invokevirtual 83	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   47: ldc 84
        //   49: invokevirtual 90	android/content/res/Resources:getDimension	(I)F
        //   52: invokevirtual 94	com/olym/mjt/widget/DigitsEditText:setTextSize	(IF)V
        //   55: aload_0
        //   56: monitorexit
        //   57: return
        //   58: aload_0
        //   59: ldc 95
        //   61: invokevirtual 98	com/olym/mjt/widget/DigitsEditText:setInputType	(I)V
        //   64: aload_0
        //   65: iconst_2
        //   66: ldc 99
        //   68: invokevirtual 94	com/olym/mjt/widget/DigitsEditText:setTextSize	(IF)V
        //   71: goto -16 -> 55
        //   74: astore_3
        //   75: aload_0
        //   76: monitorexit
        //   77: aload_3
        //   78: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	79	0	this	DigitsEditText
        //   0	79	1	paramBoolean1	boolean
        //   0	79	2	paramBoolean2	boolean
        //   74	4	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	20	74	finally
        //   20	28	74	finally
        //   32	55	74	finally
        //   58	71	74	finally
    }
}
