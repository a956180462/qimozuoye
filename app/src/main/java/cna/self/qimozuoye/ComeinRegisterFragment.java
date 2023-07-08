package cna.self.qimozuoye;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cna.self.qimozuoye.data.DataBaseHolder;
import cna.self.qimozuoye.data.UserDataBase.ComeIn;
import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentComeinRegisterBinding;

/**
 * 提前说明，ComeinRegisterFragment和 ExpensesRegisterFragment是几乎一样的
 * */

public class ComeinRegisterFragment extends Fragment {

    private FragmentComeinRegisterBinding binding;
    private boolean isInputValid = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentComeinRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // 点击输入框时显示日历
        showDateOnClick(binding.editTextDate);
        // 提交按键使能，和输入有效性有关
        binding.button.setEnabled(isInputValid);
        // 输入变化后查验有效性
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                CheckInputValid();
            }
        };
        binding.editTextNumber.addTextChangedListener(afterTextChangedListener);
        binding.editTextNumber.addTextChangedListener(afterTextChangedListener);
        // 数据提交事件
        binding.button.setOnClickListener(v -> {
            // 一般数据装载
            ComeIn comeIn = new ComeIn();
            comeIn.setAccount(LoggedInUser.getAccount());
            comeIn.setMoney(Float.parseFloat(binding.editTextNumber.getText().toString()));
            comeIn.setRemark(binding.editTextTextMultiLine.getText().toString());
            // 日期数据装载、数据插入
            try {
                String s = binding.editTextDate.getText().toString();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sf.parse(s);
                comeIn.setDate(Objects.requireNonNull(date));
                DataBaseHolder.getDb().userDAO().insertComeIn(comeIn);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "加入失败", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getContext(), "加入成功", Toast.LENGTH_SHORT).show();
            // 重置文本框
            binding.editTextNumber.setText("");
            binding.editTextDate.setText("");
            binding.editTextTextMultiLine.setText("");

        });
    }

    //点击打开日历，改变日期
    @SuppressLint("ClickableViewAccessibility")
    protected void showDateOnClick(final EditText editText){
        //点击事件传递，接触，点击打开
        editText.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                showDatePickDlg(editText);
                return true;
            }
            return false;
        });
        //改变日期
        editText.setOnFocusChangeListener((view, b) -> {
            if (b) {
                showDatePickDlg(editText);
            }

        });
    }

    //选择日期,改变文本
    protected void showDatePickDlg(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SetTextI18n")
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            // 格式化日期，否则会出现数据插入失败的问题
                            @SuppressLint("DefaultLocale")
                            String s = String.format("%04d-%02d-%02d",
                                                        year, (monthOfYear + 1), dayOfMonth);
                            editText.setText(s);
                            CheckInputValid();
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    // 输入合法性查验
    void CheckInputValid(){
        isInputValid = !(binding.editTextDate.getText().toString().isEmpty()||
                binding.editTextNumber.getText().toString().isEmpty());
        binding.button.setEnabled(isInputValid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}