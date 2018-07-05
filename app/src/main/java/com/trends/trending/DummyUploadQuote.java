package com.trends.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trends.trending.model.QuoteModel;
import com.trends.trending.utils.FirebaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by USER on 3/8/2018.
 */

public class DummyUploadQuote extends AppCompatActivity {

    private static final String QUOTENODE = "quotes";

    @BindView(R.id.author)
    EditText author;
    @BindView(R.id.quote)
    EditText quote;
    @BindView(R.id.uploadedBy)
    EditText uploadedBy;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_activity_quote);
        ButterKnife.bind(this);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new FirebaseHelper(firebaseDatabase, DummyUploadQuote.this);

    }

    public void uploadQuote(View view) {
        String name = uploadedBy.getText().toString();
        if (name.equals(""))
            name = "Admin";

        if (firebaseHelper.uploadQuote(new QuoteModel(author.getText().toString(), quote.getText().toString(), name), QUOTENODE))
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
}
