package com.project2.banhangmypham.admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.ItemStaticOrderAdapter;
import com.project2.banhangmypham.admin.repository.StaticRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.StaticOrderViewModel;
import com.project2.banhangmypham.databinding.ActivityStaticOrderBinding;
import com.project2.banhangmypham.utils.Utils;

import java.util.Calendar;

public class StaticOrderActivity extends AppCompatActivity {

    public static final String TAG = "StaticOrderActivity";
    ActivityStaticOrderBinding binding ;
    StaticOrderViewModel staticOrderViewModel;
    DatePickerDialog datePickerDialogFrom ;
    DatePickerDialog datePickerDialogTo ;
    Long timeFrom = 0L;
    Long timeTo = 0L;
    ItemStaticOrderAdapter itemStaticOrderAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStaticOrderBinding.inflate(getLayoutInflater());
        staticOrderViewModel = new ViewModelProvider(this).get(StaticOrderViewModel.class);
        staticOrderViewModel.setiStaticRepository(new StaticRepositoryImpl());
        itemStaticOrderAdapter = new ItemStaticOrderAdapter();
        setContentView(binding.getRoot());
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.ivBack.setOnClickListener(view -> finish());
        binding.llTimeFrom.setOnClickListener(view ->{
            datePickerDialogFrom = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            binding.tvTimeFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear , dayOfMonth);
                            timeFrom = calendar.getTimeInMillis();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialogFrom.show();
        });
        binding.llTimeTo.setOnClickListener(view ->{
            datePickerDialogTo =  new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            binding.tvTimeTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear , dayOfMonth);
                            timeTo = calendar.getTimeInMillis();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialogTo.show();
        });

        staticOrderViewModel.getStaticProductMutableLiveData().observe(this, result -> {
            if (result != null) {
                Log.d(TAG, "onCreate: ====> size = " + result.getData().getOrdersList().size());
                itemStaticOrderAdapter.submitList(result.getData().getOrdersList());
                binding.tvTotalRevenue.setText(Utils.convertToMoneyVN(result.getData().getInCome()));
            }
        });

        staticOrderViewModel.getStaticAllProductByTime(0L,0L, true);
        binding.rcvOrder.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvOrder.hasFixedSize();
        binding.rcvOrder.setAdapter(itemStaticOrderAdapter);
        binding.btnSearch.setOnClickListener(view -> {
            staticOrderViewModel.getStaticAllProductByTime(timeFrom, timeTo, false);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        staticOrderViewModel.clear();
    }
}