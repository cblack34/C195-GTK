//package dao;
//
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//import static resources.DBConnection.getConnection;
//
///**
// * Class dealing with Query execution. Creates a java.sql.Statement and executes it. Stores the results for retrieval.
// */
//public class Query {
//    private static String query;
//    private static Statement stmt;
//    private static ResultSet result;
//
//    /**
//     * Creates query or update statement based on first word in the query.
//     * @param query SQL Query to be executed against the db.
//     */
//    public static void makeQuery(String query){
//        query = query;
//        try{
//            stmt=conn.createStatement();
//            // determine query execution
//            if(query.toLowerCase().startsWith("select"))
//                result=stmt.executeQuery(query);
//            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
//                stmt.executeUpdate(query);
//
//        }
//        catch(Exception ex){
//            System.out.println("Error: "+ex.getMessage());
//        }
//    }
//
//    /**
//     * @return The results of the query.
//     */
//    public static ResultSet getResult(){
//        return result;
//    }
//}