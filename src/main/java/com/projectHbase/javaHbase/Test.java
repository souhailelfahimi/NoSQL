/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectHbase.javaHbase;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 *
 * @author BlackAngel
 */
public class Test {

    @Autowired
    private HbaseTemplate hbaseTemplate;
    private Table table1;
    private String tableName = "user";
    private String family1 = "PersonalData";
    private String family2 = "ProfessionalData";

    public void createHbaseTable() throws IOException {
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

//        HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
//        ht.addFamily(new HColumnDescriptor(family1));
//        ht.addFamily(new HColumnDescriptor(family2));
//        System.out.println("connecting");
//
//        System.out.println("Creating Table");
//        createOrOverwrite(admin, ht);
//        System.out.println("Done......");
        table1 = connection.getTable(TableName.valueOf(tableName));

        try {
            System.out.println("Adding user: user1");
            byte[] row1 = Bytes.toBytes("user1");
            Put p = new Put(row1);

            p.addColumn(family1.getBytes(), "name".getBytes(), Bytes.toBytes("ahmed"));
            p.addColumn(family1.getBytes(), "address".getBytes(), Bytes.toBytes("tunis"));
            p.addColumn(family2.getBytes(), "company".getBytes(), Bytes.toBytes("biat"));
            p.addColumn(family2.getBytes(), "salary".getBytes(), Bytes.toBytes("10000"));
            table1.put(p);

            System.out.println("Adding user: user2");
            byte[] row2 = Bytes.toBytes("user2");
            Put p2 = new Put(row2);
            p2.addColumn(family1.getBytes(), "name".getBytes(), Bytes.toBytes("imen"));
            p2.addColumn(family1.getBytes(), "tel".getBytes(), Bytes.toBytes("21212121"));
            p2.addColumn(family2.getBytes(), "profession".getBytes(), Bytes.toBytes("educator"));
            p2.addColumn(family2.getBytes(), "company".getBytes(), Bytes.toBytes("insat"));
            table1.put(p2);

            System.out.println("reading data...");
            Get g = new Get(row1);

            Result r = table1.get(g);

            System.out.println(Bytes.toString(r.getValue(family1.getBytes(), "name".getBytes())));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            table1.close();
            connection.close();
        }
    }

    public void findAll() {
        try {
            // Sometimes, you won't know the row you're looking for. In this case, you
// use a Scanner. This will give you cursor-like interface to the contents
// of the table.  To set up a Scanner, do like you did above making a Put
// and a Get, create a Scan.  Adorn it with column names, etc.
            Scan s = new Scan();
            s.addColumn(family1.getBytes(), Bytes.toBytes("name"));
            ResultScanner scanner = table1.getScanner(s);
            try {
                // Scanners return Result instances.
                // Now, for the actual iteration. One way is to use a while loop like so:
                for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                    // print out the row we found and the columns we were looking for
                    System.out.println("Found row: " + rr);
                }

                // The other approach is to use a foreach loop. Scanners are iterable!
                // for (Result rr : scanner) {
                //   System.out.println("Found row: " + rr);
                // }
            } finally {
                // Make sure you close your scanners when you are done!
                // Thats why we have it inside a try/finally clause
                scanner.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }
}
