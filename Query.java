public class Query {
    private int queryID;
    private String query;


    public Query(int queryID, String query) {
        this.queryID = queryID;
        this.query = query;

    }

    public String getquery() {
        return query;
    }

    public void setquery(String query) {
        this.query = query;
    }


    public int getqueryID() {
        return queryID;
    }

    public void setqueryID(int queryID) {
        this.queryID = queryID;
    }
    
}