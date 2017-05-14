package com.colorhills.all.view.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.colorhills.all.R;
import com.colorhills.all.model.Image;
import com.colorhills.all.presenter.MainPresenter;
import com.colorhills.all.view.adapters.MainAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

@EActivity(R.layout.activity_learn)
public class LearnActivity extends AppCompatActivity implements MainPresenter.View {
    int wordNum = 2;

    @ViewById(R.id.main_gv)
    protected GridView gridView;

    @ViewById(R.id.main_progress)
    protected ProgressBar progressBar;

    private MainAdapter mainAdapter;
    private ArrayList<Image> imageList;
    private int count;
    private MainPresenter mainPresenter = new MainPresenter();
    private String keyword;
    private boolean state;

    @AfterViews
    protected void init() {
        imageList = new ArrayList<>();
        mainAdapter = new MainAdapter(this, imageList);
        count = 1;

        showWord();

        scrollObservable(gridView).subscribe(state -> {
            if (state) {
                count++;
                apiCall();
            }
        });
    }


    private int MAX_VAL = 100;
    public void getPrev(View view) {
        if (wordNum != 1) {
            wordNum = wordNum - 1;
        } else {
            wordNum = MAX_VAL;
        }
        showWord();
    }

    public void getNext(View view) {
        if (wordNum != MAX_VAL) {
            wordNum = wordNum + 1;
        } else {
            wordNum = 1;
        }
        showWord();
    }

    private void showWord() {
        try {
            SQLiteOpenHelper wordDatabaseHelper = new WordDatabaseHelper(this);
            SQLiteDatabase db = wordDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("WORDS",
                    new String[]{"NAME", "TRANSLATE"},
                    "_id = ?",
                    new String[]{Integer.toString(wordNum)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String wordText = cursor.getString(0);
                String tr_wordText = cursor.getString(1);

                TextView word = (TextView) findViewById(R.id.word);
                word.setText(wordText);

                TextView tr_word = (TextView) findViewById(R.id.tr_word);
                tr_word.setText(tr_wordText);

                gridView.setAdapter(mainAdapter);
                SearchFilter(tr_wordText);
                progressBar.setVisibility(View.VISIBLE);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "Database unvisible", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private Observable<Boolean> scrollObservable(AbsListView absListView) {
        PublishSubject publishSubject = PublishSubject.create();
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    publishSubject.onNext(state);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                state = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount) >= totalItemCount;
            }
        });
        return publishSubject;
    }
//
//    private Observable<String> searchObservable(SearchView searchView) {
//        PublishSubject publishSubject = PublishSubject.create();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                publishSubject.onNext(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return publishSubject;
//    }


    private void SearchFilter(String charText) {
        count = 1;
        imageList.clear();
        keyword = charText;

        if (keyword.length() != 0) {
            apiCall();
        }
    }

    private void apiCall() {
        progressBar.setVisibility(View.VISIBLE);
        mainPresenter.initialize(LearnActivity.this, keyword, count, imageList);
        mainPresenter.execute();
    }

    @Override
    public void success(ArrayList<Image> imageList) {
        this.imageList = imageList;
        mainAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void error(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
