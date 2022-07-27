package com.example.firstquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Build;

import com.example.firstquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FirstQuiz.db";
    private static final int DATABASE_VERSION = 10;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;


    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                //QuestionsTable.COLUMN_PICTURE + " INTEGER, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTs " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Military");
        addCategory(c1);
        Category c2 = new Category("General");
        addCategory(c2);
        Category c3 = new Category("New World");
        addCategory(c3);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {

        Question q1 = new Question(" Military, Easy: When did the USA civil war start?", " Have the brakes checked immediately", "Check the brake-fluid level",
                "Check the footbrake free play", "Check that the handbrake is released", 1, Question.DIFFICULTY_EASY,
                Category.MILITARY);
        addQuestion(q1);
        Question q2 = new Question(" General, Easy: Who was the first USA president", " Have the brakes checked immediately", "Check the brake-fluid level",
                "Check the footbrake free play", "Check that the handbrake is released", 1, Question.DIFFICULTY_EASY,
                Category.GENERAL);
        addQuestion(q2);
        Question q3 = new Question(" New World, Easy: What was the first pligrim settlement called?", " Have the brakes checked immediately", "Check the brake-fluid level",
                "Check the footbrake free play", "Check that the handbrake is released", 1, Question.DIFFICULTY_EASY,
                Category.NEWWORLD);
        addQuestion(q3);
        Question q4 = new Question(" Military, Medium: When did the USA civil war start?", " Have the brakes checked immediately", "Check the brake-fluid level",
                "Check the footbrake free play", "Check that the handbrake is released", 1, Question.DIFFICULTY_MEDIUM,
                Category.MILITARY);
        addQuestion(q4);
        Question q5 = new Question(" Military, Hard: When did the USA civil war start?", " Have the brakes checked immediately", "Check the brake-fluid level",
                "Check the footbrake free play", "Check that the handbrake is released", 1, Question.DIFFICULTY_HARD,
                Category.MILITARY);
        addQuestion(q5);

        /*Question q2 = new Question("You're the first to arrive at the scene of a crash. What should you do?", "Leave as soon as another motorist arrives", "Flag down other motorists to help you", "Drag all casualties away from the vehicles", "Call the emergency services promptly", 4);
        addQuestion(q2);
        Question q3 = new Question("You're driving on an open road in dry weather. What should the distance be between you and the vehicle in front?", "A two-second time gap", "One car length", "Two metres (6 feet 6 inches)", "Two car lengths", 1);
        addQuestion(q3);
        Question q4 = new Question("You're unsure what a slow-moving motorcyclist ahead of you is going to do. What should you do?", "Pass on the left", "Pass on the right", "Stay behind", "Move closer", 3);
        addQuestion(q4);
        Question q5 = new Question("Why should you allow extra room when overtaking a motorcyclist on a windy day?", "The rider may turn off suddenly to get out of the wind", "The rider may be blown across in front of you", "The rider may stop suddenly", "The rider may be travelling faster than normal", 2);
        addQuestion(q5);
        Question q6 = new Question("What should you do when passing sheep on a road?", "Briefly sound your horn", "Go very slowly", "Pass quickly but quietly", "Herd them to the side of the road", 2);
        addQuestion(q6);
        Question q7 = new Question("What does the law require you to keep in good condition?", "Gears", "Transmission", "Door locks", "Seat belts", 4);
        addQuestion(q7);
        Question q8 = new Question("You keep well back while waiting to overtake a large vehicle. What should you do if a car moves into the gap?", "Sound your horn", "Drop back further", "Flash your headlights", "Start to overtake", 4);
        addQuestion(q8);
        Question q9 = new Question("A driver pulls out of a side road in front of you, causing you to brake hard. What should you do?", "Ignore the error and stay calm", "Flash your lights to show your annoyance", "Sound your horn to show your annoyance", "Overtake as soon as possible", 1);
        addQuestion(q9);
        Question q10 = new Question("On what type of road surface may anti-lock brakes not work effectively?", "Dry", "Loose", "Firm", "Smooth", 2);
        addQuestion(q10);
        Question q11 = new Question("Your vehicle has a puncture on a motorway. What should you do?", "Drive slowly to the next service area to get assistance", "Pull up on the hard shoulder. Change the wheel as quickly as possible", "Pull up on the hard shoulder. Use the emergency phone to get assistance", "Switch on your hazard warning lights. Stop in your lane", 3);
        addQuestion(q11);
        Question q12 = new Question("You're turning left into a side road. What hazard should you be especially aware of?", "One-way street", "Pedestrians", "Traffic congestion", "Parked vehicles", 2);
        addQuestion(q12);
        Question q13 = new Question("When will you feel the effects of engine braking?", "When you only use the handbrake", "When you're in neutral", "When you change to a lower gear", "When you change to a higher gear", 3);
        addQuestion(q13);
        Question q14 = new Question("You're on a motorway. There's a contraflow system ahead. What would you expect to find?", "Temporary traffic lights", "Lower speed limits", "Wider lanes than normal", "Speed humps", 2);
        addQuestion(q14);
        Question q15 = new Question("What information is found on a vehicle registration document?", "The registered keeper", "The type of insurance cover", "The service history details", "The date of the MOT", 1);
        addQuestion(q15);
        Question q16 = new Question("What should you do when moving off from behind a parked car?", "Give a signal after moving off", "Check both interior and exterior mirrors", "Look around after moving off", "Use the exterior mirrors only", 2);
        addQuestion(q16);
        Question q17 = new Question("You're approaching a red traffic light. What will the signal show next?", "Red and amber", "Green alone", "Amber alone", "Green and amber", 1);
        addQuestion(q17);
        Question q18 = new Question("You think the driver of the vehicle in front has forgotten to cancel their right indicator. What should you do?", "Flash your lights to alert the driver", "Sound your horn before overtaking", "Overtake on the left if there's room", "Stay behind and don't overtake", 4);
        addQuestion(q18);
        Question q19 = new Question("What should you do when overtaking a motorcyclist in strong winds?", "Pass closely", "Pass wide", "Pass very slowly", "Pass immediately", 2);
        addQuestion(q19);
        Question q20 = new Question("You've just passed your practical test. You don't hold a full licence in another category. Within two years you get six penalty points on your licence. What will you have to do?", "Retake only your theory test", "Retake only your practical test", "Retake your theory and practical tests", "Reapply for your full licence immediately", 3);
        addQuestion(q20);
        Question q21 = new Question("What's the main benefit of driving a four-wheel-drive vehicle?", "Improved grip on the road", "Shorter stopping distances", "Lower fuel consumption", "Improved passenger comfort", 1);
        addQuestion(q21);
        Question q22 = new Question("What's the right-hand lane used for on a three-lane motorway?", "Emergency vehicles only", "Vehicles towing trailers", "Overtaking", "Coaches only", 3);
        addQuestion(q22);
        Question q23 = new Question("You're following a slower-moving vehicle on a narrow country road. There's a junction just ahead on the right. What should you do?", "Overtake after checking your mirrors and signalling", "Accelerate quickly to pass before the junction", "Only consider overtaking when you're past the junction", "Slow down and prepare to overtake on the left", 3);
        addQuestion(q23);
        Question q24 = new Question("What colour are the reflective studs between a motorway and its slip road?", "Amber", "Green", "White", "Red", 2);
        addQuestion(q24);
        Question q25 = new Question("You're driving on the motorway in windy conditions. What should you do as you pass a high-sided vehicle?", "Increase your speed", "Drive alongside very closely", "Be wary of a sudden gust", "Expect normal conditions", 3);
        addQuestion(q25);*/



    }


    private void addQuestion(Question question) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        //cv.put(QuestionsTable.COLUMN_PICTURE, question.getPicture());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);


    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);


        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {

            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                //question.setPicture(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_PICTURE)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();
        return questionList;

    }

    public ArrayList<Question> getQuestions(int categoryID,String difficulty) {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[] {String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {

            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                //question.setPicture(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_PICTURE)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();
        return questionList;

    }



}
