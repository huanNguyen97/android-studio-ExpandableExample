package com.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    // Initialize data
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();
    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add data for displaying in expandable List
        loadData();

        // get reference of the Expandable
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        listAdapter = new CustomAdapter(MainActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);

        // expandAll all the Group
        expandAll();

        simpleExpandableListView.setOnChildClickListener(
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    GroupInfo headerInfo = deptList.get(groupPosition);
                    ChildInfo detailInfo = headerInfo.getProductList().get(childPosition);
                    Toast.makeText(
                        getBaseContext(),
                        "Clicked on :: "
                            + headerInfo.getName()
                            + "/" + detailInfo.getName(),
                        Toast.LENGTH_LONG
                    ).show();
                    return false;
                }
            }
        );

        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(
            new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(
                    ExpandableListView parent,
                    View view,
                    int groupPosition,
                    long id
                ) {
                    GroupInfo headerInfo = deptList.get(groupPosition);
                    Toast.makeText(
                        getBaseContext(),
                        "Header is :: "
                        + headerInfo.getName(),
                        Toast.LENGTH_LONG
                    ).show();
                    return false;
                }
            }
        );
    }

    // Method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.expandGroup(i);
        }
    }

    // Method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.collapseGroup(i);
        }
    }

    // Load some initial data into out list
    private void loadData() {
        addProduct("Android", "ListView");
        addProduct("Android", "ExpandableListView");
        addProduct("Android", "GridView");
        addProduct("Java", "PolyMorphism");
        addProduct("Java", "Collections");
    }

    // Here we maintain our products in various departments
    private int addProduct(String department, String product) {
        int groupPosition = 0;

        // Check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        if (headerInfo == null) {
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        int listSize = productList.size();
        listSize++;

        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
}