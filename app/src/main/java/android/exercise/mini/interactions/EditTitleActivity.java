package android.exercise.mini.interactions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {

  // TODO:
  //  you can add fields to this class. those fields will be accessibly inside any method
  //  (like `onCreate()` and `onBackPressed()` methods)
  // for any field, make sure to set it's initial value. You CAN'T write a custom constructor
  // for example, you can add this field:
  // `private boolean isEditing = false;`
  // in onCreate() set `this.isEditing` to `true` once the user starts editing, set to `false` once done editing
  // in onBackPressed() check `if(this.isEditing)` to understand what to do
  private boolean isEditing = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_title);

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("Page title here");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {

      /*
      TODO:
      1. animate out the "start edit" FAB
      2. animate in the "done edit" FAB
      3. hide the static title (text-view)
      4. show the editable title (edit-text)
      5. make sure the editable title's text is the same as the static one
      6. optional (HARD!) make the keyboard to open with the edit-text focused,
          so the user can start typing without the need another click on the edit-text

      to complete (1.) & (2.), start by just changing visibility. only add animations after everything else is ready
       */

      fabStartEdit.
              animate()
              .alpha(0f)
              .setDuration(300L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabStartEdit.setVisibility(View.INVISIBLE);
                }
              })
              .start();
//      fabEditDone.setAlpha(0f);
      fabEditDone.
              animate()
              .alpha(1f)
              .setDuration(300L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabEditDone.setVisibility(View.VISIBLE);
                }
              })
              .start();
      textViewTitle.
              setVisibility(View.INVISIBLE);
      editTextTitle.
              setVisibility(View.VISIBLE);
      this.isEditing = true;

    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {

      /*
      TODO:
      1. animate out the "done edit" FAB
      2. animate in the "start edit" FAB
      3. take the text from the user's input in the edit-text and put it inside the static text-view
      4. show the static title (text-view)
      5. hide the editable title (edit-text)
      6. make sure that the keyboard is closed

      to complete (1.) & (2.), start by just changing visibility. only add animations after everything else is ready
       */
      this.isEditing = false;
//      fabEditDone.setVisibility(View.INVISIBLE);
      fabEditDone.
              animate()
              .alpha(0f)
              .setDuration(300L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabEditDone.setVisibility(View.INVISIBLE);
                }
              })
              .start();

      fabStartEdit.setVisibility(View.VISIBLE);
      fabStartEdit.
              animate()
              .alpha(1f)
              .setDuration(300L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabStartEdit.setVisibility(View.VISIBLE);
                }
              })
              .start();
      textViewTitle.
              setText(editTextTitle.getText());

      textViewTitle.
              setVisibility(View.VISIBLE);
      editTextTitle.setVisibility(View.INVISIBLE);
      InputMethodManager systemService = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      systemService.hideSoftInputFromWindow(textViewTitle.getWindowToken(),0);

    });
  }

  @Override
  public void onBackPressed() {
    // BACK button was clicked
    /*
    TODO:
    if user is now editing, tap on BACK will revert the edit. do the following:
    1. hide the edit-text
    2. show the static text-view with previous text (discard user's input)
    3. animate out the "done-edit" FAB
    4. animate in the "start-edit" FAB

    else, the user isn't editing. continue normal BACK tap behavior to exit the screen.
    call `super.onBackPressed()`

    notice:
    to work with views, you will need to find them first.
    to find views call `findViewById()` in a same way like in `onCreate()`
     */

    if(this.isEditing){
      EditText editView = findViewById(R.id.editTextPageTitle);
      TextView textView = findViewById(R.id.textViewPageTitle);
      FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
      FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
      editView.setVisibility(View.INVISIBLE);
      textView.setVisibility(View.VISIBLE);
      editView.setText(textView.getText());
        fabEditDone.
                animate()
                .alpha(0f)
                .setDuration(300L)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fabEditDone.setVisibility(View.INVISIBLE);
                    }
                })
                .start();

        fabStartEdit.
                animate()
                .alpha(1f)
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fabStartEdit.setVisibility(View.VISIBLE);
                    }
                })
                .start();
      this.isEditing = false;
    }
    else{
      super.onBackPressed();
    }
  }

}