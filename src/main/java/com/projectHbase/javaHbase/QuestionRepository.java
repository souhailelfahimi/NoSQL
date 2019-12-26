package com.projectHbase.javaHbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;


public class QuestionRepository {

    Configuration conf = HBaseConfiguration.create();

    String tblName = "question";
    String colFamily = "user_question";
    String data = "data";
    String id = "id";


    public void save(Question question) throws ZooKeeperConnectionException, IOException {
        System.out.println("" + question.toString());

        HBaseAdmin admin = new HBaseAdmin(conf);

        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);

        // Create table
//            HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tblName));
//            HColumnDescriptor HC_auth = new HColumnDescriptor(auth);
//            HColumnDescriptor HC_personal = new HColumnDescriptor(personal);
//            HColumnDescriptor HC_professional = new HColumnDescriptor(professional);
//            HColumnDescriptor HC_follow = new HColumnDescriptor(follow);
//            desc.addFamily(HC_auth);
//            desc.addFamily(HC_personal);
//            desc.addFamily(HC_professional);
//            desc.addFamily(HC_follow);
//            admin.createTable(desc);
        // Insert initial values into table
        // Use HConnection.getTable() against HTablePool as last is deprecated in 0.94, 0.95/0.96, and removed in 0.98
        HTableInterface tblIface = connection.getTable(tblName);

//            Map<String, String> users = new HashMap<String, String>();
//            users.put("Paul", "paul01@mail.com");
//            users.put("Mike", "mike@mail.com");
//            users.put("User3", "unknown@unknown.com");
//            for (Map.Entry usr : users.entrySet()) {
//                String usrName = usr.getKey().toString();
//                String usrMail = usr.getValue().toString();
//                System.out.println(MessageFormat.format("Add user: {0}, mail: {1}", usrName, usrMail));
        String rowKey = question.getId() + "RK"; //System.currentTimeMillis();

        Put put = new Put(Bytes.toBytes(rowKey));
        
        put.add(Bytes.toBytes(id), Bytes.toBytes("text"), Bytes.toBytes(question.getId()));
        put.add(Bytes.toBytes(data), Bytes.toBytes("text"), Bytes.toBytes(question.getTitre()));
        put.add(Bytes.toBytes(data), Bytes.toBytes("description"), Bytes.toBytes(question.getDescription()));


        tblIface.put(put);

        tblIface.close();

    }

    public List<Question> finAll() throws ZooKeeperConnectionException, IOException {
        List<Question> questions = new ArrayList<>();
        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);
        // Table Scan
        System.out.println("Table Scan...");
        byte[] startRow = Bytes.toBytes("P"); //inclusive
        byte[] endRow = Bytes.toBytes("S"); //exclusive
        Scan scan = new Scan(); // Full Scan
        //Scan scan = new Scan(startRow, endRow);
        ResultScanner scanner = tblIface.getScanner(scan);
        for (Result res : scanner) {
            Question question = new Question();
            // extract user name  
            question.setId(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("id"))));
            question.setTitre(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("text"))));
            question.setDescription(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("description"))));

            questions.add(question);
            System.out.println("**********"+question.getTitre());

        }
        return questions;

    }

    public Question findById(String x) throws ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        Question question = new Question();
        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);

        // Get particular field
        System.out.println("Get particular Field...");
        Get get = new Get(Bytes.toBytes(x + "RK")); //rowKey
        get.addFamily(Bytes.toBytes(data));
        //get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"));

        Result res = tblIface.get(get);
        question.setId(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("id"))));
        question.setTitre(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("text"))));
        question.setDescription(Bytes.toString(res.getValue(Bytes.toBytes(data), Bytes.toBytes("description"))));


        return question;

    }

    public void update() throws ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);

        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);
        // Update, just use Put as for inserting
        System.out.println("Update...");
        Put upd = new Put(Bytes.toBytes("MikeRK"));
        upd.add(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"), Bytes.toBytes("Michael"));
        tblIface.put(upd);
    }

    public void delete() throws ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);

        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);
        // Delete
        System.out.println("Delete...");

        // deleteColumn(…) deletes the specific version based on parameters, and deleteColumns(…) deletes all the versions for
        // a specified cell
        Delete delete = new Delete(Bytes.toBytes("MikeRK"));
        delete.deleteColumn(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"));
        tblIface.delete(delete);

        //Deallocate resources
        //tblIface.close();
    }

}
