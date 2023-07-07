package cna.self.qimozuoye;

import java.util.ArrayList;
import java.util.List;

import cna.self.qimozuoye.data.UserDataBase.ComeIn;
import cna.self.qimozuoye.data.UserDataBase.Expenses;

public class DataTransfer {
    public static boolean isOnFirstFragment = false;
    public static boolean isOnLoginFragment = false;
    public static List<ComeIn>   listComeIn   = new ArrayList<>();
    public static List<Expenses> listExpenses = new ArrayList<>();
}
