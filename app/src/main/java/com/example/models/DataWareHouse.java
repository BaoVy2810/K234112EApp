package com.example.models;

import java.util.ArrayList;
import java.util.Calendar;

public class DataWareHouse {
    public static ArrayList<Category> getCategories(){
        ArrayList<Category>categories=new ArrayList<>();
        Category c1=new Category("c1","Trái cây","trái cây ăn giảm tạo nghiệp");
        Category c2=new Category("c2","Kim chi","kim chi ăn ngon giống Hàn");
        Category c3=new Category("c3","Mì","Mì ăn ngon ngon");
        Category c4=new Category("c4","Thịt","Thịt quý phái các loại");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        return categories;
    }
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Category> categories = getCategories();

        // Category 0: Trái cây
        products.add(new Product("p1", "Trái tắc túi 200g", 100, 10000, 0, 0.05, categories.get(0).getCateId()));
        products.add(new Product("p2", "Chanh không hạt 500g", 500, 25000, 0, 0.05, categories.get(0).getCateId()));
        products.add(new Product("p3", "Bơ 34 ngon như Miền Núi", 250, 15000, 0, 0.07, categories.get(0).getCateId()));
        products.add(new Product("p4", "Cam sành loại 1", 300, 35000, 0, 0.05, categories.get(0).getCateId()));

        // Category 1: Kim chi
        products.add(new Product("p5", "Kim chi cải thảo 500g", 150, 45000, 0, 0.08, categories.get(1).getCateId()));
        products.add(new Product("p6", "Kim chi củ cải", 100, 40000, 0, 0.08, categories.get(1).getCateId()));
        products.add(new Product("p7", "Kim chi hành lá", 80, 55000, 0, 0.08, categories.get(1).getCateId()));

        // Category 2: Mì
        products.add(new Product("p8", "Mì Hảo Hảo Tôm chua cay", 1000, 4500, 0, 0.1, categories.get(2).getCateId()));
        products.add(new Product("p9", "Mì Kokomi đại", 800, 3500, 0, 0.1, categories.get(2).getCateId()));
        products.add(new Product("p10", "Mì Indomie Goreng", 500, 6000, 0.05, 0.1, categories.get(2).getCateId()));
        products.add(new Product("p11", "Mì Omachi Xốt bò hầm", 600, 8500, 0, 0.1, categories.get(2).getCateId()));

        // Category 3: Thịt
        products.add(new Product("p12", "Thịt ba chỉ heo 300g", 200, 75000, 0, 0.05, categories.get(3).getCateId()));
        products.add(new Product("p13", "Thịt bò Mỹ 200g", 150, 95000, 0.1, 0.05, categories.get(3).getCateId()));
        products.add(new Product("p14", "Đùi gà góc tư", 300, 45000, 0, 0.05, categories.get(3).getCateId()));
        products.add(new Product("p15", "Sườn non heo", 100, 120000, 0, 0.05, categories.get(3).getCateId()));

        return products;
    }
    public static ArrayList<Employee> getEmployee()
    {
        ArrayList<Employee> employees=new ArrayList<>();
        employees.add(new Employee("e1","Trần Ngọc Bảo Vy","09026281091"));
        employees.add(new Employee("e2", "Nguyễn Văn An", "0901234567"));
        employees.add(new Employee("e3", "Lê Thị Bình", "0902345678"));
        employees.add(new Employee("e4", "Phạm Hồng Chương", "0903456789"));
        employees.add(new Employee("e5", "Hoàng Gia Danh", "0904567890"));
        employees.add(new Employee("e6", "Vũ Minh Anh", "0905678901"));
        employees.add(new Employee("e7", "Đặng Thu Hà", "0906789012"));
        employees.add(new Employee("e8", "Bùi Tiến Dũng", "0907890123"));
        employees.add(new Employee("e9", "Ngô Bảo Châu", "0908901234"));
        employees.add(new Employee("e10", "Đỗ Mười", "0909012345"));
        employees.add(new Employee("e11", "Lý Tự Trọng", "0901112223"));
        return employees;
    }
    public static ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        cal.set(1975, 4, 15);
        customers.add(new Customer("c1", "Nguyễn Văn An", "0901234567", "a@gmail.com", "Hà Nội", cal.getTime()));

        cal.set(1986, 9, 25);
        customers.add(new Customer("c2", "Lê Thị Bình", "0902345678", "b@gmail.com", "Hồ Chí Minh", cal.getTime()));

        cal.set(1990, 0, 10);
        customers.add(new Customer("c3", "Phạm Hồng Chương", "0903456789", "chuong@gmail.com", "Đà Nẵng", cal.getTime()));

        cal.set(1995, 11, 25);
        customers.add(new Customer("c4", "Hoàng Gia Danh", "0904567890", "danh@gmail.com", "Cần Thơ", cal.getTime()));

        cal.set(1998, 2, 15);
        customers.add(new Customer("c5", "Vũ Minh Anh", "0905678901", "anh@gmail.com", "Hải Phòng", cal.getTime()));

        cal.set(1993, 6, 12);
        customers.add(new Customer("c6", "Đặng Thu Hà", "0906789012", "ha@gmail.com", "Huế", cal.getTime()));

        cal.set(1991, 8, 5);
        customers.add(new Customer("c7", "Bùi Tiến Dũng", "0907890123", "dung@gmail.com", "Nghệ An", cal.getTime()));

        cal.set(1980, 5, 28);
        customers.add(new Customer("c8", "Ngô Bảo Châu", "0908901234", "chau@gmail.com", "Bắc Ninh", cal.getTime()));

        cal.set(1975, 10, 2);
        customers.add(new Customer("c9", "Đỗ Mười", "0909012345", "muoi@gmail.com", "Thanh Hóa", cal.getTime()));

        cal.set(2000, 1, 14);
        customers.add(new Customer("c10", "Lý Tự Trọng", "0901112223", "trong@gmail.com", "Hà Tĩnh", cal.getTime()));

        cal.set(1988, 7, 8);
        customers.add(new Customer("c11", "Phan Bội Châu", "0902223334", "pbchau@gmail.com", "Phan Thiết", cal.getTime()));

        cal.set(1982, 3, 22);
        customers.add(new Customer("c12", "Võ Nguyên Giáp", "0903334445", "giap@gmail.com", "Quảng Bình", cal.getTime()));

        return customers;
    }
    public static ArrayList<Order> getOrders()
    {
        ArrayList<Order> orders=new ArrayList<>();
        ArrayList<Employee>employees=getEmployee();
        ArrayList<Customer>customers=getCustomers();
        Calendar cal=Calendar.getInstance();

        for (int i=1;i<=100;i++){
            int year,month,day;
            if (i<=40){
                year=2024;
                month=(i-1)%12;
            }
            else if (i<=85){
                year=2025;
                month=(i-41)%12;
            }
            else{
                year=2026;
                month=(i-86)%3;}
            day=(i%28)+1;
            cal.set(year,month,day,8+(i%10),i%60,0);
            String empId = employees.get((i - 1) % employees.size()).getId();
            String cusId = customers.get((i - 1) % customers.size()).getCustomerId();
            orders.add(new Order("o"+i,empId,cusId,cal.getTime()));
        }
        return orders;
    }
    public static ArrayList<OrderDetail> setOrderDetails(ArrayList<Order>orders,ArrayList<Product> products)
    {
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        Order od0=orders.get(0);
        return orderDetails;

    }
}
