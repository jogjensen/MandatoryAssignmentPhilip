package com.example.mandatoryassignmentphilip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTwitterMessageActivity extends AppCompatActivity{
    private static final String LOG_TAG = "MYMESSAGES";
    private ProgressBar progressBar;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twittermessage);
        progressBar = findViewById(R.id.addTwitterMessageProgressbar);
        messageView = findViewById(R.id.addTwitterMessageTextView);
        GetAndShowAllMessages();
    }

    private void GetAndShowAllMessages(){
       // TwitterService twitterService = ApiUtils.getTwitterSerivce();

        messageView.setText("");
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://anbo-restmessages.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TwitterService service = retrofit.create(TwitterService.class);
        Call<List<TwitterMessage>> getAllTwitterMesasagesCall = service.getAllMessages();

        getAllTwitterMesasagesCall.enqueue(new Callback<List<TwitterMessage>>() {
            @Override
            public void onResponse(Call<List<TwitterMessage>> call, Response<List<TwitterMessage>> response) {
                Log.d(LOG_TAG, response.raw().toString());

                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    List<TwitterMessage> allTwitterMessages = response.body();
                    Log.d(LOG_TAG, allTwitterMessages.toString());
                    populateRecyclerView(allTwitterMessages);
                } else {
                    String message = "Problem" + response.code() + "" + response.message();
                    Log.d(LOG_TAG, message);
                    messageView.setText(message);

                }

            }

            @Override
            public void onFailure(Call<List<TwitterMessage>> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.e(LOG_TAG, t.getMessage());
            messageView.setText(t.getMessage());
            }
        });

    }

    private void populateRecyclerView(List<TwitterMessage> allTwitterMessages) {
        Log.d(LOG_TAG, "populaterecyclerview" + allTwitterMessages.toString());
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewSimpleAdapter<TwitterMessage> adapter = new RecyclerViewSimpleAdapter<>(allTwitterMessages);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position, item) -> {
            TwitterMessage twitterMessage = (TwitterMessage) item;
            Log.d(LOG_TAG, item.toString());

        });

    }

    /* public void addTwitterMessageButtonClicked(View view){
        EditText contentField = findViewById(R.id.addTwitterMessageContentEditText);
        EditText userField = findViewById(R.id.addTwitterMessageUserEditText);

        String content = contentField.getText().toString().trim();
        String user = userField.getText().toString().trim();
        Integer totalComments = contentField.getText().toString().trim();


        TwitterService twitterService = ApiUtils.getTwitterSerivce();

        //Save twitterMessage
        TwitterMessage twitterMessage = new TwitterMessage(content,user,totalComments);

        Call<TwitterMessage> saveTwitterMessageCall = twitterService.saveTwitterBody(twitterMessage);
        progressBar.setVisibility(view.VISIBLE);
        saveTwitterMessageCall.enqueue(new Callback<TwitterMessage>(){
            @Override
            public void onResponse(Call<TwitterMessage> call, Response<TwitterMessage> response){
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    TwitterMessage theNewTwitterMessage = response.body();
                    Log.d(LOG_TAG,theNewTwitterMessage.toString());
                    Toast.makeText(AddTwitterMessageActivity.this, "Message Added, id: " + theNewTwitterMessage.getId(), Toast.LENGTH_SHORT).show();
                }
                else {
                    String problem = "Problem " + response.code() + " " + response.message();
                    Log.e(LOG_TAG, problem);
                    messageView.setText("Problem");

                }
            }

            @Override
            public void onFailure(Call<TwitterMessage> call,Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                messageView.setText(t.getMessage());
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }
 */
}
