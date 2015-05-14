package mrhs.jamaapp.inr;

public interface Commons {
	
	// Related to log
	final boolean SHOW_LOG = true;	
	
	// Related to the database
	final String DATABASE_NAME = "islahwebdb";
	final Integer DATABASE_VERSION = 31;	
		
	
	// Related to sdcard
	final String APP_ROOT_DIRECTORY = "INR";
	final String INDEX_PREFIX = "-index";
	
	// Image type
	final Integer DIMENTION_INDEX = 60;
	final Integer MAX_IMG_SIZE = 0;
	final boolean BIG_IMAGE = false;
	final boolean INDEX_IMAGE = true;
	
	// Entry limits
	final Integer NEWS_ENTRY_COUNT = 20;
	final Integer ARTICLE_ENTRY_COUNT = 10;
	final Integer INTERVIEW_ENTRY_COUNT = 10;
	final Integer ANNOUNCE_ENTRY_COUNT = 20;
	
	// Content type
	final String NEWS = "(1)";
	final String ARTICLE = "(2)";
	final String INTERVIEW = "(3)";
	final String ANNOUNCE = "(4)";
	
	// Data type
	final String NEWS_TYPE_ISLAHWEB = "0";
	final String NEWS_TYPE_JAMAAT = "1";
	final String NEWS_TYPE_SPORT = "2";
	
	final String ARTICLE_TYPE_DINODAVAT = "0";
	final String ARTICLE_TYPE_ANDISHE = "1";
	final String ARTICLE_TYPE_AHLESONNAT = "2";
	final String ARTICLE_TYPE_FARHANG = "3";
	final String ARTICLE_TYPE_SIASI = "4";
	final String ARTICLE_TYPE_EJTEMAEI = "5";
	final String ARTICLE_TYPE_TARIKH = "6";
	final String ARTICLE_TYPE_ADABOHONAR = "7";
	
	
}