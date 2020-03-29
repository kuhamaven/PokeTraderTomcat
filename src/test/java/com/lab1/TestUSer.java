package com.lab1;

import com.lab1.entity.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class TestUSer {
    public static void main(String[] args) {


        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class);
        config.configure("hibernate.cfg.xml");

    }
}
