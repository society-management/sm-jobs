package com.sm.jobs;

import com.google.gson.Gson;

import com.sm.pojo.MemberList;
import com.sm.utility.DatabaseProp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.*;
import java.util.Date;

public class IngestMembers {

    private static StringBuilder stringBuilder = new StringBuilder();
    private static Gson gson = new Gson();
    private static MemberList memberList;

    static {
        InputStream is = IngestMembers.class.getClassLoader().getResourceAsStream("members.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readingLine = "";
        try {
           while ((readingLine = br.readLine()) != null) {
               stringBuilder.append(readingLine);
           }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println(stringBuilder);
        memberList = gson.fromJson(String.valueOf(stringBuilder), MemberList.class);

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String db = DatabaseProp.DatabaseName.getValue();
        String tableName = DatabaseProp.MemberTable.getValue();
        String host = DatabaseProp.Host.getValue();
        String port = DatabaseProp.Port.getValue();
        System.out.println("Just printing key " + DatabaseProp.Host.getKey());

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db,"haresh","hareshy22");

        truncateMembersTable(con, db, tableName);

        insertDataIntoMembersTable(con, db, tableName);

        showDataFromMembersTable(con, db, tableName);

    }

    private static void truncateMembersTable(Connection con, String db, String tableName) throws SQLException {
        String insertStatement = getTruncateStatement(db, tableName);
        PreparedStatement ps = con.prepareStatement(insertStatement);
        ps.execute();
    }

    private static String getTruncateStatement(String db, String tableName) {
        return "truncate table "+db+"."+tableName;
    }

    private static void insertDataIntoMembersTable(Connection con, String db, String tableName) throws SQLException {
        String insertStatement = getInsertStatement(db, tableName);
        PreparedStatement ps = con.prepareStatement(insertStatement);
        memberList.getMembers().forEach(x -> {
            try {
                ps.setString(1, x.getFirstName());
                ps.setString(2, x.getLastName());
                ps.setString(3, x.getBirthDate());
                ps.setString(4, x.getMemberStatus());
                ps.setLong(5, x.getMobile1());
                ps.setLong(6, x.getMobile2());
                ps.setString(7, x.getAddress1());
                ps.setString(8, x.getAddress2());
                ps.addBatch();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
        ps.executeBatch();
    }

    private static void showDataFromMembersTable(Connection con, String db, String tableName) throws SQLException {
        String selectQuery = getSelectQuery(db, tableName);
        ResultSet rs = con.prepareStatement(selectQuery).executeQuery();
        if(rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            Date birthData = rs.getDate("birthdate");
            String memberStatus = rs.getString("member_status");
            long mobile1 = rs.getLong("mobile1");
            long mobile2 = rs.getLong("mobile2");
            String address1 = rs.getString("address_line1");
            String address2 = rs.getString("address_line2");
            System.out.println(id);
            System.out.println(firstName);
            System.out.println(lastName);
            System.out.println(birthData);
            System.out.println(memberStatus);
            System.out.println(mobile1);
            System.out.println(mobile2);
            System.out.println(address1);
            System.out.println(address2);
        }
    }

    private static String getInsertStatement(String db, String tableName) {
        return "insert into "+db+"."+tableName+"(first_name, last_name, birthdate, member_status, mobile1, mobile2, address_line1, address_line2) values(?,?,?,?,?,?,?,?)";
    }

    private static String getSelectQuery(String db, String tableName) {
        return "select * from "+db+"."+tableName;
    }
}
