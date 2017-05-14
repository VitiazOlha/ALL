package com.colorhills.all.presenter;

import com.colorhills.all.model.ChannelRoot;
import com.colorhills.all.model.ChannelRootService;
import com.colorhills.all.model.Image;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

public class MainPresenter implements BasePresenter {

    private final String BASE_URL = "";
    private final String API_KEY = "";
    private final String JSON = "json";
    private final int NUM = 20;

    private String keyword;
    private int count;
    private View myView;
    private ArrayList<Image> imageList;
    private int page = 0;

    public void initialize(MainPresenter.View myView, String keyword, int count, ArrayList<Image> imageList) {
        this.myView = myView;
        this.keyword = keyword;
        this.count = count;
        this.imageList = imageList;
    }

    @Override
    public void execute() {
        Observable.create((final Subscriber<? super Result> subscriber) -> {
            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                Call<ChannelRoot> call = retrofit.create(ChannelRootService.class).getChannel(API_KEY, keyword, JSON, count, NUM);
                call.enqueue(new Callback<ChannelRoot>() {
                    @Override
                    public void onResponse(Call<ChannelRoot> call, Response<ChannelRoot> response) {
                        if (response.isSuccessful()) {
                            Observable.from(response.body().getChannel().getItem()).subscribe(channelItem -> imageList.add(new Image(page++, channelItem)));
                            //subscriber.onNext(new Result("SUCCESS", true));
                        } else {
                            //subscriber.onNext(new Result("", false));
                        }
                    }

                    @Override
                    public void onFailure(Call<ChannelRoot> call, Throwable t) {
                        //subscriber.onNext(new Result("", false));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onNext(new Result("", false));
            }
        }).subscribe(result -> {
            if (result.success) {
                myView.success(imageList);
            } else {
                myView.error(result.error);
            }

        });

    }

    public interface View {
        void success(ArrayList<Image> imageList);

        void error(String message);
    }

    public class Result {
        private String error;
        private boolean success;

        public Result(String error, boolean success) {
            this.error = error;
            this.success = success;
        }
    }
}
