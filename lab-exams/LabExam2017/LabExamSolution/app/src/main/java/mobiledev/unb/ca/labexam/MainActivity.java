package mobiledev.unb.ca.labexam;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the RecyclerView and configure it
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // TODO Create an instance of LoadDataTask and execute it
        LoadDataTask loadDataTask = new LoadDataTask();
        loadDataTask.execute();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<DictionaryEntry> mDataset;

        public MyAdapter(ArrayList<DictionaryEntry> myDataset) {
            mDataset = myDataset;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView mTextView;

            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // TODO Get the DictionaryEntry at index position in mDataSet
            // You might need to declare this variable as final.
            final DictionaryEntry dictionaryEntry = mDataset.get(position);

            // TODO Set the TextView in the ViewHolder (holder) to be the
            // word in this DictionaryEntry
            holder.mTextView.setText(dictionaryEntry.getWord());

            // TODO Set the onClickListener for the TextView in the ViewHolder (holder) such
            // that when it is clicked, it creates an explicit intent to launch DetailActivity
            // HINT: You will need to put two extra pieces of information in this intent,
            // the word, and its definition
            holder.mTextView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,
                                    DetailActivity.class);
                            intent.putExtra(DetailActivity.WORD,
                                    dictionaryEntry.getWord());
                            intent.putExtra(DetailActivity.DEFINITION,
                                    dictionaryEntry.getDefinition());
                            startActivity(intent);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    // TODO Complete the TODOs for LoadDataTask below
    // Note: This class must use DataModel to load the data on a worker/background thread, and
    // then set the data for the RecyclerView in MainActivity on the UI thread. You
    // should only implement doInBackground and onPostExecute.
    public class LoadDataTask extends AsyncTask<Void, Void, ArrayList<DictionaryEntry>> {

        protected ArrayList<DictionaryEntry> doInBackground(Void... params) {
            // TODO Use DataModel to load the data from the JSON assets file
            // and return the ArrayList of DictionaryEntrys
            DataModel dm = new DataModel(MainActivity.this);
            return dm.getEntries();
        }

        protected void onPostExecute(ArrayList<DictionaryEntry> result) {
            // TODO Use result to set the adapter for the RecyclerView in MainActivity
            RecyclerView.Adapter adapter = new MyAdapter(result);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
