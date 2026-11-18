package it.pricewatch.android;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends Activity {
 private WebView web; private String server;
 @Override public void onCreate(Bundle state) { super.onCreate(state); server=getPreferences(MODE_PRIVATE).getString("server",""); if(server.isEmpty()) showSetup(); else showWeb(); }
 private void showSetup() {
  LinearLayout layout=new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL);
  int pad=(int)(24*getResources().getDisplayMetrics().density); layout.setPadding(pad,pad,pad,pad);
  TextView title=new TextView(this); title.setText("Amazon Price Watch\n\nInserisci l'indirizzo HTTPS del tuo server Price Watch."); title.setTextSize(20);
  EditText input=new EditText(this); input.setSingleLine(true); input.setHint("https://prezzi.tuodominio.it"); input.setInputType(17);
  Button connect=new Button(this); connect.setText("Connetti"); TextView error=new TextView(this);
  layout.addView(title); layout.addView(input); layout.addView(connect); layout.addView(error); setContentView(layout);
  connect.setOnClickListener(v -> { String url=input.getText().toString().trim(); Uri uri=Uri.parse(url);
   if(!"https".equalsIgnoreCase(uri.getScheme()) || uri.getHost()==null || uri.getUserInfo()!=null || uri.getPort()!=-1) { error.setText("Inserisci un URL HTTPS valido senza credenziali o porte personalizzate."); return; }
   server=uri.buildUpon().path("").query(null).fragment(null).build().toString();
   getPreferences(MODE_PRIVATE).edit().putString("server",server).apply(); showWeb();
  });
 }
 private void showWeb() {
  web=new WebView(this); web.getSettings().setJavaScriptEnabled(true); web.getSettings().setDomStorageEnabled(true);
  web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
  web.getSettings().setAllowFileAccess(false); web.getSettings().setAllowContentAccess(false);
  web.setWebViewClient(new WebViewClient() {
   @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
    Uri uri=request.getUrl();
    if("https".equalsIgnoreCase(uri.getScheme()) && uri.getHost()!=null && uri.getHost().equalsIgnoreCase(Uri.parse(server).getHost())) return false;
    try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch(Exception ignored) {} return true;
   }
  }); setContentView(web); web.loadUrl(server);
 }
 @Override public void onBackPressed() { if(web!=null && web.canGoBack()) web.goBack(); else super.onBackPressed(); }
}
