package com.pulsa.pendaftaranLogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pulsa.MainActivity;
import com.pulsa.R;
import com.pulsa.koneksi.service_xmpp;
import com.pulsa.utils.SessionManager;

import static com.pulsa.setGet.DataPut.AKSI_REGISTRATION_COMPLETE;

/**
 * Created by IT-Staff on 29/08/2016.
 */
public class Login extends AppCompatActivity {
    private UserLoginTask userLoginTask = null;
    private View loginFormView;
    private View progressView;
    private EditText emailTextView;
    private EditText passwordTextView;
    private Button signUpTextView;
    private TextView Tunggu;
    public static String emailStr;
    public static String passwordStr;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor();
        setContentView(R.layout.login_activity);
        LocalBroadcastManager.getInstance(this).registerReceiver(mYourBroadcastReceiver, new IntentFilter(AKSI_REGISTRATION_COMPLETE));

        try {
            stopService(new Intent(getApplicationContext(), service_xmpp.class));
        } catch (Exception e) {

        }
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.email_sign_in_button);
        TextView lupaPassword = findViewById(R.id.lupaPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* dialogLupaPassword dialog = new dialogLupaPassword(Login.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();*/
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.loading);
        Tunggu = findViewById(R.id.tunggu);
        //adding underline and link to signup textview
        signUpTextView = findViewById(R.id.signUp);
        signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Linkify.addLinks(signUpTextView, Linkify.ALL);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {
        if (userLoginTask != null) {
            return;
        }

        emailTextView.setError(null);
        passwordTextView.setError(null);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        //View focusView = null;

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            //emailTextView.setError(getString(R.string.field_required));
            Toast.makeText(getApplicationContext(), "Masukkan Username & Password", Toast.LENGTH_SHORT).show();
            //focusView = emailTextView;
            cancelLogin = true;
        } else {
            if (TextUtils.isEmpty(email)) {
                //emailTextView.setError(getString(R.string.field_required));
                Toast.makeText(getApplicationContext(), "Masukkan Username", Toast.LENGTH_SHORT).show();
                //focusView = emailTextView;
                cancelLogin = true;
            }

            if (TextUtils.isEmpty(password)) {
                //passwordTextView.setError(getString(R.string.invalid_password));
                //focusView = passwordTextView;
                Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
                cancelLogin = true;
            }
        }

        if (cancelLogin) {
            // error in login
            // focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            showProgress(true);
            emailStr = email;
            passwordStr = password;
            userLoginTask = new UserLoginTask(email, password);
            userLoginTask.execute((Void) null);
        }
    }

    /**
     * Async Login Task to authenticate
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        UserLoginTask(String email, String password) {
            emailStr = email;
            passwordStr = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // this is where you should write your authentication code
            // or call external service
            // following try-catch just simulates network access
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userLoginTask = null;
            //stop the progress spinner
            sessionManager = new SessionManager(getApplicationContext());
            sessionManager.DESTROY();
            launchHomeScreen();
        }

        @Override
        protected void onCancelled() {
            userLoginTask = null;
            showProgress(false);
        }
    }

    private final BroadcastReceiver mYourBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction() != null) {
                    if (intent.getAction().equals(AKSI_REGISTRATION_COMPLETE)) {
                        String pesan = intent.getStringExtra("isi");
                        showProgress(false);
                        if (pesan.equalsIgnoreCase("ok")) {
                            sessionManager.createLoginSession(emailStr, passwordStr);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            try {
                                stopService(new Intent(getApplicationContext(), service_xmpp.class));
                            } catch (Exception e) {

                            }
                            sessionManager.DESTROY();
                            dialog(pesan);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("login succes", e.getMessage());
            }
        }
    };

    public void dialog(String pesan) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(pesan);
        alertDialogBuilder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mYourBroadcastReceiver);
        try {
            showProgress(false);
        } catch (Exception e) {
            Log.e("Error", "showProgress false");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void launchHomeScreen() {
        startService(new Intent(getBaseContext(), service_xmpp.class));
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            Tunggu.setVisibility(show ? View.VISIBLE : View.GONE);

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            Tunggu.setVisibility(show ? View.VISIBLE : View.GONE);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
