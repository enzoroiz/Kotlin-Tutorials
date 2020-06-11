package com.enzoroiz.challenge5.java;

import com.enzoroiz.challenge5.kotlin.Challenge;
import com.enzoroiz.challenge5.kotlin.EmployeeK;
import com.enzoroiz.challenge5.kotlin.KotlinStuff;

public class Main {
    public static void main(String[] args) {
        KotlinStuff.sayHelloToJava("Myself");

        EmployeeK employee = new EmployeeK("John", "Smith", 2010);
        employee.startYear = 2009;

        Challenge.doMath(5, 4);

        employee.takesDefault("arg1");
        employee.takesDefault(null);
    }
}
