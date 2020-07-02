package ntu.edu.vn.huythinh.review1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class FirstFragment extends Fragment implements View.OnClickListener {

    EditText edtName, edtDoB, edtAddr, edtNumber;
    RadioGroup rgTrans;
    ImageView imgDate;

    Spinner spnService;
    ArrayAdapter<String> serviceAdapter;
    String[] strServices;
    String service;

    String submit;
    public static Bundle bundle = new Bundle();
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtName = view.findViewById(R.id.edtName);
        edtDoB = view.findViewById(R.id.edtDoB);
        edtAddr = view.findViewById(R.id.edtAddr);
        edtNumber = view.findViewById(R.id.editNumber);
        rgTrans = view.findViewById(R.id.rgTrans);
        imgDate = view.findViewById(R.id.imageView);

        strServices = getResources().getStringArray(R.array.services);
        spnService =view.findViewById(R.id.spnService);
        serviceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strServices);
        spnService.setAdapter(serviceAdapter);
        addEvent();

        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder builder = new StringBuilder();
                builder.append("Chúc mừng khách hàng: ").append(edtName.getText()).append("\n")
                        .append("Sinh ngày: ").append(edtDoB.getText()).append("\n")
                        .append("Đã đăng ký thành công dịch vụ: ").append("\n").append(service).append("\n")
                        .append("Hình thức thanh toán: ").append(getTrans()).append("\n")
                        .append("Chúng tôi sẽ liên lạc với bạn qua SĐT:").append("\n")
                        .append(edtNumber.getText());
                submit = builder.toString();
                bundle.putString("submit",submit);
                SecondFragment fragment = new SecondFragment();
                fragment.setArguments(bundle);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    private void addEvent() {
        imgDate.setOnClickListener(this);
        spnService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service = serviceAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                service = "None";
            }
        });
    }

    private String getTrans() {
        switch (rgTrans.getCheckedRadioButtonId()){
            case (R.id.rbtnCash): return "Tiền mặt";
            case (R.id.rbtnBank): return "Ngân hàng";
            case (R.id.rbtnEwallet): return "Ví điện tử";
        }
        return "Chưa chọn";
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                StringBuilder builder = new StringBuilder();
                builder.append(year).append("-")
                        .append(month+1).append("-")
                        .append(dayOfMonth);

                edtDoB.setText(builder.toString());
            }
        };
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), listener,
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH+1),
                                        calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }
}
