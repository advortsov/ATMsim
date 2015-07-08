package com.atm.beans;

import com.atm.beans.exception.NotEnoughMoneyException;

import java.util.*;

/**
 denomination - достоинство банкноты
 */
public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count){
        if(denominations.containsKey(denomination)) // если уже есть банкнота такого достоинства
            denominations.put(denomination, denominations.get(denomination) + count); // прибавить к ней количество купюр
        else
            denominations.put(denomination,count);
    } // denomination - достоинство банкноты

    public int getTotalAmount(){
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()){
            result = result + entry.getKey() * entry.getValue();
        }
        return result;
    }

    public boolean hasMoney(){
        return denominations.size() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount){
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        
        // Вывод купюр из банкомата жадным алгоритмом

        int sum = expectedAmount;
        HashMap<Integer, Integer> temp = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        temp.putAll(denominations); // номинал - количество. Скопировали во временный хэш мэп из деноминации

        for (Map.Entry<Integer, Integer> pair : temp.entrySet()) {
            list.add(pair.getKey()); // скопировали в аррэй лист все уникальные номиналы (например для руб: 5000-1000-500-100-50-10)
        }
        Collections.sort(list);
        Collections.reverse(list); // отсортировали его в обратном порядке - 5000-1000-500-100-50-10

        TreeMap<Integer, Integer> result = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (Integer nominal : list) {
            int key = nominal; // номинал
            int value = temp.get(key);

            while (true) {
                if (sum < key || value <= 0) { // если запрашиваемая сумма меньше номинала или количество банкнот меньше нуля (??)
                    temp.put(key, value); // то кладем этот расклад в хэш мап
                    break;
                } // оставшиеся ненужные нам банкноты в этом цикле назад возвращаются!
               // Это значит например: у нас осталось 100 руб набрать. А рассматривается купюра номиналом 500.
               // Она кладется в темп, чтобы потом вернуться в деноминатионс. А мы после брейка переходим к 100! йееее))!


                sum -= key; // от первоначальной суммы отнимаем взятый номинал
                value--; // и количество банкнот етого номинала становиться на одну меньше

                if (result.containsKey(key))
                    result.put(key, result.get(key) + 1); // если в резулте уже есть банкнота етого номинала, то +1
                else
                    result.put(key, 1); // если нет то закидываем туда первую банкноту
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException(); // если вопреки всем стараниям все равно оставшаяся сумма больше нуля - то нет денег(
        else {
            for (Map.Entry<Integer, Integer> pair : result.entrySet())
                ConsoleHelper.writeMessage("\t" + pair.getKey() + " - " + pair.getValue());

            denominations.clear();
            denominations.putAll(temp); // кладем в деноминацию оставшиеся общие банкноты
            ConsoleHelper.writeMessage("Операция проведена успешно!");
        }
        return result;
    }
}
