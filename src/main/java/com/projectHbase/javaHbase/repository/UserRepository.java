package com.projectHbase.javaHbase.repository;

import com.projectHbase.javaHbase.bean.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;

public class UserRepository {

    Configuration conf = HBaseConfiguration.create();

    String tblName = "user";
    String colFamily = "usr_data";
    String auth = "auth";
    String personal = "personal";
    String professional = "professional";
    String follow = "follow";

    public void save(User user) throws ZooKeeperConnectionException, IOException {
        System.out.println("" + user.toString());

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
        String rowKey = user.getId() + "RK"; //System.currentTimeMillis();

        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(auth), Bytes.toBytes("id"), Bytes.toBytes(user.getId()));
        put.add(Bytes.toBytes(auth), Bytes.toBytes("email"), Bytes.toBytes(user.getEmail()));
        put.add(Bytes.toBytes(auth), Bytes.toBytes("password"), Bytes.toBytes(user.getPassword()));
        put.add(Bytes.toBytes(personal), Bytes.toBytes("name"), Bytes.toBytes(user.getName()));
        put.add(Bytes.toBytes(personal), Bytes.toBytes("avatar"), Bytes.toBytes(user.getAvatar()));
        put.add(Bytes.toBytes(personal), Bytes.toBytes("phone"), Bytes.toBytes(user.getPhone()));
        put.add(Bytes.toBytes(personal), Bytes.toBytes("gender"), Bytes.toBytes(user.getGender()));

        tblIface.put(put);

        tblIface.close();

    }

    public List<User> finAll() throws ZooKeeperConnectionException, IOException {
        List<User> users = new ArrayList<>();
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
            User user = new User();
            // extract user name  
            user.setId(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("id"))));
            user.setEmail(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("email"))));
            user.setPassword(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("password"))));
            user.setName(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("name"))));
            user.setAvatar(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("avatar"))));
            user.setPhone(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("phone"))));
            user.setGender(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("gender"))));

            users.add(user);
            System.out.println("**********"+user.getEmail());

        }
        return users;

    }

    public User findById(String x) throws ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        User user = new User();
        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);

        // Get particular field
        System.out.println("Get particular Field...");
        Get get = new Get(Bytes.toBytes(x + "RK")); //rowKey
        get.addFamily(Bytes.toBytes(personal));
        get.addFamily(Bytes.toBytes(auth));
        //get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("user_mail"));

        Result res = tblIface.get(get);
        user.setId(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("id"))));
        user.setEmail(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("email"))));
        user.setPassword(Bytes.toString(res.getValue(Bytes.toBytes(auth), Bytes.toBytes("password"))));

        user.setName(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("name"))));
        user.setAvatar(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("avatar"))));
        user.setPhone(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("phone"))));
        user.setGender(Bytes.toString(res.getValue(Bytes.toBytes(personal), Bytes.toBytes("gender"))));

        return user;

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

    public void delete(String id) throws ZooKeeperConnectionException, IOException {

        // Explicitly specify connection
        HConnection connection = HConnectionManager.createConnection(conf);
        HTableInterface tblIface = connection.getTable(tblName);
        // Delete
        System.out.println("Delete...");

        // deleteColumn(…) deletes the specific version based on parameters, and deleteColumns(…) deletes all the versions for
        // a specified cell
        Delete delete = new Delete(Bytes.toBytes(id+"RK"));
        delete.deleteFamily(Bytes.toBytes(auth));
        delete.deleteFamily(Bytes.toBytes(personal));
        tblIface.delete(delete);

        //Deallocate resources
        //tblIface.close();
    }

}
