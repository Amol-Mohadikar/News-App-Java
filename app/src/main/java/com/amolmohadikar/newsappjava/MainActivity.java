package com.amolmohadikar.newsappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amolmohadikar.newsappjava.Models.NewsApiResponse;
import com.amolmohadikar.newsappjava.Models.NewsHeadline;
import com.amolmohadikar.newsappjava.Models.SelectListener;
import com.amolmohadikar.newsappjava.Models.onFetchDataListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener {

    String items[] = {"au", "us", "in","ae","ar","at","be","bg","br","ca","ch","cn","co","cu","cz","de","za"};
    AutoCompleteTextView autoCompleteTextView;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog diaglog ;
    SearchView searchBar;

    ArrayAdapter<String> adapterItems;

    Button b1,b2,b3,b4,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn7);
        b7.setOnClickListener(this);

        searchBar = findViewById(R.id.search);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                diaglog.setTitle("Showing results for "+ query);
                diaglog.show();
                ApiResourceManager apiResourceManager = new ApiResourceManager(getApplicationContext());
                apiResourceManager.getNewsHeadline("in",listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                diaglog.setTitle("Showing results for "+ newText);
//                diaglog.show();
//                ApiResourceManager apiResourceManager = new ApiResourceManager(getApplicationContext());
//                apiResourceManager.getNewsHeadline("in",listener, "general", newText);
                return false;
            }
        });

        ApiResourceManager apiResourceManager = new ApiResourceManager(this);
        apiResourceManager.getNewsHeadline("in",listener, "general", null);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        diaglog = new ProgressDialog(this);
        diaglog.setTitle("Loading Latest News Articles...");
        diaglog.show();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

              apiResourceManager.getNewsHeadline(item ,listener, "general", null);
              diaglog.setTitle("Showing results for country "+ item);
              diaglog.show();

              Toast.makeText(getApplicationContext(),"country selected: "+ item,Toast.LENGTH_SHORT).show();
              }
               }
        );
    }
        private final onFetchDataListener<NewsApiResponse> listener = new onFetchDataListener<NewsApiResponse>() {
            @Override
            public void onFetchData(List<NewsHeadline> headline, String message) {
                if(headline.isEmpty()){
                    Toast.makeText(MainActivity.this, "No Results Found!!", Toast.LENGTH_SHORT).show();
                    diaglog.dismiss();
                }
                else {
                    DisplayNews(headline);
                    diaglog.dismiss();
                }


            }

            @Override
            public void getError(String message) {
                Toast.makeText(getApplicationContext(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                diaglog.dismiss();

            }
        };

        private void DisplayNews (List < NewsHeadline > headline) {
            recyclerView = findViewById(R.id.recycler_main);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new CustomAdapter(this, headline , this);
            recyclerView.setAdapter(adapter);
        }


    @Override
    public void OnNewsClicked(NewsHeadline headline) {
            startActivity(new Intent(MainActivity.this , NewsDetailActivity.class)
            .putExtra("data",headline));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        diaglog.setTitle("Showing results for category "+ category);
        diaglog.show();
        ApiResourceManager apiResourceManager = new ApiResourceManager(this);
        apiResourceManager.getNewsHeadline("in",listener, category, null);
    }
}
