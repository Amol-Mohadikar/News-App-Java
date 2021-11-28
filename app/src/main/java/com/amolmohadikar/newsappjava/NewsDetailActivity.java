package com.amolmohadikar.newsappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amolmohadikar.newsappjava.Models.NewsHeadline;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    NewsHeadline headline;
    TextView title , author , time , detail , content;
    ImageView img_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        time = findViewById(R.id.detail_time);
        author = findViewById(R.id.detail_author);
        title = findViewById(R.id.detail_title);
        detail = findViewById(R.id.detail_detail);
        content = findViewById(R.id.detail_content);
        img_detail = findViewById(R.id.img_detail);

        headline = (NewsHeadline) getIntent().getSerializableExtra("data");

        time.setText(headline.getPublishedAt());
        title.setText(headline.getTitle());
        content.setText(headline.getContent());
        author.setText(headline.getAuthor());
        detail.setText(headline.getDescription());

        Picasso.get().load(headline.getUrlToImage()).into(img_detail);


    }
}