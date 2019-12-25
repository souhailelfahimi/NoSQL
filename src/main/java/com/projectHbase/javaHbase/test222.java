package com.projectHbase.javaHbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class test222 {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();

        String tblName = "test";
        String colFamily = "usr_data";

        HBaseAdmin admin = new HBaseAdmin(conf);

        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);

        if (!admin.tableExists(tblName)) {
            // Create table
            HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tblName));
            HColumnDescriptor family = new HColumnDescriptor(colFamily);
            desc.addFamily(family);
            admin.createTable(desc);

            // Insert initial values into table
            // Use HConnection.getTable() against HTablePool as last is deprecated in 0.94, 0.95/0.96, and removed in 0.98
            HTableInterface tblIface = connection.getTable(tblName);

            Map<String, String> users = new HashMap<String, String>();
            users.put("Paul", "paul01@mail.com");
            users.put("Mike", "mike@mail.com");
            users.put("User3", "unknown@unknown.com");

            for (Map.Entry usr : users.entrySet()) {
                String usrName = usr.getKey().toString();
                String usrMail = usr.getValue().toString();
                System.out.println(MessageFormat.format("Add user: {0}, mail: {1}", usrName, usrMail));

                String rowKey = usrName + "RK"; //System.currentTimeMillis();

                Put put = new Put(Bytes.toBytes(rowKey));
                put.add(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"),
                        Bytes.toBytes(usrName));
                put.add(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"),
                        Bytes.toBytes(usrMail));

                tblIface.put(put);
            }
            tblIface.close();
        }

        HTableInterface tblIface = connection.getTable(tblName);

        // Table Scan
        System.out.println("Table Scan...");
        byte[] startRow = Bytes.toBytes("P"); //inclusive
        byte[] endRow = Bytes.toBytes("S"); //exclusive

        //Scan scan = new Scan(); // Full Scan
        Scan scan = new Scan(startRow, endRow);
        ResultScanner scanner = tblIface.getScanner(scan);

        for (Result r : scanner) {
            // extract user name
            byte[] b = r.getRow();
            String rowKey = Bytes.toString(b);
            b = r.getValue(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"));
            String user = Bytes.toString(b);
            // extract user mail
            b = r.getValue(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"));
            String mail = Bytes.toString(b);
            System.out.println(rowKey + '\t' + user + '\t' + mail);
        }

        // Get particular field
        System.out.println("Get particular Field...");
        Get get = new Get(Bytes.toBytes("MikeRK")); //rowKey
        get.addFamily(Bytes.toBytes(colFamily));
        //get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"));

        Result res = tblIface.get(get);
        byte[] usrName = res.getValue(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"));
        byte[] usrMail = res.getValue(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"));

        System.out.println("rowKey = MikeRK, user_name: " + Bytes.toString(usrName));
        System.out.println("rowKey = MikeRK, user_mail: " + Bytes.toString(usrMail));

        // Update, just use Put as for inserting
        System.out.println("Update...");
        Put upd = new Put(Bytes.toBytes("MikeRK"));
        upd.add(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"), Bytes.toBytes("Michael"));
        tblIface.put(upd);

        // Delete
        System.out.println("Delete...");

        // deleteColumn(…) deletes the specific version based on parameters, and deleteColumns(…) deletes all the versions for
        // a specified cell
        Delete delete = new Delete(Bytes.toBytes("MikeRK"));
        delete.deleteColumn(Bytes.toBytes(colFamily), Bytes.toBytes("user_name"));
        tblIface.delete(delete);

        //Deallocate resources
        tblIface.close();
    }
}
