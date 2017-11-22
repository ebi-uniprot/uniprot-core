package uk.ac.ebi.uniprot.domain.citation;

import java.util.*;
import java.util.stream.Collectors;

//you can write to stdout for debugging purposes, e.g.
//System.out.println("this is a debug message");

class Solution {
 public int solution(int[] A, int X, int Y, int Z) {
     List<Dispenser> dispensers = new ArrayList<>();
     dispensers.add(new Dispenser(X));
     dispensers.add(new Dispenser(Y));
     dispensers.add(new Dispenser(Z));
     for(int i=0; i<A.length; i++){
           int val = A[i];
           Dispenser dispenser =
           getDispense(dispensers, val);
           if(dispenser ==null){
               return -1;
           }
           dispenser.consume(val);   
         
     }
     return  dispensers.stream().mapToInt(val ->val.getLastConsumed()).max().getAsInt();
 }

  
 private Dispenser getDispense(List<Dispenser> dispensers, int val){
     List<Dispenser> left = new ArrayList<>();
     for(Dispenser dispenser: dispensers){
         if(dispenser.isAvailable() && dispenser.hasEnough(val))
             return dispenser;
         else if(!dispenser.isAvailable()){
             left.add(dispenser);
         }
     }
     List<Dispenser> sorted =left.stream().sorted().collect(Collectors.toList());
     for(Dispenser dispenser: sorted){
         if(!dispenser.isAvailable()){
             dispenser.setAvailable(true);
             if(dispenser.hasEnough(val)){
               return dispenser;  
             }
         }
     }
     return null;
 }
 static class Dispenser implements Comparable<Dispenser>{
     private int value;
     private boolean available= true;
     private int consumed =0;
     private int lastConsumed =0;
     public Dispenser(int value){
         this.value = value;
     }
     public boolean hasEnough(int d){
         return value>=d;
     }
     public void consume(int d){
         value -=d;   
         available =false;
         lastConsumed = consumed;
         consumed+= d;
     }
    public int getConsumed(){
        return consumed;
    }
    
    public int getLastConsumed(){
        return lastConsumed;
    }
     public void setAvailable(boolean b){
        
         available= b;
     }
     
     public int getValue(){
         return value;
     }
     public boolean isAvailable(){
         return  available;   
     }
    @Override
    public int compareTo(Dispenser o) {
        return this.consumed -o.getConsumed();
    }
 }
}