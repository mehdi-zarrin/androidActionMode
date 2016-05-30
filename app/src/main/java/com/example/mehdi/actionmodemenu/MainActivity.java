package com.example.mehdi.actionmodemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;
    private int currentState;
    private ActionMode actionMode;

    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.iclude_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Action Mode Test");

        usernameText = (EditText) findViewById(R.id.activity_main_username);
        passwordText = (EditText) findViewById(R.id.activity_main_password);

        changeState(STATE_VIEWING);
    }

    private void changeState(int state) {

        if(state == currentState)
            return;

        currentState = state;
        if(state == STATE_VIEWING) {
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            Log.e("ActionMode" , "action mode " + state);
            if(actionMode != null) {
                Log.e("actionMode" , "action mode is not null and should be finish!");
                actionMode.finish();
                actionMode = null;
            }

        } else if(state == STATE_EDITING) {
            usernameText.setEnabled(true);
            passwordText.setEnabled(true);
            // open action mode
            actionMode = toolbar.startActionMode(new actionModelCallback());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_main_edit) {
            changeState(STATE_EDITING);
            return true;
        }

        return false;
    }

    private class actionModelCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.activity_main_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            if(itemId == R.id.action_mode_done) {
                Log.e("DONE" , "done clicked!");
                changeState(STATE_VIEWING);
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(currentState == STATE_EDITING) {
                changeState(STATE_VIEWING);
            }
        }
    }
}
