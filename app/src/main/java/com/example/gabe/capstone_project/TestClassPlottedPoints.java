//package com.example.gabe.capstone_project;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Map;
//
//import static android.content.ContentValues.TAG;
//
///**
// * Created by Gabe on 3/15/2018.
// */
//
//
//
//public class TestClassPlottedPoints{
//
//    public static boolean flag = true;
//
//    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shelter");
//
//    public static void main(String args[]) {
//
//        while (flag) {
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shelter");
//
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//
//                    ShelterMap((Map<String, Object>) dataSnapshot.getValue());
//
//                    //String value = dataSnapshot.getValue(String.class);
//                    //Log.d(TAG, "Value is: " + value);
//                    flag = false;
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException());
//                }
//            });
//
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    private static void ShelterMap(Map<String, Object> shelterElement) {
//
//        for (Map.Entry<String, Object> entry : shelterElement.entrySet()) {
//            //Get map
//            Map singleShelterElement = (Map) entry.getValue();
//        }
//    }
//}
//
//
