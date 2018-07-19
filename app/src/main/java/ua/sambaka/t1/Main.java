package ua.sambaka.t1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static ua.sambaka.t1.R.id.cancel_action;
import static ua.sambaka.t1.R.id.contacts;

public class Main extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "my";
    public static final int ADDDIALOG = 1;
    public static final int EDITDIALOG = 2;
    private static final int NULL_ADD = 3;
    private static final int CM_EDIT_ID = 2;
    private static final int CM_DELETE_ID = 3;
    private static final int CM_SEND_VIBER = 4;
    Button btnAdd;
    LinearLayout addDialog;
    LinearLayout editDialog;

    EditText addName;
    EditText addPhone;
    EditText editName;
    EditText editPhone;

    List<Contact> list;
    CustomListAdapter mAdapter;

    int editposition = -1;

    //слушатель диалога добавления контакта
    private DialogInterface.OnClickListener myClickListenerAdd = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    break;
            }
        }
    };
    private DialogInterface.OnClickListener myClickListenerEdit = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    changeData();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //находим кнопку добавления контактов и устанавливаем обработчик нажатия
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        List<Contact> image_details = getListData();
        final ListView listView = (ListView) findViewById(contacts);
        mAdapter = new CustomListAdapter(this, image_details);
        listView.setAdapter(mAdapter);

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Contact contact = (Contact) o;
                Toast.makeText(Main.this, "Selected :" + " " + contact, Toast.LENGTH_LONG).show();
            }
        });
        registerForContextMenu(listView);

    }

    private List<Contact> getListData() {
        list = new ArrayList<Contact>();
        fillData(list);
        return list;
    }

    private void fillData(List<Contact> list) {
        list.add(new Contact("Alexs", "s1", 98000000));
        list.add(new Contact("Serg", "s2", 32000000));
        list.add(new Contact("Mary", "s3", 142000000));
        list.add(new Contact("Bob", "s4", 152000000));
        list.add(new Contact("Djo", "s5", 142008000));
        list.add(new Contact("Mark", "s6", 142000400));
        list.add(new Contact("Den", "s7", 142003000));
        list.add(new Contact("Don", "s8", 142050000));
        list.add(new Contact("Lysu", "s4", 142002000));
        list.add(new Contact("Alexs", "s1", 98000000));
        list.add(new Contact("Serg", "s2", 32000000));
        list.add(new Contact("Mary", "s3", 142000000));
        list.add(new Contact("Bob", "s5", 152000000));
        list.add(new Contact("Djo", "s4", 142008000));
        list.add(new Contact("Mark", "s1", 142000400));
        list.add(new Contact("Den", "s7", 142003000));
        list.add(new Contact("Don", "s8", 142050000));
        list.add(new Contact("Lysu", "s6", 142002000));
        list.add(new Contact("Alexs", "s1", 98000000));
        list.add(new Contact("Serg", "s2", 32000000));
        list.add(new Contact("Mary", "s2", 142000000));
        list.add(new Contact("Bob", "s5", 152000000));
        list.add(new Contact("Djo", "s6", 142008000));
        list.add(new Contact("Mark", "s2", 142000400));
        list.add(new Contact("Den", "s7", 142003000));
        list.add(new Contact("Don", "s8", 142050000));
        list.add(new Contact("Lysu", "s5", 142002000));
    }

    @Override
    public void onClick(View v) {
        showDialog(ADDDIALOG);
    }

    @Override
    public Dialog onCreateDialog(int id) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            case ADDDIALOG:
                //создаем диалог, добавлям кнопки: да, нет
                adb.setTitle("Добавить контакт");
                adb.setCancelable(false);
                adb.setPositiveButton(R.string.yes, myClickListenerAdd);
                adb.setNeutralButton(R.string.chancel, myClickListenerAdd);
                //раздуваем диалог и находим эдит тексты
                addDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogadd, null);
                addName = (EditText) addDialog.findViewById(R.id.addName);
                addPhone = (EditText) addDialog.findViewById(R.id.addPhone);
                //показываем диалог
                adb.setView(addDialog);
                break;
            case EDITDIALOG:
                adb.setTitle("Изменить");
                adb.setCancelable(false);
                adb.setPositiveButton(R.string.yes, myClickListenerEdit);
                adb.setNeutralButton(R.string.chancel, myClickListenerEdit);
                editDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_edit, null);
                adb.setView(editDialog);
                break;
        }
        return adb.create();
    }

    //подготовительная работа для отображения диалогов
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (editposition >= 0) {
            editName = (EditText) editDialog.findViewById(R.id.editName);
            editPhone = (EditText) editDialog.findViewById(R.id.editPhone);
            editName.setText(list.get(editposition).getName());
            editPhone.setText(list.get(editposition).getStringPhone());
        }
    }

    //добавление нового контакта
    void saveData() {
        if ((addName.getText().length() == 0) || (addPhone.getText().length() == 0)) {
            // showDialog(NULL_ADD);
            return;
        }
        Contact temp = new Contact(addName.getText().toString(), "s1", Integer.valueOf(addPhone.getText().toString()));
        list.add(temp);
        mAdapter.notifyDataSetChanged();
    }

    void changeData() {
        if ((editName.getText().length() == 0) || (editPhone.getText().length() == 0)) {
            // showDialog(NULL_ADD);
            return;
        }
        String name = editName.getText().toString();
        int phone = Integer.valueOf(editPhone.getText().toString());
        Contact tempContact = new Contact(name, "s1", phone);
        list.set(editposition, tempContact);
    }

    //Создание контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        editposition = v.getId();

        menu.add(0, CM_EDIT_ID, 0, "Изменить запись");
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
        menu.add(0, CM_SEND_VIBER, 0, "Отправить на вайбер");
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case CM_DELETE_ID:
            // получаем инфу о пункте списка удаляем из коллекции, используя позицию пункта в списке
            list.remove(acmi.position);
            // уведомляем, что данные изменились
            mAdapter.notifyDataSetChanged();
            break;
            case CM_EDIT_ID:
            editposition = acmi.position;
            // получаем инфу о пункте списка
            showDialog(EDITDIALOG);
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
