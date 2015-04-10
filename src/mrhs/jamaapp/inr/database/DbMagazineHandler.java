package mrhs.jamaapp.inr.database;

public class DbMagazineHandler {
	String CREATE_MAG="CREATE TABLE IF NOT EXISTS "+TABLE_MAG+ 
			" (id INTEGER PRIMARY KEY,title text not null,indexImg text not null," +
			"pageLink text not null,pdfFile text)";
}
