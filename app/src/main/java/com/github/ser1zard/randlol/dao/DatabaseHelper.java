package com.github.ser1zard.randlol.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String SCHEMA = "Randlol.db";
    private static final int VERSION = 1;
    private static final String CREATE_LANES =
            "CREATE TABLE lanes (lane TEXT PRIMARY KEY, image TEXT NOT NULL UNIQUE);";
    private static final String CREATE_CHAMPIONS = "CREATE TABLE champions (champion TEXT PRIMARY KEY, image TEXT NOT NULL UNIQUE);";
    private static final String CREATE_PREFERRED_LANES =
            "CREATE TABLE preferredLanes (" +
            "champion TEXT, " +
            "lane TEXT, " +
            "FOREIGN KEY(champion) REFERENCES champions(champion), " +
            "FOREIGN KEY(lane) REFERENCES lanes(lane));";
    private static final String INSERT_LANES = "INSERT INTO lanes (lane, image) \n" +
                                               "VALUES \n" +
                                               "('Top', 'top'),\n" +
                                               "('Jungle', 'jungle'),\n" +
                                               "('Mid', 'mid'),\n" +
                                               "('Bot', 'bot'),\n" +
                                               "('Support', 'support');";
    private static final String INSERT_CHAMPIONS = "INSERT INTO champions (champion, image) \n" +
                                                   "VALUES \n" +
                                                   "('Aatrox', 'aatrox'),\n" +
                                                   "('Ahri', 'ahri'),\n" +
                                                   "('Akali', 'akali'),\n" +
                                                   "('Akshan', 'akshan'),\n" +
                                                   "('Alistar', 'alistar'),\n" +
                                                   "('Ambessa', 'ambessa'),\n" +
                                                   "('Amumu', 'amumu'),\n" +
                                                   "('Anivia', 'anivia'),\n" +
                                                   "('Annie', 'annie'),\n" +
                                                   "('Aphelios', 'aphelios'),\n" +
                                                   "('Ashe', 'ashe'),\n" +
                                                   "('Aurelion Sol', 'aurelionsol'),\n" +
                                                   "('Aurora', 'aurora'),\n" +
                                                   "('Azir', 'azir'),\n" +
                                                   "\n" +
                                                   "('Bard', 'bard'),\n" +
                                                   "('Bel''Veth', 'belveth'),\n" +
                                                   "('Blitzcrank', 'blitzcrank'),\n" +
                                                   "('Brand', 'brand'),\n" +
                                                   "('Braum', 'braum'),\n" +
                                                   "('Briar', 'briar'),\n" +
                                                   "\n" +
                                                   "('Caitlyn', 'caitlyn'),\n" +
                                                   "('Camille', 'camille'),\n" +
                                                   "('Cassiopeia', 'cassiopeia'),\n" +
                                                   "('Cho''Gath', 'chogath'),\n" +
                                                   "('Corki', 'corki'),\n" +
                                                   "\n" +
                                                   "('Darius', 'darius'),\n" +
                                                   "('Diana', 'diana'),\n" +
                                                   "('Dr. Mundo', 'drmundo'),\n" +
                                                   "('Draven', 'draven'),\n" +
                                                   "\n" +
                                                   "('Ekko', 'ekko'),\n" +
                                                   "('Elise', 'elise'),\n" +
                                                   "('Evelynn', 'evelynn'),\n" +
                                                   "('Ezreal', 'ezreal'),\n" +
                                                   "\n" +
                                                   "('Fiddlesticks', 'fiddlesticks'),\n" +
                                                   "('Fiora', 'fiora'),\n" +
                                                   "('Fizz', 'fizz'),\n" +
                                                   "\n" +
                                                   "('Galio', 'galio'),\n" +
                                                   "('Gangplank', 'gangplank'),\n" +
                                                   "('Garen', 'garen'),\n" +
                                                   "('Gnar', 'gnar'),\n" +
                                                   "('Gragas', 'gragas'),\n" +
                                                   "('Graves', 'graves'),\n" +
                                                   "('Gwen', 'gwen'),\n" +
                                                   "\n" +
                                                   "('Hecarim', 'hecarim'),\n" +
                                                   "('Heimerdinger', 'heimerdinger'),\n" +
                                                   "\n" +
                                                   "('Hwei', 'hwei'),\n" +
                                                   "\n" +
                                                   "('Illaoi', 'illaoi'),\n" +
                                                   "('Irelia', 'irelia'),\n" +
                                                   "('Ivern', 'ivern'),\n" +
                                                   "\n" +
                                                   "('Janna', 'janna'),\n" +
                                                   "('Jarvan IV', 'jarvan'),\n" +
                                                   "('Jax', 'jax'),\n" +
                                                   "('Jayce', 'jayce'),\n" +
                                                   "('Jhin', 'jhin'),\n" +
                                                   "('Jinx', 'jinx'),\n" +
                                                   "\n" +
                                                   "('Kai''Sa', 'kaisa'),\n" +
                                                   "('Kalista', 'kalista'),\n" +
                                                   "('Karma', 'karma'),\n" +
                                                   "('Karthus', 'karthus'),\n" +
                                                   "('Kassadin', 'kassadin'),\n" +
                                                   "('Katarina', 'katarina'),\n" +
                                                   "('Kayle', 'kayle'),\n" +
                                                   "('Kayn', 'kayn'),\n" +
                                                   "('Kennen', 'kennen'),\n" +
                                                   "('Kha''Zix', 'khazix'),\n" +
                                                   "('Kindred', 'kindred'),\n" +
                                                   "('Kled', 'kled'),\n" +
                                                   "('Kog''Maw', 'kogmaw'),\n" +
                                                   "('K''Sante', 'ksante'),\n" +
                                                   "\n" +
                                                   "('LeBlanc', 'leblanc'),\n" +
                                                   "('Lee Sin', 'leesin'),\n" +
                                                   "('Lillia', 'lillia'),\n" +
                                                   "('Lissandra', 'lissandra'),\n" +
                                                   "('Lucian', 'lucian'),\n" +
                                                   "('Lulu', 'lulu'),\n" +
                                                   "('Lux', 'lux'),\n" +
                                                   "\n" +
                                                   "('Malphite', 'malphite'),\n" +
                                                   "('Malzahar', 'malzahar'),\n" +
                                                   "('Maokai', 'maokai'),\n" +
                                                   "('Master Yi', 'masteryi'),\n" +
                                                   "('Milio', 'milio'),\n" +
                                                   "('Miss Fortune', 'missfortune'),\n" +
                                                   "('Mordekaiser', 'mordekaiser'),\n" +
                                                   "('Morgana', 'morgana'),\n" +
                                                   "\n" +
                                                   "('Naafiri', 'naafiri'),\n" +
                                                   "('Nami', 'nami'),\n" +
                                                   "('Nasus', 'nasus'),\n" +
                                                   "('Nautilus', 'nautilus'),\n" +
                                                   "('Neeko', 'neeko'),\n" +
                                                   "('Nidalee', 'nidalee'),\n" +
                                                   "('Nilah', 'nilah'),\n" +
                                                   "('Nocturne', 'nocturne'),\n" +
                                                   "('Nunu & Willump', 'nunu'),\n" +
                                                   "\n" +
                                                   "('Olaf', 'olaf'),\n" +
                                                   "('Orianna', 'orianna'),\n" +
                                                   "('Ornn', 'ornn'),\n" +
                                                   "\n" +
                                                   "('Pantheon', 'pantheon'),\n" +
                                                   "('Poppy', 'poppy'),\n" +
                                                   "('Pyke', 'pyke'),\n" +
                                                   "\n" +
                                                   "('Qiyana', 'qiyana'),\n" +
                                                   "('Quinn', 'quinn'),\n" +
                                                   "\n" +
                                                   "('Rakan', 'rakan'),\n" +
                                                   "('Rammus', 'rammus'),\n" +
                                                   "('Rek''Sai', 'reksai'),\n" +
                                                   "('Rell', 'rell'),\n" +
                                                   "('Renata Glasc', 'renataglasc'),\n" +
                                                   "('Renekton', 'renekton'),\n" +
                                                   "('Rengar', 'rengar'),\n" +
                                                   "('Riven', 'riven'),\n" +
                                                   "('Rumble', 'rumble'),\n" +
                                                   "('Ryze', 'ryze'),\n" +
                                                   "\n" +
                                                   "('Samira', 'samira'),\n" +
                                                   "('Sejuani', 'sejuani'),\n" +
                                                   "('Senna', 'senna'),\n" +
                                                   "('Seraphine', 'seraphine'),\n" +
                                                   "('Sett', 'sett'),\n" +
                                                   "('Shaco', 'shaco'),\n" +
                                                   "('Shen', 'shen'),\n" +
                                                   "('Shyvana', 'shyvana'),\n" +
                                                   "('Singed', 'singed'),\n" +
                                                   "('Sion', 'sion'),\n" +
                                                   "('Sivir', 'sivir'),\n" +
                                                   "('Skarner', 'skarner'),\n" +
                                                   "('Smoulder', 'smoulder'),\n" +
                                                   "('Sona', 'sona'),\n" +
                                                   "('Soraka', 'soraka'),\n" +
                                                   "('Swain', 'swain'),\n" +
                                                   "('Sylas', 'sylas'),\n" +
                                                   "('Syndra', 'syndra'),\n" +
                                                   "\n" +
                                                   "('Tahm Kench', 'tahmkench'),\n" +
                                                   "('Taliyah', 'taliyah'),\n" +
                                                   "('Talon', 'talon'),\n" +
                                                   "('Taric', 'taric'),\n" +
                                                   "('Teemo', 'teemo'),\n" +
                                                   "('Thresh', 'thresh'),\n" +
                                                   "('Tristana', 'tristana'),\n" +
                                                   "('Trundle', 'trundle'),\n" +
                                                   "('Tryndamere', 'tryndamere'),\n" +
                                                   "('Twisted Fate', 'twistedfate'),\n" +
                                                   "\n" +
                                                   "('Udyr', 'udyr'),\n" +
                                                   "('Urgot', 'urgot'),\n" +
                                                   "\n" +
                                                   "('Varus', 'varus'),\n" +
                                                   "('Vayne', 'vayne'),\n" +
                                                   "('Veigar', 'veigar'),\n" +
                                                   "('Vel''Koz', 'velkoz'),\n" +
                                                   "('Vex', 'vex'),\n" +
                                                   "('Vi', 'vi'),\n" +
                                                   "('Viego', 'viego'),\n" +
                                                   "('Viktor', 'viktor'),\n" +
                                                   "('Vladimir', 'vladimir'),\n" +
                                                   "('Volibear', 'volibear'),\n" +
                                                   "\n" +
                                                   "('Warwick', 'warwick'),\n" +
                                                   "('Wukong', 'wukong'),\n" +
                                                   "\n" +
                                                   "('Xayah', 'xayah'),\n" +
                                                   "('Xerath', 'xerath'),\n" +
                                                   "('Xin Zhao', 'xinzhao'),\n" +
                                                   "\n" +
                                                   "('Yasuo', 'yasuo'),\n" +
                                                   "('Yone', 'yone'),\n" +
                                                   "('Yorick', 'yorick'),\n" +
                                                   "('Yuumi', 'yuumi'),\n" +
                                                   "\n" +
                                                   "('Zac', 'zac'),\n" +
                                                   "('Zed', 'zed'),\n" +
                                                   "('Zeri', 'zeri'),\n" +
                                                   "('Ziggs', 'ziggs'),\n" +
                                                   "('Zilean', 'zilean'),\n" +
                                                   "('Zoe', 'zoe'),\n" +
                                                   "('Zyra', 'zyra');";

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LANES);
        db.execSQL(CREATE_CHAMPIONS);
        db.execSQL(CREATE_PREFERRED_LANES);
        db.execSQL(INSERT_LANES);
        db.execSQL(INSERT_CHAMPIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
