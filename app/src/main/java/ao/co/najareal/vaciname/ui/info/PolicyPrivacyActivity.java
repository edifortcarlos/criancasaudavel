package ao.co.najareal.vaciname.ui.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import ao.co.najareal.vaciname.R;


public class PolicyPrivacyActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_privacy);
        setTitle(getString(R.string.policyPrivacy));

        webView = findViewById(R.id.wvPolicyPrivacy);
        webView.loadUrl("file:///android_asset/policyPrivacy.html");

    }
}
