package com.trends.trending.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trends.trending.R;
import com.trends.trending.model.QuoteModel;
import com.trends.trending.utils.FirebaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USER on 5/26/2018.
 */

public class UserUploadQuote extends AppCompatActivity {

    public static final String QUOTE_REVIEW = "quotereview";

    @BindView(R.id.user_quote)
    EditText mUserQuote;
    @BindView(R.id.quote_author)
    EditText mQuoteAuthor;
    @BindView(R.id.quote_upload)
    EditText mQuoteUpload;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upload_quote);
        ButterKnife.bind(this);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new FirebaseHelper(firebaseDatabase, this);
    }

    @OnClick(R.id.btn_upload)
    public void onViewClicked() {
        if(firebaseHelper.uploadQuote(new QuoteModel(mQuoteAuthor.getText().toString(), mUserQuote.getText().toString(), mQuoteUpload.getText().toString()), QUOTE_REVIEW))
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show();
    }
}
