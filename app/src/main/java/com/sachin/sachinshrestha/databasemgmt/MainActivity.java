package com.sachin.sachinshrestha.databasemgmt;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDataBase;
    EditText etName, etSurname, etMarks,etID;
    Button btnAddData,btnGetData,btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase = new DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etMarks = (EditText) findViewById(R.id.etMarks);
        etID = (EditText) findViewById(R.id.etID);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        addData();
        getData();
        dataUpdate();
        dataDelete();
    }

    public void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boolean isInserted = myDataBase.insertData(etName.getText().toString(),
                        etSurname.getText().toString(),etMarks.getText().toString());

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Data inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getData(){
        btnGetData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Cursor result = myDataBase.getAllData();

                if (result.getCount() == 0){
                    showMessage("ERROR", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("ID :" + result.getString(0) + "\n");
                    buffer.append("NAME :" + result.getString(1) + "\n");
                    buffer.append("SURNAME :" + result.getString(2) + "\n");
                    buffer.append("MARKS :" + result.getString(3) + "\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void dataUpdate(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDataBase.updateData(etID.getText().toString(),etName.getText().toString(),
                        etSurname.getText().toString(),etMarks.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data updated",Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Data not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void dataDelete(){
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer delRows = myDataBase.deleteData(etID.getText().toString());
                if (delRows != 0){
                    Toast.makeText(MainActivity.this, delRows + " rows deleted",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(MainActivity.this, "Data not deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}
