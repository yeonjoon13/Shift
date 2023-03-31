package com.example.shift;

public class Job {
    private String role;
    private int company;
    private boolean checked_in;

    private String description;




    public Job(String name, int number, String description) {
        this.role = name;
        this.company = number;
        this.checked_in = false;
        this.description = description;
    }

    public String getRole() { return role; }

    public int getCompany() { return company; }

    public boolean getchecked_in() { return checked_in; }

    public String getDescription() {
        return description;
    }

    public void setchecked_in(boolean a) {
        if (a != checked_in) {
            checked_in = a;
            if (a) company++;
            else company--;
        }
    }
}
