package pl.servx.servx;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import pl.servx.servx.Model.vehicle;

public class AddCarForm extends AppCompatActivity implements OnItemSelectedListener{
    Spinner spCarModel,spCarMake,spCarYear;
    Button ConfirmCar ;
    TextInputEditText textCarPlate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        spCarMake = (Spinner)findViewById(R.id.spCarMake);
        spCarModel = (Spinner)findViewById(R.id.spCarModel);
        spCarYear = (Spinner)findViewById(R.id.spCarYear);
        ConfirmCar = (Button)findViewById(R.id.ConfirmCar);
        textCarPlate = (TextInputEditText)findViewById(R.id.textCarPlate);

        spCarMake.setOnItemSelectedListener(this);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("Select Year");
        for (int i = 1996; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, years);
        adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spCarYear.setAdapter(adaptador);


        String[] makes = getResources().getStringArray(R.array.Make);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, makes);
        adaptador1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spCarMake.setAdapter(adaptador1);

        ConfirmCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicle newcar = new vehicle();
                newcar.vmake = String.valueOf(spCarMake.getSelectedItem());
                newcar.vmodel = String.valueOf(spCarModel.getSelectedItem());
                newcar.vyear = String.valueOf(spCarYear.getSelectedItem());
                table_user.child("03361424139").child("vehicle").child(textCarPlate.getText().toString()).setValue(newcar);




            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String make = String.valueOf(spCarMake.getSelectedItem());

        if(!make.equals("Select Make")){

            int resourceId = 0;
            try {
                resourceId = R.array.class.getField(make).getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String[] model = getResources().getStringArray(resourceId);

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                    R.layout.simple_spinner_item, model);
            adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            adaptador.notifyDataSetChanged();

            spCarModel.setAdapter(adaptador);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}