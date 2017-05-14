package com.colorhills.all.view.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class WordDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "words";
    private static final int DB_VERSION = 1;


    WordDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String words =
                "convenient-удобный\n" +
                "craving-страстное желание\n" +
                "creative-творческий \n" +
                "disappointment-разочарование\n" +
                "elaborate-разрабатывать\n" +
                "eligible-имеющий п-раво\n" +
                "embarrassment-затруднение \n" +
                "emergency-критическое положение \n" +
                "entourage-окружение \n" +
                "evidence-доказательство\n" +
                "extinction-вымирание\n" +
                "famine-голод\n" +
                "flood-потоп\n" +
                "generosity-щедрость\n" +
                "gluttony-обжорство\n" +
                "hiccup-икота\n" +
                "honesty-честность\n" +
                "household-быт\n" +
                "humanity-человечество\n" +
                "humiliate-унижать\n" +
                "interpret-толковать\n" +
                "investigate-исследовать\n" +
                "justice-справедливость \n" +
                "kindness-доброта\n" +
                "knowledge-знание\n" +
                "landlord-землевладелец\n" +
                "liberty-свобода\n" +
                "maintain-поддерживать\n" +
                "mature-зрелый\n" +
                "mirror-зеркало\n" +
                "naughty-непослушный\n" +
                "patience-терпение\n" +
                "persuade-убеждать\n" +
                "petrol-бензин\n" +
                "pleasure-удовольствие \n" +
                "prejudice-предубеждение\n" +
                "prescription-рекомендация\n" +
                "profit-выгода\n" +
                "promotion-продвижение\n" +
                "prosecutor-прокурор\n" +
                "quarrel-ссора \n" +
                "rapport-хорошие отношения \n" +
                "referee-судья\n" +
                "reference book-справочник\n" +
                "rehearsal-репетиция\n" +
                "remarkable-значительный\n" +
                "resentment-негодование\n" +
                "ruthless-безжалостный\n" +
                "satchel-сумка\n" +
                "software-программное обеспечение\n" +
                "spokesman-представитель\n" +
                "squeeze-сжимать\n" +
                "stationary-неизменный\n" +
                "sufficient-достаточный\n" +
                "superstition-суеверие\n" +
                "surgeon-хирург\n" +
                "surveyor-землемер\n" +
                "suspect-подозревать\n" +
                "vacancy-свободное место\n" +
                "vain-тщеславный\n" +
                "valuable-ценный\n" +
                "walkie_talkie-портативная рация\n" +
                "whistle-свистеть\n" +
                "withdraw-извлекать"+
                        "amateur-любитель \n" +
                        "ambassador-посол\n" +
                        "approve-одобрять\n" +
                        "apron-фартук\n" +
                        "arrange-организовывать\n" +
                        "arrogant-высокомерный\n" +
                        "boast-хвастаться\n" +
                        "bodyguard-телохранитель\n" +
                        "abolish-отменять\n"+
                        "celebrity-знаменитость\n" +
                "civilian-штатский\n" +
                "collapse-разрушаться\n" +
                "commercial-рекламный ролик\n" +
                "commission-комиссия\n" +
                "confidence-уверенность\n" +
                "contemptuous-презрительный\n" +
                        "correspondence-переписка\n" +
                        "courage-смелость\n" +
                        "crawl-ползать\n" +
                        "dedication-верность\n" +
                        "deliver-доставлять\n" +
                        "depth-глубина\n" +
                        "descend-спускаться \n" +
                        "destination-назначение\n" +
                        "deteriorate-ухудшать \n" +
                "contribute-делать пожертвования\n"+
                        "dismiss-отпускать\n" +
                        "dissolve-разрушать\n" +
                        "distribute-распространять\n" +
                        "district-район\n" +
                        "addiction-зависимость\n" +
                        "agriculture-сельское хозяйство\n" +
                        "ambulance-скорая помощь\n" +
                        "anger-злость\n" +
                        "canteen-столовая \n" +
                        "childhood-детство\n" ;


        db.execSQL("CREATE TABLE WORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TRANSLATE TEXT, PIC_ID);");
        String[] strs = words.split("\n");
        for (int i = 0; i < strs.length; i++) {
            String[] arr = strs[i].split("-");
            ContentValues word = new ContentValues();
            word.put("NAME", arr[1]);
            word.put("TRANSLATE", arr[0]);
            db.insert("WORDS", null, word);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
