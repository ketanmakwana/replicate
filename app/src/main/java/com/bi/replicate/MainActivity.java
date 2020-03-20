package com.bi.replicate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bi.replicate.interfaces.InternetInterface;
import com.bi.replicate.internet.ApiClient;
import com.bi.replicate.model.ApiRequestModel;
import com.bi.replicate.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.selectStaff)
    Spinner mSpinner;
    List<Result> listStaffName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listStaffName = new ArrayList<>();


        mSpinner.setAdapter(new StaffAdapter());
        loadData();

    }


    private void loadData() {
        InternetInterface mApiInterface = ApiClient.getClient().create(InternetInterface.class);
        Call<ApiRequestModel> mRequestBody = mApiInterface.request();
        mRequestBody.enqueue(new Callback<ApiRequestModel>() {
            @Override
            public void onResponse(Call<ApiRequestModel> call, Response<ApiRequestModel> response) {
                listStaffName = response.body().getResults();
            }

            @Override
            public void onFailure(Call<ApiRequestModel> call, Throwable t) {

            }
        });
    }


    private class StaffAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listStaffName.size();
        }

        @Override
        public Object getItem(int position) {
            return listStaffName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            assert mInflater != null;
            View view = mInflater.inflate(R.layout.list_item, parent, false);
            TextView txtName = view.findViewById(R.id.textName);
            txtName.setText(listStaffName.get(position).getName());
            TextView txtSelect = view.findViewById(R.id.labelSelect);
            if (position == 0) {
                txtSelect.setVisibility(View.VISIBLE);
            } else {
                txtSelect.setVisibility(View.GONE);
            }
            return view;
        }
    }
}
