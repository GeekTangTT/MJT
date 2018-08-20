package com.olym.mjt.module.qrcode.qrcoderesult;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.module.qrcode.QRCodeScanActivity;
import org.xutils.view.annotation.ContentView;

@ContentView(2131427420)
public class QRCodeResultActivity
        extends BaseTopbarActivity<QRCodeResultPresenter>
        implements IQRCodeResultView
{
    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        IntentIntegrator localIntentIntegrator = new IntentIntegrator(this);
        localIntentIntegrator.setBarcodeImageEnabled(false);
        localIntentIntegrator.setBeepEnabled(false);
        localIntentIntegrator.setCaptureActivity(QRCodeScanActivity.class);
        localIntentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        localIntentIntegrator.initiateScan();
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        IntentResult localIntentResult = IntentIntegrator.parseActivityResult(paramInt1, paramInt2, paramIntent);
        if (localIntentResult != null)
        {
            if (localIntentResult.getContents() == null)
            {
                Toast.makeText(this, "������������", 1).show();
                return;
            }
            Toast.makeText(this, "������������:" + localIntentResult.getContents(), 1).show();
            return;
        }
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }

    protected void setPresenter()
    {
        this.presenter = new QRCodeResultPresenter(this);
    }
}
